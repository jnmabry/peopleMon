package com.example.peoplemon.Stages;

import android.app.Application;

import com.example.peoplemon.PeoplemonApplication;
import com.example.peoplemon.R;
import com.example.peoplemon.Riggers.SlideRigger;

/**
 * Created by JoshuaMabry on 11/8/16.
 */

public class EditProfileStage extends IndexedStage {

    private final SlideRigger rigger;

    public EditProfileStage(Application context){
        super(EditProfileStage.class.getName());
        this.rigger = new SlideRigger(context);
    }
    public EditProfileStage() {
        this(PeoplemonApplication.getInstance());
    }

    @Override
    public int getLayoutId() {
        return R.layout.edit_profile_view;
    }

    @Override
    public Rigger getRigger() {
        return rigger;
    }

}
