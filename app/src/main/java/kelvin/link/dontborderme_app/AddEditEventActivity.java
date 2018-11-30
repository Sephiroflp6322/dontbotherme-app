package kelvin.link.dontborderme_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class AddEditEventActivity extends AppCompatActivity {
    public static final String EXTRA_EVENT_ID= "EXTRA_EVENT_ID";
    public static final String EXTRA_EVENT_TITLE= "EXTRA_EVENT_TITLE";
    public static final String EXTRA_ADDRESS = "EXTRA_ADDRESS";
    public static final String EXTRA_DESCRIPTION = "EXTRA_DESCRIPTION";
    public static final String EXTRA_START_TS= "EXTRA_START_TS";



    private EditText editTextTitle;
    private EditText editTextAddress;
    private EditText editTextDescription;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        editTextTitle = findViewById(R.id.edit_text_title);
        editTextAddress = findViewById(R.id.edit_text_address);
        editTextDescription = findViewById(R.id.edit_text_description);


        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        //Check whether it's add or update event
        Intent intent = getIntent();
        if(intent.hasExtra(String.valueOf(EXTRA_EVENT_ID))){
            setTitle("Edit Event");
            editTextTitle.setText(intent.getStringExtra(EXTRA_EVENT_TITLE));
            editTextAddress.setText(intent.getStringExtra(EXTRA_ADDRESS));
            editTextDescription.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
        }else{
            setTitle("Add Event");
        }
    }

    private void saveEvent(){
        String title = editTextTitle.getText().toString();
        String address = editTextAddress.getText().toString();
        String description = editTextDescription.getText().toString();

        if(title.trim().isEmpty()){
            Toast.makeText(this, "Please insert a title", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_EVENT_TITLE, title);
        data.putExtra(EXTRA_ADDRESS, address);
        data.putExtra(EXTRA_DESCRIPTION, description);

        //Check whether EXTRA_EVENT_ID exist in Extra. If not, this is a new event.(event_id = -1)
        //Otherwise, this is an event that's already exist.
        int event_id = getIntent().getIntExtra(EXTRA_EVENT_ID, -1);
        if(event_id != -1){
            data.putExtra(String.valueOf(EXTRA_EVENT_ID), event_id);
        }

        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_event_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.save_event:
                saveEvent();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
