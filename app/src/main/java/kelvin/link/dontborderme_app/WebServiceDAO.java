package kelvin.link.dontborderme_app;


import android.arch.lifecycle.LiveData;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class WebServiceDAO {
    private String logMessage = "WebServiceDAO:";
    private DontBorderMeWebServiceAPI webServiceAPI;
    private List<Event> cachedEvents = new ArrayList<>();


    public WebServiceDAO() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://59.149.35.12/dontborderme_webservice/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        webServiceAPI = retrofit.create(DontBorderMeWebServiceAPI.class);
    }


    //==========APIs==========

    //Arguments required: <uid>
    public void fetchAllUserEvents(String uid) throws InterruptedException {
        //_getAllUserEvents(uid);
    }

    public List<Event> getAllEvents(){
        return cachedEvents;
    }

    //Arguments required: <All fields of an event>
    public void createEvent(Map<String, String> parameters){
        _createEvent(parameters);
    }

    //Arguments required: <All fields of an event>
    public void updateEvent(Map<String, String> parameters){
        _updateEvent(parameters);
    }

    //Arguments required: <uid>, <event_id>
    public void deleteEvent(String uid, Integer event_id){
        _deleteEvent(uid, event_id);
    }

    //Arguments required: <uid>, <event_id>
    public void subscribeEvent(String uid, Integer event_id){
        _subscribeEvent(uid, event_id);
    }




    //==========Workhorses==========

    /*//Arguments required: <uid>
    private void _getAllUserEvents(String uid){
        Map<String, String> parameters = new HashMap<>();
        parameters.put("uid", uid);
        Call<List<Event>> call = webServiceAPI.getUserEvents(parameters);

        //Async call
        call.enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                if(!response.isSuccessful()){
                    Log.i(logMessage, "_getAllUserEvents(); Response code:" + response.code());
                    return;
                }

                cachedEvents = response.body();

                //For debug
                List<Event> result = cachedEvents;
                for(Event e: result){
                    Log.i(logMessage, "_getAllUserEvents(); Received Event Uid:  " + e.getUid() + "  Event_id: " + e.getEvent_id());
                }
            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t){
                Log.i(logMessage, "_getAllUserEvents(); Fialure message: " + t.getMessage());
                //response.errorBody().string()
            }
        });
    }*/



    //Arguments required: <All fields of an event>
    private void _createEvent(Map<String, String> parameters){
        Call<Void> call = webServiceAPI.createEvent(parameters);

        //Async call
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(!response.isSuccessful()){
                    Log.i(logMessage, "_createEvent(); Response code:" + response.code());
                    return;
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t){
                Log.i(logMessage, "_createEvent(); Fialure message: " + t.getMessage());
                //response.errorBody().string()
            }
        });
    }



    //Arguments required: <All fields of an event>
    private void _updateEvent(Map<String, String> parameters){
        Call<Void> call = webServiceAPI.updateEvent(parameters);

        //Async call
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(!response.isSuccessful()){
                    Log.i(logMessage, "_updateEvent(); Response code:" + response.code());
                    return;
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t){
                Log.i(logMessage, "_updateEvent(); Fialure message: " + t.getMessage());
                //response.errorBody().string()
            }
        });
    }


    //Arguments required: <uid>, <event_id>
    private void _deleteEvent(String uid, Integer event_id){
        Map<String, String> parameters = new HashMap<>();
        parameters.put("uid", uid);
        parameters.put("event_id", String.valueOf(event_id));
        Call<Void> call = webServiceAPI.deleteEvent(parameters);

        //Async call
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(!response.isSuccessful()){
                    Log.i(logMessage, "_deleteEvent(); Response code:" + response.code());
                    return;
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t){
                Log.i(logMessage, "_deleteEvent(); Fialure message: " + t.getMessage());
                //response.errorBody().string()
            }
        });
    }




    //Arguments required: <uid>, <event_id>
    private void _subscribeEvent(String uid, Integer event_id){
        Map<String, String> parameters = new HashMap<>();
        parameters.put("uid", uid);
        parameters.put("event_id", String.valueOf(event_id));
        Call<Void> call = webServiceAPI.subscribe_event(parameters);

        //Async call
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(!response.isSuccessful()){
                    Log.i(logMessage, "_subscribeEvent(); Response code:" + response.code());
                    return;
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t){
                Log.i(logMessage, "_subscribeEvent(); Fialure message: " + t.getMessage());
                //response.errorBody().string()
            }
        });
    }

}