package BuisinessLayer.LogicalOperations;

import BuisinessLayer.Policies.LeagueRankPolicy;
import BuisinessLayer.Policies.MatchAssignmentPolicy;
import CrossCuttingLayer.CRUD;
import Exceptions.AssertAlreadyExistException;
import Exceptions.AssertIsNotExistException;
import Exceptions.IllegalValueException;
import Exceptions.ProfileTypeExistException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class RepresentativeFootballAssociation extends ProfileEventMaker
{
    //Fields
    private FootballAssociation footballAssociation;

    public RepresentativeFootballAssociation(String name) {

        super(name,"RFA");
    }

    public RepresentativeFootballAssociation(String name ,FootballAssociation footballAssociation) {
        super(name, "RFA");
        this.footballAssociation = footballAssociation;
    }

    //-----Setters & Getters-----//

    public void setFootballAssociation(FootballAssociation footballAssociation) {
        this.footballAssociation = footballAssociation;
        this.footballAssociation.addToRFA(this);
    }

    public League getLeague(LeagueRank leagueRank) {
        return this.footballAssociation.getLeague(leagueRank);
    }

    public ArrayList<LeagueRankPolicy> getAllLeagueRankPolicies() {
        return this.footballAssociation.getLeagueRankPolicies();
    }

    public LeagueRankPolicy getLeagueRankPolicy(String policyName) throws Exception{
        if (policyName == null || policyName.isEmpty())
        throw new IllegalValueException();
        LeagueRankPolicy leagueRankPolicy = this.footballAssociation.isLeagueRankPolicyExistAndGet(policyName);
        if (leagueRankPolicy == null)
            throw new AssertIsNotExistException();
        return leagueRankPolicy;
    }

    public MatchAssignmentPolicy getMatchAssignmentPolicy(String policyName) throws Exception{
        if (policyName == null || policyName.isEmpty())
            throw new IllegalValueException();

        MatchAssignmentPolicy matchAssignmentPolicy = this.footballAssociation.getMatchAssignmentPolicy(policyName);
        if (matchAssignmentPolicy == null)
            throw new AssertIsNotExistException();
        return matchAssignmentPolicy;
    }

    /**
     *
     * @param leagueRank
     * @return true if the new league was created and inserted
     * @throws AssertAlreadyExistException if league is already exist
     */
    public boolean openNewLeague(LeagueRank leagueRank) throws Exception{
        if (leagueRank == null)
            throw new IllegalValueException();
        if (this.footballAssociation.isLeagueExist(leagueRank))
            throw new AssertAlreadyExistException("League is already exist");
        else {
            this.footballAssociation.addToLeagues(new League(leagueRank));
            return true;
        }
    }

    /**
     *
     * @param league
     * @param year
     * @return true if successfully add
     * @throws IllegalValueException
     */
    public boolean addSeasonToLeague(League league, int year) throws IllegalValueException {
        if(league == null){
            throw new IllegalValueException();
        }

        Season season = league.getSeasonByYear(year);

        if (season != null){
            throw new IllegalValueException("There is already season this year");
        }

        season = new Season(year,this.getAMEDYSystem(),league.getLeagueRankPolicy());
        SeasonLeagueAssignments seasonLeagueAssignments = new SeasonLeagueAssignments(league,season);
        league.putNewSeasonAssignment(season,seasonLeagueAssignments);

        return true;
    }

    /**
     *
     * @param refereeUser
     * @param refereeName
     * @param type
     * @return true if successfully registered
     * @throws IllegalValueException
     */
    public boolean registrationReferee(User refereeUser , String refereeName, RefereeType type) throws IllegalValueException, ProfileTypeExistException {
        if(refereeUser == null || refereeName == null || refereeName.equals("") || type == null) {
            throw new IllegalValueException();
        }

        if (refereeUser.getProfile("Referee") != null){
            throw new ProfileTypeExistException("The user is already referee in the system");
        }

        Referee referee = new Referee(refereeName,type);
        referee.setUser(refereeUser);
        refereeUser.addProfile(referee);

        ArrayList<Referee> referees = this.footballAssociation.getAllReferees();
        referees.add(referee);
        this.footballAssociation.setAllReferees(referees);

        CRUD.addReferee(this.getType(),referee);

        //TODO: how to send invitation to the referee

        return true;
    }

    /**
     *
     * @param refereeName
     * @return true if successfully removed
     * @throws IllegalValueException
     * @throws AssertIsNotExistException
     */
    public boolean removeReferee(String refereeName) throws IllegalValueException, AssertIsNotExistException {

        if(refereeName == null || refereeName.equals("")) {
            throw new IllegalValueException();
        }

        Referee referee = this.footballAssociation.getRefereeByName(refereeName);

        if(referee == null){
            throw new AssertIsNotExistException("Referee does not exist in the system");
        }

        ArrayList<Referee> referees = this.footballAssociation.getAllReferees();
        referees.remove(referee);
        referee.getMyUser().removeProfile(referee);
        this.footballAssociation.setAllReferees(referees);

        CRUD.removeReferee(this.getType(),referee);

        return true;
    }



    /**
     *Checkout that referee should be unique means to change referee name to referee id
     * @param refereeName
     * @param leagueRank
     * @param seasonYear
     * @return true if referee assigned successfully to a season
     * @throws AssertIsNotExistException if league does not exist
     * @throws AssertIsNotExistException if season does not exist
     * @throws AssertIsNotExistException if referee does not exist
     * @throws AssertAlreadyExistException if referee already assigned to this season
     */
    public boolean assignRefereesToSeason(String refereeName, LeagueRank leagueRank, int seasonYear) throws Exception {
        if (refereeName == null || leagueRank == null)
            throw new IllegalValueException();
        if (!this.footballAssociation.isLeagueExist(leagueRank))
            throw new AssertIsNotExistException("League does not exist in the system");

        League league = this.footballAssociation.getLeague(leagueRank);
        Season season = league.getSeasonByYear(seasonYear);

        if (season == null)
            throw new AssertIsNotExistException("Season does not exist in the system");

        Referee referee = this.footballAssociation.getRefereeByName(refereeName);

        if (referee == null)
            throw new AssertIsNotExistException("Referee does not exist in the system");
        if (season.isRefereeAssigned(league,referee))
            throw new AssertAlreadyExistException("Referee already assigned to this season");
        if (season.addRefereeToSeason(league,referee))
            return true;
        return false;

    }

    /**
     *
     * @param leagueRankPolicy
     * @return true if the policy was added successfully to the system
     * @throws Exception
     */
    public boolean addNewLeagueRankPolicyToSystem(LeagueRankPolicy leagueRankPolicy) throws Exception{
        if (leagueRankPolicy == null)
            throw new IllegalValueException();
        if (this.footballAssociation.isLeagueRankPolicyExist(leagueRankPolicy))
            throw new AssertAlreadyExistException("League rank policy already exist in the system");
        if (this.footballAssociation.addLeagueRankPolicy(leagueRankPolicy))
            return true;
        return false;
    }

    /**
     *
     * @param league
     * @param leagueRankPolicy
     * @return true only if the leagueRankPolicy was successfully added to the league
     * @throws Exception
     */
    public boolean addLeagueRankPolicyToLeague(League league, LeagueRankPolicy leagueRankPolicy) throws Exception {
        if (league == null || leagueRankPolicy == null)
            throw new IllegalValueException();
        if (!this.footballAssociation.isLeagueExist(league.getLeagueRank()))
            throw new AssertIsNotExistException("League is not exist in the system");
        if (league.isLeagueRankePolicyExist(leagueRankPolicy))
            throw new AssertAlreadyExistException("League Rank Policy already registered");
        if (this.footballAssociation.getLeague(league.getLeagueRank()).setLeagueRankPolicy(leagueRankPolicy))
            return true;
        return false;
    }

    /**
     * This function called by the rfa controller
     * User should fill and create the class that implements leagueRankPolicy and define it here
     * @param object
     * @return league rank policy based on the paramteres which will define the raking policy in the league
     * @throws Exception
     */
    public LeagueRankPolicy createNewLeagueRankPolicy(Object object) throws Exception{
        if (object == null)
            throw new IllegalValueException();
        if (object instanceof LeagueRankPolicy)
            return (LeagueRankPolicy) object;
        return null;
    }

    /**
     *
     * @param oldPolicy
     * @param newPolicy
     * @return true if the old policy was updated and all the leagues that had this policy are updated with the new policy
     * @throws Exception
     */
    public boolean updateLeagueRankPolicy(LeagueRankPolicy oldPolicy, LeagueRankPolicy newPolicy) throws Exception{
        if (oldPolicy == null || newPolicy == null)
            throw new IllegalValueException();

        if (this.footballAssociation.isLeagueRankPolicyExist(newPolicy))
            throw new AssertAlreadyExistException("New League rank policy already exist");

        return this.footballAssociation.updateLeagueRankPolicy(oldPolicy,newPolicy);
    }

    /**
     *
     * @param matchAssignmentPolicy
     * @return true if match assignment policy added to the system
     * @throws Exception
     */
    public boolean createNewMatchAssignmentPolicy(MatchAssignmentPolicy matchAssignmentPolicy) throws Exception{
        if (matchAssignmentPolicy == null)
            throw new IllegalValueException();

        if (this.footballAssociation.isMatchAssignmentPolicyExist(matchAssignmentPolicy))
            throw new AssertAlreadyExistException("This match assignment policy already exist in the system");

        return this.footballAssociation.addMatchAssignmentPolicy(matchAssignmentPolicy);
    }

    /**
     *
     * @param league
     * @param matchAssignmentPolicy
     * @return true if match assignment policy added to all SeasonLeagueAssignment that related to this league
     * @throws Exception
     */
    public boolean addMatchAssignmentPolicyToLeagueAndSeason(League league, MatchAssignmentPolicy matchAssignmentPolicy) throws Exception{
        if (league == null || matchAssignmentPolicy == null)
            throw new IllegalValueException();

        if (!this.footballAssociation.isLeagueExist(league.getLeagueRank()))
            throw new AssertIsNotExistException("League does not exist");

        if (!this.footballAssociation.isMatchAssignmentPolicyExist(matchAssignmentPolicy))
            throw new AssertIsNotExistException("Match assignment policy does not exist in the system");

        return this.footballAssociation.setMatchAssignmentPolicyToAllSeasonLeagues(league,matchAssignmentPolicy);
    }

    //-----Wrapper functions-----//
    /**
     * Wrapper function for assignRefereesToSeason(String refereeName, LeagueRank leagueRank, int seasonYear)
     */
    public boolean assignRefereesToSeason(String refereeName, LeagueRank leagueRank, Season season) throws Exception {
        if (season == null)
            throw new IllegalValueException();
        if (assignRefereesToSeason(refereeName,leagueRank,season.getYear()))
            return true;
        return false;
    }

    /**
     * Wrapper function for assignRefereesToSeason(String refereeName, LeagueRank leagueRank, int seasonYear)
     */
    public boolean assignRefereesToSeason(String refereeName, League league, Season season) throws Exception {
        if (league == null || season == null)
            throw new IllegalValueException();
        if (assignRefereesToSeason(refereeName,league.getLeagueRank(),season.getYear()))
            return true;
        return false;
    }

    /**
     * Wrapper function for assignRefereesToSeason(String refereeName, LeagueRank leagueRank, int seasonYear)
     */
    public boolean assignRefereesToSeason(String refereeName, League league, int seasonYear) throws Exception {
        if (league == null)
            throw new IllegalValueException();
        if (assignRefereesToSeason(refereeName,league.getLeagueRank(),seasonYear))
            return true;
        return false;
    }


    /**
     * This function built only for unit testing - function openNewLeague
     * @param leagueRank
     * @return true if league added successfully
     */
    public boolean addNewLeagueUnitTesting(LeagueRank leagueRank) {
        try {
            if (openNewLeague(leagueRank))
                return true;
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    /**
     * the method uses UseCase 3.6.1 - ViewMyPersonalInfo.
     * the method gets all the personal info of the entity.
     */
    @Override
    public synchronized List<Pair> getPersonalInfo() {
        this.allPersonalInfo = new ArrayList<>();
        this.allPersonalInfo.add(new Pair("Type", "RFA"));
        this.allPersonalInfo.add(new Pair("name", this.name));
        return this.allPersonalInfo;
    }
}
