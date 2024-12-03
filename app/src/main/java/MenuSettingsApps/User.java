package MenuSettingsApps;

public class User {

    private String username;
    private String email;
    private String role;
    private String phoneNumber;
    private String address;

    public User() {
    }

    public User(String username, String email) {
        this.username = username;
        this.email = email;
        this.phoneNumber=phoneNumber;
        this.address=address;
        this.role = "user";
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}

