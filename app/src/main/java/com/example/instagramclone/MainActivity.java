package com.example.instagramclone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.ViewCompat;

import android.app.Activity;
import android.content.Intent;
import android.inputmethodservice.Keyboard;
import android.os.Bundle;
import android.util.Log;
import android.view.FocusFinder;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowId;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class MainActivity extends AppCompatActivity implements View.OnKeyListener, View.OnClickListener {

    ImageView instagramLogo;
    ImageView instagramWriting;
    ConstraintLayout backgroundLayout;
    Button changerButton;
    Button signUpOrInButton;
    EditText usernameEditText;
    EditText passwordEditText;
    boolean isSignInButton = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        instagramLogo = findViewById(R.id.instagramLogo);
        instagramWriting = findViewById(R.id.instagramWriting);

        backgroundLayout = findViewById(R.id.backgroundLayout);

        instagramLogo.setOnClickListener(this);
        instagramWriting.setOnClickListener(this);
        backgroundLayout.setOnClickListener(this);

        changerButton = findViewById(R.id.changerButton);
        signUpOrInButton = findViewById(R.id.signUpOrInButton);

        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);

        checkButtonText();

        passwordEditText.setOnKeyListener(this);

        ParseAnalytics.trackAppOpenedInBackground(getIntent());
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {

        Log.i("keycode",Integer.toString(event.getKeyCode()));

        if(keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN){
            signUpOrIn(v);
        }

        return false;
    }

    public void buttonChanger(View view){

        if(isSignInButton){
            isSignInButton = false;
            checkButtonText();
        } else {
            isSignInButton = true;
            checkButtonText();
        }

    }

    public void signUpOrIn(View view){

        if(isSignInButton){

            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();

        if(passwordEditText.getText().toString().length() == 0 || usernameEditText.getText().toString().length() == 0)
            Toast.makeText(getApplicationContext(),"A username and a password required.",Toast.LENGTH_SHORT).show();
        else {

            ParseUser.logInInBackground(username, password, new LogInCallback() {
                @Override
                public void done(ParseUser user, ParseException e) {
                    if (e == null && user != null) {
                        Log.i("Login status", "OK!");

                        Intent intent = new Intent(getApplicationContext(), Feed.class);
                        startActivity(intent);
                    } else {
                        Log.i("Login status", "FAILED!");

                        Toast.makeText(getApplicationContext(), "Username or password incorrect.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        } else {

            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            if (passwordEditText.getText().toString().length() == 0 || usernameEditText.getText().toString().length() == 0)
                Toast.makeText(getApplicationContext(), "A username and a password required.", Toast.LENGTH_SHORT).show();
            else {

                ParseUser user = new ParseUser();
                user.setUsername(username);
                user.setPassword(password);

                user.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            //ok
                            Log.i("Sign up status", "Ok");

                            Toast.makeText(getApplicationContext(), "You've signed up successfully", Toast.LENGTH_LONG).show();
                        } else {
                            Log.i("Sign up status", "Failed");
                            e.printStackTrace();

                            Toast.makeText(getApplicationContext(), "Username already exists, try again", Toast.LENGTH_LONG).show();

                        }
                    }
                });
            }
        }
    }

    public void checkButtonText(){
        if(isSignInButton){
            changerButton.setText("or Signup");
            signUpOrInButton.setText("Login");
        } else {
            changerButton.setText("or Login");
            signUpOrInButton.setText("Signup");
        }
    }

    @Override
    public void onClick(View v) {

        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }
}


   /* ParseQuery<ParseObject> query = ParseQuery.getQuery("Score");
        query.whereLessThan("score", 50);

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
             if (e == null) {
                if (objects.size() > 0) {
                    for (ParseObject object : objects) {

                        object.put("score", object.getInt("score") + 10);
                        object.saveInBackground();

                        Log.i("username", object.getString("username"));
                        Log.i("score", Integer.toString(object.getInt("score")));
                    }
                } else {
                    Log.i("Query", "failed");
                }
             } else {
                 Log.i("Query", "failed");
             }
            }
        });*/

/*ParseUser.logOut();*/

