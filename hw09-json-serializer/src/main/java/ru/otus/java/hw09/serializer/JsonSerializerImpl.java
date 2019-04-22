package ru.otus.java.hw09.serializer;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import ru.otus.java.hw09.reflection.Reflector;

import java.lang.reflect.*;
import java.util.*;

public class JsonSerializerImpl implements JsonSerializer {

    @Override
    public Object getObject(JSONObject json, Class clazz) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Map<String, Object> map = new HashMap<>();

        Reflector reflector = new Reflector();

        // Class should have constructor without params
        Object clazzObject = clazz.getDeclaredConstructor().newInstance();

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.getType().isArray()) {
                addArray(json, map, field);
            } else if (field.getType().equals(List.class)) {
                addList(json, map, field);
            } else {
                System.out.println("field " + field.getName() + " is simple");
                map.put(field.getName(), json.get(field.getName()));
            }
        }

        reflector.populateFields(clazzObject, map);
        return clazzObject;

    }

    private void addList(JSONObject json, Map<String, Object> map, Field field) {
        System.out.println("field " + field.getName() + " is list");
        List list = new ArrayList();
        JSONArray jsonArray = (JSONArray) json.get(field.getName());

        Class itemType = null;

        Type type = field.getGenericType();
        if (type instanceof ParameterizedType) {
            ParameterizedType pt = (ParameterizedType) type;
            for (Type t : pt.getActualTypeArguments()) {
                if (t instanceof Class) {
                    itemType = (Class<?>) t;
                    break;
                }
            }
        }

        final Class itemClass = itemType;

        jsonArray.forEach(item -> {

            field.setAccessible(true);
            try {
                list.add(getObject((JSONObject) item, itemClass));
            } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }

        });

        map.put(field.getName(), list);
    }

    private void addArray(JSONObject json, Map<String, Object> map, Field field) {
        System.out.println("field " + field.getName() + " is array");
        JSONArray jsonArray = (JSONArray) json.get(field.getName());

        Type itemClass = field.getType().getComponentType();

        try {
            itemClass = getClass(itemClass.getTypeName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Object array = Array.newInstance((Class<?>) itemClass, jsonArray.size());

        for (int i = 0; i < jsonArray.size(); i++) {

            field.setAccessible(true);
            try {
                if (((Class) itemClass).isPrimitive()) {
                    Array.set(array, i, jsonArray.get(i));
                } else {
                    Array.set(array, i, getObject((JSONObject) jsonArray.get(i), (Class) itemClass));
                }

            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
                e.printStackTrace();
            }
        }

        map.put(field.getName(), array);
    }

    @Override
    public String serialize(Object object) throws JsonSerializerException {

        if (object == null) {
            return null;
        }

        if (isSimple(object)){
            return object.toString();
        } else if (object instanceof List) {
            return serializeList(object).toString();
        } else if (object.getClass().isArray()) {
            return serializeArray(object).toString();
        }

        return serializeObject(object).toString();
    }

    public JSONObject serializeObject(Object object) {

        if (object == null) {
            return null;
        }
        JSONObject output = new JSONObject();

        Reflector reflector = new Reflector();
        Map<String, Object> fields = reflector.getFields(object);

        for (Map.Entry<String, Object> item : fields.entrySet()) {
            Object key = item.getKey();
            Object value = item.getValue();

            if (value instanceof List) {
                value = serializeList(value);
            } else if (value != null && value.getClass().isArray()) {
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
                jsonArray.add(serializeObject(Array.get(objects, i)));
            }
        }
        return jsonArray;
    }

    private JSONArray serializeList(Object objects) {
        JSONArray jsonArray = new JSONArray();
        if (((List) objects).size() > 0) {


            for (Object object : (List) objects) {
                if (isSimple(object)) {
                    jsonArray.add(object);
                } else {
                    jsonArray.add(serializeObject(object));
                }
            }
        }

        return jsonArray;
    }

    private boolean isSimple(Object object) {
        if (
                object.getClass().equals(Integer.class) ||
                        object.getClass().equals(Long.class) ||
                        object.getClass().equals(Double.class) ||
                        object.getClass().equals(Float.class) ||
                        object.getClass().equals(Byte.class) ||
                        object.getClass().equals(Boolean.class) ||
                        object.getClass().equals(String.class) ||
                        object.getClass().equals(Character.class) ||
                        object.getClass().equals(Short.class)
        ) {
            return true;
        } else {
            return false;
        }
    }

    private Class getClass(String className) throws ClassNotFoundException {
        if ("int".equals(className)) return int.class;
        if ("long".equals(className)) return long.class;
        if ("double".equals(className)) return double.class;
        if ("float".equals(className)) return float.class;
        if ("byte".equals(className)) return byte.class;
        if ("char".equals(className)) return char.class;
        if ("short".equals(className)) return short.class;
        if ("boolean".equals(className)) return boolean.class;
        return Class.forName(className);
    }
}
