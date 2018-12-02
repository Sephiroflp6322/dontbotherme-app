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

import java.sql.Timestamp;
import java.util.Calendar;

public class LocalAddEditEventActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{
    public static final String EXTRA_EVENT_ID= "EXTRA_EVENT_ID";
    public static final String EXTRA_EVENT_TITLE= "EXTRA_EVENT_TITLE";
    public static final String EXTRA_ADDRESS = "EXTRA_ADDRESS";
    public static final String EXTRA_DESCRIPTION = "EXTRA_DESCRIPTION";
    public static final String EXTRA_START_TS= "EXTRA_START_TS";



    private EditText editTextTitle;
    private EditText editTextAddress;
    private EditText editTextDescription;
    private String start_ts;

    private Calendar calendar;
    private TextView textViewDateTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_add_edit);

        editTextTitle = findViewById(R.id.edit_text_title);
        editTextAddress = findViewById(R.id.edit_text_address);
        editTextDescription = findViewById(R.id.edit_text_description);
        textViewDateTime = (TextView)findViewById(R.id.textView_DateTime);

        calendar = Calendar.getInstance();

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        //Check whether it's add or update event
        Intent intent = getIntent();
        if(intent.hasExtra(String.valueOf(EXTRA_EVENT_ID))){
            setTitle("Edit Event");
            editTextTitle.setText(intent.getStringExtra(EXTRA_EVENT_TITLE));
            editTextAddress.setText(intent.getStringExtra(EXTRA_ADDRESS));
            editTextDescription.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
            textViewDateTime.setText(intent.getStringExtra(EXTRA_START_TS));
        }else{
            setTitle("Add Event");
        }


        //Date picker
        Button DatePicker_Btn = (Button)findViewById(R.id.local_date_picker);
        DatePicker_Btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                DatePickerFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "" + "Date picker");
            }
        });

        //Time Picker
        Button TimePicker_Btn = (Button)findViewById(R.id.local_time_picker);
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
        //TODO [low priority] Need to refactor timestamp generation
        calendar.set(Calendar.YEAR, i-1900);
        calendar.set(Calendar.MONTH, i1);
        calendar.set(Calendar.DAY_OF_MONTH, i2);
        start_ts = String.valueOf(i) + "-" + String.valueOf(i1+1)+ "-" + String.valueOf(i2);
        textViewDateTime.setText(start_ts);
        //start_ts = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        //TODO [low priority] Need to refactor timestamp generation
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        start_ts = new Timestamp(year, month, day, i, i1, 0, 0).toString().substring(0, 19);
        textViewDateTime.setText(start_ts);
    }
}
