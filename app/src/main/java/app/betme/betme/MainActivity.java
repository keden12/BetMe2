package app.betme.betme;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import Controller.BetMeAPI;
import Models.User;
import Utils.DBAdapter;
import Utils.DatabaseHelper;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    String user;
    String pass;
    String email;
    Double balance;
    Long bets;
    //currently logged in user
    static User CurrentlyLogged;
    Button LoginButton;
    EditText LogUsername,LogPassword;
    TextView Error, SignUp;
    DBAdapter myDb;


    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//define what is where
        LogUsername = (EditText) findViewById(R.id.username);
        LogPassword = (EditText) findViewById(R.id.password);
        LoginButton = (Button) findViewById(R.id.Login);
        Error = (TextView) findViewById(R.id.Incorrect);
        SignUp = (TextView) findViewById(R.id.signUp);
        myDb = new DBAdapter(this);
        myDb.open();
        LoginButton.setOnClickListener(this);
        SignUp.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
          //gets the text and authenticates
            case R.id.Login:
                final String username = LogUsername.getText().toString();
                final String password = LogPassword.getText().toString();

                if(myDb.authenticate(username,password))
                {
                    //gets values from the database
                    Cursor res = myDb.getUserByUsername(username);
                    if(res.getCount() == 0)
                    {

                    }
                    while(res.moveToNext())
                    {
                        user = res.getString(0);
                        pass = res.getString(1);
                        email = res.getString(2);
                        balance = res.getDouble(3);
                        bets = res.getLong(4);
                    }
                    CurrentlyLogged = new User(user,pass,email,balance,bets);
                    startActivity(new Intent(this,LoggedIn.class));
                    finish();
                }
                else
                {
                   //displays an error
                   Error.setVisibility(View.VISIBLE);
                }

                break;
            case R.id.signUp:
                startActivity(new Intent(this,Register.class));
                break;

        }



    }











    }




