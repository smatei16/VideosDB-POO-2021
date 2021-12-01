package entertainment;

import java.util.List;

/**
 * information about a serial
 */
public final class Show extends Video {
    /**
     * number of seasons
     */
    private final int numberOfSeasons;
    /**
     * list of seasons
     */
    private final List<Season> seasons;
    /**
     * list of sum ratings for each season
     */
    private final double[] sumRatings;
    /**
     * list of total ratings for each season
     */
    private final int[] totalRatings;
    /**
     * total duration of the show
     */
    private final int duration;

    public Show(final String name, final int year, final List<String> genres,
                final List<String> actors, final int numberOfSeasons,
                final List<Season> seasons) {
        super(name, year, genres, actors);
        this.seasons = seasons;
        this.numberOfSeasons = numberOfSeasons;

        this.sumRatings = new double[this.numberOfSeasons];
        this.totalRatings = new int[this.numberOfSeasons];

        int totalDuration = 0;
        for (Season season : this.getSeasons()) {
            totalDuration += season.getDuration();
        }
        this.duration = totalDuration;
    }

    public List<Season> getSeasons() {
        return seasons;
    }

    /**
     * updates the sum of ratings and the total number of ratings for a season
     * @param newRating the new rating given
     * @param season the season for which the rating was given
     */
    public void updateAverageRating(final double newRating, final int season) {
            this.sumRatings[season] += newRating;
            this.totalRatings[season]++;
    }

    @Override
    public double getAverageRating() {
        double totalSumRatings = 0;
        double seasonRating;
        for (int i = 0; i < this.numberOfSeasons; i++) {
            if (this.totalRatings[i] != 0) {
            seasonRating = this.sumRatings[i] / this.totalRatings[i];
            totalSumRatings += seasonRating;
            }
        }
        return totalSumRatings / this.numberOfSeasons;
    }

    @Override
    public int getDuration() {
        return duration;
    }
}
