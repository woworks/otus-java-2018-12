package ru.otus.java.hw08;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

// TODO Add documentation
// TODO clean up this class
public class TokenSpecGenerator {
    public static void main(String[] jars) throws IOException {
        Map<String, Set<String>> methodsToAnnotationValuesMap = new HashMap<>();
        if (findAnnotatedMethods(jars, methodsToAnnotationValuesMap)) {
            while (findIndirectMethodInvocations(jars, methodsToAnnotationValuesMap)) {
            }
        }
        // TODO Should we also report any API method invocations outside of
        // methods?
        // (i.e. field initializers, static blocks, ...)
        for (Map.Entry<String, Set<String>> entry : methodsToAnnotationValuesMap.entrySet()) {
            if (!entry.getKey().startsWith("com/something/myapi")) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }
        }
    }

    protected static boolean findAnnotatedMethods(String[] jars, Map<String, Set<String>> methodsToAnnotationValuesMap) {
        System.out.println("Scanning for methods annotated with @RequiredActionsPermitted");
        FindAnnotatedMethods classVisitor = new FindAnnotatedMethods(methodsToAnnotationValuesMap);
        visitClasses(jars, classVisitor);
        System.out.println("Methods and required actions permitted found until now:");
        System.out.println(methodsToAnnotationValuesMap);
        return classVisitor.hasFoundNew();
    }

    protected static boolean findIndirectMethodInvocations(String[] jars, Map<String, Set<String>> methodsToAnnotationValuesMap) {
        System.out.println("Next round of scanning for methods that indirectly call methods annotated with @RequiredActionsPermitted");
        FindMethodInvocations classVisitor = new FindMethodInvocations(methodsToAnnotationValuesMap);
        visitClasses(jars, classVisitor);
        System.out.println("Methods and required actions permitted found until now:");
        System.out.println(methodsToAnnotationValuesMap);
        return classVisitor.hasFoundNew();
    }

    protected static void visitClasses(String[] jars, ClassVisitor classVisitor) {
        for (String jar : jars) {
            JarFile jarFile = null;
            try {
                try {
                    jarFile = new JarFile(jar);
                    Enumeration<JarEntry> entries = jarFile.entries();

                    while (entries.hasMoreElements()) {
                        JarEntry entry = entries.nextElement();

                        if (entry.getName().endsWith(".class")) {
                            try {
                                InputStream stream = null;
                                try {
                                    stream = new BufferedInputStream(jarFile.getInputStream(entry), 1024);
                                    new ClassReader(stream).accept(classVisitor, 0);
                                } finally {
                                    stream.close();
                                }
                            } catch ( IOException e ) { e.printStackTrace(); }
                        }
                    }
                } finally {
                    jarFile.close();
                }
            } catch ( IOException e ) { e.printStackTrace(); }
        }
    }

    static abstract class AbstractClassVisitor extends ClassVisitor {
        private final Map<String, Set<String>> methodsToAnnotationValuesMap;
        private boolean foundNew = false;
        private String className;

        public AbstractClassVisitor(Map<String, Set<String>> methodsToAnnotationValuesMap) {
            super(Opcodes.ASM5);
            this.methodsToAnnotationValuesMap = methodsToAnnotationValuesMap;
        }

        @Override
        public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
            this.className = name;
            super.visit(version, access, name, signature, superName, interfaces);
        }

        protected void addValues(String method, Set<String> values) {
            Set<String> existingValues = methodsToAnnotationValuesMap.get(method);
            if (existingValues == null) {
                existingValues = new HashSet<String>();
                methodsToAnnotationValuesMap.put(method, existingValues);
            }
            foundNew |= existingValues.addAll(values);
        }

        public boolean hasFoundNew() {
            return foundNew;
        }

        public String getClassName() {
            return className;
        }

        public Map<String, Set<String>> getMethodsToAnnotationValuesMap() {
            return methodsToAnnotationValuesMap;
        }
    }

    static abstract class AbstractMethodVisitor extends MethodVisitor {
        private final String className;
        private final String methodName;

        public AbstractMethodVisitor(String className, String methodName) {
            super(Opcodes.ASM5);
            this.className = className;
            this.methodName = methodName;
        }

        // TODO Add method parameter types
        public String getMethodDescription() {
            return className+"."+methodName;
        }
    }

    static class FindAnnotatedMethods extends AbstractClassVisitor {
        public FindAnnotatedMethods(Map<String, Set<String>> methodsToAnnotationValuesMap) {
            super(methodsToAnnotationValuesMap);
        }

        @Override
        public MethodVisitor visitMethod(final int access, final String name, final String desc, final String signature, final String[] exceptions) {
            return new AbstractMethodVisitor(getClassName(), name) {
                private Set<String> values = new HashSet<>();
                @Override
                public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
                    if ("Lcom/something/myapi/annotation/RequiredActionsPermitted;".equals(desc)) {
                        return new AnnotationVisitor(Opcodes.ASM5) {
                            @Override
                            public AnnotationVisitor visitArray(String name) {
                                if ( !"value".equals(name) ) {
                                    return null;
                                } else {
                                    return new AnnotationVisitor(Opcodes.ASM5) {
                                        @Override
                                        public void visit(String name, Object value) {
                                            values.add((String)value);
                                        }
                                    };
                                }
                            }
                        };
                    }
                    return null;
                }

                @Override
                public void visitEnd() {
                    if ( !values.isEmpty() ) {
                        addValues(getMethodDescription(), values);
                    }
                }
            };
        }
    }

    static class FindMethodInvocations extends AbstractClassVisitor {
        public FindMethodInvocations(Map<String, Set<String>> methodsToAnnotationValuesMap) {
            super(methodsToAnnotationValuesMap);
        }

        @Override
        public MethodVisitor visitMethod(final int access, final String name, final String desc, final String signature, final String[] exceptions) {
            return new AbstractMethodVisitor(getClassName(), name) {
                private Set<String> values = new HashSet<>();
                @Override
                public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
                    String method = owner+"."+name;
                    if ( getMethodsToAnnotationValuesMap().containsKey(method) ) {
                        values.addAll(getMethodsToAnnotationValuesMap().get(method));
                    }
                }

                @Override
                public void visitEnd() {
                    if ( !values.isEmpty() ) {
                        addValues(getMethodDescription(), values);
                    }
                }
            };
        }
    }
}
