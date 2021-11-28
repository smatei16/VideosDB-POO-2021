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



public final class Database {
    private List<Actor> actorDB;
    private List<Movie> movieDB;
    private List<Show> showDB;
    private List<User> userDB;
    private List<ActionInputData> actionsDB;

    public Database(final Input input) {
        this.actorDB = this.getActorsFromInput(input);
        this.movieDB = this.getMoviesFromInput(input);
        this.showDB = this.getShowsFromInput(input);
        this.userDB = this.getUsersFromInput(input);
        this.actionsDB = input.getCommands();
    }

    /**
     *
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
     *
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
     *
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
     *
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
     *
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
     *
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
