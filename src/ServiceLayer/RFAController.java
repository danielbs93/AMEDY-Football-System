package ServiceLayer;

import BuisinessLayer.LogicalOperations.*;
import BuisinessLayer.Policies.LeagueRankPolicy;
import BuisinessLayer.Policies.MatchAssignmentPolicy;
import Exceptions.AssertIsNotExistException;
import Exceptions.IllegalValueException;
import Exceptions.ProfileTypeExistException;

import java.util.ArrayList;

public class RFAController extends ProfileEventMakerController {

    public RFAController() {
    }

    public boolean createNewLeague(RepresentativeFootballAssociation rfa, LeagueRank leagueRank) throws Exception{
        if (leagueRank == null || rfa == null)
            throw new IllegalValueException();
        return rfa.openNewLeague(leagueRank);
    }

    /**
     *
     * @param rfa
     * @param league
     * @param year
     * @return a new season add successfully to the league
     * @throws IllegalValueException
     */
    public static boolean addSeasonToLeague(RepresentativeFootballAssociation rfa, League league, int year) throws IllegalValueException {
        if( rfa == null || league == null) {
            throw new IllegalValueException();
        }
        return rfa.addSeasonToLeague(league,year);
    }

    /**
     * U.C 9.3.1
     * @param rfa
     * @param refereeUser
     * @param refereeName
     * @param type
     * @return true if referee is successfully registered
     * @throws ProfileTypeExistException
     * @throws IllegalValueException
     */
    public static boolean registrationReferee(RepresentativeFootballAssociation rfa, User refereeUser , String refereeName, RefereeType type) throws ProfileTypeExistException, IllegalValueException {
        if(refereeUser == null || refereeName == null || type == null){
            throw new IllegalValueException();
        }
        return rfa.registrationReferee(refereeUser,refereeName,type);
    }

    /**
     * U.C 9.3.2
     * @param rfa
     * @param refereeName
     * @return true if referee successfully removed
     * @throws IllegalValueException
     * @throws AssertIsNotExistException
     */
    public static boolean removeReferee(RepresentativeFootballAssociation rfa,String refereeName) throws IllegalValueException, AssertIsNotExistException {
        if (rfa == null || refereeName == null){
            throw new IllegalValueException();
        }
        return rfa.removeReferee(refereeName);
    }

    public boolean updateLeagueRankPolicy(RepresentativeFootballAssociation rfa, LeagueRankPolicy oldPolicy, LeagueRankPolicy newPolicy) throws Exception {
        if (rfa == null || oldPolicy == null || newPolicy == null)
            throw new IllegalValueException();
        return rfa.updateLeagueRankPolicy(oldPolicy,newPolicy);
    }

    /**
     * This function will add the policy to the database of the system
     *The user who call this function must make sure the parameter object that will be sent, also has a creator in RFA class
     * so the method will return the correct leagueRankPolicy for this assignment, also make sure to create a class that implements
     * this interface.
     * @param rfa
     * @param object represents the parameters that defines how to calculate the rank
     * @return true if successfully the policy added to the system
     * @throws Exception
     */
    public boolean createLeagueRankPolicy(RepresentativeFootballAssociation rfa, Object object) throws Exception{
        if (rfa == null || object == null)
            throw new IllegalValueException();
        LeagueRankPolicy leagueRankPolicy = rfa.createNewLeagueRankPolicy(object);
        if (leagueRankPolicy == null)
            throw new IllegalValueException();
        return createLeagueRankPolicy(rfa,leagueRankPolicy);
    }

    /**
     *
     * @param rfa
     * @param leagueRankPolicy
     * @return true if the policy was successfully added to the system
     * @throws Exception
     */
    public boolean createLeagueRankPolicy(RepresentativeFootballAssociation rfa, LeagueRankPolicy leagueRankPolicy) throws Exception {
        if (rfa == null || leagueRankPolicy == null)
            throw new IllegalValueException();
        return rfa.addNewLeagueRankPolicyToSystem(leagueRankPolicy);
    }
    /**
     * The user who call this function must make sure the parameter object that will be sent, also has a creator in RFA class
     * so the method will return the correct leagueRankPolicy for this assignment, also make sure to create a class that implements
     * this interface.
     * @param rfa
     * @param league
     * @param object the parameter/s which the Rank Policy will get from the rfa to calculate the rank
     * @return createLeagueRankPolicy(rfa,league,leagueRankPolicy)
     */
    public boolean addNewLeagueRankPolicyToLeague(RepresentativeFootballAssociation rfa, League league, Object object) throws Exception{
        if (rfa == null || league == null || object == null)
            throw new IllegalValueException();
        LeagueRankPolicy leagueRankPolicy = rfa.createNewLeagueRankPolicy(object);
        if (leagueRankPolicy == null)
            throw new IllegalValueException();
        return addNewLeagueRankPolicyToLeague(rfa,league,leagueRankPolicy);
    }


    /**
     *
     * @param rfa
     * @param league
     * @param leagueRankPolicy
     * @return true if the league rank policy was successfully added to league by the rfa
     * @throws Exception
     */
    public boolean addNewLeagueRankPolicyToLeague(RepresentativeFootballAssociation rfa, League league, LeagueRankPolicy leagueRankPolicy) throws Exception {
        if (rfa == null || league == null || leagueRankPolicy == null)
            throw new IllegalValueException();
        return rfa.addLeagueRankPolicyToLeague(league,leagueRankPolicy);
    }

    /**
     *
     * @param rfa
     * @param matchAssignmentPolicy
     * @return true if match assignment policy eas successfully added to the system
     * @throws Exception
     */
    public boolean createMatchAssignmentPolicy(RepresentativeFootballAssociation rfa, MatchAssignmentPolicy matchAssignmentPolicy) throws Exception {
        if (rfa == null || matchAssignmentPolicy == null )
            throw new IllegalValueException();
        return rfa.createNewMatchAssignmentPolicy(matchAssignmentPolicy);
    }

    /**
     *
     * @param rfa
     * @param league
     * @param matchAssignmentPolicy
     * @return true if successfully added match assignment policy to all SeasonLeagueAssignment of this league
     * @throws Exception
     */
    public boolean addMatchAssignmentPolicyToLeague(RepresentativeFootballAssociation rfa, League league, MatchAssignmentPolicy matchAssignmentPolicy) throws Exception {
        if (rfa == null || league == null || matchAssignmentPolicy == null)
            throw new IllegalValueException();
        return rfa.addMatchAssignmentPolicyToLeagueAndSeason(league,matchAssignmentPolicy);
    }

    /**
     *
     * @param rfa
     * @param refereeName
     * @param leagueRank
     * @param seasonYear
     * @return true is referee was added successfully to a season in a particular league
     * @throws Exception
     */
    public boolean assignRefereeToSeason(
            RepresentativeFootballAssociation rfa,
            String refereeName,
            LeagueRank leagueRank,
            int seasonYear
    ) throws Exception {

        if (rfa == null || refereeName == null || refereeName.isEmpty() || leagueRank == null)
            throw new IllegalValueException();

        return rfa.assignRefereesToSeason(refereeName,leagueRank,seasonYear);

    }

    //-----Wrappers-----//

    /**
     *
     *Wrapper function for assignRefereeToSeason (
     *             RepresentativeFootballAssociation rfa,
     *             String refereeName,
     *             LeagueRank leagueRank,
     *             int seasonYear
     *             )
     */
    public boolean assignRefereeToSeason(
            RepresentativeFootballAssociation rfa,
            String refereeName,
            League league,
            Season season
    ) throws Exception {
        if (refereeName == null || refereeName.isEmpty() || season == null || league == null)
            throw new IllegalValueException();

        return assignRefereeToSeason(rfa,refereeName,league.getLeagueRank(),season.getYear());
    }

    /**
     *
     *Wrapper function for assignRefereeToSeason (
     *             RepresentativeFootballAssociation rfa,
     *             String refereeName,
     *             LeagueRank leagueRank,
     *             int seasonYear
     *             )
     */
    public boolean assignRefereeToSeason(
            RepresentativeFootballAssociation rfa,
            Referee referee,
            League league,
            Season season
    ) throws Exception {
        if (referee == null || season == null || league == null)
            throw new IllegalValueException();

        return assignRefereeToSeason(rfa,referee.getName(),league.getLeagueRank(),season.getYear());
    }


    //-----Returning Output Lists-----//

    public ArrayList<League> showAllLeagues(ArrayList<League> allLeagues) throws IllegalValueException {
        if (allLeagues == null)
            throw new IllegalValueException();
        return allLeagues;
    }

    public ArrayList<Season> showAllLeagueSeasons(ArrayList<Season> allLeagueSeasons) throws IllegalValueException {
        if (allLeagueSeasons == null)
            throw new IllegalValueException();
        return allLeagueSeasons;
    }

    public ArrayList<Referee> showAllRefeeres(ArrayList<Referee> allReferees) throws IllegalValueException {
        if (allReferees == null)
            throw new IllegalValueException();
        return allReferees;
    }

    public ArrayList<LeagueRankPolicy> showAllLeagueRankPoicies(ArrayList<LeagueRankPolicy> allLeagueRankPolicies) throws IllegalValueException {
        if (allLeagueRankPolicies == null)
            throw new IllegalValueException();
        return allLeagueRankPolicies;
    }


}
