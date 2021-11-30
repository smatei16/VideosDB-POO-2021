package process;

import actor.Actor;
import actor.ActorsAwards;
import common.Constants;
import fileio.ActionInputData;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;

/**
 *
 */
public class ActorQuery {

    private Database database;

    public ActorQuery(final Database database) {
        this.database = database;
    }

    /**
     *
     */
    public String processActorQuery(final ActionInputData actionInputData) {
        return switch (actionInputData.getCriteria()) {
            case Constants.AVERAGE -> this.actorsAverage(actionInputData);
            case Constants.AWARDS -> this.actorsAwards(actionInputData);
            case Constants.FILTER_DESCRIPTIONS -> this.actorsFilterDescription(actionInputData);
            default -> null;
        };
    }

    /**
     *
     */
    public String actorsAverage(final ActionInputData actionInputData) {

        StringBuilder message = new StringBuilder("Query result: ");
        List<Actor> actorSortedList = new ArrayList<>(database.getActorDB());
        actorSortedList.removeIf(actor ->
                Double.compare(actor.getActorAverageRating(database), 0) == 0);

        Collections.sort(actorSortedList, new AverageRatingComparator());
        if (actionInputData.getSortType().equals(Constants.DESC)) {
            Collections.reverse(actorSortedList);
        }
        actorSortedList = actorSortedList.subList(0, Math.min(actorSortedList.size(),
                actionInputData.getNumber()));
        message.append(actorSortedList);
        return message.toString();
    }

    /**
     *
     */
    public String actorsAwards(final ActionInputData actionInputData) {
        StringBuilder message = new StringBuilder("Query result: ");
        List<Actor> actorSortedList = new ArrayList<>(database.getActorDB());
        for (String award : actionInputData.getFilters().get(Constants.FILTER_AWARDS)) {
            actorSortedList.removeIf(actor -> award != null
                    && !actor.getAwards().containsKey(convertToAward(award)));
        }
        Collections.sort(actorSortedList, new AwardsComparator());
        if (actionInputData.getSortType().equals(Constants.DESC)) {
            Collections.reverse(actorSortedList);
        }
        message.append(actorSortedList);
        return message.toString();
    }

    /**
     *
     */
    public String actorsFilterDescription(final ActionInputData actionInputData) {
        StringBuilder message = new StringBuilder("Query result: ");
        List<Actor> actorSortedList = new ArrayList<>(database.getActorDB());

        for (String keyword : actionInputData.getFilters().get(Constants.FILTER_WORDS)) {
            actorSortedList.removeIf(actor -> keyword != null
                    && !Pattern.compile("\\b" + keyword + "\\b", Pattern.CASE_INSENSITIVE)
                            .matcher(actor.getCareerDescription()).find());
        }
        Collections.sort(actorSortedList, new NameComparator());
        if (actionInputData.getSortType().equals(Constants.DESC)) {
            Collections.reverse(actorSortedList);
        }
        message.append(actorSortedList);
        return message.toString();
    }

    /**
     *
     */
    public ActorsAwards convertToAward(final String award) {
        return switch (award) {
            case "BEST_PERFORMANCE" -> ActorsAwards.BEST_PERFORMANCE;
            case "BEST_DIRECTOR" -> ActorsAwards.BEST_DIRECTOR;
            case "PEOPLE_CHOICE_AWARD" -> ActorsAwards.PEOPLE_CHOICE_AWARD;
            case "BEST_SUPPORTING_ACTOR" -> ActorsAwards.BEST_SUPPORTING_ACTOR;
            case "BEST_SCREENPLAY" -> ActorsAwards.BEST_SCREENPLAY;
            default -> null;
        };
    }

    /**
     *
     */
    class AverageRatingComparator implements Comparator<Actor> {
        @Override
        public int compare(final Actor o1, final Actor o2) {
            double ratingO1 = o1.getActorAverageRating(database);
            double ratingO2 = o2.getActorAverageRating(database);
            if (Double.compare(ratingO1, ratingO2) == 0) {
                return o1.getName().compareToIgnoreCase(o2.getName());
            }
            return Double.compare(ratingO1, ratingO2);
        }
    }

    /**
     *
     */
    class AwardsComparator implements Comparator<Actor> {
        @Override
        public int compare(final Actor o1, final Actor o2) {
            double awardsO1 = o1.getNumberOfAwards();
            double awardsO2 = o2.getNumberOfAwards();
            if (Double.compare(awardsO1, awardsO2) == 0) {
                return o1.getName().compareToIgnoreCase(o2.getName());
            }
            return Double.compare(awardsO1, awardsO2);
        }
    }

    /**
     *
     */
    class NameComparator implements Comparator<Actor> {
        @Override
        public int compare(final Actor o1, final Actor o2) {
            return o1.getName().compareToIgnoreCase(o2.getName());
        }
    }
}
