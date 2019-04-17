package ru.otus.java.hw09.serializer;

import org.json.simple.JSONObject;

import java.lang.reflect.InvocationTargetException;

public interface JsonSerializer {

    Object getObject(JSONObject json, Class clazz) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException;

    JSONObject serialize(Object object);
}
