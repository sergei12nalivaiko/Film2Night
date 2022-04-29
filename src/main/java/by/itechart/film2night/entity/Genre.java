package by.itechart.film2night.entity;

import java.util.Objects;

public class Genre {

    private int Id;
    private String name;

    private Genre() {
    }

    public int getId() {
        return Id;
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
        Genre genre = (Genre) o;
        return Id == genre.Id && Objects.equals(name, genre.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Id, name);
    }

    @Override
    public String toString() {
        return "Genre{" +
                "Id=" + Id +
                ", name='" + name + '\'' +
                '}';
    }

    public static class genreBuilder {

        private Genre newGenre;

        public genreBuilder() {
            this.newGenre = new Genre();
        }

        public genreBuilder setName(String name) {
            newGenre.name = name;
            return this;
        }

        public Genre build() {
            return newGenre;
        }
    }
}
