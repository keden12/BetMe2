package app.betme.betme;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Random;

import Models.User;
import Utils.DBAdapter;
import Utils.DatabaseHelper;

public class LoggedIn extends AppCompatActivity implements View.OnClickListener {
    EditText Money,User;
     //current user
    static User Current = MainActivity.CurrentlyLogged;
    FloatingActionButton addbet,page2,page3;
    NumberPicker condition,place,hours,amount;
    Button placebet,joinbet;
    DBAdapter myDb;
    TextView credits,itemcreator,itemplace,itemhours,itemcondition,itemamount,itemjoined,notenough,addcreds,logoff;
    ListView betList;
    int conditonget,placeget,hoursget,amountget;

    //timer miliseconds defined
    public long timeleft2hrs = 7200000;
    public long timeleft6hrs = 21600000;
    public long timeleft12hrs = 43200000;
    public long timeleft24hrs =  86400000;
    public CountDownTimer countDownTimer,countDownTimer2,countDownTimer3,countDownTimer4;
    public boolean timerrunning;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);


        myDb = new DBAdapter(this);
        myDb.open();
        Money = (EditText) findViewById(R.id.Balance);
        User = (EditText) findViewById(R.id.Player);
        addbet = (FloatingActionButton) findViewById(R.id.AddBet);
        addcreds = (TextView) findViewById(R.id.addCredits);
        String parsedBalance = Double.toString(Current.balance);
        parsedBalance = parsedBalance.replaceAll("\\s+","");
        Money.setInputType(0);
        User.setInputType(0);
        Money.setText(parsedBalance);
        User.setText(" "+Current.username);

        addbet.setOnClickListener(this);
        addcreds.setOnClickListener(this);

        populateListView();
        registerListClickCallback();

    }





 //update the timer
    public void updateTimer(long idInDB)
    {
        int hours = (int) timeleft2hrs / 3600000;
        int minutes = (int) timeleft2hrs % 3600000 / 60000;
        int seconds = (int) timeleft2hrs % 3600000 % 60000 / 1000;

        String timeleft;

        timeleft = "" + hours;
        timeleft += ":";
        if(minutes < 10) timeleft += "0";
        timeleft += minutes;
        if(seconds < 10) timeleft += "0";
        timeleft += seconds;



    }

//populate the list view with data
    private void populateListView()
    {
      Cursor cursor = myDb.getAllRowsBet();
        // Setup mapping from cursor to view fields:
        String[] fromFieldNames = new String[]
                {DBAdapter.KEY_CONDITION, DBAdapter.KEY_PLACE,DBAdapter.KEY_AMOUNT};
        int[] toViewIDs = new int[]
                { R.id.conditionList,  R.id.placeList,  R.id.amountList};



       SimpleCursorAdapter BettingAdapter;
       BettingAdapter = new SimpleCursorAdapter(getBaseContext(),R.layout.item_layout,cursor,fromFieldNames,toViewIDs,0);
       betList = (ListView) findViewById(R.id.BetList);
       betList.setAdapter(BettingAdapter);

    }



    private void registerListClickCallback() {
        betList = (ListView) findViewById(R.id.BetList);
        betList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked,
                                    int position, final long idInDB) {
                Cursor cursor = myDb.getRowBet(idInDB);
                String creator= "";
                String condition = "";
                String place = "";
                int hours = 0;
                double balance = 0.0;
                String joined = "";

                if (cursor.moveToFirst()) {
                    long idDB = cursor.getLong(DBAdapter.COL_ROWID);
                    creator = cursor.getString(DBAdapter.COL_CREATOR);
                    condition = cursor.getString(DBAdapter.COL_CONDITION);
                    place = cursor.getString(DBAdapter.COL_PLACE);
                    hours = cursor.getInt(DBAdapter.COL_HOURS);
                    balance = cursor.getDouble(DBAdapter.COL_AMOUNT);
                    joined = cursor.getString(DBAdapter.COL_JOINED);
                }


                AlertDialog.Builder mBuilder = new AlertDialog.Builder(LoggedIn.this);
                View mView = getLayoutInflater().inflate(R.layout.singleitem_layout, null);
                itemcreator = (TextView) mView.findViewById(R.id.singleitemCreator);
                itemcondition = (TextView) mView.findViewById(R.id.singleitemCondition);
                itemplace = (TextView) mView.findViewById(R.id.singleitemPlace);
                itemhours = (TextView) mView.findViewById(R.id.singleitemHours);
                itemamount = (TextView) mView.findViewById(R.id.singleitemAmount);
                itemjoined = (TextView) mView.findViewById(R.id.singleitemJoined);
                notenough = (TextView) mView.findViewById(R.id.simpleItemEnough);
                joinbet = (Button) mView.findViewById(R.id.singleitemJoinButton);
                final String finalcreator = creator;
                final String finalcondition = condition;
                final String finalplace = place;
                final int finalhours = hours;
                final double amount = balance;
                final String finaljoined = joined;
                String parsedInt = Integer.toString(finalhours);
                String parsed = Double.toString(amount);
                parsed = parsed.replaceAll("\\s+","");
                if(finalhours == 2) {

                    countDownTimer = new CountDownTimer(timeleft2hrs, 1000) {
                        @Override
                        public void onTick(long l) {
                            timeleft2hrs = l;
                            int hours = (int) timeleft2hrs / 3600000;
                            int minutes = (int) timeleft2hrs % 3600000 / 60000;
                            int seconds = (int) timeleft2hrs % 3600000 % 60000 / 1000;

                            String timeleft;

                            timeleft = "" + hours;
                            timeleft += ":";
                            if (minutes < 10) timeleft += "0";
                            timeleft += minutes;
                            timeleft += ":";
                            if (seconds < 10) timeleft += "0";
                            timeleft += seconds;



                        }

                        @Override
                        public void onFinish() {

                            myDb.deleteRowBet(idInDB);
                            if (finaljoined.equals("Free")) {
                                Cursor cursor1 = myDb.getUserByUsername(finalcreator);
                                Double winner = amount * 2;
                                winner = winner + cursor1.getDouble(3);

                                myDb.updateData(cursor1.getString(0), cursor1.getString(1), cursor1.getString(2), winner, cursor1.getLong(4));
                            } else {
                                Cursor cursor1 = myDb.getUserByUsername(finalcreator);
                                Cursor cursor2 = myDb.getUserByUsername(finaljoined);
                                Double winner = amount * 2;
                                winner = winner + cursor1.getDouble(3);

                                Double winner2 = amount * 2;
                                winner = winner + cursor2.getDouble(3);
                                Random r = new Random();
                                int Low = 1;
                                int High = 100;
                                int Result = r.nextInt(High - Low) + Low;

                                if (Result <= 50) {
                                    myDb.updateData(cursor1.getString(0), cursor1.getString(1), cursor1.getString(2), winner, cursor1.getLong(4));
                                } else {
                                    myDb.updateData(cursor2.getString(0), cursor2.getString(1), cursor2.getString(2), winner2, cursor2.getLong(4));
                                }


                            }

                        }
                    }.start();}


                else if(finalhours == 6)
                {
                countDownTimer2 = new CountDownTimer(timeleft6hrs, 1000) {
                    @Override
                    public void onTick(long l) {
                        timeleft6hrs = l;
                        int hours = (int) timeleft6hrs / 3600000;
                        int minutes = (int) timeleft6hrs % 3600000 / 60000;
                        int seconds = (int) timeleft6hrs % 3600000 % 60000 / 1000;

                        String timeleft;

                        timeleft = "" + hours;
                        timeleft += ":";
                        if (minutes < 10) timeleft += "0";
                        timeleft += minutes;
                        timeleft += ":";
                        if (seconds < 10) timeleft += "0";
                        timeleft += seconds;



                    }

                    @Override
                    public void onFinish() {

                        myDb.deleteRowBet(idInDB);
                        if (finaljoined.equals("Free")) {
                            Cursor cursor1 = myDb.getUserByUsername(finalcreator);
                            Double winner = amount * 2;
                            winner = winner + cursor1.getDouble(3);

                            myDb.updateData(cursor1.getString(0), cursor1.getString(1), cursor1.getString(2), winner, cursor1.getLong(4));
                        } else {
                            Cursor cursor1 = myDb.getUserByUsername(finalcreator);
                            Cursor cursor2 = myDb.getUserByUsername(finaljoined);
                            Double winner = amount * 2;
                            winner = winner + cursor1.getDouble(3);

                            Double winner2 = amount * 2;
                            winner = winner + cursor2.getDouble(3);
                            Random r = new Random();
                            int Low = 1;
                            int High = 100;
                            int Result = r.nextInt(High - Low) + Low;

                            if (Result <= 50) {
                                myDb.updateData(cursor1.getString(0), cursor1.getString(1), cursor1.getString(2), winner, cursor1.getLong(4));
                            } else {
                                myDb.updateData(cursor2.getString(0), cursor2.getString(1), cursor2.getString(2), winner2, cursor2.getLong(4));
                            }


                        }

                    }
                }.start();}

                else if(finalhours == 12)
                {
                countDownTimer3 = new CountDownTimer(timeleft12hrs, 1000) {
                    @Override
                    public void onTick(long l) {
                        timeleft12hrs = l;
                        int hours = (int) timeleft12hrs / 3600000;
                        int minutes = (int) timeleft12hrs % 3600000 / 60000;
                        int seconds = (int) timeleft12hrs % 3600000 % 60000 / 1000;

                        String timeleft;

                        timeleft = "" + hours;
                        timeleft += ":";
                        if (minutes < 10) timeleft += "0";
                        timeleft += minutes;
                        timeleft += ":";
                        if (seconds < 10) timeleft += "0";
                        timeleft += seconds;



                    }

                    @Override
                    public void onFinish() {

                        myDb.deleteRowBet(idInDB);
                        if (finaljoined.equals("Free")) {
                            Cursor cursor1 = myDb.getUserByUsername(finalcreator);
                            Double winner = amount * 2;
                            winner = winner + cursor1.getDouble(3);

                            myDb.updateData(cursor1.getString(0), cursor1.getString(1), cursor1.getString(2), winner, cursor1.getLong(4));
                        } else {
                            Cursor cursor1 = myDb.getUserByUsername(finalcreator);
                            Cursor cursor2 = myDb.getUserByUsername(finaljoined);
                            Double winner = amount * 2;
                            winner = winner + cursor1.getDouble(3);

                            Double winner2 = amount * 2;
                            winner = winner + cursor2.getDouble(3);
                            Random r = new Random();
                            int Low = 1;
                            int High = 100;
                            int Result = r.nextInt(High - Low) + Low;

                            if (Result <= 50) {
                                myDb.updateData(cursor1.getString(0), cursor1.getString(1), cursor1.getString(2), winner, cursor1.getLong(4));
                            } else {
                                myDb.updateData(cursor2.getString(0), cursor2.getString(1), cursor2.getString(2), winner2, cursor2.getLong(4));
                            }


                        }

                    }
                }.start();}

                else if(finalhours == 24)
                {
                countDownTimer4 = new CountDownTimer(timeleft24hrs, 1000) {
                    @Override
                    public void onTick(long l) {
                        timeleft24hrs = l;
                        int hours = (int) timeleft24hrs / 3600000;
                        int minutes = (int) timeleft24hrs % 3600000 / 60000;
                        int seconds = (int) timeleft24hrs % 3600000 % 60000 / 1000;

                        String timeleft;

                        timeleft = "" + hours;
                        timeleft += ":";
                        if (minutes < 10) timeleft += "0";
                        timeleft += minutes;
                        timeleft += ":";
                        if (seconds < 10) timeleft += "0";
                        timeleft += seconds;



                    }

                    @Override
                    public void onFinish() {

                        myDb.deleteRowBet(idInDB);
                        if (finaljoined.equals("Free")) {
                            Cursor cursor1 = myDb.getUserByUsername(finalcreator);
                            Double winner = amount * 2;
                            winner = winner + cursor1.getDouble(3);

                            myDb.updateData(cursor1.getString(0), cursor1.getString(1), cursor1.getString(2), winner, cursor1.getLong(4));
                        } else {
                            Cursor cursor1 = myDb.getUserByUsername(finalcreator);
                            Cursor cursor2 = myDb.getUserByUsername(finaljoined);
                            Double winner = amount * 2;
                            winner = winner + cursor1.getDouble(3);

                            Double winner2 = amount * 2;
                            winner = winner + cursor2.getDouble(3);
                            Random r = new Random();
                            int Low = 1;
                            int High = 100;
                            int Result = r.nextInt(High - Low) + Low;

                            if (Result <= 50) {
                                myDb.updateData(cursor1.getString(0), cursor1.getString(1), cursor1.getString(2), winner, cursor1.getLong(4));
                            } else {
                                myDb.updateData(cursor2.getString(0), cursor2.getString(1), cursor2.getString(2), winner2, cursor2.getLong(4));
                            }


                        }

                    }
                }.start();}











                itemcreator.setText(finalcreator);
                itemcondition.setText(finalcondition);
                itemplace.setText(finalplace);
                itemhours.setText(parsedInt);
                itemamount.setText(parsed);
                itemjoined.setText(finaljoined);
                if(joined.equals("Free")&&!Current.username.equals(creator))
                {
                    joinbet.setVisibility(View.VISIBLE);
                }


                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();


                joinbet.setOnClickListener(new View.OnClickListener(){

                    public void onClick(View view) {
                      if(Current.balance < amount)
                      {
                          notenough.setVisibility(View.VISIBLE);

                      }
                      else
                      {
                          dialog.cancel();
                          Current.balance = Current.balance - amount;
                          String parsedBalance = Double.toString(Current.balance);
                          parsedBalance = parsedBalance.replaceAll("\\s+","");
                          Money.setInputType(0);
                          Money.setText(parsedBalance);
                          myDb.updateData(Current.username,Current.password,Current.email,Current.balance,Current.bets);
                          myDb.updateRowBet(idInDB,finalcreator,finalcondition,finalplace,finalhours,amount,Current.username);
                      }





                    }
                    });









                }
        });
    }



//update data
    private void updateItem(long idInDB) {
        Cursor cursor = myDb.getRowBet(idInDB);
        if (cursor.moveToFirst()) {
            long idDB = cursor.getLong(DBAdapter.COL_ROWID);
            String creator = cursor.getString(DBAdapter.COL_CREATOR);
            String condition = cursor.getString(DBAdapter.COL_CONDITION);
            String place = cursor.getString(DBAdapter.COL_PLACE);
            int hours = cursor.getInt(DBAdapter.COL_HOURS);
            double balance = cursor.getDouble(DBAdapter.COL_AMOUNT);
            String joined = cursor.getString(DBAdapter.COL_JOINED);


            myDb.updateRowBet(idInDB,creator,condition,place,hours,balance,joined);
        }
        cursor.close();
        populateListView();
    }


    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
           //create a bet process
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
                        conditonget = condition.getValue();
                        placeget = place.getValue();
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
                                hoursget = hours.getValue();
                                dialog2.cancel();
                                AlertDialog.Builder builder3 = new AlertDialog.Builder(LoggedIn.this);
                                View view3 = getLayoutInflater().inflate(R.layout.dialog_addbet3,null);
                                amount = (NumberPicker) view3.findViewById(R.id.amountBet);
                                amount.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
                                placebet = (Button) view3.findViewById(R.id.placebetButton);
                                credits = (TextView) view3.findViewById(R.id.creditsError);
                                amount.setMinValue(1);
                                amount.setMaxValue(100);

                                builder3.setView(view3);
                                final AlertDialog dialog3 = builder3.create();
                                dialog3.show();

                                placebet.setOnClickListener(new View.OnClickListener(){
                                    public void onClick(View view)
                                    {
                                         amountget = amount.getValue();
                                        Double amountdouble = (double) amountget;
                                        if(amountdouble > Current.balance)
                                        {
                                            credits.setVisibility(View.VISIBLE);
                                        }
                                        else {
                                            Current.balance = Current.balance - amountdouble;
                                            myDb.updateData(Current.username,Current.password,Current.email,Current.balance,Current.bets);
                                            String parsedBalance = Double.toString(Current.balance);
                                            parsedBalance = parsedBalance.replaceAll("\\s+","");
                                            Money.setInputType(0);
                                            Money.setText(parsedBalance);
                                            String conditionstring = "";
                                            if (conditonget == 0) {
                                                conditionstring = "It will rain";
                                            } else if (conditonget == 1) {
                                                conditionstring = "It will be sunny";
                                            } else {
                                                conditionstring = "It will snow";
                                            }
                                            String placestring = "";
                                            if (placeget == 0) {
                                                placestring = "Paris";
                                            } else if (placeget == 1) {
                                                placestring = "New York";
                                            } else {
                                                placestring = "London";
                                            }
                                            int hoursint = 0;
                                            if (hoursget == 0) {
                                                hoursint = 2;
                                            } else if (hoursget == 1) {
                                                hoursint = 6;
                                            } else if (hoursget == 2) {
                                                hoursint = 12;
                                            } else {
                                                hoursint = 24;
                                            }
                                            String joined = "Free";
                                            String username = Current.username;
                                            Boolean insert = myDb.insertBet(username, conditionstring,placestring, hoursint, amountdouble, joined);
                                            if(insert == true)
                                            {
                                                myDb.updateData(Current.username,Current.password,Current.email,Current.balance,Current.bets+1);
                                                dialog3.cancel();
                                                populateListView();
                                            }
                                        }
                                    }
                                });







                            }
                        });


                    }
                });


                break;
//add credits button clicked
            case R.id.addCredits:
                startActivity(new Intent(this,AddCredits.class));


                break;




        }
    }

}
