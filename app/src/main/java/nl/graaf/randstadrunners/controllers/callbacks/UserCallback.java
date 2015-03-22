package nl.graaf.randstadrunners.controllers.callbacks;

import nl.graaf.randstadrunners.models.User;

/**
 * Created by Patrick van de Graaf on 10/03/2015.
 */
public interface UserCallback {
    public void getUserSuccess(User user);
    public void getUserFailed(String error);
}
