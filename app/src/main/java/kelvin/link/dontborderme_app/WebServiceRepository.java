package kelvin.link.dontborderme_app;

import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;
import java.util.Map;


public class WebServiceRepository {
    private WebServiceDAO webServiceDAO;
    private static List<Event> allEvents;

    public WebServiceRepository(WebServiceDAO webServiceDAO){
        this.webServiceDAO = webServiceDAO;
        UserManager userManager = UserManager.getInstance();
        String uid = userManager.getUser().getUid();
        //Updating allEvents
        new GetAllEventAsyncTask(webServiceDAO).execute(uid);
    }

    public List<Event> getAllEvents(){
        return allEvents;
    }

    public void insert(Event event){
        new InsertEventAsyncTask(webServiceDAO).execute(event);
    }

    public void update(Event event){
        new UpdateEventAsyncTask(webServiceDAO).execute(event);
    }

    public void delete(Integer event_id){
        new DeleteEventAsyncTask(webServiceDAO).execute(String.valueOf(event_id));
    }



    //TODO Need to fix doInbackground and onpostexecute
    private static class GetAllEventAsyncTask extends AsyncTask<String, Void, List<Event>> {
        private WebServiceDAO webServiceDAO;

        private GetAllEventAsyncTask(WebServiceDAO webServiceDAO){
            this.webServiceDAO = webServiceDAO;
        }

        @Override
        protected List<Event> doInBackground(String... params) {
            String uid = params[0];
            try {
                webServiceDAO.fetchAllUserEvents(uid);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Event> result) {
            super.onPostExecute(result);
            if(result != null){
                allEvents = result;
            }
        }
    }


    private static class InsertEventAsyncTask extends AsyncTask<Event, Void, Void> {
        private WebServiceDAO webServiceDAO;

        private InsertEventAsyncTask(WebServiceDAO webServiceDAO){
            this.webServiceDAO = webServiceDAO;
        }

        @Override
        protected Void doInBackground(Event... events) {
            //Preparing parameters of insertion
            Event event = events[0];

            String uid = event.getUid();
            Integer event_id = event.getEvent_id();
            String event_title = event.getEvent_title();
            String address = event.getAddress();
            String description = event.getDescription();
            String role = event.getRole();
            String start_ts = event.getStart_ts();

            Map<String, String> parameters = null;
            parameters.put("uid", uid);
            parameters.put("event_id", String.valueOf(event_id));
            parameters.put("event_title", event_title);
            parameters.put("address", address);
            parameters.put("description", description);
            parameters.put("role", role);
            parameters.put("start_ts", start_ts);

            webServiceDAO.createEvent(parameters);

            return null;
        }
    }

    private static class UpdateEventAsyncTask extends AsyncTask<Event, Void, Void>{
        private WebServiceDAO webServiceDAO;

        private UpdateEventAsyncTask(WebServiceDAO webServiceDAO){
            this.webServiceDAO = webServiceDAO;
        }

        @Override
        protected Void doInBackground(Event... events) {
            //webServiceDAO.update(events[0]);
            //TODO To be implemented
            return null;
        }
    }


    private static class DeleteEventAsyncTask extends AsyncTask<String, Void, Void>{
        private WebServiceDAO webServiceDAO;

        private DeleteEventAsyncTask(WebServiceDAO webServiceDAO){
            this.webServiceDAO = webServiceDAO;
        }

        @Override
        protected Void doInBackground(String... params) {
            String uid = params[0];
            Integer event_id = Integer.parseInt(params[1]);
            webServiceDAO.deleteEvent(uid, event_id);
            return null;
        }
    }

}