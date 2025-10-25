import java.util.HashMap;
import java.util.Map;

public class UserData {

    // Store all accounts in memory
    private static Map<String, String> accounts = new HashMap<>();

    // Add a new user account
    public static void addAccount(String username, String password) {
        accounts.put(username, password);
    }

    // Check if a user already exists
    public static boolean userExists(String username) {
        return accounts.containsKey(username);
    }

    // Validate login credentials
    public static boolean validateLogin(String username, String password) {
        return accounts.containsKey(username) && accounts.get(username).equals(password);
    }
}

