package entertainment;

import java.net.http.HttpHeaders;
import java.util.ArrayList;
import java.util.List;

public final class Movie extends Video {
    //duration of current movie
    private final int duration;

    //list of ratings for current movie
    private List<Double> ratings;

    private double sumRatings;
    private int totalRatings;

    public Movie(final String name, final int year, final List<String> genres,
                 final List<String> actors, final int duration) {
        super(name, year, genres, actors);
        this.duration = duration;
        this.ratings = new ArrayList<>();

        this.sumRatings = 0;
        this.totalRatings = 0;
    }

    public int getDuration() {
        return duration;
    }

    public List<Double> getRatings() {
        return ratings;
    }

    public void setRatings(final List<Double> ratings) {
        this.ratings = ratings;
    }

    public void updateAverageRating(double rating) {
            this.sumRatings += rating;
            this.totalRatings++;
    }

    public double getAverageRating() {
        return sumRatings / totalRatings;
    }
}
