package process;

import common.Constants;
import entertainment.Movie;
import entertainment.Season;
import entertainment.Show;
import fileio.ActionInputData;
import user.User;

/**
 * process all the information for commands
 */
public class Commands {

    private final Database database;

    public Commands(final Database database) {
        this.database = database;
    }

    /**
     * process a command based on type
     * @param actionInputData action
     * @return command result
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
     * process a favorite command
     * @param actionInputData action
     * @return command result
     */
    public String favoriteCommand(final ActionInputData actionInputData) {
        StringBuilder message = new StringBuilder();
        User user = database.getUserByName(actionInputData.getUsername());
        String title = actionInputData.getTitle();
        if (user == null) {
            return Constants.ERROR;
        }
        if (user.getFavourite().contains(title)) {
            message.append("error -> ").append(title).append(" is already in favourite list");
        } else if (!user.getHistory().containsKey(title)) {
            message.append("error -> ").append(title).append(" is not seen");
            } else {
            user.getFavourite().add(title);
            message.append("success -> ").append(title).append(" was added as favourite");
        }
        return message.toString();
    }

    /**
     * process a view command
     * @param actionInputData action
     * @return command result
     */
    public String viewCommand(final ActionInputData actionInputData) {
        StringBuilder message = new StringBuilder();
        User user = database.getUserByName(actionInputData.getUsername());
        String title = actionInputData.getTitle();
        if (user == null) {
            return Constants.ERROR;
        }
        if (user.getHistory().containsKey(title)) {
            user.getHistory().computeIfPresent(title, (key, value) -> value + 1);
        } else {
            user.getHistory().put(title, 1);
        }
        message.append("success -> ").append(title).append(" was viewed with total views of ")
                .append(user.getHistory().get(title));
        return message.toString();
    }

    /**
     * process a rating command
     * @param actionInputData action
     * @return command result
     */
    public String ratingCommand(final ActionInputData actionInputData) {
        StringBuilder message = new StringBuilder();
        StringBuilder videoTitle = new StringBuilder();
        User user = database.getUserByName(actionInputData.getUsername());
        String title = actionInputData.getTitle();
        if (user == null) {
           return Constants.ERROR;
        }
        if (actionInputData.getSeasonNumber() == 0) {
            videoTitle.append(title);
        } else {
            videoTitle.append(title).append("season ").append(actionInputData.getSeasonNumber());
        }

        if (!user.getHistory().containsKey(title)) {
            message.append("error -> ").append(title).append(" is not seen");
        } else if (user.getRated().contains(videoTitle.toString())) {
            message.append("error -> ").append(title).append(" has been already rated");
        } else if (actionInputData.getSeasonNumber() == 0) {
            Movie movie = (Movie) database.getVideoByName(title);
            if (movie == null) {
                return Constants.ERROR;
            }
            movie.getRatings().add(actionInputData.getGrade());
            user.getRated().add(videoTitle.toString());
            movie.updateAverageRating(actionInputData.getGrade());
            message.append("success -> ").append(title).append(" was rated with ")
                    .append(actionInputData.getGrade()).append(" by ").append(user.getUsername());
        } else {
            Show show = (Show) database.getVideoByName(title);
            if (show == null) {
                return Constants.ERROR;
            }
            Season season = show.getSeasons().get(actionInputData.getSeasonNumber() - 1);
            season.getRatings().add(actionInputData.getGrade());
            user.getRated().add(videoTitle.toString());
            show.updateAverageRating(actionInputData.getGrade(),
                    actionInputData.getSeasonNumber() - 1);
            message.append("success -> ").append(title).append(" was rated with ")
                    .append(actionInputData.getGrade()).append(" by ").append(user.getUsername());
        }

        return message.toString();
    }
}
