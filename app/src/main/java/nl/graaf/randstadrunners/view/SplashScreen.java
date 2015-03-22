package nl.graaf.randstadrunners.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import nl.graaf.randstadrunners.R;
import nl.graaf.randstadrunners.models.Constants;


public class SplashScreen extends Activity implements View.OnClickListener{

    private WebView webView;
    private final static String CALLBACK_URL = "http://google.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPref =getPreferences(Context.MODE_PRIVATE);
        String accessToken = sharedPref.getString(getString(R.string.access_token_key), "");

        if(accessToken.equals("")){
            //Force to login on every launch.
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.removeAllCookie();

            webView = (WebView) findViewById(R.id.webView);
            //This is important. JavaScript is disabled by default. Enable it.
            webView.getSettings().setJavaScriptEnabled(true);

            getAuthorizationCode();
        }else{
            Intent i = new Intent(this, DashboardDrawerActivity.class);
            startActivity(i);
        }
    }

    @Override
    public void onClick(View v) {
        webView.setVisibility(View.VISIBLE);

        getAuthorizationCode();
    }

    private void getAuthorizationCode() {
        String authorizationUrl = Constants.LOGIN_BASE_URL + Constants.AUTH_URL + "?response_type=code&client_id=%s&redirect_uri=%s";
        authorizationUrl = String.format(authorizationUrl, Constants.CLIENT_ID, CALLBACK_URL);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith(CALLBACK_URL)) {
                    final String authCode = Uri.parse(url).getQueryParameter("code");
                    webView.setVisibility(View.GONE);
                    getAccessToken(authCode);
                    return true;
                }

                return super.shouldOverrideUrlLoading(view, url);
            }
        });

        webView.loadUrl(authorizationUrl);
    }

    private void getAccessToken(String authCode) {
        String accessTokenUrl = Constants.LOGIN_BASE_URL + Constants.ACCESS_TOKEN_URL + "?grant_type=authorization_code&code=%s&client_id=%s&client_secret=%s&redirect_uri=%s";
        final String finalUrl = String.format(accessTokenUrl, authCode, Constants.CLIENT_ID, Constants.CLIENT_SECRET, CALLBACK_URL);

        Thread networkThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    HttpClient client = new DefaultHttpClient();
                    HttpPost post = new HttpPost(finalUrl);

                    HttpResponse response = client.execute(post);

                    String jsonString = EntityUtils.toString(response.getEntity());
                    final JSONObject json = new JSONObject(jsonString);

                    System.out.println(json);
                    String accessToken = json.getString("access_token");
                    System.out.println("THIS IS THE ACCESS TOKEN " + accessToken);

                    SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString(getString(R.string.access_token_key), accessToken);
                    editor.apply();

                    Intent i = new Intent(SplashScreen.this, DashboardDrawerActivity.class);
                    startActivity(i);

                } catch (Exception e) {
                    e.printStackTrace();
                    resetUi();
                }

            }
        });

        networkThread.start();
    }

    private void resetUi(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                webView.setVisibility(View.GONE);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
