package by.itechart.film2night.entity;

import java.util.Objects;

public class FilmTop250 {

    private int Id;
    private String name;

    private FilmTop250() {
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
        FilmTop250 that = (FilmTop250) o;
        return Id == that.Id && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Id, name);
    }

    @Override
    public String toString() {
        return "FilmTop250{" +
                "Id=" + Id +
                ", name='" + name + '\'' +
                '}';
    }

    public static class filmTop250Builder {

        private FilmTop250 newFilmTop250;

        public filmTop250Builder() {
            newFilmTop250 = new FilmTop250();
        }

        public filmTop250Builder setId(int id) {
            newFilmTop250.Id = id;
            return this;
        }

        public filmTop250Builder setName(String name) {
            newFilmTop250.name = name;
            return this;
        }

        public FilmTop250 build() {
            return newFilmTop250;
        }
    }
}
