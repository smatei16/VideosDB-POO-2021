package user;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class User {
    private String username;
    private String subscription;
    private Map<String, Integer> history;
    private List<String> favourite;
    private List<String> rated;

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
