package ru.otus.java.hw09.serializer;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.otus.java.hw09.dto.Address;
import ru.otus.java.hw09.dto.Employee;
import ru.otus.java.hw09.dto.Project;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonSerializerImplTest {

    private static final String FIRST_NAME = "John";
    private static final String LAST_NAME = "Doe";
    private static final long ID = 101L;
    private static final String COUNTRY_RUSSIA = "Russia";
    private static final String CITY_MOSCOW = "Moscow";
    private static final String PUSHKIN_STR = "Pushkin Str";
    private static final String CITY_ASTRAKHAN = "Astrakhan";
    private static final String LOMONOSOV_STR = "Lomonosov Str";
    private static final BigDecimal SALARY = new BigDecimal("1608.50");

    private static Employee testEmployee;
    private static JSONObject testEmployeeJson;

    @BeforeAll
    static void setUp() {
        testEmployee = getTestEmployee();
        testEmployeeJson = getEmployeeJson(testEmployee);
    }

    @Test
    void getObject() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        JsonSerializer serializer = new JsonSerializerImpl();
        Employee employee = (Employee) serializer.getObject(testEmployeeJson, Employee.class);
        assertTrue(EqualsBuilder.reflectionEquals(testEmployee, employee));
    }

    @Test
    void serialize() throws JsonSerializerException {
        JsonSerializer serializer = new JsonSerializerImpl();
        String serializedEmployee = serializer.serialize(testEmployee);

        assertEquals(testEmployeeJson.toString(), serializedEmployee);
    }

    @Test
    void serialize2() throws JsonSerializerException {
        JsonSerializer serializer = new JsonSerializerImpl();
        String serialized;

        serialized = serializer.serialize(null);
        System.out.println("null = " + serialized);
        assertNull(serialized);

        serialized = serializer.serialize(new int[] {1, 2, 3});

        JSONArray intArray = new JSONArray();
        intArray.addAll(Arrays.asList(1, 2, 3));
        System.out.println("{1, 2, 3} = " + serialized);
        assertEquals(intArray.toString(), serialized);

        serialized = serializer.serialize(List.of(1, 2, 3));
        assertEquals(intArray.toString(), serialized);
        System.out.println(serialized);
    }

    @Test
    void serializeNoKey() throws JsonSerializerException {
        JsonSerializer serializer = new JsonSerializerImpl();
        Assertions.assertThrows(JsonSerializerException.class, () -> {
            serializer.serialize(1);
        });

    }


    private static Employee getTestEmployee() {
        Employee employee = new Employee();
        employee.setId(ID);
        employee.setFirstName(FIRST_NAME);
        employee.setLastName(LAST_NAME);
        employee.setExternalIds(new long[]{1000L, 2000L, 3000L});
        Address address1 = new Address(COUNTRY_RUSSIA, CITY_MOSCOW, PUSHKIN_STR, 120, 13);
        Address address2 = new Address(COUNTRY_RUSSIA, CITY_ASTRAKHAN, LOMONOSOV_STR, 15, 121);
        employee.setAddresses(new Address[]{address1, address2});

        employee.setSalary(SALARY);
        Project project1 = new Project("Task Manager", 600_000L);
        Project project2 = new Project("Process Explorer", 400_000L);
        employee.setProjects(Arrays.asList(project1, project2));

        return employee;
    }

    private static JSONObject getEmployeeJson(Employee employee) {
        JSONObject jsonEmployee = new JSONObject();
        jsonEmployee.put("id", employee.getId());
        jsonEmployee.put("firstName", employee.getFirstName());
        jsonEmployee.put("lastName", employee.getLastName());

        JSONArray externalIds = new JSONArray();
        for (Long id : employee.getExternalIds()) {
            externalIds.add(id);
        }

        jsonEmployee.put("externalIds", externalIds);

        JSONArray addressesArray = new JSONArray();
        for (Address address : employee.getAddresses()) {
            JSONObject addressJson = new JSONObject();
            addressJson.put("country", address.getCountry());
            addressJson.put("city", address.getCity());
            addressJson.put("street", address.getStreet());
            addressJson.put("buildingNo", address.getBuildingNo());
            addressJson.put("apartmentNo", address.getApartmentNo());

            addressesArray.add(addressJson);
        }


        jsonEmployee.put("addresses", addressesArray);
        jsonEmployee.put("salary", SALARY);

        JSONArray projectArray = new JSONArray();
        for (Project project : employee.getProjects()) {
            JSONObject projectJson = new JSONObject();
            projectJson.put("name", project.getName());
            projectJson.put("budget", project.getBudget());
            projectArray.add(projectJson);
        }

        jsonEmployee.put("projects", projectArray);
        jsonEmployee.put("nullString", null);



        return jsonEmployee;
    }

}