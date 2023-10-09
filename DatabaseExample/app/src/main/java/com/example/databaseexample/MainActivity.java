package com.example.databaseexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    EditText et_j_fName;
    EditText et_j_lName;
    EditText et_j_uName;
    Button btn_j_addUser;
    ListView lv_j_users;

    ArrayList<User> userList;

    DatabaseHelper dbHelper;

    ArrayList<String> usernames;
    ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_j_fName = findViewById(R.id.et_v_fName);
        et_j_lName = findViewById(R.id.et_v_lName);
        et_j_uName = findViewById(R.id.et_v_uName);
        btn_j_addUser = findViewById(R.id.btn_v_addUser);
        lv_j_users = findViewById(R.id.lv_v_users);

        userList = new ArrayList<User>();
        //Make an instance of the databaseHelper and pass it this
        dbHelper = new DatabaseHelper(this);
        //call the initializeDB() function to fill the records into our table
        dbHelper.initializeDB();
        //test to make sure the records were inserted
        //we should see 4 when we run this
        Log.d("Number of records: ", dbHelper.numOfRowsInTable() + "");
        userList = dbHelper.getAllRows();



        //for testing purposes only
        //displayUsers();

        //get all usernames from our table
        usernames = dbHelper.getAllUsernames();

        //Remember that this is a simple menu. Meaning that you can only display one string per cell.
        //If you want to display multiple things in a cell, you need a custom adapter and custom cell.
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, usernames);

        //tell the listview to use the adapter
        lv_j_users.setAdapter(adapter);

        addNewUserButtonEvent();
        deleteUserEvent();
    }

    public void addNewUserButtonEvent()
    {
        btn_j_addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Log.d("Button Press","Add User");

                String u = et_j_uName.getText().toString();
                String f = et_j_fName.getText().toString();
                String l = et_j_lName.getText().toString();

                User user = new User(u,f,l);

                //Adding user to the database and arraylist
                addNewUser(user);

                //add the username to the username arraylist
                usernames.add(u);

                //this line is easily forgotten.  You need this line to so the listview will reflect the new
                //user based off the new username added to the usernames arraylist
                adapter.notifyDataSetChanged();

                //This is for testing only
                //displayUsers();


                //Clear textboxes
                et_j_fName.setText("");
                et_j_lName.setText("");
                et_j_uName.setText("");

                //Add to database
                //Save to an arraylist
                //Display to the listview

            }
        });
    }

    public void addNewUser(User u)
    {
        //Add user to the arraylist
        userList.add(u);
        //Add user to the database
        dbHelper.addNewUser(u);
    }

    public void displayUsers()
    {
        for (int i = 0; i < userList.size(); i++)
        {
            Log.d("User: ",userList.get(i).getfName());
        }
    }

    public void deleteUserEvent()
    {
        lv_j_users.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                //call the delete function in our dbHelper
                dbHelper.deleteUser(usernames.get(i));
                //remove the user from the userList
                userList.remove(i);
                //remove the user from the usernames
                usernames.remove(i);
                //update the listview to see the changes
                adapter.notifyDataSetChanged();

                return false;
            }
        });
    }
}