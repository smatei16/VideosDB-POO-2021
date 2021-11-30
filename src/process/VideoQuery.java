package process;

import common.Constants;
import entertainment.Video;
import fileio.ActionInputData;
import user.User;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;


/**
 *
 */
public class VideoQuery {
    private Database database;

    public VideoQuery(final Database database) {
        this.database = database;
    }

    /**
     *
     */
    public String processVideoQuery(final ActionInputData actionInputData) {
        return switch (actionInputData.getCriteria()) {
            case Constants.RATINGS -> this.ratingQuery(actionInputData);
            case Constants.FAVORITE -> this.favoriteQuery(actionInputData);
            case Constants.LONGEST -> this.longestQuery(actionInputData);
            case Constants.MOST_VIEWED -> this.mostViewedQuery(actionInputData);
            default -> null;
        };
    }

    /**
     *
     */
    public String ratingQuery(final ActionInputData actionInputData) {
        StringBuilder message = new StringBuilder("Query result: ");
        List<Video> videoList;

        if (actionInputData.getObjectType().equals(Constants.MOVIES)) {
            videoList = new ArrayList<>(database.getMovieDB());
        } else {
            videoList = new ArrayList<>(database.getShowDB());
        }

        filterVideos(actionInputData, videoList);
        videoList.removeIf(video -> Double.compare(video.getAverageRating(), 0) == 0);
        Collections.sort(videoList, new RatingComparator());
        if (actionInputData.getSortType().equals(Constants.DESC)) {
            Collections.reverse(videoList);
        }
        videoList = videoList.subList(0, Math.min(videoList.size(), actionInputData.getNumber()));
        message.append(videoList);
        return message.toString();
    }

    /**
     *
     */
    public String favoriteQuery(final ActionInputData actionInputData) {
        StringBuilder message = new StringBuilder("Query result: ");
        List<Video> videoList;

        if (actionInputData.getObjectType().equals(Constants.MOVIES)) {
            videoList = new ArrayList<>(database.getMovieDB());
        } else {
            videoList = new ArrayList<>(database.getShowDB());
        }

        for (User user : database.getUserDB()) {
            for (String videoName : user.getFavourite()) {
                Video video = database.getVideoByName(videoName);
                video.updateNumFavorites();
            }
        }

        filterVideos(actionInputData, videoList);
        videoList.removeIf(video -> video.getNumFavorites() == 0);
        Collections.sort(videoList, new FavoriteComparator());
        if (actionInputData.getSortType().equals(Constants.DESC)) {
            Collections.reverse(videoList);
        }
        videoList = videoList.subList(0, Math.min(videoList.size(), actionInputData.getNumber()));
        message.append(videoList);
        return message.toString();
    }

    /**
     *
     */
    public String longestQuery(final ActionInputData actionInputData) {
        StringBuilder message = new StringBuilder("Query result: ");
        List<Video> videoList;

        if (actionInputData.getObjectType().equals(Constants.MOVIES)) {
            videoList = new ArrayList<>(database.getMovieDB());
        } else {
            videoList = new ArrayList<>(database.getShowDB());
        }

        filterVideos(actionInputData, videoList);
        Collections.sort(videoList, new LengthComparator());
        if (actionInputData.getSortType().equals(Constants.DESC)) {
            Collections.reverse(videoList);
        }
        videoList = videoList.subList(0, Math.min(videoList.size(), actionInputData.getNumber()));
        message.append(videoList);
        return message.toString();
    }

    /**
     *
     */
    public String mostViewedQuery(final ActionInputData actionInputData) {
        StringBuilder message = new StringBuilder("Query result: ");
        List<Video> videoList;

        if (actionInputData.getObjectType().equals(Constants.MOVIES)) {
            videoList = new ArrayList<>(database.getMovieDB());
        } else {
            videoList = new ArrayList<>(database.getShowDB());
        }

        for (User user : database.getUserDB()) {
            for (Map.Entry<String, Integer> entry : user.getHistory().entrySet()) {
                Video video = database.getVideoByName(entry.getKey());
                video.updateTotalViews(entry.getValue());
            }
        }
        filterVideos(actionInputData, videoList);
        videoList.removeIf(video -> video.getTotalViews() == 0);
        Collections.sort(videoList, new ViewsComparator());
        if (actionInputData.getSortType().equals(Constants.DESC)) {
            Collections.reverse(videoList);
        }
        videoList = videoList.subList(0, Math.min(videoList.size(), actionInputData.getNumber()));
        message.append(videoList);
        return message.toString();
    }


    /**
     *
     */
    public void filterVideos(final ActionInputData actionInputData, final List<Video> videoList) {
        if (actionInputData.getFilters().get(Constants.FILTER_YEAR) != null) {
            List<String> filteredYears = actionInputData.getFilters().get(Constants.FILTER_YEAR);
            for (String year : filteredYears) {
                videoList.removeIf(video -> year != null
                        && !String.valueOf(video.getYear()).equals(year));
            }
        }
        if (actionInputData.getFilters().get(Constants.FILTER_GENRE) != null) {
            List<String> filteredGenres = actionInputData.getFilters().get(Constants.FILTER_GENRE);
            for (String genre : filteredGenres) {
                videoList.removeIf(video -> genre != null && !video.getGenres().contains(genre));
            }
        }
    }

    /**
     *
     */
    class RatingComparator implements Comparator<Video> {
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

    /**
     *
     */
    class FavoriteComparator implements Comparator<Video> {
        @Override
        public int compare(final Video o1, final Video o2) {
            int numFavoritesO1 = o1.getNumFavorites();
            int numFavoritesO2 = o2.getNumFavorites();
            if (numFavoritesO1 == numFavoritesO2) {
                return o1.getName().compareToIgnoreCase(o2.getName());
            }
            return Integer.compare(numFavoritesO1, numFavoritesO2);
        }
    }

    /**
     *
     */
    class LengthComparator implements Comparator<Video> {
        @Override
        public int compare(final Video o1, final Video o2) {
            int lengthO1 = o1.getDuration();
            int lengthO2 = o2.getDuration();
            if (lengthO1 == lengthO2) {
                return o1.getName().compareToIgnoreCase(o2.getName());
            }
            return Integer.compare(lengthO1, lengthO2);
        }
    }

    /**
     *
     */
    class ViewsComparator implements Comparator<Video> {
        @Override
        public int compare(final Video o1, final Video o2) {
            int totalViewsO1 = o1.getTotalViews();
            int totalViewsO2 = o2.getTotalViews();
            if (totalViewsO1 == totalViewsO2) {
                return o1.getName().compareToIgnoreCase(o2.getName());
            }
            return Integer.compare(totalViewsO1, totalViewsO2);
        }
    }
}
