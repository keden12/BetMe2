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

    public static final String KEY_ROWID = "_id";





    public DatabaseHelper(Context context)
    {
        super(context, "BettingDatabase2.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
          db.execSQL("Create table user(username text primary key,password text,email text,balance real,bets integer)");
        db.execSQL("Create table bet(id real primary key,creator text,condition text,place text,hours integer,amount real,joined text)");
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

    public long getLastID()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select max(id) from bet",null);
        cursor.moveToFirst();
        long ID = cursor.getLong(0);
        cursor.close();
        if(cursor == null)
        {
            ID = Long.valueOf(0);
        }
        return ID;
    }

    public boolean insertBet(String creator,String condition,String place,int hours,double amount,String joined)
    {
        long id = getLastID() + 1;
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contents= new ContentValues();
        contents.put("id",id);
        contents.put("creator",creator);
        contents.put("condition",condition);
        contents.put("place",place);
        contents.put("hours",hours);
        contents.put("amount",amount);
        contents.put("joined",joined);
        long ins = database.insert("bet",null,contents);
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
    public Cursor getAllBets() {
        SQLiteDatabase database  = this.getWritableDatabase();
        Cursor c = database.rawQuery("select * from bet", null);
        if(c != null)
        {
            c.moveToFirst();
        }
       return c;
    }

    public Cursor getUserByUsername(String username){
        SQLiteDatabase db  = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from user where username=?",new String[]{username});
        return res;



    }


}
