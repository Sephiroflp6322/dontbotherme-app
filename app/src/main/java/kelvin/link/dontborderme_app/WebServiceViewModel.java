package kelvin.link.dontborderme_app;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;


public class WebServiceViewModel extends AndroidViewModel {
    private WebServiceRepository repository;
    private LiveData<List<Event>> allEvents;

    public WebServiceViewModel(@NonNull Application application) {
        super(application);
        this.repository = new WebServiceRepository(new WebServiceDAO());
        allEvents = repository.getAllEvents();
    }

    //APIs
    public void insert(Event event){
        repository.insert(event);
    }

    public void update(Event event){
        repository.update(event);
    }

    public void delete(Integer event_id){
        repository.delete(event_id);
    }

    public LiveData<List<Event>> getAllEvents(){
        return allEvents;
    }
}


