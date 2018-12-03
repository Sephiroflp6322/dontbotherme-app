package kelvin.link.dontborderme_app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MeActivity extends AppCompatActivity implements LoginDialog.LoginDialogListener{


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent main_intent = new Intent(MeActivity.this, LocalEventActivity.class);
                    startActivity(main_intent);
                    return true;
                case R.id.navigation_sender:
                    Intent sender_intent = new Intent(MeActivity.this, SendEventActivity.class);
                    startActivity(sender_intent);
                    return true;
                case R.id.navigation_subscriber:
                    Intent subscribe_intent = new Intent(MeActivity.this, SubscribeEventActivity.class);
                    startActivity(subscribe_intent);
                    return true;
                case R.id.navigation_me:
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.me_activity_navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_me);


        // Setup the data source
        ArrayList<Item> itemsArrayList = generateItemsList(); // calls function to get items list

        // instantiate the custom list adapter
        CustomListAdapter adapter = new CustomListAdapter(this, itemsArrayList);

        // get the ListView and attach the adapter
        final ListView itemsListView  = (ListView) findViewById(R.id.me_list_view);
        itemsListView.setAdapter(adapter);
        itemsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                TextView textView = (TextView) view.findViewById(R.id.me_title);
                String str = textView.getText().toString();
                if(str == "User"){
                    //TODO start activity login
                    LoginDialog loginDialog = new LoginDialog();
                    loginDialog.show(getSupportFragmentManager(), "Login dialog");
                }else if(str == "Settings"){
                    //Start activity settings
                    Intent subscribe_intent = new Intent(MeActivity.this, SettingsActivity.class);
                    startActivity(subscribe_intent);
                }else{
                    //Do nothing
                }
            }
        });


    }



    @Override
    public void onResult(String username, String password) {
        UserManager.getInstance().setUser(new User(username, password));
        Toast.makeText(this, "Login Success " + username, Toast.LENGTH_LONG).show();
    }

    public class Item{
        public int icon;
        public String title;
        Item(int icon, String title){
            this.icon = icon;
            this.title = title;
        }

    }

    public ArrayList<Item> generateItemsList(){
        ArrayList<Item> items = new ArrayList<Item>();
        items.add(new Item(R.drawable.ic_user, "User"));
        items.add(new Item(R.drawable.ic_settings, "Settings"));
        return items;
    }




    public class CustomListAdapter extends BaseAdapter {
        private Context context; //context
        private ArrayList<Item> items; //data source of the list adapter

        //public constructor
        public CustomListAdapter(Context context, ArrayList<Item> items) {
            this.context = context;
            this.items = items;
        }

        @Override
        public int getCount() {
            return items.size(); //returns total of items in the list
        }

        @Override
        public Object getItem(int position) {
            return items.get(position); //returns list item at the specified position
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // inflate the layout for each list row
            if (convertView == null) {
                convertView = LayoutInflater.from(context).
                        inflate(R.layout.me_event_item, parent, false);
            }

            // get current item to be displayed
            Item currentItem = (Item) getItem(position);

            // get the TextView for item name and item description
            ImageView imageView = (ImageView)
                    convertView.findViewById(R.id.me_item_icon);
            TextView textView = (TextView)
                    convertView.findViewById(R.id.me_title);

            //sets the text for item name and item description from the current item object
            imageView.setImageResource(currentItem.icon);
            textView.setText(currentItem.title);

            // returns the view for the current row
            return convertView;
        }
    }

}
