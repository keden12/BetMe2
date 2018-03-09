package app.betme.betme;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import Models.User;

public class LoggedIn extends AppCompatActivity {
    EditText Money,User;
    User Current = MainActivity.CurrentlyLogged;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);
        Money = (EditText) findViewById(R.id.Balance);
        User = (EditText) findViewById(R.id.Player);
        String parsedBalance = Double.toString(Current.balance);
        parsedBalance = parsedBalance.replaceAll("\\s+","");
        Money.setInputType(0);
        User.setInputType(0);
        Money.setText(parsedBalance);
        User.setText(" "+Current.username);


    }

}
