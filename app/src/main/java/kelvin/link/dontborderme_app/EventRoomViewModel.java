package kelvin.link.dontborderme_app;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;


public class EventRoomViewModel extends AndroidViewModel {
    private EventRoomRepository repository;
    private LiveData<List<EventRoom>> allEvents;

    public EventRoomViewModel(@NonNull Application application) {
        super(application);

        repository = new EventRoomRepository(application);
        allEvents = repository.getAllEvents();

    }

    public void insert(EventRoom event){
        repository.insert(event);
    }

    public void update(EventRoom event){
        repository.update(event);
    }

    /*public void delete(String uid, Integer event_id){
        repository.delete(uid ,event_id);
    }*/

    public void delete(Integer event_id){
        repository.delete(event_id);
    }

    public void deleteAllEvents(){
        repository.deleteAllEvents();
    }

    public LiveData<List<EventRoom>> getAllEvents(){
        return allEvents;
    }
}
