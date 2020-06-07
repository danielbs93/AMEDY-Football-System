package BuisinessLayer.LogicalOperations;

import CrossCuttingLayer.CRUD;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class Fan extends Profile {

    //Fields
    private List<ProfileEventMaker> fanObservablesList;
    private List<Match> matchObservablesList;
    private ConcurrentHashMap<Integer, Complaint> fanComplaints;
    private List<String> SearchHistory;
    protected List<Pair> allPersonalInfo;

    public Fan(String name) {
        super(name,"Fan");
        this.fanObservablesList = new LinkedList<>();
        this.SearchHistory = new ArrayList<>();
        this.matchObservablesList = new ArrayList<>();
        this.fanComplaints = new ConcurrentHashMap<>();
    }



    public void update(ProfileEventMaker profileEventMaker, Object arg)
    {

    }

    /**
     * getter method
     * @return List of all the EventMakers the fan follows.
     */
    public List<ProfileEventMaker> getFollowingList()
    {
        return this.fanObservablesList;
    }

    /**
     * UseCase 3.2 - follow a new Event Maker.
     * the methods add a new Event Maker observable to the fan list to follow after.
     * @param profileEventMaker - instance of Event Maker to follow after.
     * @return true - if adding the succeeded. else - return false.
     */
    public boolean addNewEventMaker(ProfileEventMaker profileEventMaker)
    {
        boolean isAddedToList = false;
        if(!this.fanObservablesList.contains(profileEventMaker))
        {
            this.fanObservablesList.add(profileEventMaker);
            isAddedToList = true;
        }

        if(isAddedToList) {
            boolean isAddedToDB = CRUD.addNewFollowers(this, profileEventMaker);
            if (isAddedToDB) {
                return true;
            }
        }
        return false;
    }

    /**
     * the method remove entity from the fan observables list.
     * @param entity - entity to delete from the fan observables list.
     * @return true - if succeeded removing the the entity from the observables list. else - return false.
     */
    public boolean removeFollower(Object entity)
    {
        boolean isDeletedFromList;
        boolean isDeletedFromDB;
        if(entity instanceof ProfileEventMaker)
        {
            ProfileEventMaker profileEventMaker = (ProfileEventMaker) entity;
            isDeletedFromList = this.fanObservablesList.remove(profileEventMaker);
            if (!isDeletedFromList) {
                return false;
            }
            isDeletedFromDB = CRUD.deleteFromFollowersTable(this, profileEventMaker);
            if (!isDeletedFromDB) {
                addNewEventMaker(profileEventMaker);
                return false;
            }
            return true;
        }
        else if(entity instanceof Match)
        {
            Match match = (Match) entity;
            isDeletedFromList = this.matchObservablesList.remove(match);
            if (!isDeletedFromList) {
                return false;
            }
            isDeletedFromDB = CRUD.deleteFromFollowersTable(this, match);
            if (!isDeletedFromDB) {
                addNewMatchToFollow(match);
                return false;
            }
            return true;
        }
        return false;
    }

    /**
     * the method checks if the fan contains the current event maker.
     * @param profileEventMaker - the current event maker which the the method checks if the fan already follows after.
     * @return true - if the fan follow after the current event maker. else - return false.
     */
    public boolean isFollowAfterPEM(ProfileEventMaker profileEventMaker)
    {
        boolean isExistInFollowersList = this.fanObservablesList.contains(profileEventMaker);

        List<Pair> searchResults = (ArrayList<Pair>) CRUD.select("followers", this, profileEventMaker);
        if(searchResults.size() == 1 && isExistInFollowersList)
        {
            return true;
        }
        return false;
    }


    /**
     * UseCase 3.3 - follow after a new Match.
     * the methods add a new Match observable to the fan list to follow after.
     * @param match - instance of match to follow after.
     * @return true - if adding the succeeded. else - return false.
     */
    public boolean addNewMatchToFollow(Match match)
    {
        boolean isAddedToList = false;
        if(!this.matchObservablesList.contains(match))
        {
            this.matchObservablesList.add(match);
            isAddedToList = true;
        }

        if(isAddedToList) {
            boolean isAddedToDB = CRUD.addNewFollowers(this, match);
            if (isAddedToDB) {
                return true;
            }
        }
        return false;
    }
    /**
     * the method checks if the fan contains the given match.
     * @param match = the current match which the method checks if the fan already follow after.
     * @return true - if the fan follow after the current match. else - return false.
     */
    public boolean isFollowAfterMatch(Match match)
    {
        boolean isExistInMatchesList = this.matchObservablesList.contains(match);
        List<Pair> searchResults = (ArrayList<Pair>) CRUD.select("followers", this, match);
        if(searchResults.size() == 1 && isExistInMatchesList)
        {
            return true;
        }
        return false;
    }

    /**
     * the method add thw new complaint to the fan.
     * @return
     */
    public boolean addComplaintToFan(Complaint complaint)
    {
        if(isContainComplaint(complaint))
        {
            return false;
        }
        this.fanComplaints.put(complaint.getComplaintID(), complaint);

        if(!CRUD.addNewComplaint(complaint))
        {
            this.fanComplaints.remove(complaint.getComplaintID());
            return false;
        }
        return true;
    }

    /**
     * them method checks if a certain complaint filled by this fan.
     * the method checks it by the complaintID from the given complaint.
     * @param complaint - int. the given complaintID.
     * @return true - if the complaint filled by this fan. else - return false.
     */
    public boolean isContainComplaint(Complaint complaint)
    {
        boolean inExistInComplaintMap = false;
        if(this.fanComplaints.containsKey(complaint.getComplaintID()) && complaint.getFanComplainerID() == this.getId())
        {
            inExistInComplaintMap = true;
        }

        if(inExistInComplaintMap)
        {
            int fanComplainerIDFromDB = CRUD.checkWhoFilledComplaint(complaint);
            if(fanComplainerIDFromDB == complaint.getFanComplainerID() && fanComplainerIDFromDB == this.getId())
            {
                return true;
            }
        }
        return false;
    }

    /**
     * the method uses UseCase 3.6.1 - ViewMyPersonalInfo.
     * the method gets all the personal info of the entity.
     */
    public synchronized List<Pair> getPersonalInfo()
    {
        this.allPersonalInfo = new ArrayList<>();
        this.allPersonalInfo.add(new Pair("Type", "Fan"));
        this.allPersonalInfo.add(new Pair("name", this.name));
        return this.allPersonalInfo;
    }

}
