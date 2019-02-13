package ru.otus.java.hw03;

import java.util.Date;

public class Sample implements Comparable{
    private String stringField;
    private Long longField;
    private Date dateField;

    Sample() {
    }

    Sample(String stringField, Long longField, Date dateField) {
        this.stringField = stringField;
        this.longField = longField;
        this.dateField = dateField;
    }

    public String getStringField() {
        return stringField;
    }

    public void setStringField(String stringField) {
        this.stringField = stringField;
    }

    public Long getLongField() {
        return longField;
    }

    public void setLongField(Long longField) {
        this.longField = longField;
    }

    public Date getDateField() {
        return dateField;
    }

    public void setDateField(Date dateField) {
        this.dateField = dateField;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Sample sample = (Sample) o;

        if (!stringField.equals(sample.stringField)) return false;
        if (!longField.equals(sample.longField)) return false;
        return dateField.equals(sample.dateField);
    }

    @Override
    public int hashCode() {
        int result = stringField.hashCode();
        result = 31 * result + longField.hashCode();
        result = 31 * result + dateField.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Sample{" +
                "stringField='" + stringField + '\'' +
                ", longField=" + longField +
                ", dateField=" + dateField +
                '}';
    }


    @Override
    public int compareTo(Object o) {
        return (int) (longField - ((Sample)o).longField);
    }
}
