package ru.otus.java.hw09.dto;

public class Address {
    private String country;
    private String city;
    private String street;
    private int buildingNo;
    private int apartmentNo;

    public Address(String country, String city, String street, int buildingNo, int apartmentNo) {
        this.country = country;
        this.city = city;
        this.street = street;
        this.buildingNo = buildingNo;
        this.apartmentNo = apartmentNo;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getBuildingNo() {
        return buildingNo;
    }

    public void setBuildingNo(int buildingNo) {
        this.buildingNo = buildingNo;
    }

    public int getApartmentNo() {
        return apartmentNo;
    }

    public void setApartmentNo(int apartmentNo) {
        this.apartmentNo = apartmentNo;
    }
}
