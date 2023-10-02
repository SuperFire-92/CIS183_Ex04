package com.example.databaseexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

        addNewUserButtonEvent();
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

                User user = new User(u,l,f);

                addNewUser(user);

                displayUsers();

                //Add to database
                //Save to an arraylist
                //Display to the listview

            }
        });
    }

    public void addNewUser(User u)
    {
        userList.add(u);
    }

    public void displayUsers()
    {
        for (int i = 0; i < userList.size(); i++)
        {
            Log.d("User: ",userList.get(i).getfName());
        }
    }
}