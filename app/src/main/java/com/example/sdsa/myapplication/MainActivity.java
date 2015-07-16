package com.example.sdsa.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity
{
    public static final String server_prefix = "http://54.173.104.78:8080";
    public static final String LOGIN_URL = server_prefix + "/mcp/login_loginBtn";

    private static final String HEADER_PARAMETER_USERNAME = "mcp_user_name";
    private static final String HEADER_PARAMETER_PASSWORD = "pw";

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

        String[] param = new String[2];
        param[0] = userid;
        param[1] = password;

        // execute Async task
        new GetUserInfo().execute(param);
    }

    public void onClick_main_signup_button(View v)
    {
        Intent signupIntent = new Intent(getApplicationContext(), SignUpActivity.class);
        startActivity(signupIntent);
    }

    public class GetUserInfo extends AsyncTask<String, Void, String>
    {
        JSONObject result;

        @Override
        protected String doInBackground(String... param)
        {
            try {
                HttpResponse response;
                HttpClient client = new DefaultHttpClient();
                HttpPost post = new HttpPost(LOGIN_URL);

                List<NameValuePair> params = new ArrayList();
                params.add(new BasicNameValuePair(HEADER_PARAMETER_USERNAME, param[0]));
                params.add(new BasicNameValuePair(HEADER_PARAMETER_PASSWORD, param[1]));
                post.setEntity(new UrlEncodedFormEntity(params));

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
                    Intent userInfoIntent = new Intent(getApplicationContext(), UserInfoActivity.class);
                    userInfoIntent.putExtra("result", result.toString());
                    startActivity(userInfoIntent);
                }
            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(), "ERROR!", Toast.LENGTH_LONG).show();
            }
        }
    }

}
