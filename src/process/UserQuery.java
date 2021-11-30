package process;

import common.Constants;
import fileio.ActionInputData;
import user.User;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *
 */
public class UserQuery {
    private Database database;

    public UserQuery(final Database database) {
        this.database = database;
    }

    /**
     *
     */
    public String processUserQuery(final ActionInputData actionInputData) {
        return switch (actionInputData.getCriteria()) {
            case Constants.NUM_RATINGS -> this.numberOfRatings(actionInputData);
            default -> null;
        };
    }

    /**
     *
     */
    public String numberOfRatings(final ActionInputData actionInputData) {
        StringBuilder message = new StringBuilder("Query result: ");
        List<User> sortedUsers = new ArrayList<>(database.getUserDB());

        sortedUsers.removeIf(user -> user.getRated().size() == 0);
        Collections.sort(sortedUsers, new NumberOfRatingsComparator());
        if (actionInputData.getSortType().equals(Constants.DESC)) {
            Collections.reverse(sortedUsers);
        }

        sortedUsers = sortedUsers.subList(0,
                Math.min(sortedUsers.size(), actionInputData.getNumber()));
        message.append(sortedUsers);
        return message.toString();
    }

    /**
     *
     */
    class NumberOfRatingsComparator implements Comparator<User> {
        @Override
        public int compare(final User o1, final User o2) {
            int ratingsO1 = o1.getRated().size();
            int ratingsO2 = o2.getRated().size();
            if (ratingsO1 == ratingsO2) {
                return o1.getUsername().compareToIgnoreCase(o2.getUsername());
            }
            return Integer.compare(ratingsO1, ratingsO2);
        }
    }
}
