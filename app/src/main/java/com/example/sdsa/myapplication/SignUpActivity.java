package com.example.sdsa.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GENE on 7/15/2015.
 */
public class SignUpActivity extends Activity
{
    public static final String server_prefix = "http://54.173.104.78:8080";
    public static final String SIGNUP_URL = server_prefix + "/mcp/signup";

    public static final String HEADER_TOKEN_NAME = "token_name";
    public static final String HEADER_CONTENT_TYPE_NAME = "Content-Type";
    public static final String HEADER_TOKEN = "samsung18eba1ff45jk7858b8ae88a77fa30we";
    public static final String HEADER_CONTENT_TYPE = "application/json";

    EditText signup_firstname;
    EditText signup_lastname;
    EditText signup_asc_no;
    EditText signup_mcp_user_name;
    EditText signup_email;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        signup_firstname = (EditText) findViewById(R.id.signup_firstname);
        signup_lastname = (EditText) findViewById(R.id.signup_lastname);
        signup_asc_no = (EditText) findViewById(R.id.signup_asc_no);
        signup_mcp_user_name = (EditText) findViewById(R.id.signup_mcp_user_name);
        signup_email = (EditText) findViewById(R.id.signup_email);
    }

    public void onClick_signup_button(View v)
    {
        String firstname = signup_firstname.getText().toString();
        String lastname = signup_lastname.getText().toString();
        String asc_no = signup_asc_no.getText().toString();
        String mcp_user_name = signup_mcp_user_name.getText().toString();
        String email = signup_email.getText().toString();

        String[] param = new String[5];
        param[0] = firstname;
        param[1] = lastname;
        param[2] = asc_no;
        param[3] = mcp_user_name;
        param[4] = email;

        // execute Async task
        new Signup().execute(param);
    }

    public class Signup extends AsyncTask<String, Void, String>
    {
        JSONObject result;

        @Override
        protected String doInBackground(String... param)
        {
            try {
                HttpResponse response;
                HttpClient client = new DefaultHttpClient();
                HttpPost post = new HttpPost(SIGNUP_URL);

                post.setHeader(HEADER_TOKEN_NAME, HEADER_TOKEN);
                post.setHeader(HEADER_CONTENT_TYPE_NAME, HEADER_CONTENT_TYPE);

                JSONObject object = new JSONObject();
                try {
                    object.put("firstname", param[0]);
                    object.put("lastname", param[1]);
                    object.put("asc_no", param[2]);
                    object.put("mcp_user_name", param[3]);
                    object.put("email", param[4]);
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "ERROR!", Toast.LENGTH_LONG).show();
                }
                post.setEntity(new StringEntity(object.toString(), "UTF8"));
                response = client.execute(post);

                String json = EntityUtils.toString(response.getEntity(), "UTF-8");
                JSONObject jsonObject = new JSONObject(json);

                result = jsonObject;
            } catch (Exception e) {
                result = null;
            }
            return null;
        }

        @Override
        protected void onPostExecute(String param)
        {
            try {
                if (result == null || result.getString("success") == "false") {
                    Toast.makeText(getApplicationContext(), result.getString("msg"), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "SUCCESS!", Toast.LENGTH_LONG).show();
                    finish();
                }
            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(), "ERROR!", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onBackPressed()
    {
        finish();
    }
}
