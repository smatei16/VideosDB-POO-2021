package entertainment;

import java.util.List;

/**
 *
 */
public abstract class Video {
    private String name;
    private int year;
    private List<String> genres;
    private List<String> actors;

    private int numFavorites;
    private int totalViews;

    public Video(final String name, final int year,
                 final List<String> genres, final List<String> actors) {
        this.name = name;
        this.year = year;
        this.genres = genres;
        this.actors = actors;
        this.numFavorites = 0;
        this.totalViews = 0;
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

    /**
     *
     */
    public abstract double getAverageRating();

    /**
     *
     */
    public abstract int getDuration();

    /**
     *
     */
    public void updateNumFavorites() {
        numFavorites++;
    }

    /**
     *
     */
    public int getNumFavorites() {
        return numFavorites;
    }

    /**
     *
     */
    public void updateTotalViews(final int newViews) {
        totalViews += newViews;
    }

    /**
     *
     */
    public int getTotalViews() {
        return totalViews;
    }

    /**
     *
     */
    @Override
    public String toString() {
        return name;
    }


}
