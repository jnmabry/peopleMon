package com.example.peoplemon;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.davidstemmer.flow.plugin.screenplay.ScreenplayDispatcher;
import com.example.peoplemon.Models.Account;
import com.example.peoplemon.Models.ImageLoadedEvent;
import com.example.peoplemon.Network.RestClient;
import com.example.peoplemon.Network.UserStore;
import com.example.peoplemon.Stages.EditProfileStage;
import com.example.peoplemon.Stages.GameMainStage;
import com.example.peoplemon.Stages.LoginStage;

import org.greenrobot.eventbus.EventBus;

import java.io.ByteArrayOutputStream;

import butterknife.Bind;
import butterknife.ButterKnife;
import flow.Flow;
import flow.History;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private String TAG = "MainActivity";
    private Flow flow;
    private ScreenplayDispatcher dispatcher;
    private Context context;

    @Bind(R.id.container)
    RelativeLayout container;

    private Menu menu;
    private static int RESULT_LOAD_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        flow = PeoplemonApplication.getMainFlow();
        dispatcher = new ScreenplayDispatcher(this, container);
        dispatcher.setUp(flow);

        if (UserStore.getInstance().getToken() == null ||
                UserStore.getInstance().getTokenExpiration() == null){
            History newHistory = History.single(new LoginStage());
            flow.setHistory(newHistory, Flow.Direction.REPLACE);
        }
    }

    @Override
    public void onBackPressed(){
        if(!flow.goBack()){
            flow.removeDispatcher(dispatcher);
            flow.setHistory(History.single(new GameMainStage()), Flow.Direction.BACKWARD);
            super.onBackPressed();
        }
    }

    //This method tells the app where to go after the edit profile item is selected
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //This pinpoints where the app goes after selecting the edit profile item
            case R.id.edit_profile:
                Flow flow = PeoplemonApplication.getMainFlow();
                History newHistory = flow.getHistory().buildUpon()
                        //Pushes our screen to the corresponding view that contains edit profile information
                        .push(new EditProfileStage())
                        .build();
                flow.setHistory(newHistory, Flow.Direction.FORWARD);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // This method inflates the activity_menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_menu, menu);
        this.menu = menu;
        return true;
    }


    public void getImage(){
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try{

            if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null){

                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String imageString = cursor.getString(columnIndex);
                cursor.close();

                //Convert to Bitmap Array
                Bitmap bm = BitmapFactory.decodeFile(imageString);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
                byte[] b = baos.toByteArray();

                //Take the bitmap Array and encode it to Base64
                String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);

                Log.d("***BASE64****", encodedImage);
                makeApiCallForProfile(encodedImage);

                //Make API Call to Send Base64 to Server


                EventBus.getDefault().post(new ImageLoadedEvent(imageString));

            }else{
                Toast.makeText(this,"Error Retrieving Image", Toast.LENGTH_LONG).show();

            }

        } catch (Exception e){
            Toast.makeText(this,"Error Retrieving Image", Toast.LENGTH_LONG).show();
        }
    }

    private void makeApiCallForProfile(String imageString){

        Account user = new Account(imageString);
        RestClient restClient = new RestClient();
        restClient.getApiService().postUserInfo(user).enqueue(new Callback<Void>() {

            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                // Is the server response between 200 to 299
                if (response.isSuccessful()){



                }else{
                    Toast.makeText(context,"Get User Info Failed" + ": " + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(context,"Get User Info Failed", Toast.LENGTH_LONG).show();
            }
        });
    }

}
