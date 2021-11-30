package process;

import common.Constants;
import fileio.ActionInputData;

/**
 *
 */
public class Query {

    private Database database;

    public Query(final Database database) {
        this.database = database;
    }

    /**
     *
     */
    public String processQuery(final ActionInputData actionInputData) {
        switch (actionInputData.getObjectType()) {
            case Constants.ACTORS:
                ActorQuery actorQuery = new ActorQuery(database);
                return actorQuery.processActorQuery(actionInputData);

            case Constants.MOVIES:
            case Constants.SHOWS:
                VideoQuery videoQuery = new VideoQuery(database);
                return videoQuery.processVideoQuery(actionInputData);

            case Constants.USERS:
                UserQuery userQuery = new UserQuery(database);
                return userQuery.processUserQuery(actionInputData);

            default:
                return null;

        }
    }
}
