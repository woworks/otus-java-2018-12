package ru.otus.java.hw09.serializer;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Test;
import ru.otus.java.hw09.reflection.Reflector;

import java.lang.reflect.*;
import java.math.BigDecimal;
import java.util.*;

public class JsonSerializerImpl implements JsonSerializer {
    @Override
    public Object getObject(JSONObject json, Class clazz) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Map<String, Object> map = new HashMap<>();

        Reflector reflector = new Reflector();

        // get Object other than by constructor!!!
        Object clazzObject = clazz.getDeclaredConstructor().newInstance();

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.getType().isArray()){
                System.out.println("field " + field.getName() + " is array");
            } else if (field.getType().equals(List.class)) {

                System.out.println("field " + field.getName() + " is list");
                List list = new ArrayList();
                JSONArray jsonArray = (JSONArray)json.get(field.getName());

                Class itemType = null;

                Type type = field.getGenericType();
                //System.out.println("type: " + type);
                if (type instanceof ParameterizedType) {
                    ParameterizedType pt = (ParameterizedType) type;
                    //System.out.println("raw type: " + pt.getRawType());
                    //System.out.println("owner type: " + pt.getOwnerType());
                    //System.out.println("actual type args:");
                    for (Type t : pt.getActualTypeArguments()) {
                        System.out.println("    " + t);
                        if (t instanceof Class) {
                            @SuppressWarnings("unchecked")
                            Class<?> clazz2 = (Class<?>) t;
                            System.out.println("    clazz2 " + clazz2);
                            itemType  = clazz2;
                        }

                    }
                }

                final Class itemClass = itemType;

                jsonArray.forEach(item -> {

                        field.setAccessible(true);
                        //list.add(getObject((JSONObject) item, field.getType().getComponentType()));
                        Class cl = field.getType();
                        Class cl2 = field.getType().getComponentType();
                    try {
                        list.add(getObject((JSONObject) item, itemClass));
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    }

                });

                map.put(field.getName(), list);
            } else {
                System.out.println("field " + field.getName() + " is simple");
                map.put(field.getName(), json.get(field.getName()));
            }
        }



        /*for(Object key: json.keySet()){
            System.out.println("key = " + key);
            Object val = json.get(key);
            System.out.println("val = " + val);
            if (val instanceof JSONArray) {

                ((JSONArray)val).forEach(item -> {
                    //item.getClass()
                    //reflector.
                    //map.put((String) key, getObjectArray());

                });
            } else {
                map.put((String) key, val);

            }

        }*/

        reflector.populateFields(clazzObject, map);
        return clazzObject;

    }

    @Override
    public JSONObject serialize(Object object) {
        JSONObject output = new JSONObject();

        Reflector reflector = new Reflector();
        Map<String, Object> fields = reflector.getFields(object);

        for (Map.Entry<String, Object> item : fields.entrySet()) {
            Object key = item.getKey();
            Object value = item.getValue();

            if (value instanceof List) {
                value = serializeList(value);
            } else if (value.getClass().isArray()) {
                value = serializeArray(value);
            }

            output.put(key, value);
        }

        return output;
    }

    private JSONArray serializeArray(Object objects) {
        boolean isPrimitive = objects.getClass().getComponentType().isPrimitive();
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < Array.getLength(objects); i++) {
            if (isPrimitive) {
                jsonArray.add(Array.get(objects, i));
            } else {
                jsonArray.add(serialize(Array.get(objects, i)));
            }

        }
        return jsonArray;
    }

    private JSONArray serializeList(Object objects) {
        JSONArray jsonArray = new JSONArray();
        for (Object object: (List) objects) {
            jsonArray.add(serialize(object));
        }
        return jsonArray;
    }

    private Class getListItemType(List list) throws NoSuchFieldException {


        Field stringListField = Test.class.getDeclaredField("stringList");
        ParameterizedType stringListType = (ParameterizedType) stringListField.getGenericType();
        Class<?> stringListClass = (Class<?>) stringListType.getActualTypeArguments()[0];
        System.out.println(stringListClass);

        return String.class;
    }
}
