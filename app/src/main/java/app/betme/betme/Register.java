package app.betme.betme;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import Models.User;
import Utils.DatabaseHelper;

public class Register extends AppCompatActivity implements View.OnClickListener {

    static int registerLogin;
    static User CurrentlyRegistered;
    DatabaseHelper database;
    Button Register;
    EditText RegUsername,RegEmail,RegPass,RegConfPass;
    TextView emptyUsername, emptyEmail, emptyPassword, emptyConfPassword, usernameAlreadyExists, emailAlreadyExists;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        RegUsername = (EditText) findViewById(R.id.registerUsername);
        RegEmail = (EditText) findViewById(R.id.registerEmail);
        RegPass = (EditText) findViewById(R.id.registerPassword);
        RegConfPass = (EditText) findViewById(R.id.registerConfirmPassword);
        Register = (Button) findViewById(R.id.registerButton);
        emptyUsername = (TextView) findViewById(R.id.EmptyUsername);
        emptyEmail = (TextView) findViewById(R.id.EmptyEmail) ;
        emptyPassword = (TextView) findViewById(R.id.EmptyPass);
        emptyConfPassword = (TextView) findViewById(R.id.EmptyConfPass);
        usernameAlreadyExists = (TextView) findViewById(R.id.usernameExists);
        emailAlreadyExists = (TextView) findViewById(R.id.emailExists);
        database = new DatabaseHelper(this);

        Register.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.registerButton:

                emptyUsername.setVisibility(View.GONE);
                emptyEmail.setVisibility(View.GONE);
                emptyPassword.setVisibility(View.GONE);
                emptyConfPassword.setVisibility(View.GONE);
                usernameAlreadyExists.setVisibility(View.GONE);
                emailAlreadyExists.setVisibility(View.GONE);


                String username = RegUsername.getText().toString();
                String email = RegEmail.getText().toString();
                String password = RegPass.getText().toString();
                String confpassword = RegConfPass.getText().toString();

                Boolean checkUsername = database.checkUsername(username);
                Boolean checkEmail = database.checkEmail(email);

                if(username.equals(""))
                {
                    emptyUsername.setVisibility(View.VISIBLE);
                }
                if(email.equals(""))
                {
                    emptyEmail.setVisibility(View.VISIBLE);
                }
                if(password.equals(""))
                {
                    emptyPassword.setVisibility(view.VISIBLE);
                }
                if(confpassword.equals(""))
                {
                    emptyConfPassword.setVisibility(view.VISIBLE);
                }
                if(checkUsername == false)
                {
                    usernameAlreadyExists.setVisibility(View.VISIBLE);
                }
                if(checkEmail == false)
                {
                    emailAlreadyExists.setVisibility(View.VISIBLE);
                }
                else{
                    Boolean insert = database.insertUser(username,password,email,0.0,Long.valueOf(0));
                    if(insert == true)
                    {
                        startActivity(new Intent(this,MainActivity.class));
                    }
                }





                break;



        }
    }
}
