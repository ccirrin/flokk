package org.launchpadcs.flokk;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Event> eventsList = new ArrayList<>();
    private RecyclerView recyclerView;
    private EventsAdapter mAdapter;
    private FloatingActionButton createButton;

    public static final int CREATE_EVENT = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mAdapter = new EventsAdapter(eventsList, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);

        prepareEventData();

        createButton = (FloatingActionButton) findViewById(R.id.fab);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent e = new Intent(MainActivity.this, CreateEventActivity.class);
                startActivityForResult(e, CREATE_EVENT);
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == CREATE_EVENT && resultCode == RESULT_OK) {
            String json = data.getStringExtra("jsonObject");
            Event event = new Gson().fromJson(json, Event.class);
            eventsList.add(event);
            mAdapter.notifyDataSetChanged();

        }
    }

    private void prepareEventData() {
        eventsList.add(new Event("Birthday Party", "Celebrate with cake", "10/7/2017", "The house"));
        eventsList.add(new Event("Launchpad Meeting", "Work on android app", "10/6/2017", "Lawson"));
        eventsList.add(new Event("Club callout", "Try it out", "10/9/2017", "Engineering Fountain"));


        mAdapter.notifyDataSetChanged();
    }
}
