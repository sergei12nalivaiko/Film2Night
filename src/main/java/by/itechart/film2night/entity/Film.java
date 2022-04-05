package by.itechart.film2night.entity;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Film {

    private int kinopoiskId;
    private String nameOriginal;
    private String posterUrl;
    private Float ratingKinopoisk;
    private int ratingKinopoiskVoteCount;
    private String webUrl;
    private int year;
    private int filmLength;
    private Set<String> countries = new HashSet<>();
    private Set<String> genres = new HashSet<>();
    private Timestamp lastSync;
    private String is_blocked;

    private Film() {
    }

    public int getKinopoiskId() {
        return kinopoiskId;
    }

    public void setKinopoiskId(int kinopoiskId) {
        this.kinopoiskId = kinopoiskId;
    }

    public String getNameOriginal() {
        return nameOriginal;
    }

    public void setNameOriginal(String nameOriginal) {
        this.nameOriginal = nameOriginal;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public Float getRatingKinopoisk() {
        return ratingKinopoisk;
    }

    public void setRatingKinopoisk(Float ratingKinopoisk) {
        this.ratingKinopoisk = ratingKinopoisk;
    }

    public int getRatingKinopoiskVoteCount() {
        return ratingKinopoiskVoteCount;
    }

    public void setRatingKinopoiskVoteCount(int ratingKinopoiskVoteCount) {
        this.ratingKinopoiskVoteCount = ratingKinopoiskVoteCount;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getFilmLength() {
        return filmLength;
    }

    public void setFilmLength(int filmLength) {
        this.filmLength = filmLength;
    }

    public Set<String> getCountries() {
        return countries;
    }

    public void setCountries(Set<String> countries) {
        this.countries = countries;
    }

    public Set<String> getGenres() {
        return genres;
    }

    public void setGenres(Set<String> genres) {
        this.genres = genres;
    }

    public Timestamp getLastSync() {
        return lastSync;
    }

    public void setLastSync(Timestamp lastSync) {
        this.lastSync = lastSync;
    }

    public String getIs_blocked() {
        return is_blocked;
    }

    public void setIs_blocked(String is_blocked) {
        this.is_blocked = is_blocked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Film film = (Film) o;
        return kinopoiskId == film.kinopoiskId && ratingKinopoiskVoteCount == film.ratingKinopoiskVoteCount && year == film.year && filmLength == film.filmLength && Objects.equals(nameOriginal, film.nameOriginal) && Objects.equals(posterUrl, film.posterUrl) && Objects.equals(ratingKinopoisk, film.ratingKinopoisk) && Objects.equals(webUrl, film.webUrl) && Objects.equals(countries, film.countries) && Objects.equals(genres, film.genres) && Objects.equals(lastSync, film.lastSync) && Objects.equals(is_blocked, film.is_blocked);
    }

    @Override
    public int hashCode() {
        return Objects.hash(kinopoiskId, nameOriginal, posterUrl, ratingKinopoisk, ratingKinopoiskVoteCount, webUrl, year, filmLength, countries, genres, lastSync, is_blocked);
    }

    @Override
    public String toString() {
        return "Film{" +

                ", kinopoiskId=" + kinopoiskId +
                ", nameOriginal='" + nameOriginal + '\'' +
                ", posterUrl='" + posterUrl + '\'' +
                ", ratingKinopoisk=" + ratingKinopoisk +
                ", ratingKinopoiskVoteCount=" + ratingKinopoiskVoteCount +
                ", webUrl='" + webUrl + '\'' +
                ", year=" + year +
                ", filmLength=" + filmLength +
                ", countries=" + countries +
                ", genres=" + genres +
                ", lastSync='" + lastSync + '\'' +
                ", is_blocked='" + is_blocked + '\'' +
                '}';
    }

    public static class filmBuilder {

        private Film newFilm;

        public filmBuilder() {
            newFilm = new Film();
        }

        public filmBuilder setKinopoiskId(int kinopoiskId) {
            newFilm.kinopoiskId = kinopoiskId;
            return this;
        }

        public filmBuilder setNameOriginal(String nameOriginal) {
            newFilm.nameOriginal = nameOriginal;
            return this;
        }

        public filmBuilder setPosterUrl(String posterUrl) {
            newFilm.posterUrl = posterUrl;
            return this;
        }

        public filmBuilder setRatingKinopoisk(Float ratingKinopoisk) {
            newFilm.ratingKinopoisk = ratingKinopoisk;
            return this;
        }

        public filmBuilder setRatingKinopoiskVoteCount(int ratingKinopoiskVoteCount) {
            newFilm.ratingKinopoiskVoteCount = ratingKinopoiskVoteCount;
            return this;
        }

        public filmBuilder setWebUrl(String webUrl) {
            newFilm.webUrl = webUrl;
            return this;
        }

        public filmBuilder setYear(int year) {
            newFilm.year = year;
            return this;
        }

        public filmBuilder setFilmLength(int filmLength) {
            newFilm.filmLength = filmLength;
            return this;
        }

        public filmBuilder setCountries(Set<String> countries) {
            newFilm.countries = countries;
            return this;
        }

        public filmBuilder setGenres(Set<String> genres) {
            newFilm.genres = genres;
            return this;
        }

        public filmBuilder setLastSync(Timestamp lastSync) {
            newFilm.lastSync = lastSync;
            return this;
        }

        public filmBuilder setIs_blocked(String is_blocked) {
            newFilm.is_blocked = is_blocked;
            return this;
        }

        public Film build() {
            return newFilm;
        }

    }
}
