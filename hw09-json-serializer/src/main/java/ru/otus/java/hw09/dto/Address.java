package ru.otus.java.hw09.dto;

public class Address {
    private String country;
    private String city;
    private String street;
    private int buildingNo;
    private int apartmentNo;

    public Address() {}

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Address address = (Address) o;

        if (buildingNo != address.buildingNo) return false;
        if (apartmentNo != address.apartmentNo) return false;
        if (!country.equals(address.country)) return false;
        if (!city.equals(address.city)) return false;
        return street.equals(address.street);

    }

    @Override
    public int hashCode() {
        int result = country.hashCode();
        result = 31 * result + city.hashCode();
        result = 31 * result + street.hashCode();
        result = 31 * result + buildingNo;
        result = 31 * result + apartmentNo;
        return result;
    }

    @Override
    public String toString() {
        return "Address{" +
                "country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", buildingNo=" + buildingNo +
                ", apartmentNo=" + apartmentNo +
                '}';
    }
}
