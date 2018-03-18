package Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


// TO USE:
// Change the package (at top) to match your project.
// Search for "TODO", and make the appropriate changes.
public class DBAdapter {

    /////////////////////////////////////////////////////////////////////
    //	Constants & Data
    /////////////////////////////////////////////////////////////////////
    // For logging:
    private static final String TAG = "DBAdapter";

    // DB Fields
    public static final String KEY_ROWID = "_id";
    public static final int COL_ROWID = 0;
    /*
     * CHANGE 1:
     */
    // TODO: Setup your fields here:
    public static final String KEY_CREATOR = "creator";
    public static final String KEY_CONDITION = "condition";
    public static final String KEY_PLACE = "place";
    public static final String KEY_HOURS = "hours";
    public static final String KEY_AMOUNT = "amount";
    public static final String KEY_JOINED = "joined";

    // TODO: Setup your field numbers here (0 = KEY_ROWID, 1=...)
    public static final int COL_CREATOR = 1;
    public static final int COL_CONDITION = 2;
    public static final int COL_PLACE = 3;
    public static final int COL_HOURS = 4;
    public static final int COL_AMOUNT = 5;
    public static final int COL_JOINED = 6;


    public static final String[] ALL_KEYS = new String[] {KEY_ROWID, KEY_CREATOR, KEY_CONDITION, KEY_PLACE,KEY_HOURS,KEY_AMOUNT,KEY_JOINED};

    // DB info: it's name, and the table we are using (just one).
    public static final String DATABASE_NAME = "MyDb5";
    public static final String DATABASE_TABLE_BET = "bet";
    // Track DB version if a new version of your app changes the format.
    public static final int DATABASE_VERSION = 2;

    private static final String DATABASE_CREATE_SQL_BET =
            "create table " + DATABASE_TABLE_BET
                    + " (" + KEY_ROWID + " integer primary key autoincrement, "
                    + KEY_CREATOR + " text, "
                    + KEY_CONDITION+ " text, "
                    + KEY_PLACE + " text,"
                    + KEY_HOURS + " integer,"
                    + KEY_AMOUNT + " real,"
                    + KEY_JOINED + " text"

                    // Rest  of creation:
                    + ");";

    // Context of application who uses us.
    private final Context context;

    private DatabaseHelper myDBHelper;
    private SQLiteDatabase db;

    /////////////////////////////////////////////////////////////////////
    //	Public methods:
    /////////////////////////////////////////////////////////////////////

    public DBAdapter(Context ctx) {
        this.context = ctx;
        myDBHelper = new DatabaseHelper(context);
    }

    // Open the database connection.
    public DBAdapter open() {
        db = myDBHelper.getWritableDatabase();
        return this;
    }

    // Close the database connection.
    public void close() {
        myDBHelper.close();
    }

    // Add a new set of values to the database.
    public boolean insertBet(String creator, String condition,String place,int hours,double amount,String joined) {

        // Create row's data:
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_CREATOR, creator);
        initialValues.put(KEY_CONDITION, condition);
        initialValues.put(KEY_PLACE, place);
        initialValues.put(KEY_HOURS, hours);
        initialValues.put(KEY_AMOUNT, amount);
        initialValues.put(KEY_JOINED, joined);

        // Insert it into the database.
        long ins = db.insert(DATABASE_TABLE_BET, null, initialValues);
        if(ins == -1) return false;
        else return true;
    }

    public boolean insertUser(String username,String password,String email,double balance,Long bets)
    {

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


    public boolean updateData(String username,String password,String email,double balance,Long bets)
    {
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
        Cursor cursor = db.rawQuery("Select * from user where email=?", new String[]{email});
        if(cursor.getCount()>0) return false;
        else return true;

    }

    public boolean checkUsername(String username)
    {
        Cursor cursor = db.rawQuery("Select * from user where username=?", new String[]{username});
        if(cursor.getCount()>0) return false;
        else return true;

    }

    public boolean authenticate(String username, String password)
    {
        Cursor cursor = db.rawQuery("Select * from user where username=? and password=?",new String[]{username,password});
        if (cursor.getCount()>0) return true;
        else return false;
    }

    public Cursor getUserByUsername(String username){
        Cursor res = db.rawQuery("select * from user where username=?",new String[]{username});
        return res;
    }
    // Delete a row from the database, by rowId (primary key)
    public boolean deleteRowBet(long rowId) {
        String where = KEY_ROWID + "=" + rowId;
        return db.delete(DATABASE_TABLE_BET, where, null) != 0;
    }

    public void deleteAll() {
        Cursor c = getAllRowsBet();
        long rowId = c.getColumnIndexOrThrow(KEY_ROWID);
        if (c.moveToFirst()) {
            do {
                deleteRowBet(c.getLong((int) rowId));
            } while (c.moveToNext());
        }
        c.close();
    }

    // Return all data in the database.
    public Cursor getAllRowsBet() {
        String where = null;
        Cursor c = 	db.query(true, DATABASE_TABLE_BET, ALL_KEYS,
                where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    // Get a specific row (by rowId)
    public Cursor getRowBet(long rowId) {
        String where = KEY_ROWID + "=" + rowId;
        Cursor c = 	db.query(true, DATABASE_TABLE_BET, ALL_KEYS,
                where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    // Change an existing row to be equal to new data.
    public boolean updateRowBet(long rowId, String creator, String condition,String place,int hours,double amount,String joined) {
        String where = KEY_ROWID + "=" + rowId;
        ContentValues newValues = new ContentValues();
        newValues.put(KEY_CREATOR, creator);
        newValues.put(KEY_CONDITION, condition);
        newValues.put(KEY_PLACE, place);
        newValues.put(KEY_HOURS, hours);
        newValues.put(KEY_AMOUNT, amount);
        newValues.put(KEY_JOINED, joined);

        // Insert it into the database.
        return db.update(DATABASE_TABLE_BET, newValues, where, null) != 0;
    }



    /////////////////////////////////////////////////////////////////////
    //	Private Helper Classes:
    /////////////////////////////////////////////////////////////////////

    /**
     * Private class which handles database creation and upgrading.
     * Used to handle low-level database access.
     */
    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase _db) {
            _db.execSQL(DATABASE_CREATE_SQL_BET);
            _db.execSQL("Create table user(username text primary key,password text not null,email text not null,balance real,bets integer)");
        }

        @Override
        public void onUpgrade(SQLiteDatabase _db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading application's database from version " + oldVersion
                    + " to " + newVersion + ", which will destroy all old data!");

            // Destroy old database:
            _db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_BET);
            _db.execSQL("drop table if exists user");

            // Recreate new database:
            onCreate(_db);
        }
    }
}
