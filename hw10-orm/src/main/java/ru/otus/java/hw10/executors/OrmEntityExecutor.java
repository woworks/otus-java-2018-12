package ru.otus.java.hw10.executors;

import org.apache.commons.lang3.StringUtils;
import ru.otus.java.hw10.entity.DataSet;
import ru.otus.java.hw10.reflection.Reflector;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

public class OrmEntityExecutor implements EntityExecutor {

    private final Connection connection;

    public OrmEntityExecutor(Connection connection) {
        this.connection = connection;
    }

    @Override
    public <T extends DataSet> long save(T dataSet) throws SQLException {
        return Executor.update(connection, generateInsert(dataSet));
    }

    @Override
    public <T extends DataSet> T load(long id, Class<T> clazz) {
        try {
            return Executor.query(connection, clazz,generateSelect(id, clazz),  DataSetDAO::extract);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private <T extends DataSet> String generateSelect(long id, Class<T> clazz) {
        String tableName = clazz.getSimpleName();
        String select = String.format("SELECT * FROM %s WHERE id = %s", tableName, id);
        System.out.println("generated select String: " + select);
        return select;
    }

    private <T extends DataSet> String generateInsert(T dataSet) {
        String tableName = dataSet.getClass().getSimpleName();
        String tableColumnNames = StringUtils.join(Reflector.getFields(dataSet).keySet()) ;
        String tableColumnValues = getParamValuesString(dataSet);
        String returnString = String.format("INSERT INTO %s(%s) VALUES (%s) RETURNING id;",
                tableName,
                tableColumnNames.substring(1, tableColumnNames.length() - 1),
                tableColumnValues.substring(1, tableColumnValues.length() - 1));
        System.out.println("generated insert String: " + returnString);
        return returnString;
    }

    private <T extends DataSet> String getParamValuesString(T dataSet) {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, Object> entry: Reflector.getFields(dataSet).entrySet()){
            Object val = entry.getValue();
            if (isNumber(val)) {
                builder.append(val).append(",");
            } else {
                builder
                        .append("''")
                        .append(val)
                        .append("'")
                        .append(",");
            }
        }
        return builder.substring(0, builder.length() - 1);
    }

    private boolean isNumber(Object object) {
        return object.getClass().equals(Integer.class) ||
                object.getClass().equals(Long.class) || object.getClass().equals(long.class) ||
                object.getClass().equals(Double.class) || object.getClass().equals(double.class) ||
                object.getClass().equals(Float.class) || object.getClass().equals(float.class) ||
                object.getClass().equals(Byte.class) || object.getClass().equals(byte.class);
    }

}
