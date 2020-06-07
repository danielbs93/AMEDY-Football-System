package DataLayer;

import BuisinessLayer.LogicalOperations.*;

import java.sql.Connection;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class HashMapDataBase implements DataBase {

    private ConcurrentHashMap<String, User> users;
    private ConcurrentHashMap<String, Player> players;
    private ConcurrentHashMap<String, Coach> coaches;
    private ConcurrentHashMap<String, Event> events;
    private ConcurrentHashMap<String, League> leagues;
    private ConcurrentHashMap<String, Match> matches;
    private ConcurrentHashMap<String, Referee> referees;
    private ConcurrentHashMap<String, Season> seasons;
    private ConcurrentHashMap<String, Team> teams;
    private ConcurrentHashMap<String, Team> closedTeams;//closed teams that can be opened by team manager/owner
    private ConcurrentHashMap<String, Team> archivedTeams;//manager system closed the team permanently
    private ConcurrentHashMap<String, TeamOwner> teamOwner;
    private ConcurrentHashMap<String, TeamManager> teamManager;


    @Override
    public boolean checkConnection() {
        return true;
    }

    @Override
    public Connection getConnection() {
        return null;
    }

    @Override
    public Collection select(String type) {
        return null;
    }

    @Override
    public Collection select(String type, String firstKey, String secondKey) {
        return null;
    }

    @Override
    public User selectUser(String userName) {
        return null;
    }

    @Override
    public Collection select(String type, String key) {
        return null;
    }

    @Override
    public boolean add(Object entity, String indicator) {
        return false;
    }

    @Override
    public boolean add(Object entity) {
        return false;
    }

    @Override
    public boolean add(Object firstEntity, Object secondEntity) {
        return false;
    }

    @Override
    public boolean addNew(Object obj) {
        return false;
    }

    @Override
    public boolean remove(Object entity, String indicator) {
        return false;
    }

    @Override
    public boolean remove(Object firstEntity, Object secondEntity) {
        return false;
    }

    @Override
    public void initialDBStruct() {

        this.users = new ConcurrentHashMap<>();
        this.players = new ConcurrentHashMap<>();
        this.coaches = new ConcurrentHashMap<>();
        this.events = new ConcurrentHashMap<>();
        this.leagues = new ConcurrentHashMap<>();
        this.matches = new ConcurrentHashMap<>();
        this.referees = new ConcurrentHashMap<>();
        this.seasons = new ConcurrentHashMap<>();
        this.teams = new ConcurrentHashMap<>();
        this.closedTeams = new ConcurrentHashMap<>(); //closed teams that can be opened by team manager/owner
        this.archivedTeams = new ConcurrentHashMap<>(); //manager system closed the team permanently
        this.teamOwner = new ConcurrentHashMap<>();
        this.teamManager = new ConcurrentHashMap<>();
    }

    //    @Override
//    example:
//    ['type':'BuisinessLayer.LogicalOperations.Fan'],
//    ['birthday':16/06/1992],
//    public Object select(String type, List[] key) {
//
//        if (type == null || type.equals("")) {
//            return null;
//        }
//        if (key == null) {
//            if (type.toLowerCase().equals("player")) {
//                return this.Players;
//            }
//            if (type.toLowerCase().equals("matches")) {
//                return this.Players;
//            }
//        } else {
//            if (type.toLowerCase().equals("player")) {
//                return this.Players.get(key);
//            } else if (type.toLowerCase().equals("matches")) {
//
//            }
//            }
//
//            return null;
//        }


//        @Override
        public boolean insert (String tableName, List[]paramaters){
            return false;
        }


}
