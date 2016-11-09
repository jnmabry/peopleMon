package com.example.peoplemon.Views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Patterns;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.peoplemon.Models.Account;
import com.example.peoplemon.Network.RestClient;
import com.example.peoplemon.PeoplemonApplication;
import com.example.peoplemon.R;
import com.example.peoplemon.Stages.LoginStage;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import flow.Flow;
import flow.History;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by JoshuaMabry on 11/6/16.
 */

public class RegisterView extends LinearLayout {

    private Context context;

    @Bind(R.id.email_field)
    EditText emailField;

    @Bind(R.id.full_name_field)
    EditText fullNameField;

    @Bind(R.id.api_key_field)
    EditText apiKeyField;

    @Bind(R.id.password_field)
    EditText passwordField;

    @Bind(R.id.confirm_field)
    EditText confirmField;

    @Bind(R.id.register_button)
    Button registerButton;

    @Bind(R.id.spinner)
    ProgressBar spinner;

    public RegisterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
    }

    @OnClick(R.id.register_button)
    public void register(){
        InputMethodManager imm = (InputMethodManager)context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(emailField.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(fullNameField.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(apiKeyField.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(passwordField.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(confirmField.getWindowToken(), 0);


        String email = emailField.getText().toString();
        String fullName = fullNameField.getText().toString();
        String apiKey = "iOSandroid301november2016";
        String password = passwordField.getText().toString();
        String confirm = confirmField.getText().toString();
        String avatarBase64 = "string";

        if (fullName.isEmpty() || email.isEmpty() ||
                password.isEmpty() || confirm.isEmpty()){
            Toast.makeText(context,"Please fill out all fields",
                    Toast.LENGTH_LONG).show();
        }else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(context,"Please provide a valid email",
                    Toast.LENGTH_SHORT).show();
        }else if (!password.equals(confirm)){
            Toast.makeText(context,"Passwords do not match",
                    Toast.LENGTH_SHORT).show();
        } else{
            registerButton.setEnabled(false);
            spinner.setVisibility(VISIBLE);

            Account user = new Account(avatarBase64, apiKey,fullName,email,password);
            RestClient restClient = new RestClient();
            restClient.getApiService().register(user).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()){

                        // Old Auth Info
//                        Account regUser ashley= response.body();
//                        UserStore.getInstance().setToken(regUser.getToken());
//                        UserStore.getInstance().setTokenExpiration(regUser.getExpiration());

                        //New Authorization Info

                        Flow flow = PeoplemonApplication.getMainFlow();
                        History newHistory = History.single(new LoginStage());
                        flow.setHistory(newHistory, Flow.Direction.BACKWARD);

                    }else {
                        resetView();
                        Toast.makeText(context, "Registration Failed" + ": " + response.code()
                                , Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    resetView();
                    Toast.makeText(context,"Registration Failed", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }



    private void resetView(){
        registerButton.setEnabled(true);
        spinner.setVisibility(GONE);
    }

}
