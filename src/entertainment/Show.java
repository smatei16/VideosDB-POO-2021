package entertainment;

import java.util.List;

public final class Show extends Video {
    private int numberOfSeasons;
    private List<Season> seasons;

    private double[] sumRatings;
    private int[] totalRatings;

    public Show(final String name, final int year, final List<String> genres,
                final List<String> actors, final int numberOfSeasons,
                final List<Season> seasons) {
        super(name, year, genres, actors);
        this.seasons = seasons;
        this.numberOfSeasons = numberOfSeasons;

        this.sumRatings = new double[this.numberOfSeasons];
        this.totalRatings = new int[this.numberOfSeasons];
    }

    public List<Season> getSeasons() {
        return seasons;
    }

    public int getNumberOfSeasons() {
        return numberOfSeasons;
    }

    public void updateAverageRating(double rating, int season) {
            this.sumRatings[season] += rating;
            this.totalRatings[season]++;
    }

    public double getAverageRating() {
        double totalSumRatings = 0;
        double seasonRating = 0;
        for (int i = 0; i < this.numberOfSeasons; i++) {
            seasonRating = this.sumRatings[i] / this.totalRatings[i];
            totalSumRatings += seasonRating;
        }
        return totalSumRatings / this.numberOfSeasons;
    }
}
