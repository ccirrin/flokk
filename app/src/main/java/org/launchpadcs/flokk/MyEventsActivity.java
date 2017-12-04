package org.launchpadcs.flokk;

import android.content.Intent;
import android.drm.DrmStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;

import org.launchpadcs.flokk.Api.FlokkApiHelper;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyEventsActivity extends Fragment {
    private List<Event> eventsList = new ArrayList<>();
    private RecyclerView recyclerView;
    public MyEventsAdapter mAdapter;
    private View myView;

    public static final int REQUEST_CREATE_EVENT = 1000;
    public static final int REQUEST_EDIT_EVENT = 2000;

    public MyEventsActivity() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.activity_main, container, false);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        myView = view;
        mAdapter = new MyEventsAdapter(eventsList, getActivity());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);

        prepareEventData();
        return view;
    }

    private void prepareEventData() {
        FlokkApiHelper.getInstance(getActivity()).getUserEvents(new Email(HomeActivity.email)).enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                if(response.code() != 200) {
                    Toast.makeText(getActivity(), response.toString(), Toast.LENGTH_LONG).show();
                    return;
                }
                recyclerView = (RecyclerView) myView.findViewById(R.id.recycler_view);

                mAdapter = new MyEventsAdapter(response.body(), getActivity());
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
                recyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getActivity(), t.getStackTrace().toString(), Toast.LENGTH_LONG).show();
            }
        });
        mAdapter.notifyDataSetChanged();
    }
}
