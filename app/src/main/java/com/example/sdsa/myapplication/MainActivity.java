package com.example.sdsa.myapplication;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity
{
    EditText main_useridEditText;
    EditText main_passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        main_useridEditText = (EditText) findViewById(R.id.main_useridEditText);
        main_passwordEditText = (EditText) findViewById(R.id.main_passwordEditText);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClick_main_button(View v)
    {
        String userid = main_useridEditText.getText().toString();
        String password = main_passwordEditText.getText().toString();

        Intent userInfoIntent = new Intent(this, UserInfoActivity.class);
        userInfoIntent.putExtra("userid", userid);
        userInfoIntent.putExtra("password", password);
        startActivity(userInfoIntent);
    }

}
