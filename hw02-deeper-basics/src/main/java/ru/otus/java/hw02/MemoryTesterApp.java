package ru.otus.java.hw02;

import ru.otus.java.hw02.agent.MemoryCounterAgent;
import ru.otus.java.hw02.common.ObjectFactory;
import ru.otus.java.hw02.common.Objects;
import ru.otus.java.hw02.runtime.RuntimeMemoryCounter;
import ru.otus.java.hw02.runtime.RuntimeMemoryCounter2;

import java.io.Serializable;
import java.util.List;


public class MemoryTesterApp {
    public static void main(String[] args) throws Exception {

        List<Object> objects = ObjectFactory.getObjects();

        for (Object object: objects) {
            printObjectSizeMemory(object);
        }

    }

    private static void printObjectSizeAgent(Object obj) {
        System.out.println(String.format("object value: %s", obj.toString()));
        System.out.println(String.format("\t%s:%s", obj.getClass()
                .getSimpleName(), MemoryCounterAgent.getSize(obj)));
    }

    private static void printObjectSizeMemory(Object obj) throws Exception {
        Long size = RuntimeMemoryCounter2.getObjectSize((Serializable) obj);
        System.out.println(String.format("\t%20s:%20s",obj.getClass().getSimpleName(), size));
    }

    private static void pringSizeInfo() {
        System.out.println("Size table");
        System.out.println("-----------------------------------------------");
        System.out.println(String.format("\tBoolean           :%20s", Objects.BOOLEAN_FIELD_SIZE));
        System.out.println(String.format("\tByte              :%20s", Objects.BYTE_FIELD_SIZE));
        System.out.println(String.format("\tChar              :%20s", Objects.CHAR_FIELD_SIZE));
        System.out.println(String.format("\tShort             :%20s", Objects.SHORT_FIELD_SIZE));
        System.out.println(String.format("\tInteger           :%20s", Objects.INT_FIELD_SIZE));
        System.out.println(String.format("\tLong              :%20s", Objects.LONG_FIELD_SIZE));
        System.out.println(String.format("\tFloat             :%20s", Objects.FLOAT_FIELD_SIZE));
        System.out.println(String.format("\tDouble            :%20s", Objects.DOUBLE_FIELD_SIZE));
        System.out.println(String.format("\tObject Shell Size :%20s", Objects.OBJECT_SHELL_SIZE));
        System.out.println(String.format("\tObject Reference  :%20s", Objects.OBJREF_SIZE));
        System.out.println("-----------------------------------------------\n\n");
    }

}