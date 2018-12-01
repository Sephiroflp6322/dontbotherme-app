package kelvin.link.dontborderme_app;



public class EventItem {
    private int mImageResource;
    private Event event;

    public EventItem(int imageResource, Event event){
        this.mImageResource = imageResource;
        this.event = event;
    }



    //Getters
    public int getmImageResource() {
        return mImageResource;
    }

    public Event getEvent() {
        return event;
    }


    //Setters
    public void setmImageResource(int mImageResource) {
        this.mImageResource = mImageResource;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}
