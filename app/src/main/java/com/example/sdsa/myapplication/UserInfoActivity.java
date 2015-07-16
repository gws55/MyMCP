package com.example.sdsa.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by GENE on 7/8/2015.
 */
public class UserInfoActivity extends Activity
{
    TextView content;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo);

        try {
            JSONObject param = new JSONObject(this.getIntent().getStringExtra("result"));
            printInfo(param);
        }
        catch (JSONException e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    protected void printInfo(JSONObject result)
    {
        content = (TextView) findViewById(R.id.userInfo_content);
        try {
            Toast.makeText(getApplicationContext(), result.getString("msg"), Toast.LENGTH_LONG).show();

            JSONObject res = result.getJSONObject("result");
            String info = "Name: " + res.getString("firstname") + " " + res.getString("lastname") + "\n" +
                    "Email: " + res.getString("email") + "\n" +
                    "Role: " + res.getString("role_cd");
            content.setText(info);
        } catch (Exception e) {
            content.setText("ERROR!");
        }
    }

    @Override
    public void onBackPressed()
    {
        finish();
    }

}

