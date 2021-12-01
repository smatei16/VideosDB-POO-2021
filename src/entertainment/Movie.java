package entertainment;

import java.util.ArrayList;
import java.util.List;

/**
 * information about a movie
 */
public final class Movie extends Video {
    /**
     * duration of the movie
     */
    private final int duration;
    /**
     * list of ratings for the movie
     */
    private List<Double> ratings;
    /**
     * the sum of ratings for the movie
     */
    private double sumRatings;
    /**
     * the number of ratings for the movie
     */
    private int totalRatings;

    public Movie(final String name, final int year, final List<String> genres,
                 final List<String> actors, final int duration) {
        super(name, year, genres, actors);
        this.duration = duration;
        this.ratings = new ArrayList<>();

        this.sumRatings = 0;
        this.totalRatings = 0;
    }

    public Movie(final String name, final int year, final List<String> genres,
                 final List<String> actors, final int duration,
                 final double sumRatings, final int totalRatings) {
        this(name, year, genres, actors, duration);
        this.sumRatings = sumRatings;
        this.totalRatings = totalRatings;
    }

    @Override
    public int getDuration() {
        return duration;
    }

    public List<Double> getRatings() {
        return ratings;
    }

    public void setRatings(final List<Double> ratings) {
        this.ratings = ratings;
    }

    /**
     * updates the sum of ratings and the total number of ratings
     * @param newRating the new rating given
     */
    public void updateAverageRating(final double newRating) {
            this.sumRatings += newRating;
            this.totalRatings++;
    }

    @Override
    public double getAverageRating() {
        return totalRatings == 0 ? 0 : sumRatings / totalRatings;
    }
}
