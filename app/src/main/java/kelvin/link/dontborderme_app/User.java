package kelvin.link.dontborderme_app;

public class User {
    private String uid;
    private String password;

    public User(String uid, String password) {
        this.uid = uid;
        this.password = password;
    }


    //Getters
    public String getUid() {
        return uid;
    }

    public String getPassword() {
        return password;
    }

    //Setters
    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
