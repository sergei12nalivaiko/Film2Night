package by.itechart.film2night.entity;

import java.util.Objects;
import java.util.Set;

public class FilmTop250 {

    private int Id;
    private String name;
    private int year;
    private Float rating;
    private String posterUrl;
    private String posterUrlPreview;
    private Set<Country> countries;
    private Set<Genre> genres;

    public FilmTop250() {
    }

    public FilmTop250(int id, String name, int year, Float rating, String posterUrl,
                      String posterUrlPreview, Set<Country> countries, Set<Genre> genres) {
        Id = id;
        this.name = name;
        this.year = year;
        this.rating = rating;
        this.posterUrl = posterUrl;
        this.posterUrlPreview = posterUrlPreview;
        this.countries = countries;
        this.genres = genres;
    }

    public FilmTop250(int id, String name, int year, Float rating, String posterUrl,
                      String posterUrlPreview) {
        Id = id;
        this.name = name;
        this.year = year;
        this.rating = rating;
        this.posterUrl = posterUrl;
        this.posterUrlPreview = posterUrlPreview;
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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public String getPosterUrlPreview() {
        return posterUrlPreview;
    }

    public void setPosterUrlPreview(String posterUrlPreview) {
        this.posterUrlPreview = posterUrlPreview;
    }

    public Set<Country> getCountries() {
        return countries;
    }

    public void setCountries(Set<Country> countries) {
        this.countries = countries;
    }

    public Set<Genre> getGenres() {
        return genres;
    }

    public void setGenres(Set<Genre> genres) {
        this.genres = genres;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FilmTop250 that = (FilmTop250) o;
        return Id == that.Id && year == that.year && Objects.equals(name, that.name) && Objects.equals(rating, that.rating) && Objects.equals(posterUrl, that.posterUrl) && Objects.equals(posterUrlPreview, that.posterUrlPreview) && Objects.equals(countries, that.countries) && Objects.equals(genres, that.genres);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Id, name, year, rating, posterUrl, posterUrlPreview, countries, genres);
    }

    @Override
    public String toString() {
        return "FilmTop250{" +
                "Id=" + Id +
                ", name='" + name + '\'' +
                ", year=" + year +
                ", rating=" + rating +
                ", posterUrl='" + posterUrl + '\'' +
                ", posterUrlPreview='" + posterUrlPreview + '\'' +
                ", countries=" + countries +
                ", genres=" + genres +
                '}';
    }
}
