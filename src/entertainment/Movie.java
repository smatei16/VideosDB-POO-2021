package entertainment;

import java.util.ArrayList;
import java.util.List;

public final class Movie extends Video {
    //duration of current movie
    private final int duration;

    //list of ratings for current movie
    private List<Double> ratings;

    public Movie(final String name, final int year, final List<String> genres,
                 final List<String> actors, final int duration) {
        super(name, year, genres, actors);
        this.duration = duration;
        this.ratings = new ArrayList<>();
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
}
