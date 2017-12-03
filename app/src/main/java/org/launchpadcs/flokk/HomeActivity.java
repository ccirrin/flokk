package org.launchpadcs.flokk;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;

import org.launchpadcs.flokk.Api.FlokkApiHelper;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {
    private Button myEventsButton;
    private FloatingActionButton createButton;
    private RecyclerView eventsRecycler;
    public static String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        myEventsButton = (Button) findViewById(R.id.myEventsButton);
        createButton = (FloatingActionButton) findViewById(R.id.createButton);
        eventsRecycler = (RecyclerView) findViewById(R.id.eventsRecycler);
        email = LoginActivity.email;
        System.out.println(email);
        myEventsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, MyEventsActivity.class);

                startActivity(intent);
            }
        });
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, CreateEventActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        FlokkApiHelper.getInstance(this).getAllEvents().enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                if(response.code() != 200) {
                    Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();
                    return;
                }
                GlobalEventsAdapter allEvents = new GlobalEventsAdapter(response.body(), HomeActivity.this);
                eventsRecycler.setAdapter(allEvents);
                eventsRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getApplicationContext(), t.getStackTrace().toString(), Toast.LENGTH_LONG).show();
            }
        });
    }


    @Override
    public void onBackPressed() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }
}
