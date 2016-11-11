package com.example.peoplemon.Stages;

import android.app.Application;

import com.davidstemmer.screenplay.stage.Stage;
import com.example.peoplemon.PeoplemonApplication;
import com.example.peoplemon.R;
import com.example.peoplemon.Riggers.SlideRigger;

/**
 * Created by JoshuaMabry on 11/10/16.
 */

public class CaughtPeopleListStage extends IndexedStage{

    private final SlideRigger rigger;

    public CaughtPeopleListStage(Application context){
        super(EditProfileStage.class.getName());
        this.rigger = new SlideRigger(context);
    }
    public CaughtPeopleListStage() {
        this(PeoplemonApplication.getInstance());
    }

    @Override
    public int getLayoutId() {
        return R.layout.caught_people_listview;
    }

    @Override
    public Stage.Rigger getRigger() {
        return rigger;
    }
}
