package org.launchpadcs.flokk;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;

import org.launchpadcs.flokk.Api.FlokkApiHelper;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends Fragment {
    private RecyclerView eventsRecycler;
    public static String email;

    public HomeActivity() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.activity_home, container, false);
        eventsRecycler = (RecyclerView) view.findViewById(R.id.eventsRecycler);
        email = LoginActivity.email;
        System.out.println(email);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        FlokkApiHelper.getInstance(getActivity()).getAllEvents().enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                if(response.code() != 200) {
                    Toast.makeText(getActivity(), response.toString(), Toast.LENGTH_LONG).show();
                    return;
                }
                GlobalEventsAdapter allEvents = new GlobalEventsAdapter(response.body(), getActivity());
                eventsRecycler.setAdapter(allEvents);
                eventsRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getActivity(), t.getStackTrace().toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

}
