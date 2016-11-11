package com.example.peoplemon.Views;

import android.Manifest;
import android.animation.IntEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.AttributeSet;
import android.util.Base64;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.peoplemon.MainActivity;
import com.example.peoplemon.Models.Account;
import com.example.peoplemon.Models.User;
import com.example.peoplemon.Network.RestClient;
import com.example.peoplemon.PeoplemonApplication;
import com.example.peoplemon.R;
import com.example.peoplemon.Stages.CaughtPeopleListStage;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import flow.Flow;
import flow.History;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by JoshuaMabry on 11/7/16.
 */

public class GameMainView extends RelativeLayout implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,GoogleMap.OnMarkerClickListener {

    private Context context;
    private GoogleMap mMap;
    private double lat = 37.816380;
    private double lng = -82.809195;

    private Handler checkNear;
    private Handler checkingIn;

    public static Location mLocation;



    public ArrayList<User> people;

    // Creates a new starter point based on lat,lng given above
    LatLng Home = new LatLng(lat, lng);

    public GameMainView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
        mapView.getMapAsync(this);
        mapView.onCreate(((MainActivity) getContext()).savedInstanceState);
        mapView.onResume();

        checkNear = new Handler();
        final Runnable r = new Runnable() {
            public void run() {
                checkNearby();
                checkNear.postDelayed(this, 2000);
            }
        };
        checkNear.postDelayed(r, 2000);

        checkingIn = new Handler();
        final Runnable ci = new Runnable() {
            public void run() {
                checkIn();
                checkingIn.postDelayed(this, 2000);
//                listCaughtPeople();
            }
        };
        checkingIn.postDelayed(ci, 2000);
    }


    @Bind(R.id.map)
    MapView mapView;

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setOnMyLocationChangeListener(myLocationChangeListener);
        Toast.makeText(context, "Map loaded", Toast.LENGTH_SHORT).show();
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        }

        mLocation = new Location("");

        mLocation.setLatitude(lat);
        mLocation.setLongitude(lng);



        //Add in my own user profile








        


        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Home, 15));
        mMap.setMyLocationEnabled(true);
        mMap.addMarker(new MarkerOptions()
                .position(Home)
                .title("Marker in DR")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher))
                .snippet("#EFAImpact")
                .draggable(true));


        GroundOverlayOptions radar = new GroundOverlayOptions()
                .image(BitmapDescriptorFactory.fromResource(R.mipmap.radar))
                .position(Home, 200f, 200f);
        GroundOverlay imageOverlay = mMap.addGroundOverlay(radar);


        final Circle circle = mMap.addCircle(new CircleOptions().center(Home)
                .strokeColor(Color.BLUE).radius(80));
        ValueAnimator vAnimator = new ValueAnimator();
        vAnimator.setRepeatCount(ValueAnimator.INFINITE);
        vAnimator.setRepeatMode(ValueAnimator.RESTART);  /* PULSE */
        vAnimator.setIntValues(80, 0);
        vAnimator.setDuration(2500);
        vAnimator.setEvaluator(new IntEvaluator());
        vAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        vAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float animatedFraction = valueAnimator.getAnimatedFraction();
                // Log.e("", "" + animatedFraction);
                circle.setRadius(animatedFraction * 80);
            }
        });
        vAnimator.start();


    }

    private GoogleMap.OnMyLocationChangeListener myLocationChangeListener = new GoogleMap.OnMyLocationChangeListener() {
        @Override
        public void onMyLocationChange(Location location) {
            mMap.clear();
            lat = location.getLatitude();
            lng = location.getLongitude();
            Home = new LatLng(lat, lng);
            mLocation = location;


            //Moves the camera to the new location
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Home, 18));

//            LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());


            //Adds marker to the map
            mMap.addMarker(new MarkerOptions()
                    .position(Home)
                    .title("Marker in DR")
                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher))
                    .snippet("#EFAImpact")
                    .draggable(true));

            GroundOverlayOptions radar = new GroundOverlayOptions()
                    .image(BitmapDescriptorFactory.fromResource(R.mipmap.radar))
                    .position(Home, 200f, 200f);
            GroundOverlay imageOverlay = mMap.addGroundOverlay(radar);


            final Circle circle = mMap.addCircle(new CircleOptions().center(Home)
                    .strokeColor(Color.BLUE).radius(80));
            ValueAnimator vAnimator = new ValueAnimator();
            vAnimator.setRepeatCount(ValueAnimator.INFINITE);
            vAnimator.setRepeatMode(ValueAnimator.RESTART);  /* PULSE */
            vAnimator.setIntValues(80, 0);
            vAnimator.setDuration(2500);
            vAnimator.setEvaluator(new IntEvaluator());
            vAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
            vAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    float animatedFraction = valueAnimator.getAnimatedFraction();
                    // Log.e("", "" + animatedFraction);
                    circle.setRadius(animatedFraction * 80);
                }
            });
            vAnimator.start();

        }
    };


    public void checkIn() {
        Account account = new Account(lat, lng);
        RestClient restClient = new RestClient();
        restClient.getApiService().checkIn(account).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
            }
        });
    }

    public void checkNearby() {
        RestClient restClient = new RestClient();
        restClient.getApiService().findNearby(50).enqueue(new Callback<User[]>() {
            @Override
            public void onResponse(Call<User[]> call, Response<User[]> response) {
                if (response.isSuccessful()) {
                    for (User user : response.body()) {

                        lat = user.getLatitude();
                        lng = user.getLongitude();
                        String userId = user.getUserId();


                        if(user.getAvatarBase64() == null || user.getAvatarBase64().length()<=100){

                        final LatLng userpos = new LatLng(lat, lng);
                        mMap.addMarker(new MarkerOptions().title(user.getUserName())
                                //           .icon(BitmapDescriptorFactory.fromBitmap(decodedByte))
                                .snippet(user.getUserId())
                                .position(userpos));
                        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener(){
                            @Override
                            public boolean onMarkerClick(Marker marker) {
                                //Toast.makeText(context, "You caught " + marker.getSnippet(), Toast.LENGTH_SHORT).show();
                                Location userLoc = new Location("");
                                userLoc.setLatitude(marker.getPosition().latitude);
                                userLoc.setLongitude(marker.getPosition().longitude);
                                final String CaughtUserId = marker.getSnippet();
                                final User user = new User(CaughtUserId, mLocation.distanceTo(userLoc));
                                RestClient restClient = new RestClient();
                                restClient.getApiService().catchUser(user).enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                        if (response.isSuccessful()) {
                                            Toast.makeText(context, "Person Caught!", Toast.LENGTH_SHORT).show();
                                        }else{
                                            Toast.makeText(context,"That person is out side your radius", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                    @Override
                                    public void onFailure(Call<Void> call, Throwable t) {
                                        t.printStackTrace();
                                    }
                                });
                                marker.remove();
                                return false;
                            }
                        });
                        }else{

                            String encodedImage = user.getAvatarBase64();
                            byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
                            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                            decodedByte = Bitmap.createScaledBitmap(decodedByte, 120, 120, false);

                            final LatLng userpos = new LatLng(lat, lng);
                            mMap.addMarker(new MarkerOptions().title(user.getUserName())
                                    .icon(BitmapDescriptorFactory.fromBitmap(decodedByte))
                                    .snippet(user.getUserId())
                                    .position(userpos));
                            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener(){
                                @Override
                                public boolean onMarkerClick(Marker marker) {
                                    //Toast.makeText(context, "You caught " + marker.getSnippet(), Toast.LENGTH_SHORT).show();
                                    Location userLoc = new Location("");
                                    userLoc.setLatitude(marker.getPosition().latitude);
                                    userLoc.setLongitude(marker.getPosition().longitude);
                                    final String CaughtUserId = marker.getSnippet();
                                    final User user = new User(CaughtUserId, mLocation.distanceTo(userLoc));
                                    RestClient restClient = new RestClient();
                                    restClient.getApiService().catchUser(user).enqueue(new Callback<Void>() {
                                        @Override
                                        public void onResponse(Call<Void> call, Response<Void> response) {
                                            if (response.isSuccessful()) {
                                                Toast.makeText(context, "Person Caught!", Toast.LENGTH_SHORT).show();
                                            }else{
                                                Toast.makeText(context,"That person is out side your radius", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                        @Override
                                        public void onFailure(Call<Void> call, Throwable t) {
                                            t.printStackTrace();
                                        }
                                    });
                                    marker.remove();
                                    return false;
                                }
                            });

                        }
                    }

                } else {
                    Toast.makeText(context, "Error Checking In", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<User[]> call, Throwable t) {
            }
        });
    }

//    private void listCaughtPeople(){
//
//        RestClient restClient = new RestClient();
//        restClient.getApiService().caughtUsers().enqueue(new Callback<User[]>() {
//
//            @Override
//            public void onResponse(Call<User[]> call, Response<User[]> response) {
//                // Is the server response between 200 to 299
//                if (response.isSuccessful()){
//                    people = new ArrayList<User>(Arrays.asList(response.body()));
//
//                    for (User user : people){
//                        Log.d(user.getUserName(),"***CAUGHT***");
//                        Log.d(user.getAvatarBase64(),"***CAUGHT***");
//                    }
//
//                }else{
//                    Toast.makeText(context,"Get User Info Failed" + ": " + response.code(), Toast.LENGTH_LONG).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<User[]> call, Throwable t) {
//                Toast.makeText(context,"Get User Info Failed", Toast.LENGTH_LONG).show();
//            }
//        });
//    }

    @OnClick(R.id.view_caught_button)
    public void showAddCategoryView(){

        //Magical code for switching between stages
        Flow flow = PeoplemonApplication.getMainFlow();
        History newHistory = flow.getHistory().buildUpon()
                .push(new CaughtPeopleListStage())
                .build();
        flow.setHistory(newHistory, Flow.Direction.FORWARD);

    }

}