package com.example.peoplemon;

import android.app.Application;

import com.example.peoplemon.Stages.GameMainStage;

import flow.Flow;
import flow.History;

/**
 * Created by JoshuaMabry on 11/5/16.
 */

public class PeoplemonApplication extends Application {

    private static PeoplemonApplication application;

    //Need to figure out if this is the main screen for our program
    public final Flow mainFlow = new Flow(History.single(new GameMainStage()));

    public static final String API_BASE_URL = "https://efa-peoplemon-api.azurewebsites.net/";

    @Override
    public void onCreate() {
        super.onCreate();

        application = this;
    }

    public static PeoplemonApplication getInstance() {
        return application;
    }
    public static Flow getMainFlow() {
        return getInstance().mainFlow;
    }

}
