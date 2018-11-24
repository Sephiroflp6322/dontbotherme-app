package kelvin.link.dontborderme_app;


public class UserEvent {
    private String uid;
    private Integer event_id;
    private String role;
    private String start_ts;

    public UserEvent(String uid, Integer event_id, String role, String start_ts) {
        setUid(uid);
        setEvent_id(event_id);
        setRole(role);
        setStart_ts(start_ts);
    }


    //Getters
    public String getUid() {
        return uid;
    }

    public Integer getEvent_id() {
        return event_id;
    }

    public String getRole() {
        return role;
    }

    public String getStart_ts() {
        return start_ts;
    }

    //Setters

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setEvent_id(Integer event_id) {
        this.event_id = event_id;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setStart_ts(String start_ts) {
        this.start_ts = start_ts;
    }
}
