package by.itechart.film2night.entity;

import java.util.Objects;
import java.util.Set;

public class Film {

    private int Id;
    private String name;
    private String posterUrl;
    private String posterUrlPreview;
    private String webUrl;
    private Boolean is_blocked;
    private int year;
    private Float ratingKinopoisk;
    private Set<Country> countries;
    private Set<Genre> genres;

    public Film() {
    }

    public Film(int id, String name, String posterUrl, String posterUrlPreview, String webUrl,
                Boolean is_blocked, int year, Float ratingKinopoisk, Set<Country> countries, Set<Genre> genres) {
        Id = id;
        this.name = name;
        this.posterUrl = posterUrl;
        this.posterUrlPreview = posterUrlPreview;
        this.webUrl = webUrl;
        this.is_blocked = is_blocked;
        this.year = year;
        this.ratingKinopoisk = ratingKinopoisk;
        this.countries = countries;
        this.genres = genres;
    }

    public Film(int id, String name, String posterUrl, String posterUrlPreview, String webUrl,
                Boolean is_blocked, int year, Float ratingKinopoisk) {
        Id = id;
        this.name = name;
        this.posterUrl = posterUrl;
        this.posterUrlPreview = posterUrlPreview;
        this.webUrl = webUrl;
        this.is_blocked = is_blocked;
        this.year = year;
        this.ratingKinopoisk = ratingKinopoisk;
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

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public Boolean getIs_blocked() {
        return is_blocked;
    }

    public void setIs_blocked(Boolean is_blocked) {
        this.is_blocked = is_blocked;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Float getRatingKinopoisk() {
        return ratingKinopoisk;
    }

    public void setRatingKinopoisk(Float ratingKinopoisk) {
        this.ratingKinopoisk = ratingKinopoisk;
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
        Film film = (Film) o;
        return Id == film.Id && year == film.year && Objects.equals(name, film.name) && Objects.equals(posterUrl, film.posterUrl) && Objects.equals(posterUrlPreview, film.posterUrlPreview) && Objects.equals(webUrl, film.webUrl) && Objects.equals(is_blocked, film.is_blocked) && Objects.equals(ratingKinopoisk, film.ratingKinopoisk) && Objects.equals(countries, film.countries) && Objects.equals(genres, film.genres);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Id, name, posterUrl, posterUrlPreview, webUrl, is_blocked, year, ratingKinopoisk, countries, genres);
    }

    @Override
    public String toString() {
        return "Film{" +
                "Id=" + Id +
                ", name='" + name + '\'' +
                ", posterUrl='" + posterUrl + '\'' +
                ", posterUrlPreview='" + posterUrlPreview + '\'' +
                ", webUrl='" + webUrl + '\'' +
                ", is_blocked=" + is_blocked +
                ", year=" + year +
                ", ratingKinopoisk=" + ratingKinopoisk +
                ", countries=" + countries +
                ", genres=" + genres +
                '}';
    }
}
