package ru.otus.java.hw10.executors;

import ru.otus.java.hw10.entity.DataSet;
import ru.otus.java.hw10.reflection.Reflector;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

class DataSetDAO {
    private static <T extends DataSet> T create(ResultSet resultSet, Class<T> clazz) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return (T)getObject(resultSet, clazz);
    }

    static <T extends DataSet> T extract(ResultSet resultSet, Class<T> clazz) throws SQLException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        if (!resultSet.next()) {
            return null;
        }
        return create(resultSet, clazz);
    }

    private static Object getObject(ResultSet resultSet, Class clazz) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, SQLException {


        Map<String, Object> map = new HashMap<>();

        Object clazzObject = clazz.getDeclaredConstructor().newInstance();

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
                if (field.getType().equals(String.class)){
                    map.put(field.getName(), resultSet.getString(field.getName()));
                } else if (field.getType().equals(int.class) || field.getType().equals(Integer.class)){
                    map.put(field.getName(), resultSet.getInt(field.getName()));
                }
                // add all the other types
        }

        Reflector.populateFields(clazzObject, map);
        return clazzObject;

    }
}
