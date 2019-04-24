package api.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection =  "users")
public class User {

    @Id
    public String id;

    private final String userName;
    private final String email;
    private final String password;
    private final String firstName;
    private final String lastName;

    public User(String userName, String email, String password, String firstName, String lastName) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public String toString() {
        return String.format(
                "User[id=%s, userName='%s', firstName='%s', lastName='%s', email='%s']",
                id, getUserName(), getFirstName(), getLastName(), getEmail());
    }
}
