package process;

import actor.Actor;
import entertainment.Movie;
import entertainment.Show;
import entertainment.Video;
import fileio.ActionInputData;
import fileio.Input;
import fileio.ActorInputData;
import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.UserInputData;
import user.User;
import java.util.ArrayList;
import java.util.List;

/**
 * all the information in the database
 */
public final class Database {
    /**
     * actor database
     */
    private final List<Actor> actorDB;
    /**
     * movie database
     */
    private final List<Movie> movieDB;
    /**
     * show database
     */
    private final List<Show> showDB;
    /**
     * user database
     */
    private final List<User> userDB;
    /**
     * action database
     */
    private final List<ActionInputData> actionsDB;

    public Database(final Input input) {
        this.actorDB = this.getActorsFromInput(input);
        this.movieDB = this.getMoviesFromInput(input);
        this.showDB = this.getShowsFromInput(input);
        this.userDB = this.getUsersFromInput(input);
        this.actionsDB = input.getCommands();
    }

    /**
     * get list of actors from input
     * @param input input data
     * @return list of actors
     */
    public List<Actor> getActorsFromInput(final Input input) {
        List<Actor> actors = new ArrayList<>();
        for (ActorInputData actor : input.getActors()) {
            actors.add(new Actor(actor.getName(),
                    actor.getCareerDescription(),
                    actor.getFilmography(),
                    actor.getAwards()));
        }
        return actors;
    }

    /**
     * get list of movies from input
     * @param input input data
     * @return list of movies
     */
    public List<Movie> getMoviesFromInput(final Input input) {
        List<Movie> movies = new ArrayList<>();
        for (MovieInputData movie : input.getMovies()) {
            movies.add(new Movie(movie.getTitle(),
                    movie.getYear(),
                    movie.getGenres(),
                    movie.getCast(),
                    movie.getDuration()));
        }
        return movies;
    }

    /**
     * get a list of shows from input
     * @param input input data
     * @return list of shows
     */
    public List<Show> getShowsFromInput(final Input input) {
        List<Show> shows = new ArrayList<>();
        for (SerialInputData show : input.getSerials()) {
            shows.add(new Show(show.getTitle(),
                    show.getYear(),
                    show.getGenres(),
                    show.getCast(),
                    show.getNumberSeason(),
                    show.getSeasons()));
        }
        return shows;
    }

    /**
     * get a list of users from input
     * @param input input data
     * @return list of users
     */
    public List<User> getUsersFromInput(final Input input) {
        List<User> users = new ArrayList<>();
        for (UserInputData user : input.getUsers()) {
            users.add(new User(user.getUsername(),
                    user.getSubscriptionType(),
                    user.getHistory(),
                    user.getFavoriteMovies()));
        }
        return users;
    }

    /**
     * get a user instance from the database based on username
     * @param name username
     * @return user instance
     */
    public User getUserByName(final String name) {
        for (User user : userDB) {
            if (user.getUsername().equals(name)) {
                return user;
            }
        }
        return null;
    }

    /**
     * get a video instance from the database based on title
     * @param name video title
     * @return video instance
     */
    public Video getVideoByName(final String name) {
        for (Video movie : movieDB) {
            if (movie.getName().equals(name)) {
                return movie;
            }
        }
        for (Video show : showDB) {
            if (show.getName().equals(name)) {
                return show;
            }
        }
        return null;
    }

    public List<Actor> getActorDB() {
        return actorDB;
    }

    public List<Movie> getMovieDB() {
        return movieDB;
    }

    public List<Show> getShowDB() {
        return showDB;
    }

    public List<User> getUserDB() {
        return userDB;
    }

    public List<ActionInputData> getActionsDB() {
        return actionsDB;
    }
}
