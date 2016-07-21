import java.util.*;

/**
 * Created by onotole on 12.07.16.
 */
public class UserLists {

    private List<String> allowedUsers = Arrays.asList("allowedUser1", "allowedUser2", "allowedUser3");
    private Set<PokerPlayer> currentPokerPlayers;
    private PokerPlayer mainUser = null;

    public void setMainUser(PokerPlayer mainUser) {
        this.mainUser = mainUser;
    }

    public UserLists() {
        currentPokerPlayers = new HashSet<>();
    }

    public PokerPlayer getMainUser() {
        return mainUser;
    }

    public Set<PokerPlayer> getCurrentPokerPlayers() {
        return currentPokerPlayers;
    }

    public void init() {
        mainUser = null;
        currentPokerPlayers = new HashSet<>();
    }

    public void addCurrentUser(PokerPlayer username) {
        currentPokerPlayers.add(username);
    }

    public boolean isAllowedUser(String username) {
        return allowedUsers.contains(username);
    }

    public String getCurrentUsersString() {
        String result = "";
        for (PokerPlayer pokerPlayer : currentPokerPlayers) {
            result = result + pokerPlayer + "\n";
        }
        return result;
    }
}
