package ServiceLayer;

import BuisinessLayer.LogicalOperations.*;
import Exceptions.*;

import javax.security.auth.login.FailedLoginException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static BuisinessLayer.LogicalOperations.AMEDYSystem.systemLogger;


public class GuestController extends BaseController {

    /**
     * Use-Case 2.2
     *
     * Registering a new user to the system. the new user will be a fan user.
     */
    public static User signup(Guest guest) throws BadUsernameException, NotValidPasswordException, UsernameTakenException, NotValidNameException {

        systemLogger.info("[SystemController][signup] entered signup method - taking username argument from user...");
        AMEDYSystem amedySystem = guest.getSystem();

        String[] systemManagerFillDetails = {"username", "password", "verifyPassword", "name"};
        Map<String, String> userAnswer = Utils.getDetailsFromUser(amedySystem.getScanner(), systemManagerFillDetails);

        User user = Utils.createUserFromDetails(userAnswer, amedySystem);
        systemLogger.info("[SystemController][signup] User object created.");

        Fan fanProfile = new Fan(userAnswer.get("name"));
        user.addProfile(fanProfile);
        systemLogger.info("[SystemController][signup] fan profile created for user.");
//        chooseProfile(user, system);

        guest.createNewUser(user);
        amedySystem.login(user);

        return user;
    }

    /**
     * the method responsible on logging in peoples who already have an account.
     *
     * @return
     */
    public static Profile signin(Guest guest) throws FailedLoginException, ProfileDoesntExistsException {

        systemLogger.info("[GuestController][signin] - entered method.");
        AMEDYSystem system = guest.getSystem();

        String[] systemManagerFillDetails = {"username", "password"};
        Map<String, String> userAnswer = Utils.getDetailsFromUser(system.getScanner(), systemManagerFillDetails);

        if(!userAnswer.containsKey("username") || !userAnswer.containsKey("password")) {

            throw new IllegalArgumentException("User details Map doesn't contain requirement fields.");
        }

        User user;

        try {
            systemLogger.info("[GuestController][signin] - checking guest inputs. username and password verity.");
            Utils.verifyUsername(userAnswer.get("username"));
            Utils.checkPasswordLength(userAnswer.get("password").length());

            user = guest.getUser(userAnswer);
            systemLogger.info("[GuestController][signin] - Trying to register the user to system.");
            //assign to system

        } catch (BadUsernameException e) {

            throw new FailedLoginException("failed to login with the given username and password");
        }

        if(user != null) {
            system.login(user);
        }
        else {
            throw new FailedLoginException("failed to login with the given username and password");
        }

        systemLogger.info("[GuestController][signin] - User retrieved, login done.");

        systemLogger.info("[GuestController][signin] - User choose profile.");
        String chosenProfile = letUserChooseProfile(user);

        return user.getProfile(chosenProfile);
    }


    /***
     * This function allow the signin user to choose a profile.
     *
     * @param user user that need toc choose a profile.
     *
     * @return string represent the profile.
     *
     * @throws ProfileDoesntExistsException
     */
    private static String letUserChooseProfile(User user) throws ProfileDoesntExistsException {

        AMEDYSystem system = user.getAMEDYSystem();
        List<Profile> profiles = user.getProfiles();

        System.out.println("choose profile:");

        for (int i = 0; i < profiles.size(); i++) {
            System.out.println(profiles.get(0).getType());
        }

        Scanner sc = system.getScanner();

        String input = sc.nextLine();

        if(user.getProfile(input) == null) {
            throw new ProfileDoesntExistsException("use insert not valid profile");
        }

        return input;
    }

}
