package DataLayer;

import BuisinessLayer.LogicalOperations.*;
import BuisinessLayer.Policies.LeagueRankPolicy;
import BuisinessLayer.Policies.MatchAssignmentPolicy;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class DataBaseReturnTrueAndAdding implements DataBase {

    //Fields
    private ConcurrentHashMap<String, User> users;

    private ConcurrentHashMap<String, SystemManager> systemManager;
    private ConcurrentHashMap<String, Referee> referees;
    private ConcurrentHashMap<String, Player> players;
    private ConcurrentHashMap<String, Coach> coaches;
    private ConcurrentHashMap<String, TeamOwner> teamOwners;
    private ConcurrentHashMap<String, TeamManager> teamManagers;
    private ConcurrentHashMap<String, RepresentativeFootballAssociation> representativeFootballAssociation;

    private ConcurrentHashMap<String, Event> events;
    private ConcurrentHashMap<String, LeagueRankPolicy> leagueRankPolicies;
    private ConcurrentHashMap<String, MatchAssignmentPolicy> matchAssignmentsPolicies;
    private ConcurrentHashMap<String, League> leagues;
    private ConcurrentHashMap<String, Match> matches;
    private ConcurrentHashMap<String, Season> seasons;
    private ConcurrentHashMap<String, Team> teams;
    private ConcurrentHashMap<String, Team> closedTeams;//closed teams that can be opened by team manager/owner
    private ConcurrentHashMap<String, Team> archivedTeams;//manager system closed the team permanently
    private List<Pair> followTable;  //represent the table of which fan follow after ProfileEventMaker and opposite.
    private ConcurrentHashMap<Integer, Complaint> complaints;


    @Override
    public void initialDBStruct() {

        this.users = new ConcurrentHashMap<>();
        this.players = new ConcurrentHashMap<>();
        this.coaches = new ConcurrentHashMap<>();
        this.events = new ConcurrentHashMap<>();
        this.leagueRankPolicies = new ConcurrentHashMap<>();
        this.matchAssignmentsPolicies = new ConcurrentHashMap<>();
        this.leagues = new ConcurrentHashMap<>();
        this.matches = new ConcurrentHashMap<>();
        this.referees = new ConcurrentHashMap<>();
        this.representativeFootballAssociation = new ConcurrentHashMap<>();
        this.seasons = new ConcurrentHashMap<>();
        this.teams = new ConcurrentHashMap<>();
        this.closedTeams = new ConcurrentHashMap<>(); //closed teams that can be opened by team manager/owner
        this.archivedTeams = new ConcurrentHashMap<>(); //manager system closed the team permanently
        this.teamOwners = new ConcurrentHashMap<>();
        this.teamManagers = new ConcurrentHashMap<>();
        this.followTable = new ArrayList<>();
        this.complaints = new ConcurrentHashMap<>();
        this.systemManager = new ConcurrentHashMap<>();
    }

    @Override
    public boolean checkConnection() {
        return true;
    }

    @Override
    public Connection getConnection() {
        return null;
    }

    @Override
    public Collection select(String type, String key) {

        if(type == null || key == null) {
            return new ArrayList();
        }

        //User
        if(type.toLowerCase().equals("user")) {

            if(this.users.containsKey(key)) {
                List list = new ArrayList();

                list.add(this.users.get(key));

                return list;
            }

            return new ArrayList();
        }

        //SystemManager
        if(type.toLowerCase().equals("systemmanager")) {

            if(this.systemManager.containsKey(key)) {
                List list = new ArrayList();

                list.add(this.systemManager.get(key));

                return list;
            }

            return new ArrayList();
        }

        //Referee
        if(type.toLowerCase().equals("referee")) {

            if(this.referees.containsKey(key)) {
                List list = new ArrayList();

                list.add(this.referees.get(key));

                return list;
            }

            return new ArrayList();
        }

        //Player
        if(type.toLowerCase().equals("player")) {

            if(this.players.containsKey(key)) {
                List list = new ArrayList();

                list.add(this.players.get(key));

                return list;
            }

            return new ArrayList();
        }

        //Coach
        if(type.toLowerCase().equals("coach")) {

            if(this.coaches.containsKey(key)) {
                List list = new ArrayList();

                list.add(this.coaches.get(key));

                return list;
            }

            return new ArrayList();
        }

        //TeamOwner
        if(type.toLowerCase().equals("teamowner")) {

            if(this.teamOwners.containsKey(key)) {
                List list = new ArrayList();

                list.add(this.teamOwners.get(key));

                return list;
            }

            return new ArrayList();
        }

        //TeamManager
        if(type.toLowerCase().equals("teammanager")) {

            if(this.teamManagers.containsKey(key)) {
                List list = new ArrayList();

                list.add(this.teamManagers.get(key));

                return list;
            }

            return new ArrayList();
        }

        //Event
        if(type.toLowerCase().equals("event")) {

            if(this.events.containsKey(key)) {
                List list = new ArrayList();

                list.add(this.events.get(key));

                return list;
            }

            return new ArrayList();
        }


        //League
        if(type.toLowerCase().equals("league")) {

            if(this.leagues.containsKey(key)) {
                List list = new ArrayList();

                list.add(this.leagues.get(key));

                return list;
            }

            return new ArrayList();
        }

        //Season
        if(type.toLowerCase().equals("season")) {

            if(this.seasons.containsKey(key)) {
                List list = new ArrayList();

                list.add(this.seasons.get(key));

                return list;
            }

            return new ArrayList();
        }

        //Team
        if(type.toLowerCase().equals("team")) {

            if (this.teams.containsKey(key)) {
                List list = new ArrayList();

                list.add(this.teams.get(key));

                return list;
            }

            if (this.closedTeams.containsKey(key)) {
                List list = new ArrayList();

                list.add(this.closedTeams.get(key));

                return list;
            }

            if (this.archivedTeams.containsKey(key)) {
                List list = new ArrayList();

                list.add(this.archivedTeams.get(key));

                return list;
            }
        }
        if(type.toLowerCase().equals("active")) {

            if(this.teams.containsKey(key)) {
                List list = new ArrayList();

                list.add(this.teams.get(key));

                return list;
            }

            return new ArrayList();
        }

        //ClosedTeams
        if(type.toLowerCase().equals("closedteam")) {

            if(this.closedTeams.containsKey(key)) {
                List list = new ArrayList();

                list.add(this.closedTeams.get(key));

                return list;
            }

            return new ArrayList();
        }

        //ArchivedTeams
        if(type.toLowerCase().equals("archiveteam")) {

            if(this.archivedTeams.containsKey(key)) {
                List list = new ArrayList();

                list.add(this.archivedTeams.get(key));

                return list;
            }

            return new ArrayList();
        }

        //complaints
        if(type.toLowerCase().equals("complaints"))
        {
            Complaint complaint = this.complaints.get(Integer.parseInt(key));
            List<Integer> fanComplainerID = new ArrayList<>();
            fanComplainerID.add(complaint.getFanComplainerID());
            return fanComplainerID;
        }

        return new ArrayList();
    }

    @Override
    public Collection select(String type, String firstKey, String secondKey)
    {
        if(type.equals("followers"))
        {
            List<Pair> followersPairs = new ArrayList<>();
            for(Pair currentPair : this.followTable)
            {
                if(firstKey.equals(currentPair.getKey()) && secondKey.equals(currentPair.getValue()))
                {
                    followersPairs.add(currentPair);
                }
            }
            return followersPairs;
        }
        return new ArrayList<Pair>();
    }

    @Override
    public User selectUser(String key) {

        if(key.equals("userinsertiontest")) {
            return this.users.get(key);
        }

        if(this.users.containsKey(key)) {

            return this.users.get(key);
        }

        return null;
    }

    @Override
    public boolean add(Object entity) {
        return add(entity, null);
    }

    @Override
    public boolean add(Object entity, String whichTeams) {

        //User
        if (entity instanceof User) {

            User user = (User)entity;

            if(this.users.containsKey(user.getKey()))
            {
                AMEDYSystem.systemLogger.warning("[DataBaseReturnTrueAndAdding.add.user]: username taken.");

                return false;
            }

            this.users.put(user.getUserName(), user);

            AMEDYSystem.systemLogger.warning("[DataBaseReturnTrueAndAdding.add.user]: user-" + user.getUserName()+ " add.");

            return true;
        }

        //SystemManager
        if (entity instanceof SystemManager) {

            SystemManager systemManager = (SystemManager) entity;

            if(this.systemManager.containsKey(systemManager.getKey()))
            {
                AMEDYSystem.systemLogger.warning("[DataBaseReturnTrueAndAdding.add.referee]: name taken.");

                return false;
            }

            this.systemManager.put(systemManager.getKey(), systemManager);

            AMEDYSystem.systemLogger.info("[DataBaseReturnTrueAndAdding.add.user]: systemManager-" + systemManager.getName() + "add.");

            return true;
        }

        //Referee
        if (entity instanceof Referee) {

            Referee referee = (Referee)entity;

            if(this.referees.containsKey(referee.getName())) //TODO: change to getKey();
            {
                AMEDYSystem.systemLogger.warning("[DataBaseReturnTrueAndAdding.add.referee]: name taken.");

                return false;
            }

            this.referees.put(referee.getName(), referee);

            AMEDYSystem.systemLogger.info("[DataBaseReturnTrueAndAdding.add.user]: referee-" + referee.getName() + "add.");

            return true;
        }

        //Player
        if (entity instanceof Player) {

            Player player = (Player)entity;

            if(this.players.containsKey(player.getName()))
            {
                AMEDYSystem.systemLogger.warning("[DataBaseReturnTrueAndAdding.add.player]: name taken.");

                return false;
            }

            this.players.put(player.getName(), player);

            AMEDYSystem.systemLogger.info("[DataBaseReturnTrueAndAdding.add.user]: player-" + player.getName() + "add.");

            return true;
        }

        //Coach
        if (entity instanceof Coach) {

            Coach coach = (Coach)entity;

            if(this.coaches.containsKey(coach.getName()))
            {
                AMEDYSystem.systemLogger.warning("[DataBaseReturnTrueAndAdding.add.coach]: name taken.");

                return false;
            }

            this.coaches.put(coach.getName(), coach);

            AMEDYSystem.systemLogger.info("[DataBaseReturnTrueAndAdding.add.user]: coach-" + coach.getName() + "add.");

            return true;
        }

        //TeamOwner
        if (entity instanceof TeamOwner) {

            TeamOwner teamOwner = (TeamOwner)entity;

            if(this.teamOwners.containsKey(teamOwner.getName()))
            {
                AMEDYSystem.systemLogger.warning("[DataBaseReturnTrueAndAdding.add.teamOwner]: name taken.");

                return false;
            }

            this.teamOwners.put(teamOwner.getName(), teamOwner);

            AMEDYSystem.systemLogger.info("[DataBaseReturnTrueAndAdding.add.user]: teamOwner-" + teamOwner.getName() + "add.");

            return true;
        }

        //TeamManagers
        if (entity instanceof TeamManager) {

            TeamManager teamManager = (TeamManager)entity;

            if(this.teamManagers.containsKey(teamManager.getName()))
            {
                AMEDYSystem.systemLogger.warning("[DataBaseReturnTrueAndAdding.add.teamManager]: name taken.");

                return false;
            }

            this.teamManagers.put(teamManager.getName(), teamManager);

            AMEDYSystem.systemLogger.info("[DataBaseReturnTrueAndAdding.add.user]: teamManager-" + teamManager.getName() + "add.");

            return true;
        }

        //RFA
        if (entity instanceof RepresentativeFootballAssociation) {

            RepresentativeFootballAssociation representativeFootballAssociation = (RepresentativeFootballAssociation) entity;

            if(this.representativeFootballAssociation.containsKey(representativeFootballAssociation.getName()))
            {
                AMEDYSystem.systemLogger.warning("[DataBaseReturnTrueAndAdding.add.teamManager]: name taken.");

                return false;
            }

            this.representativeFootballAssociation.put(representativeFootballAssociation.getName(), representativeFootballAssociation);

            AMEDYSystem.systemLogger.info("[DataBaseReturnTrueAndAdding.add.user]: teamManager-" + representativeFootballAssociation.getName() + "add.");

            return true;
        }

        //Event
        if (entity instanceof Event) {

            Event event = (Event)entity;

            if(this.events.containsKey(event.toString())) //TODO: change to something valuable, unique key fr example
            {
                AMEDYSystem.systemLogger.warning("[DataBaseReturnTrueAndAdding.add.event]: name taken.");

                return false;
            }

            this.events.put(event.toString(), event);

            AMEDYSystem.systemLogger.info("[DataBaseReturnTrueAndAdding.add.user]: event-" + event.getDescription() + "add.");

            return true;
        }


        //League
        if (entity instanceof League) {

            League league = (League)entity;

            if(this.leagues.containsKey(league.getLeagueRank().toString())) //TODO: change to something valuable, unique key fr example
            {
                AMEDYSystem.systemLogger.warning("[DataBaseReturnTrueAndAdding.add.league]: name taken.");

                return false;
            }

            this.leagues.put(league.toString(), league);

            AMEDYSystem.systemLogger.info("[DataBaseReturnTrueAndAdding.add.user]: league-" + league.getLeagueRank().name() + "add.");

            return true;
        }

        //LeagueRankPolicy
        if (entity instanceof LeagueRankPolicy) {

            LeagueRankPolicy leagueRankPolicy = (LeagueRankPolicy) entity;

            if(this.leagueRankPolicies.containsKey(leagueRankPolicy.getName())) //TODO: change to something valuable, unique key fr example
            {
                AMEDYSystem.systemLogger.warning("[DataBaseReturnTrueAndAdding.add.league]: name taken.");

                return false;
            }

            this.leagueRankPolicies.put(leagueRankPolicy.getName(), leagueRankPolicy);

            AMEDYSystem.systemLogger.info("[DataBaseReturnTrueAndAdding.add.user]: league-" + leagueRankPolicy.getName() + "add.");

            return true;
        }

        //MatchAssignmentsPolicy
        if (entity instanceof MatchAssignmentPolicy) {

            MatchAssignmentPolicy matchAssignmentPolicy = (MatchAssignmentPolicy) entity;

            if(this.matchAssignmentsPolicies.containsKey(matchAssignmentPolicy.getName())) //TODO: change to something valuable, unique key fr example
            {
                AMEDYSystem.systemLogger.warning("[DataBaseReturnTrueAndAdding.add.league]: name taken.");

                return false;
            }

            this.matchAssignmentsPolicies.put(matchAssignmentPolicy.getName(), matchAssignmentPolicy);

            AMEDYSystem.systemLogger.info("[DataBaseReturnTrueAndAdding.add.user]: league-" + matchAssignmentPolicy.getName() + "add.");

            return true;
        }

        //Match
        if (entity instanceof Match) {

            Match match = (Match)entity;

            if(this.matches.containsKey(match.toString())) //TODO: change to something valuable, unique key fr example
            {
                AMEDYSystem.systemLogger.warning("[DataBaseReturnTrueAndAdding.add.match]: name taken.");

                return false;
            }

            this.matches.put(match.toString(), match);

            AMEDYSystem.systemLogger.info("[DataBaseReturnTrueAndAdding.add.user]: match add.");

            return true;
        }

        //Season
        if (entity instanceof Season) {

            Season season = (Season)entity;

            if(this.seasons.containsKey(seasons.toString())) //TODO: change to something valuable, unique key fr example
            {
                AMEDYSystem.systemLogger.warning("[DataBaseReturnTrueAndAdding.add.season]: name taken.");

                return false;
            }

            this.seasons.put(season.toString(), season);

            AMEDYSystem.systemLogger.info("[DataBaseReturnTrueAndAdding.add.user]: season add.");

            return true;
        }

        //Team
        if (entity instanceof Team) {

            Team team = (Team)entity;

            if(whichTeams.toLowerCase().equals("active")) {

                if(this.teams.containsKey(team.getTeamName()))
                {
                    AMEDYSystem.systemLogger.warning("[DataBaseReturnTrueAndAdding.add.team]: name taken.");

                    return false;
                }

                this.teams.put(team.getTeamName(), team);

                AMEDYSystem.systemLogger.info("[DataBaseReturnTrueAndAdding.add.user]: team-" + team.getTeamName() + "add.");

                return true;
            }
            else if(whichTeams.toLowerCase().equals("close")) {

                if(this.closedTeams.containsKey(team.getTeamName()))
                {
                    AMEDYSystem.systemLogger.warning("[DataBaseReturnTrueAndAdding.add.closedteams]: name taken.");

                    return false;
                }

                this.closedTeams.put(team.getTeamName(), team);

                return true;
            }
            else if(whichTeams.toLowerCase().equals("archivedteam")) {

                if(this.archivedTeams.containsKey(team.getTeamName()))
                {
                    AMEDYSystem.systemLogger.warning("[DataBaseReturnTrueAndAdding.add.archivedTeams]: name taken.");

                    return false;
                }

                this.archivedTeams.put(team.getTeamName(), team);

                return true;
            }
        }

        if(entity instanceof Complaint)
        {
            Complaint complaint = (Complaint) entity;
            if(this.complaints.containsKey(complaint.getComplaintID()))
            {
                AMEDYSystem.systemLogger.warning("[DataBaseReturnTrueAndAdding][add][Comaplint]: complaint already exist in the database.");
                return false;
            }
            this.complaints.put(complaint.getComplaintID(), complaint);
            return true;
        }

        return false;
    }



    @Override
    public boolean add(Object firstEntity, Object secondEntity)
    {
        if(firstEntity instanceof Fan)
        {
            Fan fan = (Fan) firstEntity;
            Pair newPair = null;
            if(secondEntity instanceof ProfileEventMaker)
            {
                ProfileEventMaker profileEventMaker = (ProfileEventMaker) secondEntity;
                newPair = new Pair(fan.getName(), profileEventMaker.getName());
                this.followTable.add(newPair);
                return true;
            }
            else if(secondEntity instanceof Match)
            {
                Match match = (Match) secondEntity;
                newPair = new Pair(fan.getName(), match.getMatchID() + "");
                this.followTable.add(newPair);
                return true;
            }
        }
        return false;
    }



    @Override
    public Collection select(String type) {

        if (type == null || type.equals("")) {
            return null;
        }

        if (type.toLowerCase().equals("user")) {
            return this.users.values();
        }

        if (type.toLowerCase().equals("player")) {
            return this.players.values();
        }

        if (type.toLowerCase().equals("matches")) {
            return this.matches.values();
        }

        if (type.toLowerCase().equals("referee")) {
            return this.referees.values();
        }

        if (type.toLowerCase().equals("league")) {
            return this.leagues.values();
        }

        if (type.toLowerCase().equals("leaguerankpolicy")) {
            return this.leagueRankPolicies.values();
        }

        if (type.toLowerCase().equals("matchassignmentpolicy")) {
            return this.matchAssignmentsPolicies.values();
        }

        if (type.toLowerCase().equals("complaint")) {
            return this.complaints.values();
        }

        return null;
    }

    @Override
    public boolean addNew(Object whatToCreate) {

        return false;
    }

    @Override
    public boolean remove(Object entity, String whichTeams){
        //User
        if (entity instanceof User) {

            User user = (User)entity;

            if(!this.users.containsKey(user.getUserName()))
            {
                AMEDYSystem.systemLogger.warning("[DataBaseReturnTrueAndAdding.remove.user]: username not found.");

                return false;
            }

            this.users.remove(user.getUserName(), user);

            AMEDYSystem.systemLogger.info("[DataBaseReturnTrueAndAdding.remove.user]: " + user.getUserName() + " deleted.");

            return true;
        }

        //SystemManager
        if (entity instanceof SystemManager) {

            SystemManager systemManager = (SystemManager) entity;

            if(!this.systemManager.containsKey(systemManager.getName()))
            {
                AMEDYSystem.systemLogger.warning("[DataBaseReturnTrueAndAdding.add.referee]: systemManager not found.");

                return false;
            }

            this.systemManager.remove(systemManager.getName(), systemManager);

            AMEDYSystem.systemLogger.info("[DataBaseReturnTrueAndAdding.remove.user]: " + systemManager.getName() + " deleted.");

            return true;
        }

        //Referee
        if (entity instanceof Referee) {

            Referee referee = (Referee)entity;

            if(!this.referees.containsKey(referee.getName()))
            {
                AMEDYSystem.systemLogger.warning("[DataBaseReturnTrueAndAdding.add.referee]: referee not found.");

                return false;
            }

            this.referees.remove(referee.getName(), referee);

            AMEDYSystem.systemLogger.info("[DataBaseReturnTrueAndAdding.remove.user]: " + referee.getName() + " deleted.");

            return true;
        }

        //Player
        if (entity instanceof Player) {

            Player player = (Player)entity;

            if(!this.players.containsKey(player.getName()))
            {
                AMEDYSystem.systemLogger.warning("[DataBaseReturnTrueAndAdding.add.player]: player not found.");

                return false;
            }

            this.players.remove(player.getName(), player);

            AMEDYSystem.systemLogger.info("[DataBaseReturnTrueAndAdding.remove.user]: " + player.getName() + " deleted.");

            return true;
        }

        //Coach
        if (entity instanceof Coach) {

            Coach coach = (Coach)entity;

            if(!this.coaches.containsKey(coach.getName()))
            {
                AMEDYSystem.systemLogger.warning("[DataBaseReturnTrueAndAdding.add.coach]: coach not found.");

                return false;
            }

            this.coaches.remove(coach.getName(), coach);

            AMEDYSystem.systemLogger.info("[DataBaseReturnTrueAndAdding.remove.user]: " + coach.getName() + " deleted.");

            return true;
        }

        //TeamOwner
        if (entity instanceof TeamOwner) {

            TeamOwner teamOwner = (TeamOwner)entity;

            if(!this.teamOwners.containsKey(teamOwner.getName()))
            {
                AMEDYSystem.systemLogger.warning("[DataBaseReturnTrueAndAdding.add.teamOwner]: teamOwner not found.");

                return false;
            }

            this.teamOwners.remove(teamOwner.getName(), teamOwner);

            AMEDYSystem.systemLogger.info("[DataBaseReturnTrueAndAdding.remove.user]: " + teamOwner.getName() + " deleted.");

            return true;
        }

        //TeamManagers
        if (entity instanceof TeamManager) {

            TeamManager teamManager = (TeamManager)entity;

            if(!this.teamManagers.containsKey(teamManager.getName()))
            {
                AMEDYSystem.systemLogger.warning("[DataBaseReturnTrueAndAdding.add.teamManager]: teamManager not found.");

                return false;
            }

            this.teamManagers.remove(teamManager.getName(), teamManager);

            AMEDYSystem.systemLogger.info("[DataBaseReturnTrueAndAdding.remove.user]: " + teamManager.getName() + " deleted.");

            return true;
        }

        //representativeFootballAssociation
        if (entity instanceof RepresentativeFootballAssociation) {

            RepresentativeFootballAssociation representativeFootballAssociation = (RepresentativeFootballAssociation) entity;

            if(!this.representativeFootballAssociation.containsKey(representativeFootballAssociation.getName()))
            {
                AMEDYSystem.systemLogger.warning("[DataBaseReturnTrueAndAdding.add.teamManager]: representativeFootballAssociation not found.");

                return false;
            }

            this.teamManagers.remove(representativeFootballAssociation.getName(), representativeFootballAssociation);

            AMEDYSystem.systemLogger.info("[DataBaseReturnTrueAndAdding.remove.user]: " + representativeFootballAssociation.getName() + " deleted.");

            return true;
        }

        //Event
        if (entity instanceof Event) {

            Event event = (Event)entity;

            if(!this.events.containsKey(event.toString())) //TODO: change to something valuable, unique key fr example
            {
                AMEDYSystem.systemLogger.warning("[DataBaseReturnTrueAndAdding.add.event]: event not found.");

                return false;
            }

            this.events.remove(event.toString(), event);

            AMEDYSystem.systemLogger.info("[DataBaseReturnTrueAndAdding.add.user]: event-" + event.getDescription() + "deleted.");

            return true;
        }


        //League
        if (entity instanceof League) {

            League league = (League)entity;

            if(!this.leagues.containsKey(league.toString())) //TODO: change to something valuable, unique key fr example
            {
                AMEDYSystem.systemLogger.warning("[DataBaseReturnTrueAndAdding.add.league]: league not found.");

                return false;
            }

            this.leagues.remove(league.toString(), league);

            AMEDYSystem.systemLogger.info("[DataBaseReturnTrueAndAdding.add.user]: league-" + league.getLeagueRank().name() + "deleted.");

            return true;
        }

        //Match
        if (entity instanceof Match) {

            Match match = (Match)entity;

            if(!this.matches.containsKey(match.toString())) //TODO: change to something valuable, unique key fr example
            {
                AMEDYSystem.systemLogger.warning("[DataBaseReturnTrueAndAdding.add.match]: match not found.");

                return false;
            }

            this.matches.remove(match.toString(), match);

            AMEDYSystem.systemLogger.info("[DataBaseReturnTrueAndAdding.add.user]: match deleted.");

            return true;
        }

        //Season
        if (entity instanceof Season) {

            Season season = (Season)entity;

            if(!this.seasons.containsKey(seasons.toString())) //TODO: change to something valuable, unique key fr example
            {
                AMEDYSystem.systemLogger.warning("[DataBaseReturnTrueAndAdding.add.season]: season not found.");

                return false;
            }

            this.seasons.remove(season.toString(), season);

            AMEDYSystem.systemLogger.info("[DataBaseReturnTrueAndAdding.add.user]: season deleted.");

            return true;
        }

        //Team
        if (entity instanceof Team) {

            Team team = (Team)entity;

            if(whichTeams.toLowerCase().equals("active")) {

                if(!this.teams.containsKey(team.getTeamName()))
                {
                    AMEDYSystem.systemLogger.warning("[DataBaseReturnTrueAndAdding.add.team]: team not found.");

                    return false;
                }

                this.teams.remove(team.getTeamName(), team);

                AMEDYSystem.systemLogger.info("[DataBaseReturnTrueAndAdding.add.user]: team-" + team.getTeamName() + "deleted.");

                return true;
            }
            else if(whichTeams.toLowerCase().equals("closedteam")) {

                if(!this.closedTeams.containsKey(team.getTeamName()))
                {
                    AMEDYSystem.systemLogger.warning("[DataBaseReturnTrueAndAdding.add.closedteams]: team not found.");

                    return false;
                }

                this.closedTeams.remove(team.getTeamName(), team);

                return true;
            }
            else if(whichTeams.toLowerCase().equals("archivedteam")) {

                if(!this.archivedTeams.containsKey(team.getTeamName()))
                {
                    AMEDYSystem.systemLogger.warning("[DataBaseReturnTrueAndAdding.add.archivedTeams]: team not found.");

                    return false;
                }

                this.archivedTeams.remove(team.getTeamName(), team);

                return true;
            }
        }
        return false;

    }

    @Override
    public boolean remove(Object firstEntity, Object secondEntity)
    {
        if(firstEntity instanceof Fan && secondEntity instanceof ProfileEventMaker) /*remove from table followers.*/
        {
            Fan fan = (Fan) firstEntity;
            ProfileEventMaker profileEventMaker = (ProfileEventMaker) secondEntity;
            for(Pair currentPair: this.followTable)
            {
                if(currentPair.getKey().equals(fan.getName()) && currentPair.getValue().equals(profileEventMaker.getName()))
                {
                    this.followTable.remove(currentPair);
                    return true;
                }
            }
        }
        return false;
    }


    public Referee selectReferee(String name) {
        if (name != null && !name.isEmpty())
            return this.referees.get(name);
        return null;
    }

    public Match selectMatch(String name) {
        if (name != null && !name.isEmpty())
            return this.matches.get(name);
        return null;
    }


    public Event selectEvent(String name) {
        if (name != null && !name.isEmpty())
            return this.events.get(name);
        return null;
    }

    public RepresentativeFootballAssociation selectRFA(String name) {
        if (name != null && !name.isEmpty())
            return this.representativeFootballAssociation.get(name.toLowerCase());
        return null;
    }

    public LeagueRankPolicy selectLeagueRankPolicy(String leagueName) {
        if (leagueName != null && !leagueName.isEmpty())
            return this.leagueRankPolicies.get(leagueName);
        return null;
    }

}

