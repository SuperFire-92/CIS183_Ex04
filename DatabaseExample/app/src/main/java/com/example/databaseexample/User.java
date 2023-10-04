package com.example.databaseexample;

public class User
{
    private String fName;
    private String lName;
    private String uName;

    public User()
    {

    }

    public User(String u, String f, String l)
    {
        fName = f;
        lName = l;
        uName = u;
    }

    public String getfName()
    {
        return fName;
    }

    public void setfName(String f)
    {
        fName = f;
    }

    public String getlName()
    {
        return lName;
    }

    public void setlName(String l)
    {
        lName = l;
    }

    public String getuName()
    {
        return uName;
    }

    public void setuName(String u)
    {
        uName = u;
    }


}
