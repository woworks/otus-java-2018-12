package ru.otus.java.hw10.executors;

import org.apache.commons.lang3.StringUtils;
import ru.otus.java.hw10.entity.DataSet;
import ru.otus.java.hw10.reflection.Reflector;

import java.sql.Connection;
import java.util.Collection;
import java.util.Map;

public class OrmEntityExecutor implements EntityExecutor {

    private final Connection connection;

    public OrmEntityExecutor(Connection connection) {
        this.connection = connection;
    }

    @Override
    public <T extends DataSet> void init(Collection<T> dataSets) {
        for (T dataSet: dataSets){
            String query = getExecuteQuery(dataSet);
            System.out.println("generated execute query: " + query);
            Executor.execute(connection, query);
        }
    }

    private <T extends DataSet> String getExecuteQuery(T dataSet) {
        String classNameLower = dataSet.getClass().getSimpleName().toLowerCase();
        String createTableTemplate = "" +
         "CREATE TABLE IF NOT EXISTS %s " +
         "(" +
         "id SERIAL, " +
         getFieldDefinitions(dataSet) +
         "CONSTRAINT %s_pkey PRIMARY KEY (id) " +
         ")";

        return String.format(createTableTemplate, classNameLower, classNameLower, classNameLower);

    }

    private  <T extends DataSet> String getFieldDefinitions(T dataSet) {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, Object> entry: Reflector.getFields(dataSet).entrySet()) {
            builder.append(entry.getKey().toLowerCase());
            if (isNumber(entry.getValue())) {
                builder.append(" bigint,");
            } else if (entry.getValue().getClass().equals(String.class)){
                builder.append(" character varying(255),");
            }
        }

        return builder.toString();
    }

    @Override
    public <T extends DataSet> long save(T dataSet) {
        return Executor.create(connection, generatePreparedInsert(dataSet), Reflector.getFields(dataSet).values());
    }

    @Override
    public <T extends DataSet> T load(long id, Class<T> clazz) {
        try {
            return Executor.query(connection, id, clazz, generatePreparedSelect(clazz),  DataSetDAO::extract);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private <T extends DataSet> String generatePreparedSelect(Class<T> clazz) {
        String tableName = clazz.getSimpleName();
        String select = String.format("SELECT * FROM %s WHERE id = ?", tableName);
        System.out.println("generated prepared select String: " + select);
        return select;
    }

    private <T extends DataSet> String generatePreparedInsert(T dataSet) {
        String tableName = dataSet.getClass().getSimpleName();
        String tableColumnNames = StringUtils.join(Reflector.getFields(dataSet).keySet()) ;
        String tableColumnValues = StringUtils.repeat("?,", Reflector.getFields(dataSet).keySet().size());
        String returnString = String.format("INSERT INTO %s(%s) VALUES (%s) RETURNING id;",
                tableName,
                tableColumnNames.substring(1, tableColumnNames.length() - 1),
                tableColumnValues.substring(0, tableColumnValues.length() - 1));
        System.out.println("generated prepared insert String: " + returnString);
        return returnString;
    }

    private boolean isNumber(Object object) {
        return object.getClass().equals(Integer.class) ||
                object.getClass().equals(Long.class) || object.getClass().equals(long.class) ||
                object.getClass().equals(Double.class) || object.getClass().equals(double.class) ||
                object.getClass().equals(Float.class) || object.getClass().equals(float.class) ||
                object.getClass().equals(Byte.class) || object.getClass().equals(byte.class);
    }
}
