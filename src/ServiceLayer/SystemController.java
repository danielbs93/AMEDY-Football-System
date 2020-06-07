package ServiceLayer;

import BuisinessLayer.LogicalOperations.AMEDYSystem;
import BuisinessLayer.LogicalOperations.SystemManager;
import Exceptions.*;

import javax.security.sasl.AuthenticationException;
import java.util.*;


public class SystemController {


    /***
     * First system initialization.
     * In this process the system will ask for System Manager Details.
     *
     * @UC 1.1
     */
    public static boolean boot(AMEDYSystem system) throws FailConnectUnionAccountSystemException, UserAddException, BadUsernameException, NotValidPasswordException, NotValidNameException {

        AMEDYSystem.systemLogger.info("[AMEDYSystem][boot]:boot started.");

        AMEDYSystem.systemLogger.info("[AMEDYSystem][boot]:trying to connect to External Systems.");

        //Union Accounting System
        AMEDYSystem.systemLogger.info("[AMEDYSystem][boot]:trying to connect to Union Accounting System.");
        system.connectToUnionAccountingSystem();

        Utils.sendAlert("[AMEDYSystem][boot]:connect to Union Accounting System successfully");

        //Country Law System
        AMEDYSystem.systemLogger.info("[AMEDYSystem][boot]:trying to connect to Country Law System.");
        system.connectToCountryLawSystem();

        Utils.sendAlert("[AMEDYSystem][boot]:connect to Country Law System successfully");

        Utils.sendAlert("[AMEDYSystem][boot]:connect to External Systems successfully");

        //build DB struct
        system.buildDBStruct();

        //SystemManager details
        AMEDYSystem.systemLogger.info("[AMEDYSystem][boot]:starting get system manager from user");
        String[] systemManagerFillDetails = {"username", "password", "verifyPassword", "name"};
        Map<String, String> userAnswer = Utils.getDetailsFromUser(system.getScanner(), systemManagerFillDetails);

        //Assign authorization
        AMEDYSystem.systemLogger.info("[AMEDYSystem][boot]:set system manager authorization");

        SystemManager systemManager = createSystemManagerFromDetails(userAnswer, system);

        //verifyDetails
        verifySystemManagerDetails(userAnswer);

        //Add user to DB
        system.registerUserToDB(systemManager);
        AMEDYSystem.systemLogger.info("[AMEDYSystem][boot]:register system manager successfully.");

        try {
            system.setAuthorization(systemManager, "System Manager");

        }
        catch (AuthenticationException autExc) {

            system.removeUser(systemManager);

            throw new UserAddException();
        }

        system.setSystemManager(systemManager);

        AMEDYSystem.systemLogger.info("[AMEDYSystem][boot]:System Manager set successfully");

        return true;
    }

    private static boolean verifySystemManagerDetails(Map<String, String> userAnswer) throws NotValidPasswordException, BadUsernameException, NotValidNameException {

        if(userAnswer == null) {
            throw new NullPointerException();
        }

        Utils.checkUserDetailsAndName(userAnswer);

        return true;
    }

    /***
     * Create System Manager user from Map form details.
     *
     * @param userDetails Map<String, String> containing field as key and answer as value.
     * @param system system to assign the System Manager to.
     *
     * @return System Manager.
     */
    private static SystemManager createSystemManagerFromDetails(Map<String, String> userDetails, AMEDYSystem system) {

        //get user details
        String systemManagerUsername = userDetails.get("username");
        String systemManagerPassword = userDetails.get("password");
        String systemManagerName = userDetails.get("name");

        return new SystemManager(systemManagerUsername, systemManagerPassword, systemManagerName, system);
    }

}
