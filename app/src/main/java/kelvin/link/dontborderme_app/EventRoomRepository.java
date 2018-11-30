package kelvin.link.dontborderme_app;


import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class EventRoomRepository {
    private EventRoomDAO eventRoomDAO;
    private LiveData<List<EventRoom>> allEvents;

    public EventRoomRepository(Application application){
        EventRoomDatabase database = EventRoomDatabase.getInstance(application);
        eventRoomDAO = database.eventRoomDAO();
        allEvents = eventRoomDAO.getAllEvents();
    }

    public void insert(EventRoom eventRoom){
        new InsertEventAsyncTask(eventRoomDAO).execute(eventRoom);
    }

    public void update(EventRoom eventRoom){
        new UpdateEventAsyncTask(eventRoomDAO).execute(eventRoom);
    }

    public void delete(String uid, Integer event_id ){
        new DeleteEventAsyncTask(eventRoomDAO).execute(uid, String.valueOf(event_id));
    }

    public void deleteAllEvents(){
        new DeleteAllEventAsyncTask(eventRoomDAO).execute();
    }

    public LiveData<List<EventRoom>> getAllEvents(){
        return allEvents;
    }


    private static class InsertEventAsyncTask extends AsyncTask<EventRoom, Void, Void>{
        private EventRoomDAO eventRoomDAO;

        private InsertEventAsyncTask(EventRoomDAO eventRoomDAO){
            this.eventRoomDAO = eventRoomDAO;
        }

        @Override
        protected Void doInBackground(EventRoom... events) {
            eventRoomDAO.insert(events[0]);
            return null;
        }
    }

    private static class UpdateEventAsyncTask extends AsyncTask<EventRoom, Void, Void>{
        private EventRoomDAO eventRoomDAO;

        private UpdateEventAsyncTask(EventRoomDAO eventRoomDAO){
            this.eventRoomDAO = eventRoomDAO;
        }

        @Override
        protected Void doInBackground(EventRoom... events) {
            eventRoomDAO.update(events[0]);
            return null;
        }
    }


    private static class DeleteEventAsyncTask extends AsyncTask<String, Void, Void>{
        private EventRoomDAO eventRoomDAO;

        private DeleteEventAsyncTask(EventRoomDAO eventRoomDAO){
            this.eventRoomDAO = eventRoomDAO;
        }

        @Override
        protected Void doInBackground(String... params) {
            String uid = params[0];
            Integer event_id = Integer.parseInt(params[1]);
            eventRoomDAO.delete(uid, event_id);
            return null;
        }
    }



    private static class DeleteAllEventAsyncTask extends AsyncTask<Void, Void, Void>{
        private EventRoomDAO eventRoomDAO;

        private DeleteAllEventAsyncTask(EventRoomDAO eventRoomDAO){
            this.eventRoomDAO = eventRoomDAO;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            eventRoomDAO.deleteAllEvents();
            return null;
        }
    }
}
