package actor;

import entertainment.Movie;
import entertainment.Show;
import process.Database;
import java.util.List;
import java.util.Map;

/**
 * information about an actor
 */
public final class Actor {
    /**
     * Name of the actor
     */
    private final String name;
    /**
     * Description of the actor's career
     */
    private final String careerDescription;
    /**
     * videos starring actor
     */
    private final List<String> filmography;
    /**
     * awards won by the actor
     */
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
     * get the actor's average rating
     * @param database the database
     * @return a double
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
                        && Double.compare(show.getAverageRating(), 0) != 0) {
                    totalRating += show.getAverageRating();
                    totalVideos++;
                    break;
                }
            }
        }
        return totalVideos == 0 ? 0 : totalRating / totalVideos;
    }

    /**
     * get the actor's total number of awards
     * @return an integer
     */
    public int getNumberOfAwards() {
        int numberOfAwards = 0;
        for (int number : this.awards.values()) {
            numberOfAwards += number;
        }
        return numberOfAwards;
    }

    @Override
    public String toString() {
        return name;
    }
}
