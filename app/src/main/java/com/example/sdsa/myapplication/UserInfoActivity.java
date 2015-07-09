package com.example.sdsa.myapplication;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GENE on 7/8/2015.
 */
public class UserInfoActivity extends Activity
{
    public static final String server_prefix = "http://54.173.104.78:8080";
    public static final String LOGIN_URL = server_prefix + "/mcp/login_loginBtn";

    private static final String HEADER_PARAMETER_USERNAME = "mcp_user_name";
    private static final String HEADER_PARAMETER_PASSWORD = "pw";

    TextView content;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo);

        String[] param = new String[2];
        param[0] = this.getIntent().getExtras().getString("userid");
        param[1] = this.getIntent().getExtras().getString("password");

        // execute Async task
        new GetUserInfo().execute(param);
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
            printInfo(result);
        }
    }
}

