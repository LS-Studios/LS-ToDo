package de.stubbe.lstodo.server.retrofit;

import android.os.Build;

import androidx.annotation.RequiresApi;

import de.stubbe.lstodo.BuildConfig;

public class ApiUtils {

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static TodoServerService getTodoServerService() {
        return APIClient.getClient(BuildConfig.Base_URL).create(TodoServerService.class);
    }

}