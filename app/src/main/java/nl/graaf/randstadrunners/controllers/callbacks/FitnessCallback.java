package nl.graaf.randstadrunners.controllers.callbacks;

import java.util.ArrayList;

import nl.graaf.randstadrunners.models.FeedActivity;

/**
 * Created by Patrick van de Graaf on 24/03/2015.
 */
public interface FitnessCallback {
    public void getFeedSuccess(ArrayList<FeedActivity> activities);
    public void getFeedFailed(String error);
}
