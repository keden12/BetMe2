package app.betme.betme;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import Models.User;

public class LoggedIn extends AppCompatActivity implements View.OnClickListener {
    EditText Money,User;
    User Current = MainActivity.CurrentlyLogged;
    FloatingActionButton addbet,page2,page3;
    NumberPicker condition,place,hours,amount;
    Button placebet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);

        Money = (EditText) findViewById(R.id.Balance);
        User = (EditText) findViewById(R.id.Player);
        addbet = (FloatingActionButton) findViewById(R.id.AddBet);
        String parsedBalance = Double.toString(Current.balance);
        parsedBalance = parsedBalance.replaceAll("\\s+","");
        Money.setInputType(0);
        User.setInputType(0);
        Money.setText(parsedBalance);
        User.setText(" "+Current.username);

        addbet.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.AddBet:
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(LoggedIn.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_addbet, null);
                condition = (NumberPicker) mView.findViewById(R.id.conditionBet);
                place = (NumberPicker) mView.findViewById(R.id.placeBet);
                page2 = (FloatingActionButton) mView.findViewById(R.id.pageBet);
                condition.setMinValue(0);
                condition.setMaxValue(2);
                condition.setDisplayedValues( new String[] { "It will rain", "It will be sunny", "It will snow" } );
                place.setMinValue(0);
                place.setMaxValue(2);
                place.setDisplayedValues( new String[] { "Paris", "New York", "London" } );
                condition.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
                place.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);



                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();
                page2.setOnClickListener(new View.OnClickListener(){

                    public void onClick(View view){

                        dialog.cancel();
                        AlertDialog.Builder builder = new AlertDialog.Builder(LoggedIn.this);
                        View view2 = getLayoutInflater().inflate(R.layout.dialog_addbet2, null);
                        hours = (NumberPicker) view2.findViewById(R.id.hoursBet);
                        page3 = (FloatingActionButton) view2.findViewById(R.id.page3Bet);
                        hours.setMinValue(0);
                        hours.setMaxValue(3);
                        hours.setDisplayedValues( new String[] { "2", "6", "12","24" } );
                        hours.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
                        builder.setView(view2);
                        final AlertDialog dialog2 = builder.create();
                        dialog2.show();

                        page3.setOnClickListener(new View.OnClickListener(){

                            public void onClick(View view){
                                dialog2.cancel();
                                AlertDialog.Builder builder3 = new AlertDialog.Builder(LoggedIn.this);
                                View view3 = getLayoutInflater().inflate(R.layout.dialog_addbet3,null);
                                amount = (NumberPicker) view3.findViewById(R.id.amountBet);
                                amount.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
                                placebet = (Button) view3.findViewById(R.id.placebetButton);
                                amount.setMinValue(1);
                                amount.setMaxValue(100);

                                builder3.setView(view3);
                                final AlertDialog dialog3 = builder3.create();
                                dialog3.show();

                                placebet.setOnClickListener(new View.OnClickListener(){

                                    public void onClick(View view)
                                    {
                                        dialog3.cancel();








                                    }
                                });







                            }
                        });


                    }
                });


                break;




        }
    }
}
