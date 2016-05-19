package com.omegadevs.todo;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import org.w3c.dom.Text;

import java.util.Calendar;

public class NewTodoActivity extends AppCompatActivity {

    EditText taskName;
    Button findMyLocation,datePicker,timePicker;
    int placePickerRequest = 1;
    TextView addressTxt,dateTxt,timeTxt;
    String address,date,time;
    int month_x,year_x,day_x;
    int hour_x,minute_x;
    static final int DATE_DIALOG_ID = 0;
    static final int TIME_DIALOG_ID = 1;
    SQLiteDatabase db;
    String table_Name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_todo);
        taskName = (EditText) findViewById(R.id.input_task);
        findMyLocation = (Button) findViewById(R.id.btn_location);
        addressTxt = (TextView) findViewById(R.id.txt_location);
        datePicker = (Button) findViewById(R.id.btn_datepicker);
        dateTxt = (TextView) findViewById(R.id.txt_datepicker);
        timePicker = (Button) findViewById(R.id.btn_timepicker);
        timeTxt = (TextView) findViewById(R.id.txt_timepicker);

        db = openOrCreateDatabase("tasks",MODE_PRIVATE,null);

        Bundle bundle = getIntent().getExtras();
        table_Name = bundle.getString("tablename");

        findMyLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                Intent intent;

                try {
                    intent = builder.build(NewTodoActivity.this);
                    startActivityForResult(intent,placePickerRequest);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }


            }
        });

        showDialogOnButtonClick();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.donefab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String finaltaskname = taskName.getText().toString().trim();

                if (finaltaskname.isEmpty() || date.isEmpty() || time.isEmpty() || address.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Incomplete Information provided !", Toast.LENGTH_SHORT).show();
                }
                else
                {

                    //db.execSQL("insert into '"+tableName+"' values('"+finaltaskname+"','"+address+"','"+date+"','"+time+"');");
                    Toast.makeText(getApplicationContext(), "data added to the table :" + table_Name , Toast.LENGTH_SHORT).show();

                    Intent addnew = new Intent(NewTodoActivity.this,MenuActivity.class);
                    addnew.putExtra("tablename",table_Name);
                    startActivity(addnew);
                    finish();

                }


            }
        });

        }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == placePickerRequest) {

            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                address = String.format("%s", place.getAddress());
                addressTxt.setText(address);

            }

        }
    }

    public void showDialogOnButtonClick(){

        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);
            }
        });

        timePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(TIME_DIALOG_ID);
            }
        });
    }
    @Override
    protected Dialog onCreateDialog(int id){
        if(id==DATE_DIALOG_ID)
        {
            Calendar c = Calendar.getInstance();
            year_x = c.get(Calendar.YEAR);
            month_x = c.get(Calendar.MONTH);
            day_x = c.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(this,dPickerListener,year_x,month_x,day_x);

        }
        else if(id==TIME_DIALOG_ID){
            return new TimePickerDialog(NewTodoActivity.this,tPickerListener,hour_x,minute_x,false);
        }
        else
            return null;
    }

    protected DatePickerDialog.OnDateSetListener dPickerListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

            year_x=year;
            month_x=monthOfYear;
            day_x=dayOfMonth;
            date = dayOfMonth + "/" + monthOfYear + "/" + year;
            dateTxt.setText(date);

        }
    };

    protected TimePickerDialog.OnTimeSetListener tPickerListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            hour_x=hourOfDay;
            minute_x=minute;
            time=hour_x + ":" + minute_x;
            timeTxt.setText(time);
        }
    };


    }
