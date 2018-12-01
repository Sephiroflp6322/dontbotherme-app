package kelvin.link.dontborderme_app;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SubscribeEventActivity extends AppCompatActivity {
    private String logMessage = "SubscribeActivity:";
    private ArrayList<EventItem> eventItemArrayList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private EventItemAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private DontBorderMeWebServiceAPI webServiceAPI;
    private User user;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent main_intent = new Intent(SubscribeEventActivity.this, LocalEventActivity.class);
                    startActivity(main_intent);
                    return true;
                case R.id.navigation_sender:
                    Intent sender_intent = new Intent(SubscribeEventActivity.this, SendEventActivity.class);
                    startActivity(sender_intent);
                    return true;
                case R.id.navigation_subscriber:
                    return true;
            }
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscribe_event);


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.subscribe_activity_navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_subscriber);

        //Setting up user info
        user = UserManager.getInstance().getUser();

        //Setting up retrofit connection
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://59.149.35.12/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        webServiceAPI = retrofit.create(DontBorderMeWebServiceAPI.class);


        mRecyclerView = findViewById(R.id.subscribe_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new EventItemAdapter(eventItemArrayList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new EventItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //TODO Set onclick event
            }

            @Override
            public void onDeleteClick(int position) {
                Integer event_id = eventItemArrayList.get(position).getEvent().getEvent_id();
                deleteEvent(user.getUid(), event_id);
                removeItem(position);
            }
        });

        getAllUserEvents(user.getUid());


    }



    public void removeItem(int position){
        eventItemArrayList.remove(position);
        mAdapter.notifyItemRemoved(position);
    }


    private void updateAdapter(List<Event> events){
        eventItemArrayList.clear();
        for(Event e: events){
            eventItemArrayList.add(new EventItem(R.drawable.ic_remind, e));
            Log.i(logMessage, "description: " + e.getRole());
        }
    }


    //Arguments required: <uid>
    private void getAllUserEvents(String uid){
        Map<String, String> parameters = new HashMap<>();
        parameters.put("uid", uid);
        Call<List<Event>> call = webServiceAPI.getUserEvents(parameters);

        //Async call
        call.enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                if(!response.isSuccessful()){
                    Log.i(logMessage, "getAllUserEvents(); Response code:" + response.code());
                    return;
                }
                updateAdapter(response.body());
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t){
                Log.i(logMessage, "getAllUserEvents(); Fialure message: " + t.getMessage());
            }
        });
    }


    //Arguments required: <uid>, <event_id>
    private void deleteEvent(String uid, Integer event_id){
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
            }
        });
    }


}