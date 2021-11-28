package process;

import common.Constants;
import entertainment.Movie;
import entertainment.Season;
import entertainment.Show;
import fileio.ActionInputData;
import user.User;

public final class Commands {

    private Database database;

    public Commands(final Database database) {
        this.database = database;
    }

    /**
     *
     */
    public String processCommand(final ActionInputData actionInputData) {
        return switch (actionInputData.getType()) {
            case Constants.FAVORITE -> this.favoriteCommand(actionInputData);
            case Constants.VIEW -> this.viewCommand(actionInputData);
            case Constants.RATING -> this.ratingCommand(actionInputData);
            default -> null;
        };
    }

    /**
     *
     */
    public String favoriteCommand(final ActionInputData actionInputData) {
        StringBuilder message = new StringBuilder();
        User user = database.getUserByName(actionInputData.getUsername());
        String title = actionInputData.getTitle();

        if (user.getFavourite().contains(title)) {
            message.append("error -> " + title + " is already in favourite list");
        } else if (!user.getHistory().containsKey(title)) {
            message.append("error -> " + title + " is not seen");
            } else {
            user.getFavourite().add(title);
            message.append("success -> " + title + " was added as favourite");
        }

        return message.toString();
    }

    /**
     *
     */
    public String viewCommand(final ActionInputData actionInputData) {
        StringBuilder message = new StringBuilder();
        User user = database.getUserByName(actionInputData.getUsername());
        String title = actionInputData.getTitle();

        if (user.getHistory().containsKey(title)) {
            user.getHistory().computeIfPresent(title, (key, value) -> value + 1);
        } else {
            user.getHistory().put(title, 1);
        }
        message.append("success -> " + title + " was viewed with total views of "
                + user.getHistory().get(title));

        return message.toString();
    }

    /**
     *
     */
    public String ratingCommand(final ActionInputData actionInputData) {
        StringBuilder message = new StringBuilder();
        StringBuilder videoTitle = new StringBuilder();
        User user = database.getUserByName(actionInputData.getUsername());
        String title = actionInputData.getTitle();

        if (actionInputData.getSeasonNumber() == 0) {
            videoTitle.append(title);
        } else {
            videoTitle.append(title + "season " + actionInputData.getSeasonNumber());
        }


        if (!user.getHistory().containsKey(title)) {
            message.append("error -> " + title + " is not seen");
        } else if (user.getRated().contains(videoTitle.toString())) {
            message.append("error -> " + title + " has already been rated");
        } else if (actionInputData.getSeasonNumber() == 0) {
            Movie movie = (Movie) database.getVideoByName(title);
            movie.getRatings().add(actionInputData.getGrade());
            user.getRated().add(videoTitle.toString());
            message.append("success -> " + title + " was rated with "
                    + actionInputData.getGrade() + " by " + user.getUsername());
        } else {
            Show show = (Show) database.getVideoByName(title);
            Season season = show.getSeasons().get(actionInputData.getSeasonNumber() - 1);
            season.getRatings().add(actionInputData.getGrade());
            user.getRated().add(videoTitle.toString());
            message.append("success -> " + title + " was rated with "
                    + actionInputData.getGrade() + " by " + user.getUsername());
        }

        return message.toString();
    }
}
