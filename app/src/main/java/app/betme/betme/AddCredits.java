package app.betme.betme;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import Models.User;
import Utils.DBAdapter;

public class AddCredits extends AppCompatActivity implements View.OnClickListener {
    RadioButton paypal,debit;
    Button addcreds;
    User Current;
    NumberPicker amount;
    DBAdapter myDb;
    RadioGroup group;
    TextView error;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_credits);


        myDb = new DBAdapter(this);
        myDb.open();

        addcreds = (Button) findViewById(R.id.addcredsButton);
        amount = (NumberPicker) findViewById(R.id.addcredsPicker) ;
        group = (RadioGroup) findViewById(R.id.payGroup);
        paypal = (RadioButton) findViewById(R.id.paypal);
        debit = (RadioButton) findViewById(R.id.debit);
        error = (TextView) findViewById(R.id.NotSelected);
        Current = LoggedIn.Current;
        amount.setMinValue(1);
        amount.setMaxValue(1000);
        amount.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);


        addcreds.setOnClickListener(this);












    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
                //update the balance of currently logged user
            case R.id.addcredsButton:
                  
                int amountget = amount.getValue();
                Double amountdoub = (double) amountget;
                Current.balance = Current.balance + amountdoub;
                myDb.updateData(Current.username,Current.password,Current.email,Current.balance,Current.bets);
                int selectedId = group .getCheckedRadioButtonId();
                if(selectedId != R.id.paypal&& selectedId != R.id.debit)
                {
                    //display an error
                  error.setVisibility(View.VISIBLE);
                } else {
                    //thank for using whichever
                RadioButton radioButton = (RadioButton) findViewById(selectedId);
                Toast.makeText(AddCredits.this,"Thank you for using "+
                        radioButton.getText(), Toast.LENGTH_SHORT).show();
                  //start a new activity
                startActivity(new Intent(this,LoggedIn.class));
                break;
                }


        }
    }
}
