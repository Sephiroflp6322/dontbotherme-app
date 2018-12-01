package kelvin.link.dontborderme_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SendEventActivity extends AppCompatActivity {

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


    }

}
