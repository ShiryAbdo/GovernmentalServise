package governmental.service.egypt.data;

/**
 * Created by falcon on 27/09/2017.
 */

public class users {
    public String email;


    public String password;
    public String username;

    public users() {
        // Default constructor required for calls to DataSnapshot.getValue(users.class)
    }

    public users(String email , String password , String username) {
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


