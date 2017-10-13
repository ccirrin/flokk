package org.launchpadcs.flokk;

/**
 * Created by ccirr on 10/11/2017.
 */

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java .util.List;

import static android.app.Activity.RESULT_OK;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.MyViewHolder> {

    private List<Event> eventsList;
    private MainActivity con;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, description, date, location;
        public CardView cardView;
        public Button editButton, deleteButton;
        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            description = (TextView) view.findViewById(R.id.description);
            date = (TextView) view.findViewById(R.id.date);
            location = (TextView) view.findViewById(R.id.location);
            cardView = (CardView) view.findViewById(R.id.cv);
            editButton = (Button) view.findViewById(R.id.editButton);
            deleteButton = (Button) view.findViewById(R.id.deleteButton);
        }
    }

    public EventsAdapter(List<Event> eventsList, MainActivity con) {
        this.eventsList = eventsList;
        this.con = con;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Event event = eventsList.get(position);
        holder.title.setText(event.getTitle());
        holder.description.setText(event.getDescription());
        holder.date.setText(event.getDate());
        holder.location.setText(event.getLocation());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(con, "Event was clicked on", Toast.LENGTH_SHORT).show();
            }
        });
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(con);
                builder.setCancelable(false);
                builder.setTitle("Delete");
                builder.setMessage("Are you sure you want to delete " + event.getTitle());
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        eventsList.remove(position);
                        notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
            }
        });
        holder.editButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(con, EditEventActivity.class);
                String json = new Gson().toJson(event);
                intent.putExtra("jsonObject", json);
                intent.putExtra("position", position);
                con.startActivityForResult(intent, MainActivity.REQUEST_EDIT_EVENT);
            }
        });
    }

    public int getItemCount() {
        return eventsList.size();
    }


}
