package org.launchpadcs.flokk;

/**
 * Created by ccirr on 10/11/2017.
 */

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

import org.launchpadcs.flokk.Api.FlokkApi;
import org.launchpadcs.flokk.Api.FlokkApiHelper;
import org.w3c.dom.Text;

import java .util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyEventsAdapter extends RecyclerView.Adapter<MyEventsAdapter.MyViewHolder> {

    private List<Event> eventsList;
    private MyEventsActivity con;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, description, date, time, location;
        public CardView cardView;
        public Button editButton, deleteButton;
        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            description = (TextView) view.findViewById(R.id.description);
            date = (TextView) view.findViewById(R.id.date);
            time = (TextView) view.findViewById(R.id.time);
            location = (TextView) view.findViewById(R.id.location);
            cardView = (CardView) view.findViewById(R.id.cv);
            editButton = (Button) view.findViewById(R.id.editButton);
            deleteButton = (Button) view.findViewById(R.id.deleteButton);
        }
    }

    public MyEventsAdapter(List<Event> eventsList, MyEventsActivity con) {
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
        holder.time.setText(event.getTime());
        holder.location.setText(event.getLocation());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(con, EventInformation.class);
                String json = new Gson().toJson(event);
                intent.putExtra("jsonObject", json);
                con.startActivity(intent);

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
                        FlokkApiHelper.getInstance(con).deleteEvent(new CarloInteger(event._id)).enqueue(new Callback<Message>() {
                            @Override
                            public void onResponse(Call<Message> call, Response<Message> response) {
                                if(response.code() != 200) {
                                    Toast.makeText(con, "Something went kinda wrong", Toast.LENGTH_SHORT);
                                    return;
                                }
                                Toast.makeText(con, "Something went right", Toast.LENGTH_SHORT);
                            }

                            @Override
                            public void onFailure(Call<Message> call, Throwable t) {
                                Toast.makeText(con, "Something went wrong", Toast.LENGTH_SHORT);

                            }
                        });
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
                final Intent intent = new Intent(con, EditEventActivity.class);
                intent.putExtra("jsonObject", new Gson().toJson(event));
                con.startActivity(intent);
            }
        });
    }

    public int getItemCount() {
        return eventsList.size();
    }


}
