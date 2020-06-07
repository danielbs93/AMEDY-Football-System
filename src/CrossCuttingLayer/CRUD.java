package CrossCuttingLayer;

import java.util.Collection;
import java.util.List;

import BuisinessLayer.LogicalOperations.*;
import DataLayer.DataBase;
import java.util.ArrayList;
import java.util.Map;


public class CRUD {
    //

    //Fields
    private static DataLayer.DataBase database = null;

    /***
     * Constructor.
     *
     * @param database database to verify all CrossCuttingLayer.CRUD operations.
     */
    public CRUD(DataBase database) {
//        if(CRUD.database == null) {
//            this.database = database;
//        }
        CRUD.database = database; //TODO:Change CRUD dynamically
    }

    //SELECT
    public static List selectUser(String askBy, String key) {

        List<String> ableToSelectUser = new ArrayList<String>() {{
            add("System");
            add("User");
            add("SystemManager");
        }};

        if(ableToSelectUser.contains(askBy)) {
            return new ArrayList(CRUD.database.select("user", key));
        }

        return new ArrayList();
    }

    /**
     *
     * @param askBy
     * @param whereToSelectFrom
     * @param key String - username of the User.
     * @return
     */
    public static List select(String askBy, String whereToSelectFrom, String key) {

        List<String> ableToSelect = new ArrayList<String>() {{
            add("System");
            add("User");
            add("SystemManager");
        }};

        if(ableToSelect.contains(askBy)) {
            if(key == null || key.equals("")) {
                return new ArrayList(CRUD.database.select(whereToSelectFrom));
            }
            else{
                return new ArrayList(CRUD.database.select(whereToSelectFrom, key));
            }
        }

        return new ArrayList();
    }

    /**
     * the method get from DB all the pairs by fan name, and event maker name.
     * @param type - where to look for the data.
     * @param fan - the fan we look for.
     * @param entity - the event maker we look for.
     * @return List - of all the pairs.
     */
    public static Collection select(String type, Fan fan, Object entity)
    {
        if(type.equals("followers"))
        {
            if(entity instanceof ProfileEventMaker)
            {
                ProfileEventMaker profileEventMaker = (ProfileEventMaker) entity;
                return database.select("followers", fan.getName(), profileEventMaker.getName());
            }
            else if(entity instanceof Match)
            {
                Match match = (Match) entity;
                return database.select("followers", fan.getName(), match.getMatchID() + "");
            }
        }
        return new ArrayList<Pair>();
    }

    /**
     *
     * @param complaint
     * @return
     */
    public static int checkWhoFilledComplaint(Complaint complaint)
    {
        List<Integer> results = (ArrayList<Integer>) database.select("complaints", complaint.getComplaintID() + "");
        return results.get(0);
    }

    //ADD
    /***
     * Adding a new user to the database.
     *
     * @param askBy who is asking to create, for validation check.
     * @param user player.
     *
     * @return true if succeeded, false otherwise.
     */
    public static boolean addUser(String askBy, User user) {

        List<String> ableToAddUser = new ArrayList<String>() {{
            add("System"); //TODO: change all to lower case - yarden
            add("Guest"); //TODO: change all to lower case - yarden
        }};

        if(ableToAddUser.contains(askBy)) {
            return CRUD.database.add(user);
        }

        return false;
    }


    /***
     * Adding a new player to the database.
     *
     * @param askBy who is asking to create, for validation check.
     * @param player player.
     *
     * @return true if succeeded, false otherwise.
     */
    public static boolean addPlayer(String askBy, Player player) {

        List<String> ableToAddPlayer = new ArrayList<String>() {{
            add("System");
        }};

        if(ableToAddPlayer.contains(askBy)) {
            return CRUD.database.add(player);
        }

        return false;
    }

    /***
     * Adding a team to the database.
     *
     * @param askBy who is asking to create, for validation check.
     * @param team
     *
     * @return true if succeeded, false otherwise.
     */
    public static boolean addTeam(String askBy, Team team,String whichTeam) {

        List<String> ableToAddPlayer = new ArrayList<String>() {{
            add("System");
            add("SystemManager");
        }};

        if(ableToAddPlayer.contains(askBy)) {
            return CRUD.database.add(team,whichTeam);
        }

        return false;
    }



    /***
     * Adding a referee to the database.
     *
     * @param askBy who is asking to create, for validation check.
     * @param referee
     *
     * @return true if succeeded, false otherwise.
     */
    public static boolean addReferee(String askBy,Referee referee) {

        List<String> ableToAddPlayer = new ArrayList<String>() {{
            add("System");
            add("SystemManager");
            add("RFA");
        }};

        if(ableToAddPlayer.contains(askBy)) {
            return CRUD.database.add(referee);
        }

        return false;
    }


    public static boolean addNewStruct(String askBy, Object whatToCreate) {

        List<String> ableToAddNewStruct = new ArrayList<String>() {{
            add("System");
        }};

        if(ableToAddNewStruct.contains(askBy)) {
            return CRUD.database.addNew(whatToCreate);
        }

        return false;
    }

    /**
     * the method add to the DB a new pair of fan and entity the the followers table.
     * @param fan - the fan who want to follow (observer) after the entity.
     * @param entity - the entity who get followed (observable) by the fan.
     * @return true - if succeeded adding to the DB, else - return false.
     */
    public static boolean addNewFollowers(Fan fan, Object entity) {

        if (database.add(fan, entity)) {
            return true;
        }
        return false;

    }

    public static boolean addNewComplaint(Complaint complaint)
    {
        if(database.add(complaint))
        {
            return true;
        }
        return false;
    }

    //REMOVE
    /***
     * Remove user from the database.
     *
     * @param askBy who is asking to create, for validation check.
     * @param user to delete.
     *
     * @return true if succeeded, false otherwise.
     */
    public static boolean removeUser(String askBy, User user,String indicator) {

        List<String> ableToAddUser = new ArrayList<String>() {{
            add("System");
            add("SystemManager");
        }};

        if(ableToAddUser.contains(askBy)) {
            return CRUD.database.remove(user,indicator);
        }

        return false;
    }

    /***
     * Remove profile from the database.
     *
     * @param askBy who is asking to create, for validation check.
     * @param profile to delete.
     *
     * @return true if succeeded, false otherwise.
     */
    public static boolean removeProfile(String askBy, Profile profile,String indicator) {

        List<String> ableToAddUser = new ArrayList<String>() {{
            add("System");
            add("SystemManager");
        }};

        if(ableToAddUser.contains(askBy)) {
            return CRUD.database.remove(profile,indicator);
        }

        return false;
    }

    /***
     * Remove team from the database.
     *
     * @param askBy who is asking to create, for validation check.
     * @param team to delete.
     *
     * @return true if succeeded, false otherwise.
     */
    public static boolean removeTeam(String askBy,Team team,String whichTeam) {

        List<String> ableToAddUser = new ArrayList<String>() {{
            add("System");
            add("SystemManager");
        }};

        if(ableToAddUser.contains(askBy)) {
            return CRUD.database.remove(team,whichTeam);
        }

        return false;
    }

    /***
     * Adding a referee to the database.
     *
     * @param askBy who is asking to create, for validation check.
     * @param referee
     *
     * @return true if succeeded, false otherwise.
     */
    public static boolean removeReferee(String askBy, Referee referee) {

        List<String> ableToAddPlayer = new ArrayList<String>() {{
            add("System");
            add("SystemManager");
            add("RFA");
        }};

        if(ableToAddPlayer.contains(askBy)) {
            return CRUD.database.remove(referee,null);
        }

        return false;
    }

    /**
     * delete a record from a table by 2 foreign keys.
     * @param firstEntity - String. first foreign key.
     * @param secondEntity String. second foreign key.
     * @return true - if succeeded deleting the table. else - return false.
     */
    public static boolean deleteFromFollowersTable(Object firstEntity, Object secondEntity) {
        if(database.remove(firstEntity, secondEntity))
        {
            return true;
        }
        return false;
    }

//----------------------------------------------------------------------------------------------------------------------

    public static List selectPlayer(String tableName, String key){

        return new ArrayList(database.select(tableName, key));
    }

    public static Object selectTable(String tableName){

        return database.select(tableName, null);
    }

    public static boolean updateTable(String tableName, String key, Object entity) {
        return true;
    }

    public static boolean deleteFromTable(String tableName, Object entity) {
        return true;
    }

    public static boolean deleteFromTable(String tableName, String key) {
        return true;
    }

    public static Object selectRefereeMatches(String matches, String ID) {
//        CRUD.database.select(matches, ID);
        return null;
    }

    public static boolean createAuthorization(String system, String username, String userType) {
        return true; //STAB
    }

    public static boolean deleteUser(String askBy, User user) {

        List<String> ableToAddUser = new ArrayList<String>() {{
            add("System");
            add("SystemManager");
        }};

        if(ableToAddUser.contains(askBy)) {
            return CRUD.database.remove(user, null);
        }

        return false;
    }

    /***
     * Check if a given username is available.
     *
     * @param askBy type of profile.
     * @param username username to check available.
     *
     * @return true if username available, false if taken.
     */
    public static boolean checkUsernameAvailability(String askBy, String username) {

        List<String> ableToAddUser = new ArrayList<String>() {{
            add("System");
            add("SystemManager");
            add("Guest");
        }};

        if(ableToAddUser.contains(askBy)) {
            return CRUD.database.select("user", username).size() == 0;
        }

        return false;
    }

    /***
     * Get user from system DB and verify password.
     *
     * @param askBy how ask to preform this action.
     * @param userAnswer Map<String, String> contining user input details.
     *
     * @return user if all details, null otherwise.
     */
    public static User getUser(String askBy, Map<String, String> userAnswer) {

        List<String> ableToAddUser = new ArrayList<String>() {{
            add("System");
            add("SystemManager");
        }};

        if(ableToAddUser.contains(askBy)) {


            if(userAnswer.containsKey("username") || userAnswer.containsKey("password")) {

                String username = userAnswer.get("username");

                User user = CRUD.database.selectUser(username);

                if(user != null) {
                    String password = user.getPassword();

                    if (password.equals(userAnswer.get("password"))) {

                        return user;
                    }
                }
            }
        }

        return null;
    }


//----------------------------------------------------------------------------------------------------------------------

//    public static Object selectPlayer(String tableName, List[] key){
//
//        return database.select(tableName, key);
//    }
//
//    public static Object selectTable(String tableName){
//
//        return database.select(tableName, null);
//    }
//
//    public static boolean insertToTable(String tableName, Object entity){
//        return true;
//    }
//
//    public static boolean updateTable(String tableName, String key, Object entity) {
//        return true;
//    }
//
//    public static boolean deleteFromTable(String tableName, Object entity) {
//        return true;
//    }
//
//    public static boolean deleteFromTable(String tableName, String key) {
//        return true;
//    }
//
//    public static List select(String a, String b, List paramters){return null;}
//
//    public static boolean createUser(String table, List list){return true;}
//
//    public static boolean createAuthorization(String table, String name, String type) {return true;}
//
//    public static void createTable(String table, List parameters){}
//
//
//    public static Object selectRefereeMatches(String matches, String ID) {
////        CRUD.database.select(matches, ID);
//        return null;
//    }

//----------------------------------------------------------------------------------------------------------------------
//
//    //CREATE
//    /***
//     *
//     *
//     * @param askBy who is ask to do this CREATE TABLE operation, for permission.
//     * @param createStmt query to send that database include the whole query.
//     */
//    public static void createTable(String askBy, List<String> createStmt) {
//
//        List<String> ableToAddUser = new ArrayList<String>() {{
//            add("System");
//        }};
//
//        if(ableToAddUser.contains(askBy)) {
//            for(String stmt : createStmt) {
//                insertQuery(stmt, new ArrayList());
//            }
//        }
//    }
//
//    /***
//     *
//     * @param askBy who is ask to do this Operation, for verify permission.
//     * @param username username to insert the database.
//     * @param userType type of the authorization for this given user.
//     *
//     * @return true if the create succeeded, false otherwise.
//     */
//    public static boolean createAuthorization(String askBy, String username, String userType) {
//
//        List<String> ableToAddUser = new ArrayList<String>() {{
//            add("System");
//            add("BuisinessLayer.LogicalOperations.SystemManager");
//            add("RFA");
//        }};
//
//        if(ableToAddUser.contains(askBy)) {
//            List<BuisinessLayer.LogicalOperations.Pair> arguments = new ArrayList<>();
//
//            arguments.add(new BuisinessLayer.LogicalOperations.Pair("username", username));
//            arguments.add(new BuisinessLayer.LogicalOperations.Pair("permissionLevel", userType));
//
//            return insertQuery("Authorization", arguments);
//        }
//
//        return false;
//    }
//
//    /**
//     * Create new user.
//     *
//     * @param askBy who is ask to do this Operation, for verify permission.
//     * @param userAnswer list of all user details.
//     *
//     * @return true if the create succeeded, false otherwise.
//     */
//    public static boolean createUser(String askBy, List userAnswer) {
//
//        List<String> ableToAddUser = new ArrayList<String>() {{
//            add("System");
//        }};
//
//        if(ableToAddUser.contains(askBy)) {
//
//            return insertQuery("Users", userAnswer);
//        }
//
//        return false;
//    }
//
//    //READ
//    /***
//     *
//     */
//    public static List select(String askBy, String table, List params) {
//
//        List<String> ableToAddUser = new ArrayList<String>() {{
//            add("System");
//            add("BuisinessLayer.LogicalOperations.SystemManager");
//        }};
//
//        if(ableToAddUser.contains(askBy)) {
//
//            return selectQuery(table, params);
//
//        }
//
//        return null;
//    }
//
//    //CREATE
//    /***
//     * preform query by given the query kind and the query parameters.
//     *
//     * @param table name of the relevent table, if there is not relevant table, it will be use for the full query.
//     * @param params list of Pairs, each pair contain the field as a key and the parameter.
//     *
//     * @return true if the create succeeded, false otherwise.
//     */
//    private static boolean insertQuery(String table, List params) {
//
//        Connection conn = null;
//        PreparedStatement pstmt = null;
//
//        try{
//            //STEP 3: Open a connection
//            BuisinessLayer.LogicalOperations.AMEDYSystem.systemLogger.info("Connecting to a selected database...");
//            conn = CrossCuttingLayer.CRUD.database.getConnection();
//            BuisinessLayer.LogicalOperations.AMEDYSystem.systemLogger.info("Connected database successfully...");
//
//            //STEP 4: Execute a query
//            BuisinessLayer.LogicalOperations.AMEDYSystem.systemLogger.info("Inserting records into the table...");
//            String query = "";
//
//            String firstRow = String.format("INSERT INTO %s(", table);
//            String secondRow = " VALUES (";
//
//            for(int i = 0 ; i < params.size(); i++) {
//                BuisinessLayer.LogicalOperations.Pair p = (BuisinessLayer.LogicalOperations.Pair) params.get(i);
//                if(i + 1 < params.size() ) {
//                    firstRow += p.getKey() + ", ";
//                    secondRow += String.format("'%s', ", p.getValue());
//                }
//                else {
//                    firstRow += p.getKey() + ")";
//                    secondRow += String.format("'%s')", p.getValue());
//                }
//            }
//
//            query = firstRow + secondRow;
//
//            pstmt = conn.prepareStatement(query);
//            pstmt.executeUpdate();
//        }
//        catch(SQLException se){
//            //Handle errors for JDBC
////            se.printStackTrace();
//            BuisinessLayer.LogicalOperations.AMEDYSystem.systemLogger.warning(se.toString());
//
//        }catch(Exception e){
//            //Handle errors for Class.forName
////            e.printStackTrace();
//            BuisinessLayer.LogicalOperations.AMEDYSystem.systemLogger.warning(e.toString());
//
//        }finally {
//            //finally block used to close resources
//            try {
//                if (pstmt != null) {
//
//                    conn.close();
//                    return true;
//                }
//
//            } catch (SQLException se) {
////                se.printStackTrace();
//                BuisinessLayer.LogicalOperations.AMEDYSystem.systemLogger.warning(se.toString());
//                return false;
//            }// do nothing
//            try {
//                if (conn != null) {
//
//                    conn.close();
//                    return false;
//                }
//            } catch (SQLException se) {
//
////                se.printStackTrace();
//                BuisinessLayer.LogicalOperations.AMEDYSystem.systemLogger.warning(se.toString());
//                return false;
//            }
//        }
//        return false;
//    }
//
//    //SELECT
//    private static List selectQuery(String table, List params) {
//
//        Connection conn = null;
//        Statement stmt = null;
//        ResultSet result = null;
//        List<List<BuisinessLayer.LogicalOperations.Pair>> allResults = new ArrayList<>();
//
//        try{
//            //STEP 3: Open a connection
//            BuisinessLayer.LogicalOperations.AMEDYSystem.systemLogger.info("Connecting to a selected database...");
//            conn = CrossCuttingLayer.CRUD.database.getConnection();
//            BuisinessLayer.LogicalOperations.AMEDYSystem.systemLogger.info("Connected database successfully...");
//
//            //STEP 4: Execute a query
//            BuisinessLayer.LogicalOperations.AMEDYSystem.systemLogger.info("Inserting records into the table...");
//            String query = "";
//
//            stmt = conn.createStatement();
//
//            String firstRow = "SELECT *";
//            String secondRow = " FROM " + table;
//
//            if(params != null) {
//
//                String thirdRow = " WHERE ";
//
//                //firstRow = "SELECT (";
//
//                for(int i = 0 ; i < params.size(); i++) {
//
//                    BuisinessLayer.LogicalOperations.Pair pair = (BuisinessLayer.LogicalOperations.Pair)params.get(i);
//
//                    if(i + 1 < params.size() ) {
//                        thirdRow += String.format("%s='%s', ",pair.getKey(), pair.getValue());
//                    }
//                    else {
//                        thirdRow += String.format("%s='%s'",pair.getKey(), pair.getValue());
//                    }
//                }
//
//                query = firstRow + secondRow + thirdRow;
//            }
//            else {
//                query = firstRow + secondRow;
//            }
//
//
//            //execute
//            result = stmt.executeQuery(query);
//
//
//            while(result.next())
//            {
//                List<BuisinessLayer.LogicalOperations.Pair> currentRowData = new ArrayList<>();
//                for(int i = 1; i <= ((JDBC4ResultSet) result).getColumnCount(); i++ )
//                {
//                    String columnName = ((JDBC4ResultSet) result).getColumnLabel(i);
//                    String fieldData = result.getString(i);
//                    currentRowData.add(new BuisinessLayer.LogicalOperations.Pair(columnName, fieldData));
//                }
//                allResults.add(currentRowData);
//            }
//        }
//        catch(SQLException se){
//            //Handle errors for JDBC
////            se.printStackTrace();
//            BuisinessLayer.LogicalOperations.AMEDYSystem.systemLogger.warning(se.toString());
//
//        }catch(Exception e){
//            //Handle errors for Class.forName
////            e.printStackTrace();
//            BuisinessLayer.LogicalOperations.AMEDYSystem.systemLogger.warning(e.toString());
//
//        }finally {
//            //finally block used to close resources
//            try {
//                if (stmt != null) {
//
//                    conn.close();
//                    return allResults;
//                }
//
//            } catch (SQLException se) {
////                se.printStackTrace();
//                BuisinessLayer.LogicalOperations.AMEDYSystem.systemLogger.warning(se.toString());
//                return null;
//            }// do nothing
//            try {
//                if (conn != null) {
//
//                    conn.close();
//                    return null;
//                }
//            } catch (SQLException se) {
//
////                se.printStackTrace();
//                BuisinessLayer.LogicalOperations.AMEDYSystem.systemLogger.warning(se.toString());
//                return null;
//            }
//        }
//        return null;
//
//    }
//
}
