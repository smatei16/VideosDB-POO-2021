package entertainment;

import java.util.List;

public final class Show extends Video {
    private int numberOfSeasons;
    private List<Season> seasons;

    private double[] sumRatings;
    private int[] totalRatings;
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

    public int getNumberOfSeasons() {
        return numberOfSeasons;
    }

    /**
     *
     */
    public void updateAverageRating(final double rating, final int season) {
            this.sumRatings[season] += rating;
            this.totalRatings[season]++;
    }

    @Override
    public double getAverageRating() {
        double totalSumRatings = 0;
        double seasonRating = 0;
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
