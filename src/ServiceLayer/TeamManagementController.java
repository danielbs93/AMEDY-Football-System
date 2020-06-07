package ServiceLayer;

import BuisinessLayer.LogicalOperations.*;
import Exceptions.AssertAlreadyExistException;
import Exceptions.AssertIsNotExistException;
import Exceptions.IllegalValueException;
import Exceptions.PermissionDeniedException;

import java.util.Date;

public abstract class TeamManagementController extends ProfileEventMakerController {
    public TeamManagementController() {
    }
    public static boolean addNewAssetToTeam(TeamOwner teamOwner, String typeOfAssert, Object assertObject) throws IllegalValueException, AssertAlreadyExistException, PermissionDeniedException {
        if (typeOfAssert==null || typeOfAssert==""){
            throw new IllegalValueException("Illegal assert type");
        }

        if (typeOfAssert.equals("player")){
            return  teamOwner.addNewPlayer((Player)assertObject);
        }
        if (typeOfAssert.equals("coach")){
            return teamOwner.addNewCoach((Coach) assertObject);
        }
        if (typeOfAssert.equals("stadium")){
            return teamOwner.addNewStadium((Stadium) assertObject);
        }
        if (typeOfAssert.equals("teamManager")){
            return  teamOwner.addNewTeamManager((TeamManager) assertObject);
        }
        return false;
    }

    public static boolean removeAssetFromTeam(TeamOwner teamOwner, String typeOfAssert, Object assertObject) throws IllegalValueException, AssertIsNotExistException, PermissionDeniedException {
        if (typeOfAssert==null || typeOfAssert==""){
            throw new IllegalValueException();
        }

        if (typeOfAssert.equals("player")){
            return  teamOwner.removePlayer((Player)assertObject);
        }
        if (typeOfAssert.equals("coach")){
            return teamOwner.removeCoach((Coach) assertObject);
        }
        if (typeOfAssert.equals("stadium")){
            return teamOwner.removeStadium((Stadium) assertObject);
        }
        if (typeOfAssert.equals("teamManager")){
            return  teamOwner.removeTeamManager((TeamManager) assertObject);
        }

        return false;
    }

    public static boolean editAssetInfo(TeamOwner teamOwner, String typeOfAssert, Object assertObject, String [] info) throws IllegalValueException, AssertIsNotExistException, PermissionDeniedException, AssertAlreadyExistException {

        if (typeOfAssert==null || typeOfAssert==""){
            throw new IllegalValueException();
        }
        if (info == null){
            throw new IllegalValueException();
        }

        if (typeOfAssert.equals("player")){
            String name = info[0];
            Date birthday = new Date(Integer.parseInt(info[1]),Integer.parseInt(info[2]),Integer.parseInt(info[3]));
            PlayerType playerType = null;
            if (info[4] == "goalKeeper"){
                playerType = PlayerType.GoalKeeper;
            }
            if (info[4] == "defender"){
                playerType = PlayerType.Defender;
            }
            if (info[4] == "midfielders"){
                playerType = PlayerType.Midfielders;
            }
            if (info[4] == "striker"){
                playerType = PlayerType.Striker;
            }
            if (playerType == null){
                throw new IllegalValueException("player type is not exist");
            }
            return  teamOwner.editPlayer((Player)assertObject,name, birthday, playerType);
        }
        if (typeOfAssert.equals("coach")){
            String name = info[0];
            String qualification = info[1];
            return teamOwner.editCoach((Coach) assertObject,name, qualification);
        }
        if (typeOfAssert.equals("stadium")){
            String name = info[0];
            return teamOwner.editStadium((Stadium) assertObject, name);
        }
        if (typeOfAssert.equals("teamManager")){
            String name = info[0];
            return  teamOwner.editTeamManager((TeamManager) assertObject, name);
        }
        return false;
    }
}
