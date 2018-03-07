package app.betme.betme;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import Controller.BetMeAPI;
import Models.Bets;
import Models.User;
import Utils.Serializer;
import Utils.XMLSerializer;
import edu.princeton.cs.introcs.In;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    static File  datastore = new File("datastore.xml");
    static Serializer serializer = new XMLSerializer(datastore);
    static BetMeAPI betting = new BetMeAPI(serializer);
    static User CurrentlyLogged;
    Button LoginButton;
    EditText LogUsername,LogPassword;
    TextView Error;



    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BetMeAPI.PACKAGE_NAME = getApplicationContext().getPackageName();
        betting.addUser("keden12","admin12","keden123@hotmail.com",100.00,Long.valueOf(0));

        if (datastore.isFile())
        {
            try {
                betting.load();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        else
        {
            try {
                betting.InitialLoad();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {
            BetMeAPI.store();
        } catch (Exception e) {
            e.printStackTrace();
        }

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
                    CurrentlyLogged = betting.getUserByUsername(username);
                    startActivity(new Intent(this,LoggedIn.class));
                }
                else
                {
                   Error.setVisibility(View.VISIBLE);
                }

                break;
        }



    }



    public List<User> loadUsers(int stream) throws Exception {
        InputStream usersFile = getResources().openRawResource(stream);
        BufferedReader inUsers = new BufferedReader(
                new InputStreamReader(usersFile, Charset.forName("UTF-8"))
        );
        String delims = "[|]";
        String line = "";
        List<User> users = new ArrayList<User>();
        while ( (line = inUsers.readLine()) != null) {
            String userDetails = inUsers.readLine();
            String[] userTokens = userDetails.split(delims);
            if (userTokens.length == 5) {
                String username = userTokens[0];
                String password = userTokens[1];
                String email = userTokens[2];
                double balance = Double.valueOf(userTokens[3]);
                Long bets = Long.valueOf(userTokens[4]);
                users.add(new User(username, password, email, balance, bets));
            } else {
                throw new Exception("Invalid User length: " + userTokens.length);
            }
        }
        return users;
    }


    public List<Bets> loadBets(int stream) throws Exception {
        InputStream betsFile = getResources().openRawResource(stream);
        BufferedReader inBets = new BufferedReader(
                new InputStreamReader(betsFile, Charset.forName("UTF-8"))
        );
        String delims = "[|]";
        String line = "";
        List<Bets> bets = new ArrayList<Bets>();
        while ((line = inBets.readLine()) != null) {
            String betDetails = inBets.readLine();
            String[] betTokens = betDetails.split(delims);
            if (betTokens.length == 5) {
                String condition = betTokens[1];
                String place = betTokens[2];
                Long hours = Long.valueOf(betTokens[3]);
                double amount = Double.valueOf(betTokens[4]);
                bets.add(new Bets(condition, place, hours,amount));
            }
            else
            {
                throw new Exception("Invalid Movie length: " + betTokens.length);
            }
        }
        return bets;
    }












    }




