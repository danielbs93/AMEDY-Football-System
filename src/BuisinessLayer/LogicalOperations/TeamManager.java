package BuisinessLayer.LogicalOperations;

import Exceptions.AssertAlreadyExistException;
import Exceptions.AssertIsNotExistException;
import Exceptions.IllegalValueException;
import Exceptions.PermissionDeniedException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class TeamManager extends TeamManagement {

    //Connections

    private ConcurrentHashMap<String ,Boolean> permissions;

    public TeamManager( String name, Team team)  {
        super(name, "TeamManager");

        this.team = team;
        if (team!=null) {
            try {
                this.team.addTeamManager(this);
            } catch (IllegalValueException e) {
                e.printStackTrace();
            } catch (AssertAlreadyExistException e) {
                e.printStackTrace();
            }
        }
        permissions = new ConcurrentHashMap<>();
        permissions.put("addNewPlayer",false);
        permissions.put("addNewCoach",false);
        permissions.put("addNewStadium",false);
        permissions.put("addNewTeamManager",false);
        permissions.put("removePlayer",false);
        permissions.put("removeCoach",false);
        permissions.put("removeStadium",false);
        permissions.put("removeTeamManager",false);
        permissions.put("editPlayer",false);
        permissions.put("editCoach",false);
        permissions.put("editStadium",false);
        permissions.put("editTeamManager",false);
    }

    /**
     * the method uses UseCase 3.6.1 - ViewMyPersonalInfo.
     * the method gets all the personal info of the entity.
     */
    @Override
    public synchronized List<Pair> getPersonalInfo()
    {
        this.allPersonalInfo = new ArrayList<>();
        this.allPersonalInfo.add(new Pair("Type", "TeamManager"));
        this.allPersonalInfo.add(new Pair("name", this.name));
        this.allPersonalInfo.add(new Pair("team", this.team.getTeamName()));
        return this.allPersonalInfo;
    }
    public Team getTeam() {
        return team;
    }



    public void setTeam(Team team) {
        this.team = team;
    }

    public ConcurrentHashMap<String ,Boolean> getPermissions(){
        return permissions;
    }
    public void setPermissions(ConcurrentHashMap<String ,Boolean> permissions)  {
        this.permissions = permissions;
    }
    public boolean setAccessPermission(String permissionName, boolean access) throws IllegalValueException {
        if (permissionName== null || !permissions.containsKey(permissionName)){
            throw new IllegalValueException();
        }
        return permissions.replace(permissionName,access);
    }
    /**
     * sub function for UC 6.1 - addAssert
     *
     * @param newPlayer
     */
    @Override
    public boolean addNewPlayer(Player newPlayer) throws IllegalValueException, AssertAlreadyExistException, PermissionDeniedException {
        if (permissions!=null && permissions.get("addNewPlayer")==true) {
            return super.addNewPlayer(newPlayer);
        }
        throw new PermissionDeniedException();
    }

    @Override
    public boolean addNewCoach(Coach newCoach) throws IllegalValueException, AssertAlreadyExistException, PermissionDeniedException {
        if (permissions!=null && permissions.get("addNewCoach")==true) {
            return super.addNewCoach(newCoach);
        }
        throw new PermissionDeniedException();
    }

    @Override
    public boolean addNewStadium(Stadium newStadium) throws IllegalValueException, AssertAlreadyExistException, PermissionDeniedException {
        if (permissions!=null && permissions.get("addNewStadium")==true) {
            return super.addNewStadium(newStadium);
        }
        throw new PermissionDeniedException();
    }

    @Override
    public boolean addNewTeamManager(TeamManager newTeamManager) throws IllegalValueException, AssertAlreadyExistException, PermissionDeniedException {
        if (permissions!=null && permissions.get("addNewTeamManager")==true) {
            return super.addNewTeamManager(newTeamManager);
        }
        throw new PermissionDeniedException();
    }

    /**
     * sub functions for UC 6.1 - removeAssert
     *
     * @param player
     */
    @Override
    public boolean removePlayer(Player player) throws IllegalValueException, AssertIsNotExistException, PermissionDeniedException {
        if (permissions!=null && permissions.get("removePlayer")==true) {
            return super.removePlayer(player);
        }
        throw new PermissionDeniedException();
    }

    @Override
    public boolean removeCoach(Coach coach) throws IllegalValueException, AssertIsNotExistException, PermissionDeniedException {
        if (permissions!=null && permissions.get("removeCoach")==true) {
            return super.removeCoach(coach);
        }
        throw new PermissionDeniedException();
    }

    @Override
    public boolean removeStadium(Stadium stadium) throws IllegalValueException, AssertIsNotExistException, PermissionDeniedException {
        if (permissions!=null && permissions.get("removeStadium")==true) {
            return super.removeStadium(stadium);
        }
        throw new PermissionDeniedException();

    }

    @Override
    public boolean removeTeamManager(TeamManager teamManager) throws IllegalValueException, AssertIsNotExistException, PermissionDeniedException {
        if (permissions!=null && permissions.get("removeTeamManager")==true) {
            return super.removeTeamManager(teamManager);
        }
        throw new PermissionDeniedException();
    }

    /**
     * sub function for UC 6.1 - editAssert
     *
     * @param player
     * @param name
     * @param birthday
     * @param playerType
     */
    @Override
    public boolean editPlayer(Player player, String name, Date birthday, PlayerType playerType) throws IllegalValueException, AssertIsNotExistException, PermissionDeniedException, AssertAlreadyExistException {
        if (permissions!=null && permissions.get("editPlayer")==true) {
            return super.editPlayer(player, name, birthday, playerType);
        }
        throw new PermissionDeniedException();

    }

    @Override
    public boolean editCoach(Coach coach, String name, String qualification) throws IllegalValueException, AssertIsNotExistException, PermissionDeniedException {
        if (permissions!=null && permissions.get("editCoach")==true) {
            return super.editCoach(coach, name, qualification);
        }
        throw new PermissionDeniedException();
    }

    @Override
    public boolean editStadium(Stadium stadium, String stadiumName) throws IllegalValueException, AssertIsNotExistException, PermissionDeniedException {
        if (permissions!=null && permissions.get("editStadium")==true) {
            return super.editStadium(stadium, stadiumName);
        }
        throw new PermissionDeniedException();
    }

    @Override
    public boolean editTeamManager(TeamManager teamManager, String name) throws IllegalValueException, AssertIsNotExistException, PermissionDeniedException, AssertAlreadyExistException {
        if (permissions!=null && permissions.get("editTeamManager")==true) {
            return super.editTeamManager(teamManager, name);
        }
        throw new PermissionDeniedException();
    }

}
