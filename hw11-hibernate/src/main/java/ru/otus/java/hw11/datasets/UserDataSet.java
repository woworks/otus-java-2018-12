package ru.otus.java.hw11.datasets;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class UserDataSet extends DataSet {

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private int age;

    @OneToMany(cascade = CascadeType.ALL)
    private List<PhoneDataSet> phones;

    @OneToOne(cascade = CascadeType.ALL)
    private AddressDataSet address;

    //Important for Hibernate
    public UserDataSet() {
    }

    public UserDataSet(String name, int age, List<PhoneDataSet> phones, AddressDataSet address) {
        this.name = name;
        this.age = age;
        this.phones = phones;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<PhoneDataSet> getPhones() {
        return phones;
    }

    public void setPhones(List<PhoneDataSet> phones) {
        this.phones = phones;
    }


    public AddressDataSet getAddress() {
        return address;
    }

    public void setAddress(AddressDataSet address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "UserDataSet{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", phones=" + phones +
                ", address=" + address +
                '}';
    }
}

