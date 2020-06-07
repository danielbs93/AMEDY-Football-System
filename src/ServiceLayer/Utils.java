package ServiceLayer;

import BuisinessLayer.LogicalOperations.AMEDYSystem;
import BuisinessLayer.LogicalOperations.User;
import Exceptions.BadUsernameException;
import Exceptions.NotValidNameException;
import Exceptions.NotValidPasswordException;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static BuisinessLayer.LogicalOperations.AMEDYSystem.systemLogger;

public class Utils {


    /**
     * Raise the given message to the BuisinessLayer.LogicalOperations.User.
     * @param message
     */
    public static void sendAlert(String message) {
        AMEDYSystem.systemLogger.info(String.format("[SendAlert]: user got message: %s", message));
        System.out.println(message);
        //TODO: raise a alert to user(WEB\GUI)
    }

    /***
     * Create user object from given details.
     *
     * @param userDetails Map containing key-Field : value:user input.
     * @param system system to attach to user.
     *
     * @return true if creation seceded, false otherwise.
     *
     * @throws BadUsernameException
     * @throws NotValidNameException
     */
    public static User createUserFromDetails(Map<String, String> userDetails, AMEDYSystem system) throws BadUsernameException, NotValidPasswordException, NotValidNameException {

        if(userDetails == null) {
            throw new NullPointerException();
        }

        checkUserDetailsAndName(userDetails);

        return new User(userDetails.get("username"), userDetails.get("password"), system);
    }

    /**
     * Get details from user. get String Array with all the relevant fields
     * and ask the  user field by field what his answer.
     * return String array with all user answer.
     *
     * @param systemManagerDetails String array containing all details required.
     *
     * @return string array with user answers.
     */
    public static Map getDetailsFromUser(Scanner sc, String[] systemManagerDetails) {

        Map<String, String> details = new HashMap<String,String>();

        for(int i = 0 ; i < systemManagerDetails.length; i++) {

            System.out.println(String.format("Please enter %s:", systemManagerDetails[i]));
            String userInput = sc.nextLine();

            details.put(systemManagerDetails[i], userInput);
        }

        return details;
    }

    /**
     * The method checks if the inserted password by the user is at list 6 characters long and he entered the password twice correctly.
     *
     * @param password - String. the first time the user entered his password.
     * @param reEnterPassword - String. the second time the user entered his password.
     *
     * @return false - if the the password is invalid, else - return true.
     */
    public static boolean verifyPassword(String password, String reEnterPassword) throws NotValidPasswordException {

        //password must be more than 5 chars
        if(!checkPasswordLength(password.length())) {

            systemLogger.info("[SystemController][verifyPassword] - passwords inserted by the users is too short.");
            System.out.println("password have to be at list 6 characters long.");

            throw new NotValidPasswordException("Passwords inserted by the users is too short.");
        }

        //Check if both password are the same
        if(!password.equals(reEnterPassword))
        {
            systemLogger.info("[SystemController][verifyPassword] - passwords inserted by the users doesnt match.");

            throw new NotValidPasswordException("Passwords inserted by the users doesnt match.");
        }

        return true;
    }


    /***
     * Check valid length of password.
     *
     * @param passwordLength number of chars in password.
     *
     * @return true of password valid, false otherwise
     */
    public static boolean checkPasswordLength(int passwordLength) {

        final int MIN_PASSWORD_LENGTH = 6;

        return MIN_PASSWORD_LENGTH < passwordLength;
    }

    /**
     * The method checks if the name inserted bvy the user is at list 2 characters and conatins only a-zA-Z characters.
     *
     * @param name - String. the name inserted by the user.
     *
     * @return false - if the name is invalid, else - return true.
     */
    private static boolean verifyName(String name) throws NotValidNameException {

        //check name length
        if(name.length() <= 1)
        {
            AMEDYSystem.systemLogger.info("[SystemController][verifyName] - name inserted by the users is too short.");

            throw new NotValidNameException("Name have to be at list 2 characters long.");
        }

        //check name letters
        if(!(name.matches("[a-zA-Z ]+")))
        {
            AMEDYSystem.systemLogger.info("[SystemController][verifyName] - name inserted by the users contains other characters than a-zA-Z.");
            System.out.println("name have to be only characters a-zA-Z.");

            throw new NotValidNameException("Name have to be only characters a-zA-Z.");
        }

        return true;
    }

    /**
     *the method checks if the username inserted by the user is at list 4 characters long and doesnt contains spaces.
     *
     * @param username - String. the username inserted by the user.
     *
     * @return false - if the username is invalid. else - return true.
     */
    public static boolean verifyUsername(String username) throws BadUsernameException {

        //Check username length
        if(username.length() < 4 )
        {
            systemLogger.info("[SystemController][verifyUsername] - username inserted by user was too short.");

            throw new BadUsernameException("Username inserted by user was too short.");
        }

        //check username letters
        if(!(username.matches("^[a-zA-Z0-9]+$")))
        {
            systemLogger.info("[SystemController][verifyUsername] - username inserted by user included spaces.");

            throw new BadUsernameException("username is too short or username contain non alpha-beta characters.");
        }

        return true;
    }


    public static boolean checkBasicUserDetails(Map<String, String> userDetails) throws BadUsernameException, NotValidPasswordException {

        if(userDetails == null) {
            throw new NullPointerException();
        }

        //check keys existence
        if(!userDetails.containsKey("username") ||
                !userDetails.containsKey("password") ||
                !userDetails.containsKey("verifyPassword")) {

            throw new IllegalArgumentException("User details Map doesn't contain requirement fields(username, password, verifyPassword).");
        }

        //Check validation of username
        verifyUsername(userDetails.get("username"));

        //Check validation of password
        verifyPassword(userDetails.get("password"), userDetails.get("verifyPassword"));

        return true;
    }

    public static boolean checkUserDetailsAndName(Map<String, String> userDetails) throws BadUsernameException, NotValidPasswordException, NotValidNameException {

        if(userDetails == null) {
            throw new IllegalArgumentException();
        }

        checkBasicUserDetails(userDetails);

        if(!userDetails.containsKey("name")) {
            throw new IllegalArgumentException("User details Map doesn't contain requirement 'name' field.");
        }
        verifyName(userDetails.get("name"));

        return true;
    }
}
