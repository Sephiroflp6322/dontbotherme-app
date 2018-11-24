package kelvin.link.dontborderme_app;


public class UserManager {
    private User user;

    private static UserManager instance = null;

    UserManager(String uid, String password){
        user = new User(null, null);
        this.user.setUid(uid);
        this.user.setPassword(password);
    }

    //Implementing singleton
    public static UserManager getInstance(){
        if(instance == null){
            //TODO [Hardcode] Need to implement as auto fetch user info
            instance = new UserManager("kelvin@gmail.com", "12345678");
            return instance;
        }else{
            return instance;
        }
    }

    //Getters
    public User getUser(){
        return user;
    }
}
