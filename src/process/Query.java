package process;

import actor.Actor;
import common.Constants;
import fileio.ActionInputData;
import org.json.simple.JSONArray;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Query {

    private Database database;

    public Query(final Database database) {
        this.database = database;
    }

    public String processQuery(final ActionInputData actionInputData) {
        return switch (actionInputData.getType()) {
            case Constants.ACTORS -> this.favoriteCommand(actionInputData);

            default -> null;
        };
    }

    public String actorsAverage(final ActionInputData actionInputData) {
        List<Actor> actorSortedList = new ArrayList<>();
        for (Actor actor : database.getActorDB()) {
            actorSortedList.add(new Actor(actor.getName(), actor.getCareerDescription(),
                    actor.getFilmography(), actor.getAwards()));
        }

        if(actionInputData.getSortType())
    }

}
