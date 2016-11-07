package com.example.peoplemon.Stages;

import android.app.Application;

import com.example.peoplemon.PeoplemonApplication;
import com.example.peoplemon.R;
import com.example.peoplemon.Riggers.SlideRigger;

/**
 * Created by JoshuaMabry on 11/6/16.
 */

public class GameMainStage extends IndexedStage {

    private final SlideRigger rigger;

    public GameMainStage(Application context){
        super(GameMainStage.class.getName());
        this.rigger = new SlideRigger(context);
    }
    public GameMainStage() {
        this(PeoplemonApplication.getInstance());
    }

    @Override
    public int getLayoutId() {
        return R.layout.game_main_view;
    }

    @Override
    public Rigger getRigger() {
        return rigger;
    }

}
