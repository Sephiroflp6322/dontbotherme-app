package kelvin.link.dontborderme_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class MeActivity extends AppCompatActivity {


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


    }

}
