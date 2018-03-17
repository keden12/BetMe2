package Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import Models.User;

/**
 * Created by Kamil on 2018-03-09.
 */

public class DatabaseHelper extends SQLiteOpenHelper{
    public DatabaseHelper(Context context)
    {
        super(context, "Betting.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
          db.execSQL("Create table user(username text primary key,password text,email text,balance real,bets integer)");
        db.execSQL("Create table bet(condition text,place text,hours integer,amount real,creator text primary key,joined text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists user");
        db.execSQL("drop table if exists bet");
    }

    public boolean insertUser(String username,String password,String email,double balance,Long bets)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username",username);
        contentValues.put("password",password);
        contentValues.put("email",email);
        contentValues.put("balance",balance);
        contentValues.put("bets",bets);
        long ins = db.insert("user",null,contentValues);
        if(ins == -1) return false;
        else return true;
    }

    public boolean insertBet(String condition,String place,int hours,double amount,String creator,String joined)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("condition",condition);
        contentValues.put("place",place);
        contentValues.put("hours",hours);
        contentValues.put("amount",amount);
        contentValues.put("creator",creator);
        contentValues.put("joined",joined);
        long ins = db.insert("bet",null,contentValues);
        if(ins == -1) return false;
        else return true;
    }

    public boolean updateData(String username,String password,String email,double balance,Long bets)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username",username);
        contentValues.put("password",password);
        contentValues.put("email",email);
        contentValues.put("balance",balance);
        contentValues.put("bets",bets);
        db.update("user", contentValues, "username=?",new String[] {username});
        return true;
    }
    public boolean checkEmail(String email)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from user where email=?", new String[]{email});
        if(cursor.getCount()>0) return false;
        else return true;

    }

    public boolean checkUsername(String username)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from user where username=?", new String[]{username});
        if(cursor.getCount()>0) return false;
        else return true;

    }

    public boolean authenticate(String username, String password)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from user where username=? and password=?",new String[]{username,password});
        if (cursor.getCount()>0) return true;
        else return false;
    }

    public String[] getUserDetails() {

        final String TABLE_NAME = "user";

        String selectQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db  = this.getReadableDatabase();
        Cursor cursor      = db.rawQuery(selectQuery, null);
        String[] data      = null;

        if (cursor.moveToFirst()) {
            do {
                // get the data into array, or class variable
            } while (cursor.moveToNext());
        }
        cursor.close();
        return data;
    }


    public Cursor getUserByUsername(String username){
        SQLiteDatabase db  = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from user where username=?",new String[]{username});
        return res;



    }


}
