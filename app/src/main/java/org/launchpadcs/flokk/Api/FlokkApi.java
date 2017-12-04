package org.launchpadcs.flokk.Api;

import org.launchpadcs.flokk.CarloInteger;
import org.launchpadcs.flokk.Email;
import org.launchpadcs.flokk.Event;
import org.launchpadcs.flokk.Message;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by ccirr on 10/19/2017.
 */

public interface FlokkApi {

    @GET("/events")
    Call<List<Event>> getAllEvents();

    @POST("/events/create")
    Call<Event> postEvent(@Body Event event);

    @POST("/events/delete")
    Call<Message> deleteEvent(@Body CarloInteger carloInteger);

    @POST("/events/update")
    Call<Message> editEvent(@Body Event event);

    @POST("/events/user")
    Call<List<Event>> getUserEvents(@Body Email email);

}
