package org.launchpadcs.flokk.Api;

import android.content.Context;

import org.launchpadcs.flokk.R;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ccirr on 10/19/2017.
 */

public class FlokkApiHelper {
    private static FlokkApi flokkApi;

    public static FlokkApi getInstance(Context context) {
        if(flokkApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://gentle-journey-72147.herokuapp.com").addConverterFactory(GsonConverterFactory.create())
                    .build();
            flokkApi = retrofit.create(FlokkApi.class);

        }
        return flokkApi;
    }
}
