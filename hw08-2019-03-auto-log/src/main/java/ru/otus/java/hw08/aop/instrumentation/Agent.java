package ru.otus.java.hw08.aop.instrumentation;

import org.objectweb.asm.*;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.List;

import static org.objectweb.asm.Opcodes.H_INVOKESTATIC;

public class Agent {
    private static final String LOG_ANNOTATION_CLASS = "Lru/otus/java/hw08/annotation/annotations/Log;";

    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println("...... premain");
        inst.addTransformer(new ClassFileTransformer() {
            @Override
            public byte[] transform(ClassLoader loader, String className,
                                    Class<?> classBeingRedefined,
                                    ProtectionDomain protectionDomain,
                                    byte[] classfileBuffer) {
                List<String> methodsToBeLogged =
                        findAnnotatedMethods(classfileBuffer);

                if (!methodsToBeLogged.isEmpty()) {
                    return addProxyMethods(classfileBuffer, className, methodsToBeLogged);
                }
                return classfileBuffer;
            }
        });

    }

    private static byte[] addProxyMethods(byte[] originalClass, String className, List<String> methodsToBeLogged) {
        System.out.println("addProxyMethods:: methodsToBeLogged = " + methodsToBeLogged);
        System.out.println("addProxyMethods:: className = " + className);
        ClassReader cr = new ClassReader(originalClass);
        ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_MAXS);
        ClassVisitor cv = new ClassVisitor(Opcodes.ASM5, cw) {
            @Override
            public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
                if (methodsToBeLogged.contains(name)) {
                    return super.visitMethod(access, name + "Proxied", descriptor, signature, exceptions);
                } else {
                    return super.visitMethod(access, name, descriptor, signature, exceptions);
                }
            }
        };
        cr.accept(cv, Opcodes.ASM5);

        for (String methodName : methodsToBeLogged) {
            String descriptor = "(Ljava/lang/String;)V";
            if ("secureLoggedAccessInt".equals(methodName)) {
                descriptor = "(Ljava/lang/Integer;)V";
            }


            MethodVisitor mv = cw.visitMethod(Opcodes.ACC_PUBLIC, methodName, descriptor, null, null);

            Handle handle = new Handle(
                    H_INVOKESTATIC,
                    Type.getInternalName(java.lang.invoke.StringConcatFactory.class),
                    "makeConcatWithConstants",
                    MethodType.methodType(CallSite.class, MethodHandles.Lookup.class, String.class, MethodType.class, String.class, Object[].class).toMethodDescriptorString(),
                    false);


            mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            mv.visitLdcInsn("[LOGGER] [" + methodName + "] logged. Param: ");
            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "print", "(Ljava/lang/String;)V", false);

            mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            mv.visitVarInsn(Opcodes.ALOAD, 1);
            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/Object;)V", false);


/*            mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            mv.visitVarInsn(Opcodes.ALOAD, 1);
            mv.visitInvokeDynamicInsn("makeConcatWithConstants", "(Ljava/lang/String;)Ljava/lang/String;", handle, "[LOGGER] ["  + methodName + "] logged. Param:\u0001");

            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);*/

            mv.visitVarInsn(Opcodes.ALOAD, 0);
            mv.visitVarInsn(Opcodes.ALOAD, 1);

            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, className, methodName + "Proxied", descriptor, false);

            mv.visitInsn(Opcodes.RETURN);
            mv.visitMaxs(0, 0);
            mv.visitEnd();
        }


        byte[] finalClass = cw.toByteArray();

        String proxyClassname = className.substring(className.lastIndexOf('/') + 1) + "Proxy.class";
        try (OutputStream fos = new FileOutputStream(proxyClassname)) {
            fos.write(finalClass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return finalClass;
    }

    static List<String> findAnnotatedMethods(byte[] originalClass) {
        List<String> methods = new ArrayList<>();
        ClassReader cr = new ClassReader(originalClass);
        ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_MAXS);
        ClassVisitor cv = new ClassVisitor(Opcodes.ASM5, cw) {
            @Override
            public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
                return new MethodVisitor(api, super.visitMethod(access, name, descriptor, signature, exceptions)) {
                    @Override
                    public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
                        if (LOG_ANNOTATION_CLASS.equals(descriptor)) {
                            methods.add(name);
                        }
                        return super.visitAnnotation(descriptor, visible);
                    }
                };
            }
        };
        cr.accept(cv, Opcodes.ASM5);

        return methods;
    }

}