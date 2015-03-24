package nl.graaf.randstadrunners.controllers;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.util.AQUtility;
import com.androidquery.util.XmlDom;

import java.util.ArrayList;

import nl.graaf.randstadrunners.controllers.callbacks.FitnessCallback;
import nl.graaf.randstadrunners.models.Constants;
import nl.graaf.randstadrunners.models.FeedActivity;

/**
*
 * Created by Patrick van de Graaf on 24/03/2015.
 *
 */

public class FitnessController {

    private static FitnessController _instance;

    private FitnessController() {
    }

    public static FitnessController getInstance(){
        if(_instance == null){
            _instance = new FitnessController();
        }
        return _instance;
    }

    public void getUserInfo( Context context, FitnessCallback fitnessCallback){
        LoadFeed lf = new LoadFeed();
        lf.setContext(context);
        lf.setCallback(fitnessCallback);
        lf.execute();
    }

    private class LoadFeed extends AsyncTask<String, String, String> {

        private Context context;
        private FitnessCallback fc;

        public void setContext(Context context){
            this.context = context;
        }

        public void setCallback(FitnessCallback fc) {
            this.fc = fc;
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
                        Log.e("XML", "" + xml + url);
                        Log.e("Status", "" + status.getMessage());
                        Log.e("Headers", "" + status.getHeaders());
                        fc.getFeedSuccess(new ArrayList<FeedActivity>());
                    }else{
                        fc.getFeedFailed("timeout");
                    }
                }
            };

            cb.header("SOAPAction", "");
            cb.header("Content-Type", "text/xml; charset=utf-8");

            cb.url(Constants.BASE_URL + Constants.FITNESS_URL).timeout(15000);
            cb.encoding("UTF-8");
            cb.type(XmlDom.class);
            AQUERY_OBJECT.ajax(cb);
            return null;
        }
    }

}
