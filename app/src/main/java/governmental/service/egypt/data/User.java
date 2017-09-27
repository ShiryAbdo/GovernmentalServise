package governmental.service.egypt.data;

/**
 * Created by falcon on 27/09/2017.
 */

public class User {

    public String username;
    public String email;
    public String password;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String username, String email ,String password) {
        this.username = username;
        this.email = email;
        this.password=password;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}


