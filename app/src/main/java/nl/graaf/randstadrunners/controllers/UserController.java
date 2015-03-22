package nl.graaf.randstadrunners.controllers;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.util.AQUtility;
import com.androidquery.util.XmlDom;

import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HTTP;

import java.io.UnsupportedEncodingException;

import nl.graaf.randstadrunners.controllers.callbacks.UserCallback;
import nl.graaf.randstadrunners.models.Constants;
import nl.graaf.randstadrunners.models.User;

/**
 * Created by Patrick van de Graaf on 10/03/2015.
 */
public class UserController {

    private static UserController _instance;

    private UserController() {
    }

    public static UserController getInstance(){
        if(_instance == null){
            _instance = new UserController();
        }
        return _instance;
    }

    public void getUserInfo(String json, Context context, UserCallback userCallback){
        LoadUserInfo lui = new LoadUserInfo();
        lui.setJson(json);
        lui.setContext(context);
        lui.setCallback(userCallback);
        lui.execute();
    }

    private class LoadUserInfo extends AsyncTask<String, String, String> {

        private String json;
        private Context context;
        private UserCallback uc;

        public void setContext(Context context){
            this.context = context;
        }
        public void setJson(String json) {
            this.json = json;
        }

        public void setCallback(UserCallback uc) {
            this.uc = uc;
        }



        @Override
        protected String doInBackground(String... strings) {
            AQuery AQUERY_OBJECT = new AQuery(context);
            AQUtility.setDebug(Constants.debuggingPhase);
            AjaxCallback<XmlDom> cb = new AjaxCallback<XmlDom>() {
                @Override
                public void callback(String url, final XmlDom xml, AjaxStatus status) {
                    if (xml != null) {
                        Log.e("Error", "" + status.getError());
                        Log.e("OrdersXML", "" + xml + url);
                        Log.e("Status", "" + status.getMessage());
                        Log.e("Headers", "" + status.getHeaders());
                        uc.getUserSuccess(new User());
                    }else{
                        uc.getUserFailed("timeout");
                    }
                }
            };

            cb.header("SOAPAction", "");
            cb.header("Content-Type", "text/xml; charset=utf-8");


            StringEntity entity = null;
            try {
                entity = new StringEntity(json, HTTP.UTF_8);
            } catch (UnsupportedEncodingException e) {
                Log.e("StringEntityError", e.getLocalizedMessage());
                e.printStackTrace();
            }

            cb.param(AQuery.POST_ENTITY, entity);
            cb.url(Constants.INSIDION_BASE_URL).timeout(15000);
            cb.encoding("UTF-8");
            cb.type(XmlDom.class);
            AQUERY_OBJECT.ajax(cb);
            return null;
        }
    }
}
