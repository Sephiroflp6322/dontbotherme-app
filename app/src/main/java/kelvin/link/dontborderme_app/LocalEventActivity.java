package kelvin.link.dontborderme_app;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import android.widget.Toast;
import android.widget.Toolbar;

import java.util.List;

public class LocalEventActivity extends AppCompatActivity{
    public static final int ADD_EVENT_REQUEST = 1;
    public static final int EDIT_EVENT_REQUEST = 2;
    private EventRoomViewModel eventRoomViewModel;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    //Intent sender_intent = new Intent(SendEventActivity.this, LocalEventActivity.class);
                    //startActivity(sender_intent);
                    return true;
                case R.id.navigation_sender:
                    Intent sender_intent = new Intent(LocalEventActivity.this, SendEventActivity.class);
                    startActivity(sender_intent);
                    return true;
                case R.id.navigation_subscriber:
                    Intent subscriber_intent = new Intent(LocalEventActivity.this, SubscribeEventActivity.class);
                    startActivity(subscriber_intent);
                    return true;
                case R.id.navigation_me:
                    Intent settings_intent = new Intent(LocalEventActivity.this, MeActivity.class);
                    startActivity(settings_intent);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local);


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.local_activity_navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_home);


        //Configurating Recyclerview
        RecyclerView recyclerView = findViewById(R.id.local_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final EventRoomAdapter adapter = new EventRoomAdapter();
        recyclerView.setAdapter(adapter);

        eventRoomViewModel = ViewModelProviders.of(this).get(EventRoomViewModel.class);
        eventRoomViewModel.getAllEvents().observe(this, new Observer<List<EventRoom>>() {
            @Override
            public void onChanged(@Nullable List<EventRoom> eventRooms) {
                adapter.setEvents(eventRooms);
            }
        });


        //For insert
        FloatingActionButton buttonAddNote = findViewById(R.id.button_add_note);
        buttonAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LocalEventActivity.this, LocalAddEditEventActivity.class);
                startActivityForResult(intent, ADD_EVENT_REQUEST);
            }
        });

        //For delete
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                //viewHolder.getAdapterPosition()
                EventRoom swipedEvent = adapter.getEventAt(viewHolder.getAdapterPosition());
                //String uid = swipedEvent.getUid();
                Integer event_id = swipedEvent.getEvent_id();
                eventRoomViewModel.delete(event_id);
                Toast.makeText(LocalEventActivity.this, "Event Deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);


        //For Update
        adapter.setOnItemClickListener(new EventRoomAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(EventRoom event) {
                Intent intent = new Intent(LocalEventActivity.this, LocalAddEditEventActivity.class);
                intent.putExtra(LocalAddEditEventActivity.EXTRA_EVENT_ID, event.getEvent_id());
                intent.putExtra(LocalAddEditEventActivity.EXTRA_EVENT_TITLE, event.getEvent_title());
                intent.putExtra(LocalAddEditEventActivity.EXTRA_ADDRESS, event.getAddress());
                intent.putExtra(LocalAddEditEventActivity.EXTRA_DESCRIPTION, event.getDescription());
                intent.putExtra(LocalAddEditEventActivity.EXTRA_START_TS, event.getStart_ts());

                startActivityForResult(intent, EDIT_EVENT_REQUEST);
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        UserManager userManager = UserManager.getInstance();
        //Insert a new event or update an existing event
        if(requestCode == ADD_EVENT_REQUEST && resultCode == RESULT_OK){

            String event_title = data.getStringExtra(LocalAddEditEventActivity.EXTRA_EVENT_TITLE);
            String address = data.getStringExtra(LocalAddEditEventActivity.EXTRA_ADDRESS);
            String description = data.getStringExtra(LocalAddEditEventActivity.EXTRA_DESCRIPTION);
            String start_ts = data.getStringExtra(LocalAddEditEventActivity.EXTRA_START_TS);

            //TODO event_id should be compatible with server
            //String uid = userManager.getUser().getUid();
            EventRoom event = new EventRoom(null , event_title, address,description, "s",start_ts);
            eventRoomViewModel.insert(event);

            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();

        }else if(requestCode == EDIT_EVENT_REQUEST && resultCode == RESULT_OK){
            Integer event_id = data.getIntExtra(LocalAddEditEventActivity.EXTRA_EVENT_ID, -1);
            if(event_id == -1){
                Toast.makeText(this, "Fail to Save", Toast.LENGTH_SHORT).show();
                return;
            }

            String event_title = data.getStringExtra(LocalAddEditEventActivity.EXTRA_EVENT_TITLE);
            String address = data.getStringExtra(LocalAddEditEventActivity.EXTRA_ADDRESS);
            String description = data.getStringExtra(LocalAddEditEventActivity.EXTRA_DESCRIPTION);
            String start_ts = data.getStringExtra(LocalAddEditEventActivity.EXTRA_START_TS);

            //String uid = userManager.getUser().getUid();
            EventRoom eventRoom = new EventRoom(event_id ,event_title, address, description,"s",start_ts);
            eventRoom.setEvent_id(event_id);
            eventRoomViewModel.update(eventRoom);

            Toast.makeText(this, "Updated", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Fail to Update", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.local_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.local_delete_all_events:
                eventRoomViewModel.deleteAllEvents();
                Toast.makeText(this, "All Events Deleted", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



}
