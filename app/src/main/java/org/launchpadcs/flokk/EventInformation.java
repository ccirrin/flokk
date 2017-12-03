package org.launchpadcs.flokk;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;

import org.w3c.dom.Text;

public class EventInformation extends AppCompatActivity {

    private Event event;
    private TextView title;
    private TextView description;
    private TextView date;
    private TextView time;
    private TextView location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_information);
        event = new Gson().fromJson(getIntent().getStringExtra("jsonObject"), Event.class);
        title = (TextView) findViewById(R.id.title);
        description = (TextView) findViewById(R.id.description);
        date = (TextView) findViewById(R.id.date);
        time = (TextView) findViewById(R.id.time);
        location = (TextView) findViewById(R.id.location);
        title.setText(event.getTitle());
        description.setText(event.getDescription());
        date.setText(event.getDate());
        time.setText(event.getTime());
        location.setText(event.getLocation());
    }
}
