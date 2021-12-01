package entertainment;

import java.util.List;

/**
 * information about a video
 */
public abstract class Video {
    /**
     * name of the video
     */
    private final String name;
    /**
     * release year of the video
     */
    private final int year;
    /**
     * list of genres of the video
     */
    private final List<String> genres;
    /**
     * cast of the video
     */
    private final List<String> actors;
    /**
     * number of appearances of the video in the users' favorite lists
     */
    private int numFavorites;
    /**
     * total views of the video
     */
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
     * get the video name
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * get the release year of the video
     * @return year
     */
    public int getYear() {
        return year;
    }

    /**
     * get the list of genres of the video
     * @return a list of genres
     */
    public List<String> getGenres() {
        return genres;
    }

    /**
     * get the cast of the video
     * @return a list of name of actors
     */
    public List<String> getActors() {
        return actors;
    }

    /**
     * get the average rating of the video
     * @return a double
     */
    public abstract double getAverageRating();

    /**
     * get the duration of the video
     * @return an integer
     */
    public abstract int getDuration();

    /**
     * increments the number of appearances in favorite lists
     */
    public void updateNumFavorites() {
        numFavorites++;
    }

    /**
     * get the number of appearances in favorite lists
     * @return an integer
     */
    public int getNumFavorites() {
        return numFavorites;
    }

    /**
     * updates the total views of the video
     * @param newViews the number of new views
     */
    public void updateTotalViews(final int newViews) {
        totalViews += newViews;
    }

    /**
     * get the total number of views of the video
     * @return totalViews
     */
    public int getTotalViews() {
        return totalViews;
    }

    /**
     * overrides the toString method
     * @return name
     */
    @Override
    public String toString() {
        return name;
    }


}
