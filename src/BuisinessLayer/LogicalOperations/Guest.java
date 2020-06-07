package BuisinessLayer.LogicalOperations;


import CrossCuttingLayer.CRUD;
import Exceptions.UsernameTakenException;

import java.util.Map;

public class Guest extends BaseFunctionality {

    //Fields


    /***
     * Constructor.
     *
     * @param system System which the user connected.
     */
    public Guest(AMEDYSystem system)
    {
        super(system);
    }

    /***
     * Create a new given user in the system.
     *
     * @param newUser new user that need to by insert to DB.
     *
     * @return user if insert seceded.
     *
     * @throws UsernameTakenException
     */
    public synchronized User createNewUser(User newUser) throws UsernameTakenException {

        if(newUser == null) {
            throw new NullPointerException();
        }

        if(checkUsernameAvailability(newUser.getUserName())) {

            CRUD.addUser("Guest", newUser);

            return newUser;
        }

        throw new UsernameTakenException();
    }

    /***
     *
     * Get the system the current guest initial with.
     *
     * @return Guest's AMEDYSystem.
     */
    public AMEDYSystem getSystem() {
        return this.system;
    }

    /***
     * Check the database if username is available or already in use.
     *
     * @param username
     *
     * @return
     */
    private boolean checkUsernameAvailability(String username) {

        AMEDYSystem.systemLogger.info("[Guest][checkUsername]: check username availability");

        boolean isUsernameExists = CRUD.checkUsernameAvailability("Guest", username);

        return isUsernameExists;
    }

    /***
     *
     * @param userAnswer
     *
     * @return User according to detaild specified, null otherwise.
     */
    public User getUser(Map<String, String> userAnswer) {

        User user = this.system.getUser(userAnswer);

        return user;
    }

}
