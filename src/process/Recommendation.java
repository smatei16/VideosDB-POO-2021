package process;

import common.Constants;
import entertainment.Video;
import fileio.ActionInputData;
import user.User;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * process all the information for recommendations
 */
public class Recommendation {
    private final Database database;

    public Recommendation(final Database database) {
        this.database = database;
    }

    /**
     * process a recommendation based on type
     * @param actionInputData action
     * @return recommendation result
     */
    public String processRecommendation(final ActionInputData actionInputData) {
        return switch (actionInputData.getType()) {
            case Constants.STANDARD -> this.standardRecommendation(actionInputData);
            case Constants.BEST_UNSEEN -> this.bestUnseenRecommendation(actionInputData);
            case Constants.POPULAR -> this.popularRecommendation(actionInputData);
            case Constants.FAVORITE -> this.favoriteRecommendation(actionInputData);
            case Constants.SEARCH -> this.searchRecommendation(actionInputData);
            default -> null;
        };
    }

    /**
     * process a standard recommendation
     * @param actionInputData action
     * @return recommendation result
     */
    public String standardRecommendation(final ActionInputData actionInputData) {
        User user = database.getUserByName(actionInputData.getUsername());
        List<Video> videoList = new ArrayList<>(database.getMovieDB());
        videoList.addAll(database.getShowDB());
        if (user == null) {
            return Constants.ERROR;
        }
        for (Video video : videoList) {
            if (!user.getHistory().containsKey(video.getName())) {
                return "StandardRecommendation result: " + video.getName();
            }
        }
        return "StandardRecommendation cannot be applied!";
    }

    /**
     * process a best unseen recommendation
     * @param actionInputData action
     * @return recommendation result
     */
    public String bestUnseenRecommendation(final ActionInputData actionInputData) {
        User user = database.getUserByName(actionInputData.getUsername());
        List<Video> videoList = new ArrayList<>(database.getMovieDB());
        if (user == null) {
            return Constants.ERROR;
        }
        videoList.addAll(database.getShowDB());
        videoList.removeIf(video -> user.getHistory().containsKey(video.getName()));
        videoList.sort(new BestUnseenComparator());
        if (videoList.isEmpty()) {
            return "BestRatedUnseenRecommendation cannot be applied!";
        } else {
            return "BestRatedUnseenRecommendation result: " + videoList.get(0).getName();
        }
    }

    /**
     * process a popular recommendation
     * @param actionInputData action
     * @return recommendation result
     */
    public String popularRecommendation(final ActionInputData actionInputData) {
        User user = database.getUserByName(actionInputData.getUsername());
        if (user == null) {
            return Constants.ERROR;
        }
        if (!user.getSubscription().equals(Constants.PREMIUM)) {
            return "PopularRecommendation cannot be applied!";
        }
        List<Video> videoList = new ArrayList<>(database.getMovieDB());
        videoList.addAll(database.getShowDB());
        videoList.removeIf(video -> user.getHistory().containsKey(video.getName()));
        List<String> genres = getGenresList();
        genres.sort(new GenrePopularityComparator());
        if (videoList.isEmpty()) {
            return "PopularRecommendation cannot be applied!";
        }
        for (String genre : genres) {
            for (Video video : videoList) {
                if (video.getGenres().contains(genre)) {
                    return "PopularRecommendation result: " + video.getName();
                }
            }
        }
        return null;
    }

    /**
     * process a favorite recommendation
     * @param actionInputData action
     * @return recommendation result
     */
    public String favoriteRecommendation(final ActionInputData actionInputData) {
        User user = database.getUserByName(actionInputData.getUsername());
        if (user == null) {
            return Constants.ERROR;
        }
        if (!user.getSubscription().equals(Constants.PREMIUM)) {
            return "FavoriteRecommendation cannot be applied!";
        }
        List<Video> videoList = new ArrayList<>(database.getMovieDB());
        videoList.addAll(database.getShowDB());
        videoList.removeIf(video -> user.getHistory().containsKey(video.getName()));
        if (videoList.isEmpty()) {
            return "FavoriteRecommendation cannot be applied!";
        }
        videoList.sort(new VideoFavoriteComparator());
        return "FavoriteRecommendation result: " + videoList.get(0).getName();
    }

    /**
     * process a search recommendation
     * @param actionInputData action
     * @return recommendation result
     */
    public String searchRecommendation(final ActionInputData actionInputData) {
        User user = database.getUserByName(actionInputData.getUsername());
        if (user == null) {
            return Constants.ERROR;
        }
        if (!user.getSubscription().equals(Constants.PREMIUM)) {
            return "SearchRecommendation cannot be applied!";
        }
        List<Video> videoList = new ArrayList<>(database.getMovieDB());
        videoList.addAll(database.getShowDB());
        videoList.removeIf(video -> user.getHistory().containsKey(video.getName()));
        videoList.removeIf(video -> !video.getGenres().contains(actionInputData.getGenre()));
        if (videoList.isEmpty()) {
            return "SearchRecommendation cannot be applied!";
        }
        videoList.sort(new SearchComparator());
        return "SearchRecommendation result: " + videoList;
    }

    /**
     * get the list of all genres
     * @return list of genres
     */
    public List<String> getGenresList() {
        List<Video> videoList = new ArrayList<>(database.getMovieDB());
        videoList.addAll(database.getShowDB());
        List<String> genresList = new ArrayList<>();
        for (Video video : videoList) {
            for (String genre : video.getGenres()) {
                if (!genresList.contains(genre)) {
                    genresList.add(genre);
                }
            }
        }
        return genresList;
    }

    /**
     * get popularity for a genre
     * @param genre desired genre
     * @return integer
     */
    public int getGenrePopularity(final String genre) {
        List<Video> videoList = new ArrayList<>(database.getMovieDB());
        videoList.addAll(database.getShowDB());
        int popularity = 0;

        for (Video video : videoList) {
            if (video.getGenres().contains(genre)) {
                popularity++;
            }
        }
        return popularity;
    }

    /**
     * get number of appearances of a video in users' favorite lists
     * @param name video name
     * @return integer
     */
    public int getVideoFavorite(final String name) {
        int numberFavorite = 0;
        for (User user : database.getUserDB()) {
            if (user.getFavourite().contains(name)) {
                numberFavorite++;
            }
        }
        return numberFavorite;
    }

    /**
     * comparator for AverageRating sort
     */
    static class BestUnseenComparator implements Comparator<Video> {
        @Override
        public int compare(final Video o1, final Video o2) {
            double ratingO1 = o1.getAverageRating();
            double ratingO2 = o2.getAverageRating();
            return Double.compare(ratingO2, ratingO1);
        }
    }

    /**
     * comparator for genre popularity sort
     */
    class GenrePopularityComparator implements Comparator<String> {
        @Override
        public int compare(final String o1, final String o2) {
            return Integer.compare(getGenrePopularity(o2), getGenrePopularity(o1));
        }
    }

    /**
     * comparator for favorite sort
     */
    class VideoFavoriteComparator implements Comparator<Video> {
        @Override
        public int compare(final Video o1, final Video o2) {
            int numberFavoriteO1 = getVideoFavorite(o1.getName());
            int numberFavoriteO2 = getVideoFavorite(o2.getName());
            return Integer.compare(numberFavoriteO2, numberFavoriteO1);
        }
    }

    /**
     * comparator for search recommendation sort
     */
    static class SearchComparator implements Comparator<Video> {
        @Override
        public int compare(final Video o1, final Video o2) {
            double ratingO1 = o1.getAverageRating();
            double ratingO2 = o2.getAverageRating();
            if (Double.compare(ratingO1, ratingO2) == 0) {
                return o1.getName().compareToIgnoreCase(o2.getName());
            }
            return Double.compare(ratingO1, ratingO2);
        }
    }
}
