package kelvin.link.dontborderme_app;

import com.google.gson.annotations.SerializedName;


public class Event {
    private String uid;
    private Integer event_id;
    private String event_title;
    private String address;

    @SerializedName("body")
    private String description;

    private String role;
    private String start_ts;

    public Event(String uid, Integer event_id, String event_title, String address, String description, String role, String start_ts) {
        setUid(uid);
        setEvent_id(event_id);
        setEvent_title(event_title);
        setAddress(address);
        setDescription(description);
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

    public String getEvent_title() {
        return event_title;
    }

    public String getAddress() {
        return address;
    }

    public String getDescription() {
        return description;
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

    public void setEvent_title(String event_title) {
        this.event_title = event_title;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setStart_ts(String start_ts) {
        this.start_ts = start_ts;
    }
}
