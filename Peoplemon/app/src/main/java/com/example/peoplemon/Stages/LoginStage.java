package com.example.peoplemon.Stages;

import android.app.Application;

import com.example.peoplemon.PeoplemonApplication;
import com.example.peoplemon.R;
import com.example.peoplemon.Riggers.SlideRigger;

/**
 * Created by JoshuaMabry on 11/6/16.
 */

public class LoginStage extends IndexedStage {

    private final SlideRigger rigger;

    public LoginStage(Application context){
        super(LoginStage.class.getName());
        this.rigger = new SlideRigger(context);
    }
    public LoginStage() {
        this(PeoplemonApplication.getInstance());
    }

    @Override
    public int getLayoutId() {
        return R.layout.login_view;
    }

    @Override
    public Rigger getRigger() {
        return rigger;
    }
}
