package AcceptanceTesting;

import BuisinessLayer.LogicalOperations.*;
import BuisinessLayer.Policies.LeagueRankPolicy;
import BuisinessLayer.Policies.LeagueRankPolicyStub;
import BuisinessLayer.Policies.MatchAssignmentPolicyStub;
import CrossCuttingLayer.CRUD;
import DataLayer.DataBaseReturnTrueAndAdding;
import Exceptions.AssertAlreadyExistException;
import Exceptions.AssertIsNotExistException;
import Exceptions.IllegalValueException;
import Exceptions.ProfileTypeExistException;
import ServiceLayer.RFAController;
import UnitTesting.myFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.ConnectException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class RfaAcceptanceTest {

    //Fields
    private static DataBaseReturnTrueAndAdding database;
    private static RFAController rfaController;
    private static RepresentativeFootballAssociation representativeFootballAssociation;
    private static User rfaUser;
    private static User refereeUser;
    private static User willBeRefereeUser;
    private static FootballAssociation footballAssociation;
    private static League league;
    private static Season season;
    private static Season season1;
    private static Season season2;
    private static SeasonLeagueAssignments seasonLeagueAssignments;
    private static SeasonLeagueAssignments seasonLeagueAssignments1;
    private static SeasonLeagueAssignments seasonLeagueAssignments2;
    private static Referee referee1;
    private static Referee referee2;
    private static Referee referee3;


    @BeforeEach
    void setUp() throws ConnectException {

        //Initializing
        rfaController = new RFAController();
        database = new DataBaseReturnTrueAndAdding();
        footballAssociation = new FootballAssociation("Israel");
        Authentication authentication = new Authentication(database);
        new CRUD(database);

        AMEDYSystem amedySystem = new AMEDYSystem(authentication);

        //Creating
        rfaUser = new User("DanielRFA","12345678",amedySystem);
        refereeUser = new User("EranReferee","12345678",amedySystem);
        willBeRefereeUser = new User("MichalReferee","12345678",amedySystem);
        representativeFootballAssociation = (RepresentativeFootballAssociation) myFactory.Generate("rfa","Daniel");
        rfaUser.addProfile(representativeFootballAssociation);
        representativeFootballAssociation.setUser(rfaUser);
        league = (League) myFactory.Generate("league","league1");
        season = new Season(2021,amedySystem,(LeagueRankPolicy)myFactory.Generate("leaguerankpolicy","leaguerankpolicy"));
        season1 = (Season) myFactory.Generate("season","2020");
        season2 = (Season) myFactory.Generate("season","2019");
        referee1 = (Referee) myFactory.Generate("referee","mainreferee");
        referee1.setName("r1");
        referee2 = (Referee) myFactory.Generate("referee","sidereferee");
        referee2.setName("r2");
        referee3 = (Referee) myFactory.Generate("referee","sidereferee");
        referee3.setName("r3");
        refereeUser.addProfile(referee1);
        referee1.setUser(refereeUser);

        //Connecting
        footballAssociation.addToRFA(representativeFootballAssociation);
        representativeFootballAssociation.setFootballAssociation(footballAssociation);
        footballAssociation.addToLeagues(league);
        seasonLeagueAssignments = new SeasonLeagueAssignments(league,season);
        seasonLeagueAssignments1 = new SeasonLeagueAssignments(league,season1);
        seasonLeagueAssignments2 = new SeasonLeagueAssignments(league,season2);


        //Simulate DB
        database.initialDBStruct();
        database.add(footballAssociation);
        database.add(representativeFootballAssociation);
        database.add(rfaUser);
        database.add(league);
        database.add(season1);
        database.add(season2);
        database.add(seasonLeagueAssignments1);
        database.add(seasonLeagueAssignments2);
        database.add(refereeUser);
        database.add(willBeRefereeUser);
        database.add(referee1);
        database.add(referee2);
        database.add(referee3);

    }

    @Test
    void testInitNewLeague() {

        //Connecting to the system
        String rfaName = representativeFootballAssociation.getName();
        RepresentativeFootballAssociation rfa = database.selectRFA(rfaName);

        //UC 9.1
        //Testing correct inserting of a new league
        try {
            assertTrue(rfaController.createNewLeague(rfa, LeagueRank.league2));
        } catch (Exception e) {
            e.getMessage();
        }

        //Testing exception for insertion of the same league
        try {
            rfaController.createNewLeague(rfa,LeagueRank.league2);
        } catch (Exception e) {
            assertTrue(e instanceof AssertAlreadyExistException);
            assertTrue(e.getMessage().equals("League is already exist"));
        }

    }

    @Test
    void testAddSeasonToLeague(){
        //Checking add a new season with new year
        try {
            assertTrue(RFAController.addSeasonToLeague(representativeFootballAssociation,league,2022));
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Checking insert new season with a exist year
        try{
            RFAController.addSeasonToLeague(representativeFootballAssociation,league,2022);
        }catch (Exception e){
            assertTrue(e instanceof IllegalValueException);
            assertTrue(e.getMessage().equals("There is already season this year"));
        }

        //Checking insert new season to illegal league argument
        try{
            RFAController.addSeasonToLeague(representativeFootballAssociation,null,2030);
        }catch (Exception e){
            assertTrue(e instanceof IllegalValueException);
        }
    }

    @Test
    void testRegistrationReferee(){
        //Checking registration referee that isn't in the system
        try{
            assertTrue(RFAController.registrationReferee(representativeFootballAssociation,willBeRefereeUser,"ref1",RefereeType.MainReferee));
        }catch (Exception e){

        }
        //Checking if the user is already referee
        try{
            RFAController.registrationReferee(representativeFootballAssociation,willBeRefereeUser,"ref1",RefereeType.MainReferee);
        }catch (Exception e){
            assertTrue(e instanceof ProfileTypeExistException);
            assertTrue(e.getMessage().equals("The user is already referee in the system"));
        }
        //Checking registration referee with null refereeName
        try{
            RFAController.registrationReferee(representativeFootballAssociation,willBeRefereeUser,null,RefereeType.MainReferee);
        }catch(Exception e){
            assertTrue(e instanceof IllegalValueException);
        }
        //Checking registration referee with null user
        try{
            RFAController.registrationReferee(representativeFootballAssociation,null,"ref2", RefereeType.MainReferee);
        }catch(Exception e){
            assertTrue(e instanceof IllegalValueException);
        }
    }

    @Test
    void testRemoveReferee(){
        //Checking remove referee that in the system
        try {
            assertTrue(RFAController.removeReferee(representativeFootballAssociation,referee1.getName()));
        }catch (Exception e){

        }

        //Checking remove referee that isn't in the system
        try{
            RFAController.removeReferee(representativeFootballAssociation,referee1.getName());
        }catch(Exception e){
            assertTrue(e instanceof AssertIsNotExistException);
            assertTrue(e.getMessage().equals("Referee does not exist in the system"));
        }

        //Checking remove referee with null refereeName
        try{
            RFAController.removeReferee(representativeFootballAssociation,null);
        }catch(Exception e){
            assertTrue(e instanceof IllegalValueException);
        }

    }

    @Test
    void testRefereeAssignmentToSeason() {

        //Connecting to the system
        String rfaName = representativeFootballAssociation.getName();
        RepresentativeFootballAssociation rfa = database.selectRFA(rfaName);

        //Simulate extracting league list from DB and presenting it to the user
        for (Object l: database.select("league")) {
            footballAssociation.addToLeagues((League)l);
        }

        try {
            rfaController.showAllLeagues(footballAssociation.getAllLeagues());
        } catch (IllegalValueException e) {
            e.getMessage();
        }

        //User choose league
        League l1 = rfa.getLeague(league.getLeagueRank());

        //Simulate extracting season list from DB and presenting it to the user
        ArrayList<Season> allLeagueSeasons = l1.getAllSeasons();

        try {
            rfaController.showAllLeagueSeasons(allLeagueSeasons);
        } catch (IllegalValueException e) {
            e.getMessage();
        }

        //User choose season
        Season s1 = allLeagueSeasons.get(allLeagueSeasons.indexOf(season1));

        //Simulate extracting all referees from the DB and presenting it to the user
        footballAssociation.setAllReferees(new ArrayList<>(database.select("referee")));

        try {
            rfaController.showAllRefeeres(footballAssociation.getAllReferees());
        } catch (IllegalValueException e) {
            e.getMessage();
        }

        //User choose referee
        String refereeName = referee1.getName();
        Referee r1 = database.selectReferee(refereeName);

        //Assigning referee to season
        try {
            assertTrue(rfaController.assignRefereeToSeason(rfa,r1,l1,s1));
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Testing exception of assigning the same referee to the season
        try {
            rfaController.assignRefereeToSeason(rfa,r1,l1,s1);
        } catch (Exception e) {
            assertTrue(e instanceof AssertAlreadyExistException);
            assertTrue(e.getMessage().equals("Referee already assigned to this season"));
        }

        //Testing exception of an unregistered referee to the system
        Referee notRegisteredReferee = (Referee) myFactory.Generate("referee","mainreferee");
        notRegisteredReferee.setName("notRegistered");

        try {
            rfaController.assignRefereeToSeason(rfa,notRegisteredReferee,l1,s1);
        } catch (Exception e) {
            assertTrue(e instanceof AssertIsNotExistException);
            assertTrue(e.getMessage().equals("Referee does not exist in the system"));
        }

        //Testing exception of referee insertion to a not existing season
        Season notExisitingSeason = (Season) myFactory.Generate("season","3000");

        try {
            rfaController.assignRefereeToSeason(rfa,referee2,l1,notExisitingSeason);
        } catch (Exception e) {
            assertTrue(e instanceof AssertIsNotExistException);
            assertTrue(e.getMessage().equals("Season does not exist in the system"));
        }

        //Testing exception of insertion
        League notExistingLeague = (League) myFactory.Generate("league","league3");

        try {
            rfaController.assignRefereeToSeason(rfa,r1,notExistingLeague,s1);
        } catch (Exception e) {
            assertTrue(e instanceof AssertIsNotExistException);
            assertTrue(e.getMessage().equals("League does not exist in the system"));
        }

        //Testing null insertions
        try {
            rfaController.assignRefereeToSeason(rfa, (Referee) null,l1,s1);
            rfaController.assignRefereeToSeason(rfa,r1,null,s1);
            rfaController.assignRefereeToSeason(rfa,r1,l1,null);
        } catch (Exception e) {
            assertTrue(e instanceof IllegalValueException);
        }

    }

    @Test
    void testInitNewRankingPolicy() {

        //Connecting to the system
        String rfaName = representativeFootballAssociation.getName();
        RepresentativeFootballAssociation rfa = database.selectRFA(rfaName);

        //Simulate extracting league list from DB and presenting it to the user
        for (Object l: database.select("league")) {
            footballAssociation.addToLeagues((League)l);
        }

        try {
            rfaController.showAllLeagues(footballAssociation.getAllLeagues());
        } catch (IllegalValueException e) {
            e.getMessage();
        }

        //User choose league
        League l1 = rfa.getLeague(league.getLeagueRank());

        //RFA fills the LeagueRankPolicy format
        LeagueRankPolicyStub leagueRankPolicyStub = new LeagueRankPolicyStub("policy1");

        //Testing correct insertion of new League rank policy to the system
        try {
            assertTrue(rfaController.createLeagueRankPolicy(rfa,leagueRankPolicyStub));
        } catch (Exception e) {
            e.getMessage();
        }

        //Testing correct insertion of league rank policy to league
        try {
            assertTrue(rfaController.addNewLeagueRankPolicyToLeague(rfa,l1,leagueRankPolicyStub));
        } catch (Exception e) {
            e.getMessage();
        }

        //Testing insertion exception of an existing league rank policy to the system
        try {
            rfaController.createLeagueRankPolicy(rfa,leagueRankPolicyStub);
        } catch (Exception e) {
            assertTrue(e instanceof AssertAlreadyExistException);
            assertTrue(e.getMessage().equals("League rank policy already exist in the system"));
        }

        //Testing insertion exception of an existing league rank policy to league
        try {
            rfaController.addNewLeagueRankPolicyToLeague(rfa,l1,leagueRankPolicyStub);
        } catch (Exception e) {
            assertTrue(e instanceof AssertAlreadyExistException);
            assertTrue(e.getMessage().equals("League Rank Policy already registered"));
        }


    }

    @Test
    void testUpdateRankingPolicy() {

        //Creating league rank policy and adding it to the DB
        LeagueRankPolicyStub leagueRankPolicyStub = new LeagueRankPolicyStub("policy1");
        database.add(leagueRankPolicyStub);

        //Setting it to the league
        league.setLeagueRankPolicy(leagueRankPolicyStub);

        //Connecting to the system
        String rfaName = representativeFootballAssociation.getName();
        RepresentativeFootballAssociation rfa = database.selectRFA(rfaName);

        //Simulate extracting league list from DB and presenting it to the user
        for (Object l: database.select("league")) {
            footballAssociation.addToLeagues((League)l);
        }

        try {
            rfaController.showAllLeagues(footballAssociation.getAllLeagues());
        } catch (IllegalValueException e) {
            e.getMessage();
        }

        //User choose league
        League l1 = rfa.getLeague(league.getLeagueRank());

        //Simulating extracting league rank policies from DB and presenting it to the user
        try {
            rfa.addNewLeagueRankPolicyToSystem(leagueRankPolicyStub);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ArrayList<LeagueRankPolicy> allLeaguesRankPolicies = new ArrayList<>(database.select("leaguerankpolicy"));

        try {
            rfaController.showAllLeagueRankPoicies(allLeaguesRankPolicies);
        } catch (IllegalValueException e) {
            e.printStackTrace();
        }

        //User choose policy
        LeagueRankPolicy lrpStub = allLeaguesRankPolicies.get(allLeaguesRankPolicies.indexOf(leagueRankPolicyStub));

        //Testing updating policy
        LeagueRankPolicyStub newLRP = new LeagueRankPolicyStub("updatedPolicy");
        try {
            assertTrue(rfaController.updateLeagueRankPolicy(rfa,lrpStub,newLRP));
            assertTrue(l1.getLeagueRankPolicy().equals(newLRP));
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Testing insertion exception of an existing league rank policy
        try {
            rfaController.updateLeagueRankPolicy(rfa,newLRP,newLRP);
        } catch (Exception e) {
            assertTrue(e instanceof AssertAlreadyExistException);
            assertTrue(e.getMessage().equals("New League rank policy already exist"));
        }


    }

    @Test
    void InitNewAssigningMatchPolicy() {

        //Connecting to the system
        String rfaName = representativeFootballAssociation.getName();
        RepresentativeFootballAssociation rfa = database.selectRFA(rfaName);

        //Simulate extracting league list from DB and presenting it to the user
        for (Object l: database.select("league")) {
            footballAssociation.addToLeagues((League)l);
        }

        try {
            rfaController.showAllLeagues(footballAssociation.getAllLeagues());
        } catch (IllegalValueException e) {
            e.getMessage();
        }

        //User choose league
        League l1 = rfa.getLeague(league.getLeagueRank());

        //RFA fills the LeagueRankPolicy format
        MatchAssignmentPolicyStub mapStub = new MatchAssignmentPolicyStub("policy1");

        //Testing correct insertion to the system
        try {
            assertTrue(rfaController.createMatchAssignmentPolicy(rfa,mapStub));
        } catch (Exception e) {
            e.getMessage();
        }

        //Testing insertion exception of an existing match assignment policy to the system
        try {
            rfaController.createMatchAssignmentPolicy(rfa,mapStub);
        } catch (Exception e) {
            assertTrue(e instanceof AssertAlreadyExistException);
            assertTrue(e.getMessage().equals("This match assignment policy already exist in the system"));
        }

        //Testing assigning match assignment policy to league -> will add to all SeasonLeagueAssignment related to this league
        try {
            assertTrue(rfaController.addMatchAssignmentPolicyToLeague(rfa,l1,mapStub));
            for (SeasonLeagueAssignments sla: l1.getAllSeasonLeagueAssignments()) {
                assertTrue(sla.getMyMatchAssignment().equals(mapStub));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Testing league does not exist exception
        League notExistLeague = (League) myFactory.Generate("league","league3");

        try {
            rfaController.addMatchAssignmentPolicyToLeague(rfa,notExistLeague,mapStub);
        } catch (Exception e) {
            assertTrue(e instanceof AssertIsNotExistException);
            assertTrue(e.getMessage().equals("League does not exist"));
        }

        //Testing match assignment policy does not exist exception
        MatchAssignmentPolicyStub notExistMAP = new MatchAssignmentPolicyStub("notExist");

        try {
            rfaController.addMatchAssignmentPolicyToLeague(rfa,l1,notExistMAP);
        } catch (Exception e) {
            assertTrue(e instanceof AssertIsNotExistException);
            assertTrue(e.getMessage().equals("Match assignment policy does not exist in the system"));
        }

    }

}
