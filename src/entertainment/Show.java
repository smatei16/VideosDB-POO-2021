package entertainment;

import java.util.List;

public final class Show extends Video {
    private int numberOfSeasons;
    private List<Season> seasons;

    public Show(final String name, final int year, final List<String> genres,
                final List<String> actors, final int numberOfSeasons,
                final List<Season> seasons) {
        super(name, year, genres, actors);
        this.seasons = seasons;
        this.numberOfSeasons = numberOfSeasons;
    }

    public List<Season> getSeasons() {
        return seasons;
    }

    public int getNumberOfSeasons() {
        return numberOfSeasons;
    }
}
