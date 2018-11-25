package kelvin.link.dontborderme_app;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.service.autofill.FillEventHistory;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    //for scan QR code
    Button scan_btn;
    public static TextView resultTextView;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
             = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
            android.support.v4.app.FragmentTransaction transaction = fragmentManager.beginTransaction();

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText("Receiver");
                    return true;
                case R.id.navigation_sender:
                    mTextMessage.setText("Sender");
                    return true;
                case R.id.navigation_history:
                    mTextMessage.setText("History");
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);




        //================Weibin Start================
        scan_btn = (Button) findViewById(R.id.btn_scan);
        resultTextView = (TextView)findViewById(R.id.result_text);
        scan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),ScanCodeActivity.class));
            }
        });
        //================Weibin End================


        //================Kelvin Start================

        //================Kelvin End================
    }


    //================Weibin Start================

    //================Weibin End================


    //================Kelvin Start================

    //================Kelvin End================

}
