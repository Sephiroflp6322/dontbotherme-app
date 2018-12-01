package kelvin.link.dontborderme_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class SubscribeEventActivity extends AppCompatActivity {
    private ArrayList<EventItem> eventItemArrayList;
    private RecyclerView mRecyclerView;
    private EventItemAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


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

        createExampleList();
        buildRecyclerView();


    }



    public void removeItem(int position){
        eventItemArrayList.remove(position);
        mAdapter.notifyItemRemoved(position);
    }

    /*public void changeItem(int position, String text){
        eventItemArrayList.get(position).setEvent(text);
        mAdapter.notifyItemChanged(position);
    }*/

    public void createExampleList(){
        eventItemArrayList = new ArrayList<>();
    }


    public void buildRecyclerView(){
        mRecyclerView = findViewById(R.id.subscribe_recyclerview);
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
                removeItem(position);
            }
        });
    }

}
