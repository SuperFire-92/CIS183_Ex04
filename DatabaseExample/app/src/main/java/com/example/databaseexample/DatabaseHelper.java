package com.example.databaseexample;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseLockedException;
import android.database.sqlite.SQLiteOpenHelper;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper
{
    private static final String DATABASE_NAME = "Users.db";
    private static final String TABLE_NAME = "Users";
    public DatabaseHelper(Context context)
    {
        //We will use this to create the database
        //it accepts the context, the name, factory (null), and version number
        //if your database becomes corrupt or the information in the database is wrong,
        //you can change the version number
        //super is used to call the functionality of the base class SQLiteOpenHelper and
        //then executes the extended class (DatabaseHelper).

        super(context,DATABASE_NAME,null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        //Create table in the database.
        //execute the sql statement on the database that was passed to the function onCreate called db
        //This can be tricky because we have to write our sql statements as strings

        //3 attributes: username, first name, and last name.
        //all three attributes will be TEXT and username will be the primary key.
        //username has to be unique.
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (username TEXT PRIMARY KEY NOT NULL, firstname TEXT, lastname TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1)
    {
        //This is used if we change the version number of the database.

        //delete the table if you upgrade the database (change the version number in the create)
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        //Create a new table once the old table has been deleted.
        onCreate(db);

    }

    //this is used to insert default info into the table.
    public boolean initializeDB()
    {
        if (numOfRowsInTable() == 0)
        {
            //connect to the database
            //notice we are getting a writable database because we need to insert information into our database.
            SQLiteDatabase db = this.getWritableDatabase();

            //execute insert statements
            db.execSQL("INSERT INTO " + TABLE_NAME + " VALUES('Zmoore','Zack','Moore');");
            db.execSQL("INSERT INTO " + TABLE_NAME + " VALUES('S_Thomas','Shannon','Thomas');");
            db.execSQL("INSERT INTO " + TABLE_NAME + " VALUES('BigG','Gabriel','Smith');");
            db.execSQL("INSERT INTO " + TABLE_NAME + " VALUES('HMoore','Harrison','Moore');");

            db.close();

            return true;
        }
        else
        {
            return false;
        }
    }

    public int numOfRowsInTable()
    {
        //Look at the database we created
        //get a readable version
        SQLiteDatabase db = this.getReadableDatabase();
        //store the number of records in the table called TABLE_NAME
        int numRows = (int) DatabaseUtils.queryNumEntries(db, TABLE_NAME);

        db.close();
        return numRows;
    }

    @SuppressLint("Range")
    public ArrayList<User> getAllRows()
    {
        //This will be used to store the info that the table returns
        ArrayList<User> listUsers = new ArrayList<User>();

        //query to get all rows and attributes from our table
        //select * means get all attributes (for specific attributes, put the attribute's name
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " ORDER BY username" + ";";

        SQLiteDatabase db = this.getReadableDatabase();

        //Execute the query. Cursor will be used to cycle through the results.
        Cursor cursor = db.rawQuery(selectQuery, null);

        //used to store attributes
        String fname;
        String lname;
        String uname;

        //if there was something returned in cursor, move the cursor to the beginning of the list
        //this lets us see if the cursor has anything in it.
        if(cursor.moveToFirst())
        {
            do
            {
                //find the username column from the returned results
                uname = cursor.getString(cursor.getColumnIndex("username"));

                //find the first name column from the returned results
                fname = cursor.getString(cursor.getColumnIndex("firstname"));

                //find the last name column from the returned results
                lname = cursor.getString(cursor.getColumnIndex("lastname"));

                //add the returned results to my list
                listUsers.add(new User(uname, fname, lname));
            }
            while (cursor.moveToNext());
        }

        db.close();

        return listUsers;
    }

    public void addNewUser(User u)
    {
        //get an instance of a writable database

        SQLiteDatabase db = this.getWritableDatabase();

        //This line is a little complicated. The sql statement should look as follows:
        //INSERT INTO Users VALUES('zmoore','Zack','Moore');

        db.execSQL("INSERT INTO " + TABLE_NAME + " VALUES('" + u.getuName() + "','" + u.getfName() + "','" + u.getlName() + "');");
    }

    //When making the getColumnIndex, must supress range
    @SuppressLint("Range")
    public ArrayList<String> getAllUsernames()
    {
        ArrayList<String> usernames = new ArrayList<String>();

        //Query to get all usernames from table
        String selectUserNames = "SELECT username FROM " + TABLE_NAME +  " ORDER BY username" + ";";
        //Get instance of a readable database and store in db
        SQLiteDatabase db = this.getReadableDatabase();

        //Execute the query. Cursor will be used to cycle results
        Cursor cursor = db.rawQuery(selectUserNames, null);

        String username;

        //If there was something returned move the cursor to the beginning of the list
        if (cursor.moveToFirst())
        {
            do {
                username = cursor.getString(cursor.getColumnIndex("username"));

                usernames.add(username);
            } while (cursor.moveToNext());
        }

        //MUST CLOSE DATABASE
        db.close();

        return usernames;
    }

    //used to delete a specific user
    //this will be passed a username because it is our primary key
    //you MUST delete off the primary key
    public void deleteUser(String uName)
    {
        //get an instance of our database
        //needs to be writable
        SQLiteDatabase db = this.getWritableDatabase();

        //create our delete command
        //DELETE FROM Users WHERE username = 'zmoor';
        db.execSQL("DELETE FROM " + TABLE_NAME + " WHERE username = '" + uName + "';");

        //close the database
        db.close();
    }

    public void updateUser(User u)
    {
        //get writable database
        SQLiteDatabase db = this.getWritableDatabase();

        //create our update command
        //needs to look like this:
        //UPDATE Users SET firstname = 'Zack', lastname = 'Moore' WHERE username = 'zmoore';
        String updateCommand = "UPDATE " + TABLE_NAME + " SET firstname = '" + u.getfName() + "', lastname = '" + u.getlName() + "' WHERE username = '" + u.getuName() + "';";
        db.execSQL(updateCommand);
        db.close();
    }
}
