package ServiceLayer;

import BuisinessLayer.LogicalOperations.*;
import BuisinessLayer.RecommendationSystem.RecommendSystem;
import Exceptions.*;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class SystemManagerController extends UserController {


    public SystemManagerController() {
    }

    /**
     *
     * @param systemManager
     * @param teamNameToClose
     * @return true if the team closed successfully
     * @throws ProfileDoesntExistsException
     * @throws TeamStatusException
     */
    public static boolean closeTeamPermanently(SystemManager systemManager, String teamNameToClose) throws ProfileDoesntExistsException, TeamStatusException {

        return systemManager.closeTeamPermanently(teamNameToClose);

    }

    /**
     *
     * @param systemManager
     * @param userNameToRemove
     * @return true if user removed successfully
     * @throws AssertIsNotExistException
     * @throws PermissionDeniedException
     */
    public static boolean removeUser(SystemManager systemManager,String userNameToRemove) throws AssertIsNotExistException, PermissionDeniedException, TeamStatusException {

        return systemManager.removeUser(userNameToRemove);

    }

    /**
     * U.C 8.3
     * @param systemManager
     * @return  list of complaints
     * @throws ListIsEmptyException
     */
    public static List<Complaint> showComplaint(SystemManager systemManager) throws ListIsEmptyException {

        return systemManager.showComplaints();

    }

    /**
     * U.C 8.3
     * @param systemManager
     * @param complaintNumber
     * @param answer
     * @return true if response successfully
     * @throws IllegalValueException
     */
    public static boolean responseComplaint(SystemManager systemManager, int complaintNumber, String answer) throws IllegalValueException {

        return systemManager.responseComplaint(complaintNumber,answer);
    }

    /**
     * U.C 8.4
     * @param systemManager
     * @return List of the activities of the system
     */
    public static List<String> showActivities(SystemManager systemManager) {

        return systemManager.showActivities();

    }

    /**
     * U.C 8.5
     * @param systemManager
     * @return RecommendSystem
     */
    public static RecommendSystem buildRecommendSystem(SystemManager systemManager) {

        return systemManager.buildRecommendSystem();

    }

    /***
     * Interact with the register user to build profile accordint to his choice.
     *
     * @param user user need to add a new profile.
     * @param system system the user belong.
     *
     * @return true if seceded, false otherwise.
     * @throws ProfileDoesntExistsException
     */
    private static boolean chooseProfile(User user, AMEDYSystem system) throws ProfileDoesntExistsException {

        Scanner sc = system.getScanner();

        Utils.sendAlert("insert chosen profile:");
        String chosenProfile = sc.nextLine();

        Utils.sendAlert("insert profile name:");
        String profileName = sc.nextLine();

        ProfileType profileType = EnumCreator.createProfileType(chosenProfile);

        Profile profile;

        if(profileType == ProfileType.Fan) {

            profile = new Fan(profileName);

        }
        else if(profileType == ProfileType.Referee) {

            Utils.sendAlert("insert qualification (RefereeType):");
            String qualification = sc.nextLine();

            RefereeType refereeType = EnumCreator.createRefereeType(qualification);
            profile = new Referee(profileName, refereeType);
        }
        else if(profileType == ProfileType.Player) {

            Utils.sendAlert("insert role (PlayerType):");
            String role = sc.nextLine();

            Utils.sendAlert("insert birthday:");
            String inputDate = sc.nextLine();

            Date date = new Date(inputDate);
            PlayerType playerType = EnumCreator.createPlayerType(role);

            profile = new Player(profileName, date, playerType);
        }
        else if(profileType == profileType.Coach) {

            Utils.sendAlert("insert qualification:");
            String qualification = sc.nextLine();

            Utils.sendAlert("insert role:");
            String role = sc.nextLine();

            profile = new Coach(profileName, qualification, role);
        }
        else if(profileType == profileType.TeamManager) {

            profile = new TeamManager(profileName, null); //TODO:yarden - change signature without team
        }
        else if(profileType == profileType.TeamOwner) {

            profile = new TeamOwner(profileName, null); //TODO:yarden - change signature without team
        }
        else {
            throw new ProfileDoesntExistsException();
        }

        user.addProfile(profile);

        return true;
    }



}
