package actor;

import entertainment.Movie;
import entertainment.Show;
import process.Database;

import java.util.List;
import java.util.Map;

public final class Actor {
    private final String name;
    private final String careerDescription;
    private final List<String> filmography;
    private final Map<ActorsAwards, Integer> awards;

    public Actor(final String name, final String careerDescription, final List<String> filmography,
                 final Map<ActorsAwards, Integer> awards) {
        this.name = name;
        this.careerDescription = careerDescription;
        this.filmography = filmography;
        this.awards = awards;
    }

    public String getName() {
        return name;
    }

    public String getCareerDescription() {
        return careerDescription;
    }

    public List<String> getFilmography() {
        return filmography;
    }

    public Map<ActorsAwards, Integer> getAwards() {
        return awards;
    }

    /**
     *
     */
    public double getActorAverageRating(final Database database) {
        int totalVideos = 0;
        double totalRating = 0;

        for (String video : this.filmography) {
            for (Movie movie : database.getMovieDB()) {
                if (movie.getName().equals(video)
                        && Double.compare(movie.getAverageRating(), 0) != 0) {
                   totalRating += movie.getAverageRating();
                   totalVideos++;
                   break;
                }
            }

            for (Show show : database.getShowDB()) {
                if (show.getName().equals(video)
                        && Double.compare(show.getAverageRating(), Double.valueOf(0)) != 0) {
                    totalRating += show.getAverageRating();
                    totalVideos++;
                    break;
                }
            }
        }
        return totalVideos == 0 ? 0 : totalRating / totalVideos;
    }

    @Override
    public String toString() {
        return name;
    }
}
