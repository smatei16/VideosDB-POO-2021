package user;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * information about a user
 */
public final class User {
    /**
     * username
     */
    private final String username;
    /**
     * subscription type
     */
    private final String subscription;
    /**
     * list of watched videos and number of views for each video
     */
    private final Map<String, Integer> history;
    /**
     * list of favorite videos
     */
    private final List<String> favourite;
    /**
     * list of rated videos
     */
    private final List<String> rated;

    public User(final String username, final String subscription, final Map<String,
            Integer> history, final List<String> favourite) {
        this.username = username;
        this.subscription = subscription;
        this.history = history;
        this.favourite = favourite;
        this.rated = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public String getSubscription() {
        return subscription;
    }

    public Map<String, Integer> getHistory() {
        return history;
    }

    public List<String> getFavourite() {
        return favourite;
    }

    public List<String> getRated() {
        return rated;
    }

    @Override
    public String toString() {
        return username;
    }
}
