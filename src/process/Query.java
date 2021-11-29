package process;

import actor.Actor;
import actor.ActorsAwards;
import common.Constants;
import fileio.ActionInputData;
import org.json.simple.JSONArray;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Query {

    private Database database;

    public Query(final Database database) {
        this.database = database;
    }

    public String processQuery(final ActionInputData actionInputData) {
//        return switch (actionInputData.getObjectType()) {
//            case Constants.ACTORS -> this.actorsAverage(actionInputData);
//
//            default -> null;
//        };
        if(actionInputData.getObjectType().equals(Constants.ACTORS)
                && actionInputData.getCriteria().equals("average"))
            return this.actorsAverage(actionInputData);
        else if(actionInputData.getObjectType().equals(Constants.ACTORS)
                && actionInputData.getCriteria().equals(Constants.AWARDS))
            return this.actorsAwards(actionInputData);
        return null;
    }

    public String actorsAverage(final ActionInputData actionInputData) {
        List<Actor> actorSortedList = new ArrayList<>();
        StringBuilder message = new StringBuilder();
        for (Actor actor : database.getActorDB()) {
            if (Double.compare(actor.getActorAverageRating(database), Double.valueOf(0)) != 0)
            actorSortedList.add(new Actor(actor.getName(), actor.getCareerDescription(),
                    actor.getFilmography(), actor.getAwards()));
        }

        if (actionInputData.getSortType().equals(Constants.ASC)) {
            Collections.sort(actorSortedList, new Comparator<Actor>() {
                @Override
                public int compare(Actor o1, Actor o2) {
                    double ratingO1 = o1.getActorAverageRating(database);
                    double ratingO2 = o2.getActorAverageRating(database);
                    if(Double.compare(ratingO1, ratingO2) == 0)
                        return o1.getName().compareToIgnoreCase(o2.getName());
                    return Double.compare(ratingO1, ratingO2);
                }
            });
        } else {
            Collections.sort(actorSortedList, new Comparator<Actor>() {
                @Override
                public int compare(Actor o1, Actor o2) {
                    double ratingO1 = o1.getActorAverageRating(database);
                    double ratingO2 = o2.getActorAverageRating(database);
                    if(Double.compare(ratingO2, ratingO1) == 0)
                        return o2.getName().compareToIgnoreCase(o1.getName());
                    return Double.compare(ratingO2, ratingO1);
                }
            });
        }
        message.append("Query result: [");
         for (int i = 0; i < actorSortedList.size() && i < actionInputData.getNumber(); i++) {
            if (i == 0) {
                message.append(actorSortedList.get(i).getName());
            } else {
                message.append(", " + actorSortedList.get(i).getName());
            }
         }
         message.append("]");

        return message.toString();
    }

    public String actorsAwards(final ActionInputData actionInputData) {
        List<Actor> actorSortedList = new ArrayList<>();
        StringBuilder message = new StringBuilder();
        for (Actor actor : database.getActorDB()) {
            if (Double.compare(actor.getActorAverageRating(database), Double.valueOf(0)) != 0)
                actorSortedList.add(new Actor(actor.getName(), actor.getCareerDescription(),
                        actor.getFilmography(), actor.getAwards()));
        }


        for (String award : actionInputData.getFilters().get(3)) {
                actorSortedList.removeIf(actor -> !actor.getAwards().containsKey(convertToAward(award)));
            }
        if (actionInputData.getSortType().equals(Constants.ASC)) {
            Collections.sort(actorSortedList, new Comparator<Actor>() {
                @Override
                public int compare(Actor o1, Actor o2) {
                    double awardsO1 = o1.getAwards().size();
                    double awardsO2 = o2.getAwards().size();
                    if(Double.compare(awardsO1, awardsO2) == 0)
                        return o1.getName().compareToIgnoreCase(o2.getName());
                    return Double.compare(awardsO1, awardsO2);
                }
            });
        } else {
            Collections.sort(actorSortedList, new Comparator<Actor>() {
                @Override
                public int compare(Actor o1, Actor o2) {
                    double awardsO1 = o1.getAwards().size();
                    double awardsO2 = o2.getAwards().size();
                    if(Double.compare(awardsO2, awardsO1) == 0)
                        return o2.getName().compareToIgnoreCase(o1.getName());
                    return Double.compare(awardsO2, awardsO1);
                }
            });
        }

        message.append("Query result: [");
        for (int i = 0; i < actorSortedList.size() && i < actionInputData.getNumber(); i++) {
            if (i == 0) {
                message.append(actorSortedList.get(i).getName());
            } else {
                message.append(", " + actorSortedList.get(i).getName());
            }
        }
        message.append("]");

        return message.toString();
    }

    public ActorsAwards convertToAward(String award) {
        return switch (award) {
            case "BEST_PERFORMANCE" -> ActorsAwards.BEST_PERFORMANCE;
            case "BEST_DIRECTOR" -> ActorsAwards.BEST_DIRECTOR;
            case "PEOPLE_CHOICE_AWARD" -> ActorsAwards.PEOPLE_CHOICE_AWARD;
            case "BEST_SUPPORTING_ACTOR" -> ActorsAwards.BEST_SUPPORTING_ACTOR;
            case "BEST_SCREENPLAY" -> ActorsAwards.BEST_SCREENPLAY;
            default -> null;
        };

    }
}
