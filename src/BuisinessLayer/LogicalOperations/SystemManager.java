package BuisinessLayer.LogicalOperations;


import BuisinessLayer.RecommendationSystem.RecommendSystem;
import Exceptions.*;
import CrossCuttingLayer.CRUD;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class SystemManager extends User
{

    private String name;

    public SystemManager(String userName, String password, String name, AMEDYSystem amedySystem)
    {
        super(userName,password,amedySystem);

        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    protected String getUserType() {
        return "SystemManager";
    }

    /**
     * U.C 8.1 - close team permanently, without option to reopen
     * @param teamName to close
     * @return true if succeeded, false otherwise.
     * @throws AssertIsNotExistException
     * @throws TeamStatusException
     */
    public boolean closeTeamPermanently(String teamName) throws ProfileDoesntExistsException, TeamStatusException {
        List<Team> teams = CRUD.select(this.getUserType(),"Team",teamName);
        if (teams.size() == 0){
            throw new ProfileDoesntExistsException("Team does not exist");
        }

        Team team = teams.get(0);
        if (team.getStatus().toString().equals(TeamStatus.CLOSE_PERMANENTLY.toString())){
            throw new TeamStatusException("The team is already close permanently");
        }

        //TODO: Send notifications to team management

        //Move the team from active/close to closePermanently in the database
        if(!CRUD.removeTeam(this.getUserType(),team,team.getStatus().toString())){
            return false;
        }
        team.setStatus(TeamStatus.CLOSE_PERMANENTLY);
        if(!CRUD.addTeam(this.getUserType(),team,"archivedteam")){
            return false;
        }
        AMEDYSystem.systemLogger.info("[SystemManager][closeTeamPermanently] - systemManager closed team.");
        return true;
    }

    /**
     * U.C 8.2 - remove user by the systemManager by the constraint
     * @param userName
     * @return  true if succeeded, false otherwise.
     * @throws AssertIsNotExistException
     * @throws PermissionDeniedException
     */
    public boolean removeUser(String userName) throws AssertIsNotExistException, PermissionDeniedException, TeamStatusException {
        List<User> user = CRUD.selectUser(this.getUserType(),userName);
        if (user.size() == 0){
            throw new AssertIsNotExistException("Username does not exist");
        }
        ArrayList<Profile> userProfiles = user.get(0).getProfiles();

        //Check if there is at least one systemManager
        if (user.get(0) instanceof SystemManager){
            if(this.getAMEDYSystem().getSystemManagerSize() <= 1){
                throw new PermissionDeniedException("There must be at least one system manager");
            }
        }

        for (Profile userProfile:userProfiles) {
            //Check if there is at least one teamOwner to this team
            if (userProfile.ClassType.toLowerCase().equals("teamowner")){

                if(((TeamOwner)userProfile).getTeam().getTeamOwners().size() == 1){
                    if (!((TeamOwner)userProfile).getTeam().getTeamOwners().get(0).closeTeam()){
                        return false;
                    }
//                    throw new PermissionDeniedException("There must be at least one team owner for team");
                }

                if(!CRUD.removeProfile(this.getUserType(),userProfile,null)) {
                    return false;
                }
            }
            else{
                if(!CRUD.removeProfile(this.getUserType(),userProfile,null)) {
                    return false;
                }
            }

        }
        CRUD.removeUser(this.getUserType(),user.get(0),null);
        AMEDYSystem.systemLogger.info("[SystemManager][removeUser] - systemManager removed user.");
        return true;

    }


    public List<Complaint> showComplaints() throws ListIsEmptyException {

        List<Complaint> complaints = CRUD.select(this.getUserType(),"complaint",null);
        if (complaints == null || complaints.size() == 0){
            throw new ListIsEmptyException("No complaints in the system");
        }

        return complaints;
    }

    public boolean responseComplaint(int complaintNumber, String answer) throws IllegalValueException {

        List<Complaint> complaints = CRUD.select(this.getUserType(),"complaint",null);
        // 0 - close option, other choose a complaint so that an index = numOfComplaint -1
        if (complaintNumber == 0){
            return true;
        }

        if(complaintNumber > complaints.size()){
            throw new IllegalValueException();
        }

        if (answer == null || answer.equals("")) {
            throw new IllegalValueException();
        }

        complaints.get(complaintNumber-1).setSystemManagerAnswer(answer);
        complaints.get(complaintNumber-1).setSystemManagerHandle(this);

        return true;
    }


    /**
     * U.C 8.4 - System manager watch the activities of the system by reading the log file
     * @return
     */
    public List<String> showActivities()
    {
        List<String> logList = new ArrayList<>();
        try{
            FileInputStream fstream = new FileInputStream("Logs/logger");
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String strLine;
            int index = 0;
            /* read log line by line */
            while ((strLine = br.readLine()) != null)   {
                logList.add(index++,strLine);
            }
            fstream.close();
            AMEDYSystem.systemLogger.info("[SystemManager][showActivities] - systemManager watched system activities.");
        } catch (Exception e) {
            AMEDYSystem.systemLogger.warning("[SystemManager][showActivities] - Error: " + e.getMessage());
        }

        return logList;
    }

    /**
     * U.C 8.5 - System manager build a recommend system
     * @return
     */
    public RecommendSystem buildRecommendSystem()
    {
        RecommendSystem recommendSystem = new RecommendSystem();
        AMEDYSystem.systemLogger.info("[SystemManager][buildRecommendSystem] - systemManager developed a recommendation system.");
        return recommendSystem;
    }
}
