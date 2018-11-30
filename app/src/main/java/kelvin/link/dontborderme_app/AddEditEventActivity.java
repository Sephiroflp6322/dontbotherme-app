package kelvin.link.dontborderme_app;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class AddEditEventActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{
    public static final String EXTRA_EVENT_ID= "EXTRA_EVENT_ID";
    public static final String EXTRA_EVENT_TITLE= "EXTRA_EVENT_TITLE";
    public static final String EXTRA_ADDRESS = "EXTRA_ADDRESS";
    public static final String EXTRA_DESCRIPTION = "EXTRA_DESCRIPTION";
    public static final String EXTRA_START_TS= "EXTRA_START_TS";



    private EditText editTextTitle;
    private EditText editTextAddress;
    private EditText editTextDescription;
    private String start_ts;

    private TextView textViewDateTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);

        editTextTitle = findViewById(R.id.edit_text_title);
        editTextAddress = findViewById(R.id.edit_text_address);
        editTextDescription = findViewById(R.id.edit_text_description);
        textViewDateTime = (TextView)findViewById(R.id.textView_DateTime);


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


        //Date picker
        Button DatePicker_Btn = (Button)findViewById(R.id.btn_date_picker);
        DatePicker_Btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                DatePickerFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "" + "Date picker");
            }
        });

        //Time Picker
        Button TimePicker_Btn = (Button)findViewById(R.id.btn_time_picker);
        TimePicker_Btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                TimePickerFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "Time picker");
            }
        });


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
        data.putExtra(EXTRA_START_TS, start_ts);

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


    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        Calendar calendar;
        calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, i);
        calendar.set(Calendar.MONTH, i1);
        calendar.set(Calendar.DAY_OF_MONTH, i2);
        //TODO Need to transform to string timestamp format
        start_ts = "2018-11-30 00:00:00";
        textViewDateTime.setText(start_ts);
        //start_ts = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        start_ts = "2018-11-30 23:03:00";
        textViewDateTime.setText(start_ts);
        //TextView textView = (TextView)findViewById(R.id.textView);
        //textView.setText("Hour: " + i + " Minute: " + i1);
    }
}
