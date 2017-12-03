package org.launchpadcs.flokk;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import org.launchpadcs.flokk.Api.FlokkApiHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditEventActivity extends AppCompatActivity {

    private Event event;
    private EditText title;
    private EditText description;
    private TextView date;
    private TextView time;
    private PlaceAutocompleteFragment location;
    private Button post;
    private String locationSelected;
    private double latitude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        event = new Gson().fromJson(getIntent().getStringExtra("jsonObject"), Event.class);
        title = (EditText) findViewById(R.id.title);
        description = (EditText) findViewById(R.id.description);
        date = (TextView) findViewById(R.id.date);
        time = (TextView) findViewById(R.id.time);

        date.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(EditEventActivity.this);
                final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy");
                datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        date.setText(sdf.format(new Date(year, month, dayOfMonth)));
                    }
                });
                datePickerDialog.show();
            }
        });

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Date currentTime = new Date();
                final SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
                currentTime.setSeconds(0);
                TimePickerDialog timePickerDialog = new TimePickerDialog(EditEventActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        currentTime.setHours(hourOfDay);
                        currentTime.setMinutes(minute);
                        time.setText(sdf.format(currentTime));
                    }
                },currentTime.getHours(), currentTime.getMinutes(), false);
                timePickerDialog.show();
            }
        });

        locationSelected = event.getLocation();
        location = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.location);

        location.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                locationSelected = place.getName().toString();
                LatLng placeLatLng = place.getLatLng();
                latitude = placeLatLng.latitude;
                longitude = placeLatLng.longitude;
            }

            @Override
            public void onError(Status status) {
                locationSelected = "";
            }
        });

        post = (Button) findViewById(R.id.postButton);
        title.setText(event.getTitle());
        description.setText(event.getDescription());
        date.setText(event.getDate());
        time.setText(event.getTime());
        location.setText(event.getLocation());

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(title.getText().toString().equals("") || description.getText().toString().equals("") || date.getText().toString().equals("") || locationSelected.equals("") || time.getText().toString().equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(EditEventActivity.this);
                    builder.setCancelable(false);
                    builder.setTitle("Error");
                    builder.setMessage("You must fill out all fields.");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.create().show();
                }
                else {
                    event.setTitle(title.getText().toString());
                    event.setDescription(description.getText().toString());
                    event.setDate(date.getText().toString());
                    event.setTime(time.getText().toString());
                    event.setLocation(locationSelected);
                    event.setLatitude(latitude);
                    event.setLongitude(longitude);
                    FlokkApiHelper.getInstance(getApplicationContext()).editEvent(event).enqueue(new Callback<Message>() {
                        @Override
                        public void onResponse(Call<Message> call, Response<Message> response) {
                            if (response.code() != 200) {
                                Toast.makeText(getApplicationContext(), "Something went kinda wrong", Toast.LENGTH_SHORT);
                                return;
                            }
                            Toast.makeText(getApplicationContext(), "Something went right", Toast.LENGTH_SHORT);
                            Intent intent = new Intent(EditEventActivity.this, MyEventsActivity.class);
                            startActivity(intent);
                        }

                        @Override
                        public void onFailure(Call<Message> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT);
                        }
                    });
                }

            }
        });


    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(EditEventActivity.this, HomeActivity.class);
        startActivity(intent);
    }
}
