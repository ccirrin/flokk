package org.launchpadcs.flokk;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;

public class CreateEventActivity extends AppCompatActivity {

    EditText title, description, date, location;
    Button post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        final Intent myIntent = getIntent();

        title = (EditText)findViewById(R.id.title);
        description = (EditText)findViewById(R.id.description);
        date = (EditText)findViewById(R.id.date);
        location = (EditText)findViewById(R.id.location);

        post = (Button)findViewById(R.id.postButton);
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titleStr = title.getText().toString();
                String descStr = description.getText().toString();
                String dateStr = date.getText().toString();
                String locStr = location.getText().toString();
                Event event = new Event(titleStr, descStr, dateStr, locStr);
                String json = createJSON(event);
                myIntent.putExtra("jsonObject", json);
                setResult(RESULT_OK, myIntent);
                finish();
            }
        });
    }

    public String createJSON(Event event) {
        Gson gson = new Gson();
        return gson.toJson(event);
    }
}
