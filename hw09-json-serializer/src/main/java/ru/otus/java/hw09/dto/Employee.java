package ru.otus.java.hw09.dto;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class Employee {

    private long id;
    private String firstName;
    private String lastName;
    private BigDecimal salary;
    private Address[] addresses;
    private List<Project> projects;
    private long[] externalIds;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public Address[] getAddresses() {
        return addresses;
    }

    public void setAddresses(Address[] addresses) {
        this.addresses = addresses;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public long[] getExternalIds() {
        return externalIds;
    }

    public void setExternalIds(long[] externalIds) {
        this.externalIds = externalIds;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Employee employee = (Employee) o;

        if (id != employee.id) return false;
        if (!firstName.equals(employee.firstName)) return false;
        if (!lastName.equals(employee.lastName)) return false;
        if (!salary.equals(employee.salary)) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        if (!Arrays.equals(addresses, employee.addresses)) return false;
        if (projects != null ? !projects.equals(employee.projects) : employee.projects != null) return false;
        return Arrays.equals(externalIds, employee.externalIds);

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + firstName.hashCode();
        result = 31 * result + lastName.hashCode();
        result = 31 * result + salary.hashCode();
        result = 31 * result + Arrays.hashCode(addresses);
        result = 31 * result + (projects != null ? projects.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(externalIds);
        return result;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", salary=" + salary +
                ", addresses=" + Arrays.toString(addresses) +
                ", projects=" + projects +
                ", externalIds=" + Arrays.toString(externalIds) +
                '}';
    }
}
