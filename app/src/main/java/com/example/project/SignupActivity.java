package com.example.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class SignupActivity extends AppCompatActivity {
    private TextInputLayout nameTIL, emailTIL, contactTIL, passTIL, confirmPassTIL;
    private AppCompatEditText nameET, emailET, contactET, passET, confirmPassET;
    private AppCompatButton signupBN;


    private String url, TAG = "USER_SIGNUP_FRAGMENT";
    private MediaType JSON;
    private OkHttpClient client;
    private Request request;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        bindControls();
        bindListeners();
        setEditTextError();
        disableCopyPaste();
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

        confirmPassET.setCustomSelectionActionModeCallback(new ActionMode.Callback() {

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

        confirmPassET.setLongClickable(false);
        confirmPassET.setTextIsSelectable(false);
    }

    private void bindControls() {
        nameTIL = findViewById(R.id.signup_name_til);
        emailTIL = findViewById(R.id.signup_email_til);
        contactTIL = findViewById(R.id.signup_number_til);
        passTIL = findViewById(R.id.signup_pass_til);
        confirmPassTIL = findViewById(R.id.signup_confirmpass_til);

        nameET = findViewById(R.id.signup_name_et);
        emailET = findViewById(R.id.signup_email_et);
        contactET = findViewById(R.id.signup_email_et);
        passET = findViewById(R.id.signup_email_et);
        confirmPassET = findViewById(R.id.signup_email_et);

        signupBN = findViewById(R.id.signup_btn);
    }
    private void setEditTextError() {
        nameET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String name = nameET.getText().toString().trim();
                if (checkIsNullOrEmpty(name)) {
                    enableEdittextError(nameTIL, "Field can not be empty");
                } else {
                    if (name.length() < 3) {
                        enableEdittextError(nameTIL, "Name must be of minimum 3 characters");
                    } else {
                        disableEditextError(nameTIL);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
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
        contactET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String contact = contactET.getText().toString().trim();
                if (checkIsNullOrEmpty(contact)) {
                    enableEdittextError(contactTIL, "Field can not be empty");
                } else {
                    if (contact.length() == 11) {
                        disableEditextError(contactTIL);
                    } else {
                        enableEdittextError(contactTIL, "Number must be of minimun 11 characters");
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
                        enableEdittextError(passTIL, "Password must be of minimum 8 characters and should contain at least 1 Upper case, 1 Lower case, 1 Special Character and 1 Number in it");
                    } else {
                        disableEditextError(passTIL);
                    }
                    if (confirmPassET.getText().toString().trim().equals(pass)) {
                        disableEditextError(confirmPassTIL);
                    } else {
                        enableEdittextError(confirmPassTIL, "Passwords do not match");
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        confirmPassET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String confirmPass = confirmPassET.getText().toString().trim();
                if (checkIsNullOrEmpty(confirmPass)) {
                    enableEdittextError(confirmPassTIL, "Field can not be empty");
                } else {
                    if (passET.getText().toString().trim().equals(confirmPass)) {
                        disableEditextError(confirmPassTIL);
                    } else {
                        enableEdittextError(confirmPassTIL, "Passwords do not match");
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
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

    private boolean checkIsUpperCase(String pass) {
        for(int i = 0; i < pass.length(); i++) {
            if (Character.isUpperCase(pass.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    private boolean checkIsLowerCase(String pass) {
        for(int i = 0; i < pass.length(); i++) {
            if (Character.isLowerCase(pass.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    private boolean checkIsNumber(String pass) {
        for(int i = 0; i < pass.length(); i++) {
            if (Character.isDigit(pass.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    private boolean checkIsSpecialCharacter(String pass) {
        Pattern p = Pattern.compile("[^A-Za-z0-9]");
        Matcher m = p.matcher(pass);
        boolean b = m.find();
        if (b == true) {
            return b;
        } else {
            return b;
        }
    }

    private void bindListeners() {
        signupBN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == signupBN) {
                    userSignupMethod();
                }
            }
        });

    }

    public void userSignupMethod()
    {
        String name = nameET.getText().toString().trim();
        String email = emailET.getText().toString().trim();
        String contact = contactET.getText().toString().trim();
        String pass = passET.getText().toString().trim();
        String confirmPass = confirmPassET.getText().toString().trim();

       /* if (checkIsNullOrEmpty(name) || checkIsNullOrEmpty(email) || checkIsNullOrEmpty(contact) || checkIsNullOrEmpty(pass) || checkIsNullOrEmpty(confirmPass) ) {
            if (checkIsNullOrEmpty(name)) {
                enableEdittextError(nameTIL, "Field can not be empty");
            }
            if (checkIsNullOrEmpty(email)) {
                enableEdittextError(emailTIL, "Field can not be empty");
            }
            if (checkIsNullOrEmpty(contact)) {
                enableEdittextError(contactTIL, "Field can not be empty");
            }
            if (checkIsNullOrEmpty(pass)) {
                enableEdittextError(passTIL, "Field can not be empty");
            }
            if (checkIsNullOrEmpty(confirmPass)) {
                enableEdittextError(confirmPassTIL, "Passwords do not match");
            }
        } else if (!checkIsUpperCase(pass) || !checkIsLowerCase(pass) || !checkIsNumber(pass) || !checkIsSpecialCharacter(pass)) {
            if (!checkIsUpperCase(pass)) {
                enableEdittextError(passTIL, "Password should contain at least one Upper Case character.");
            } else if (!checkIsLowerCase(pass)) {
                enableEdittextError(passTIL, "Password should contain at least one Lower Case character.");
            } else if (!checkIsNumber(pass)) {
                enableEdittextError(passTIL, "Password should contain at least one Number");
            } else if (!checkIsSpecialCharacter(pass)) {
                enableEdittextError(passTIL, "Password should contain at least one Special character.");
            }
        } else if (!nameTIL.isErrorEnabled() && !emailTIL.isErrorEnabled() && !contactTIL.isErrorEnabled() && !passTIL.isErrorEnabled() && !confirmPassTIL.isErrorEnabled()) {
       */     startActivity(new Intent(getApplicationContext(), DrawerActivity.class));
            //hitApiRegisterClient(name, email, contact, pass, confirmPass);

    }
    private boolean checkIsNullOrEmpty(String value) {
        if (value.isEmpty() || value.equals("")){
            return true;
        } else {
            return false;
        }
    }
}
