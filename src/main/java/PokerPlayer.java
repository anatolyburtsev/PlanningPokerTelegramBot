import org.telegram.telegrambots.api.objects.User;

/**
 * Created by onotole on 12.07.16.
 */
public class PokerPlayer {
    private String username;
    private String firstName;
    private String lastName;
    private int id;
    private String chatId;

    public String getUsername() {
        return username;
    }

    public PokerPlayer(User user, Long chatId) {
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.id = user.getId();
        this.chatId = "" + chatId;

        this.username = user.getUserName();
    }

    public int getId() {
        return id;
    }

    public String getChatId() {
        return chatId;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String toString() {
        return String.format("%s (%s %s)", username, firstName, lastName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PokerPlayer that = (PokerPlayer) o;

        if (id != that.id) return false;
        if (username != null ? !username.equals(that.username) : that.username != null) return false;
        if (firstName != null ? !firstName.equals(that.firstName) : that.firstName != null) return false;
        return lastName != null ? lastName.equals(that.lastName) : that.lastName == null;

    }

    @Override
    public int hashCode() {
        int result = username != null ? username.hashCode() : 0;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + id;
        return result;
    }
}
