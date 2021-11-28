package entertainment;

import java.util.List;

public abstract class Video {
    private String name;
    private int year;
    private List<String> genres;
    private List<String> actors;

    public Video(final String name, final int year,
                 final List<String> genres, final List<String> actors) {
        this.name = name;
        this.year = year;
        this.genres = genres;
        this.actors = actors;
    }

    /**
     *
     */
    public String getName() {
        return name;
    }

    /**
     *
     */
    public int getYear() {
        return year;
    }

    /**
     *
     */
    public List<String> getGenres() {
        return genres;
    }

    /**
     *
     */
    public List<String> getActors() {
        return actors;
    }
}
