package kelvin.link.dontborderme_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SendEventActivity extends AppCompatActivity {
    private String logMessage = "ScanCodeActivity";
    private DontBorderMeWebServiceAPI webServiceAPI;
    private User user;


    private ArrayList<EventItem> eventItemArrayList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private EventItemAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent main_intent = new Intent(SendEventActivity.this, LocalEventActivity.class);
                    startActivity(main_intent);
                    return true;
                case R.id.navigation_sender:
                    //Intent main_intent = new Intent(SendEventActivity.this, SendEventActivity.class);
                    //startActivity(main_intent);
                    return true;
                case R.id.navigation_subscriber:
                    Intent subscriber_intent = new Intent(SendEventActivity.this, SubscribeEventActivity.class);
                    startActivity(subscriber_intent);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_event);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.send_activity_navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_sender);


        //Setting up user info
        user = UserManager.getInstance().getUser();

        //Setting up retrofit connection
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://59.149.35.12/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        webServiceAPI = retrofit.create(DontBorderMeWebServiceAPI.class);


        mRecyclerView = findViewById(R.id.send_recycler_view);
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
                //deleteEvent(user.getUid(), event_id);
                //removeItem(position);
            }
        });

    }


    private void updateAdapter(List<Event> events){
        eventItemArrayList.clear();
        for(Event e: events){
            eventItemArrayList.add(new EventItem(R.drawable.ic_remind, e));
            Log.i(logMessage, "Description:"+ e.getDescription());
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        getSendEvents(user.getUid());
    }

    public void removeItem(int position){
        eventItemArrayList.remove(position);
        mAdapter.notifyItemRemoved(position);
    }



    //Arguments required: <uid>
    private void getSendEvents(String uid){
        Map<String, String> parameters = new HashMap<>();
        parameters.put("uid", uid);
        Call<List<Event>> call = webServiceAPI.getSendEvents(parameters);

        //Async call
        call.enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                if(!response.isSuccessful()){
                    Log.i(logMessage, "getSendEvents(); Response code:" + response.code());
                    return;
                }
                updateAdapter(response.body());
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t){
                Log.i(logMessage, "getSendEvents(); Fialure message: " + t.getMessage());
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.send_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.sender_add_event:
                startActivity(new Intent(getApplicationContext(), ScanCodeActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
