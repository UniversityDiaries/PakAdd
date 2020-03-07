package com.example.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


import com.google.android.material.textfield.TextInputLayout;


import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Login extends AppCompatActivity {
    private TextInputLayout emailTIL, passTIL;
    private AppCompatEditText emailET, passET;
    private AppCompatButton loginBN;
    private AppCompatTextView dontHaveAnAccountTV;

    private String url, TAG = "LOGIN_ACTIVITY";
    private OkHttpClient client;
    private Request request;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        bindControls();
        bindListeners();
        setEditTextError();
        disableCopyPaste();
    }

    private void bindListeners() {
        loginBN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v==loginBN){
                loginMethod();}

            }

        });

        dontHaveAnAccountTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v==dontHaveAnAccountTV)
                {
                    startActivity(new Intent(getApplicationContext(), SignupActivity.class));
                }
            }
        });
    }

    private void bindControls() {
        emailTIL = findViewById(R.id.login_email_til);
        passTIL = findViewById(R.id.login_pass_til);

        emailET = findViewById(R.id.login_email_et);
        passET = findViewById(R.id.login_pass_et);

        loginBN = findViewById(R.id.login_btn);

        dontHaveAnAccountTV = findViewById(R.id.login_signup_tv);

    }

    private void setEditTextError() {
        emailET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String email = emailET.getText().toString().trim();
                if (checkIsNullOrEmpty(email)) {
                    enableEdittextError(emailTIL, "Field can not be empty");
                } else {
                    if (isEmailAddressValid(email)) {
                        disableEditextError(emailTIL);
                    } else {
                        enableEdittextError(emailTIL, "Invalid email address");
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        passET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String pass = passET.getText().toString().trim();
                if (checkIsNullOrEmpty(pass)) {
                    enableEdittextError(passTIL, "Field can not be empty");
                } else {
                    if (pass.length() < 8) {
                        enableEdittextError(passTIL, "Password length should be minimum 8 characters");
                    } else {
                        disableEditextError(passTIL);
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }





    private void loginMethod() {
        String email = emailET.getText().toString().trim();
        String pass = passET.getText().toString().trim();
        if (checkIsNullOrEmpty(email) || checkIsNullOrEmpty(pass)) {
            if (checkIsNullOrEmpty(email)) {
                enableEdittextError(emailTIL, "Field can not be empty");
            }
            if (checkIsNullOrEmpty(pass)) {
                enableEdittextError(passTIL, "Field can not be empty");
            }
        } else if (!emailTIL.isErrorEnabled() && !passTIL.isErrorEnabled()){
            startActivity(new Intent(getApplicationContext(), SignupActivity.class));
            //hitApiLogin(email, pass);
        }
    }

    private boolean checkIsNullOrEmpty(String value) {
        if (value.isEmpty() || value.equals("")){
            return true;
        } else {
            return false;
        }
    }
    private void enableEdittextError(TextInputLayout view, String errorMsg){
        view.setErrorEnabled(true);
        view.setError(errorMsg);
    }

    private void disableEditextError(TextInputLayout view){
        view.setErrorEnabled(false);
    }

    private boolean isEmailAddressValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    private void disableCopyPaste() {
        passET.setCustomSelectionActionModeCallback(new ActionMode.Callback() {

            public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                return false;
            }

            public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                return false;
            }

            public boolean onActionItemClicked(ActionMode actionMode, MenuItem item) {
                return false;
            }

            public void onDestroyActionMode(ActionMode actionMode) {
            }
        });

        passET.setLongClickable(false);
        passET.setTextIsSelectable(false);
    }

}
