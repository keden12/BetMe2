package app.betme.betme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;

import Controller.BetMeAPI;
import Models.User;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    static User CurrentlyLogged;
    Button LoginButton;
    EditText LogUsername,LogPassword;
    TextView Error;



    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BetMeAPI.addUser("keden12","admin12","keden123@hotmail.com",100.00,Long.valueOf(0));
        BetMeAPI.addUser("mozella","admin10","mozella@gmail.com",5000.00,Long.valueOf(0));


        LogUsername = (EditText) findViewById(R.id.username);
        LogPassword = (EditText) findViewById(R.id.password);
        LoginButton = (Button) findViewById(R.id.Login);
        Error = (TextView) findViewById(R.id.Incorrect);

        LoginButton.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.Login:
                final String username = LogUsername.getText().toString();
                final String password = LogPassword.getText().toString();

                if(BetMeAPI.authenticate(username,password))
                {
                    CurrentlyLogged = BetMeAPI.getUserByUsername(username);
                    startActivity(new Intent(this,LoggedIn.class));
                }
                else
                {
                   Error.setVisibility(View.VISIBLE);
                }

                break;
        }



    }












    }




