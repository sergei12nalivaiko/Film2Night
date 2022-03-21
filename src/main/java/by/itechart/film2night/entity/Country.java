package by.itechart.film2night.entity;

import java.util.Objects;

public class Country {

    private int Id;
    private String name;

    public Country() {
    }

    public Country(int id, String name) {
        Id = id;
        this.name = name;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Country country = (Country) o;
        return Id == country.Id && Objects.equals(name, country.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Id, name);
    }

    @Override
    public String toString() {
        return "Country{" +
                "Id=" + Id +
                ", name='" + name + '\'' +
                '}';
    }
}
