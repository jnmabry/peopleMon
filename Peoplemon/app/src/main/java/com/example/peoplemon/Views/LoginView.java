package com.example.peoplemon.Views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.peoplemon.Models.Account;
import com.example.peoplemon.Network.RestClient;
import com.example.peoplemon.R;
import com.example.peoplemon.Stages.GameMainStage;
import com.example.peoplemon.Stages.RegisterStage;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import flow.Flow;
import flow.History;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.peoplemon.PeoplemonApplication.getMainFlow;

/**
 * Created by JoshuaMabry on 11/6/16.
 */

public class LoginView extends LinearLayout {

    private Context context;

    @Bind(R.id.full_name_field)
    EditText fullNameField;

    @Bind(R.id.password_field)
    EditText passwordField;

    @Bind(R.id.login_button)
    Button loginButton;

    @Bind(R.id.register_button)
    Button registerButton;

    @Bind(R.id.spinner)
    ProgressBar spinner;

    public LoginView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
    }

    @OnClick(R.id.register_button)
    public void showRegisterView(){
        Flow flow = getMainFlow();
        History newHistory = flow.getHistory().buildUpon()
                .push(new RegisterStage())
                .build();
        flow.setHistory(newHistory, Flow.Direction.FORWARD);
    }

    @OnClick(R.id.login_button)
    public void login(){
        InputMethodManager imm = (InputMethodManager)context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(fullNameField.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(passwordField.getWindowToken(), 0);

        String grantType = "password";
        String fullName = fullNameField.getText().toString();
        String password = passwordField.getText().toString();

        if (fullName.isEmpty() || password.isEmpty()){
            Toast.makeText(context,"Please provide full name and password",
                    Toast.LENGTH_LONG).show();
        } else{
            loginButton.setEnabled(true);
            registerButton.setEnabled(true);
            spinner.setVisibility(VISIBLE);

//            Auth auth = new Auth(grantType,fullName,password);
            RestClient restClient = new RestClient();
            restClient.getApiService().login(grantType, fullName, password).enqueue(new Callback<Account>() {

                @Override
                public void onResponse(Call<Account> call, Response<Account> response) {
                    // Is the server response between 200 to 299
                    if (response.isSuccessful()){

                        //Get user that is returned
//                        Account authUser = response.body();

                        //Get auth token and expiration date
//                        UserStore.getInstance().setToken(authUser.getToken());
//                        UserStore.getInstance().setTokenExpiration(authUser.getExpiration());

                        //Get main flow and set to single history
                        Flow flow = getMainFlow();
                        History newHistory = History.single(new GameMainStage());
                        flow.setHistory(newHistory, Flow.Direction.REPLACE);

                    }else{
                        resetView();
                        Toast.makeText(context,"Login Failed" + ": " + response.code(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<Account> call, Throwable t) {
                    resetView();
                    Toast.makeText(context,"Login Failed", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private void resetView(){
        loginButton.setEnabled(true);
        registerButton.setEnabled(true);
        spinner.setVisibility(GONE);
    }

}
