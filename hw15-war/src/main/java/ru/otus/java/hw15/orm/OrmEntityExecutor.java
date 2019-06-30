package ru.otus.java.hw15.orm;

import org.apache.commons.lang3.StringUtils;
import ru.otus.java.hw15.datasets.DataSet;
import ru.otus.java.hw15.reflection.Reflector;

import java.sql.Connection;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OrmEntityExecutor implements EntityExecutor {

    private final Connection connection;

    private final Map<Class, String> preparedInserts = new HashMap<>();

    OrmEntityExecutor(Connection connection) {
        this.connection = connection;
    }

    @Override
    public <T extends DataSet> void init(Collection<T> dataSets) {
        for (T dataSet : dataSets) {
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

    private <T extends DataSet> String getFieldDefinitions(T dataSet) {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, Class> entry : Reflector.getFields(dataSet).entrySet()) {

            if (Reflector.isNumber(entry.getValue())) {
                builder.append(entry.getKey().toLowerCase());
                builder.append(" bigint,");
            } else if (entry.getValue().equals(String.class)) {
                builder.append(entry.getKey().toLowerCase());
                builder.append(" character varying(255),");
            } else {
                System.err.println("Undefined field type: " + entry.getValue());
            }
        }

        return builder.toString();
    }

    @Override
    public <T extends DataSet> long save(T dataSet) {
        return Executor.create(connection, getPreparedInsert(dataSet), Reflector.getObjectFieldValues(dataSet));
    }

    @Override
    public <T extends DataSet> T load(long id, Class<T> clazz) {
        try {
            return Executor.query(connection, id, clazz, generatePreparedSelect(clazz), DataSetDAO::extract);
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


    private <T extends DataSet> String getPreparedInsert(T dataSet) {
        if (preparedInserts.containsKey(dataSet.getClass())){
            return preparedInserts.get(dataSet.getClass());
        } else {
            preparedInserts.put(dataSet.getClass(), generatePreparedInsert(dataSet));
            return preparedInserts.get(dataSet.getClass());
        }
    }


    private <T extends DataSet> String generatePreparedInsert(T dataSet) {
        String tableName = dataSet.getClass().getSimpleName();
        String tableColumnNames = StringUtils.join(getObjectColumnNames(dataSet));
        String tableColumnValues = StringUtils.repeat("?,", getObjectColumnNames(dataSet).size());
        String returnString = String.format("INSERT INTO %s(%s) VALUES (%s) RETURNING id;",
                tableName,
                tableColumnNames.substring(1, tableColumnNames.length() - 1),
                tableColumnValues.substring(0, tableColumnValues.length() - 1));
        System.out.println("generated prepared insert String: " + returnString);
        return returnString;
    }

    private <T extends DataSet> List<String> getObjectColumnNames(T dataSet) {
        return
                Reflector.getFields(dataSet).entrySet()
                        .stream()
                        .filter(entry -> entry.getValue().equals(String.class) || Reflector.isNumber(entry.getValue()))
                        .map(entry -> entry.getKey())
                        .collect(Collectors.toList());

    }


}
