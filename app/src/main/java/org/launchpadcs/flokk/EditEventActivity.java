package org.launchpadcs.flokk;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;

public class EditEventActivity extends AppCompatActivity {

    private Event event;
    private EditText title;
    private EditText description;
    private EditText date;
    private EditText location;
    private Button post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        event = new Gson().fromJson(getIntent().getStringExtra("jsonObject"), Event.class);
        title = (EditText) findViewById(R.id.title);
        description = (EditText) findViewById(R.id.description);
        date = (EditText) findViewById(R.id.date);
        location = (EditText) findViewById(R.id.location);
        post = (Button) findViewById(R.id.postButton);
        title.setText(event.getTitle());
        description.setText(event.getDescription());
        date.setText(event.getDate());
        location.setText(event.getLocation());
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                event.setTitle(title.getText().toString());
                event.setDescription(description.getText().toString());
                event.setDate(date.getText().toString());
                event.setLocation(location.getText().toString());
                String json = new Gson().toJson(event);
                Intent ourIntent = getIntent();
                ourIntent.putExtra("jsonObject", json);
                setResult(RESULT_OK, ourIntent);
                finish();

            }
        });


    }
}
