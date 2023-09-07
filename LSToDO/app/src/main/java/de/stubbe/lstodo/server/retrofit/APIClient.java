package de.stubbe.lstodo.server.retrofit;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.time.LocalDate;

import de.stubbe.lstodo.data.gson.LocalDateToStringAdapter;
import de.stubbe.lstodo.data.gson.StringToLocalDateAdapter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

class APIClient {

    private static Retrofit retrofit = null;

    @RequiresApi(api = Build.VERSION_CODES.O)
    static Retrofit getClient(String baseUrl) {
        if (retrofit == null) {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDate.class, new LocalDateToStringAdapter())
                    .registerTypeAdapter(LocalDate.class, new StringToLocalDateAdapter())
                    .create();

            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }

        return retrofit;
    }

}