package BuisinessLayer.LogicalOperations;

import DataLayer.DataBase;
import CrossCuttingLayer.CRUD;
import Exceptions.FailConnectUnionAccountSystemException;

import javax.security.sasl.AuthenticationException;
import java.util.*;
import java.io.File;
import java.io.IOException;
import java.util.logging.*;
import java.text.SimpleDateFormat;
import java.util.concurrent.ConcurrentHashMap;


public class AMEDYSystem {

    public static Logger systemLogger = createLogger("AMEDY", Level.WARNING, true, true);

    private Scanner sc;

    private DataBase database;
    private Authentication authentication;

    private List<SystemManager> systemManagers;
    private ConcurrentHashMap<String ,User> allUsersLoggedIn;

    /***
     * Constructor.
     *
     * @param authentication authentication system to will use to verify users.
     */
    public AMEDYSystem(Authentication authentication) {

        this.sc = new Scanner(System.in);

        this.authentication = authentication;
        this.database = authentication.getDB();

        this.systemManagers = new ArrayList<>();
        this.allUsersLoggedIn = new ConcurrentHashMap<>();
    }

    /***
     * get Scanner.
     *
     * @return Scanner for getting system input.
     */
    public Scanner getScanner() {

        return this.sc;
    }

    /***
     * Get the number of System Manager.
     *
     * @return number of system manager in the system.
     */
    public int getSystemManagerSize() {

        return this.systemManagers.size();
    }

    /***
     * Setter for insert a new System Manager.
     *
     * @param systemManager new System Manager that need to be insert to the system.
     *
     * @return true is seceded, false otherwise.
     */
    public boolean setSystemManager(SystemManager systemManager) {

        return this.systemManagers.add(systemManager);
    }

    /***
     * initial struct at the DataBase object
     */
    public void buildDBStruct() {
        this.database.initialDBStruct();
    }

    /***
     * Signout a given user from the system.
     *
     * @param user user need to be log out from system.
     */
    public boolean logout(User user) {

        if(this.allUsersLoggedIn.containsKey(user.getKey())) {

            this.allUsersLoggedIn.remove(user.getKey());

            return true;
        }

        return false;
    }

    /***
     * Connecting to Law System.
     *
     * @return true if connection seceded, false otherwise.
     */
    public boolean connectToCountryLawSystem() throws FailConnectUnionAccountSystemException {

//        if(!CountryLawSystem.Connect()) {
//
//            throw new FailConnectUnionAccountSystemException();
//        }

        return true;  //TODO: implement this method
    }

    /***
     * Connecting to Union Accounting System.
     *
     * @return true if connection seceded, false otherwise.
     */
    public boolean connectToUnionAccountingSystem() throws FailConnectUnionAccountSystemException {

//        if(!AccountingSystem.Connect()) {
//
//            throw new FailConnectUnionAccountSystemException();
//        }

        return true;  //TODO: implement this method
    }

    /***
     * Register user to database.
     *
     * @param newUser user need to insert to the database.
     *
     * @return true if register seceded, false otherwise.
     */
    public boolean registerUserToDB(User newUser) {

        if(!checkIfUserExistInDB(newUser.getUserName())) {

            return CRUD.addUser("System", newUser);
        }

        return false;
    }

    /**
     * The method checks in the database if the inserted username inserted by the user exist in the Database.
     *
     * @param key - key for user.
     *
     * @return true - if the inserted username exist. else - false.
     */
    private boolean checkIfUserExistInDB(String key) {

        systemLogger.info("[AMEDYsystem][checkIfUserExistInDB] - entered the method.");

        systemLogger.info("[AMEDYsystem][checkIfUserExistInDB] - checks if the user " +  key + " exist in the Database.");
        List results = CRUD.select("System","User", key);

        if (results != null && results.size() >= 1) {

            systemLogger.info("[AMEDYsystem][checkIfUserExistInDB] -the inserted username '"+ key +"' by user is already exist in the system.");

            return true;
        }

        return false;
    }

    /***
     * Set the proper authorization at the DB.
     *
     * @param user user that need to add him authorization.
     * @param profile which profile (Authorization) need to insert to the given user.
     *
     * @return true if seceded, false otherwise.
     *
     * @throws AuthenticationException
     */
    public boolean setAuthorization(User user, String profile) throws AuthenticationException {

        if(!CRUD.createAuthorization("System", user.getUserName(), profile)) {
            throw new AuthenticationException();
        }

        return true;
    }

    /***
     * Remove given user from DB.
     *
     * @param systemManager
     */
    public boolean removeUser(SystemManager systemManager) {

        CRUD.deleteUser("System", systemManager);

        AMEDYSystem.systemLogger.info("[SystemController][removeUser] - assigning authorizations to the new user.");

        return true;
    }

    /**
     * Initial logger.
     *
     * @param loggerName log name
     * @param printToConsole true if print log to console required.
     * @param isDate true if add to log name the current date.
     *
     * @return Logger
     */
    private static Logger createLogger(String loggerName,Level logLevel, boolean printToConsole, boolean isDate) {

        String fileName;

        if (isDate) {
            String date = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
            fileName = String.format("%s_%s.log", loggerName, date);
        }
        else {
            fileName = String.format("%s.log", loggerName);
        }

        Logger log = Logger.getLogger(String.format("Logs/AMEDY-Logger-%s.log", new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(Calendar.getInstance().getTime()))); //TODO: initial the logger correctly
        log.setLevel(logLevel);

        FileHandler fh;

        try {
            File directory = new  File("Logs/");
            if(!directory.exists()) {
                directory.mkdir();
            }

            fh = new FileHandler(String.format("Logs/%s", fileName));
            log.addHandler(fh);

            if(!printToConsole) {
                log.setUseParentHandlers(false);
            }

            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return log;
    }

    /***
     * Login user into the system.
     *
     * @param user
     */
    public void login(User user) {

        if(user != null && !this.allUsersLoggedIn.containsKey(user.getKey())) {
            this.allUsersLoggedIn.put(user.getUserName(), user);
        }
    }

    /***
     * Retrieve user if exists.
     *
     * @param userAnswer map containing field as key and user answer as value.
     *
     * @return User if exists.
     */
    public User getUser(Map<String, String> userAnswer) {

        AMEDYSystem.systemLogger.info("[AMEDYSystem][getUser]: trying to retrive user from DB.");

        return CRUD.getUser("System", userAnswer);
    }

    /***
     * Count how many users are logged in.
     *
     * @return number of log in users.
     */
    public int getNumberOfUsers() {
        return this.allUsersLoggedIn.size();
    }
}
