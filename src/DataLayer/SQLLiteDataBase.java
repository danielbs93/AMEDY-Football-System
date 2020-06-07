package DataLayer;

import BuisinessLayer.LogicalOperations.AMEDYSystem;
import BuisinessLayer.LogicalOperations.User;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SQLLiteDataBase implements DataBase {


    //Fields
    private String path;


    /***
     * Constructor. New DataLayer.DataBase
     */
    public SQLLiteDataBase() throws FileNotFoundException {
        this("AMEDY.db");
    }


    /***
     * Constructor.
     *
     * @param DBPath path to DB.
     * @throws FileNotFoundException if file not exists.
     */
    public SQLLiteDataBase(String DBPath) throws FileNotFoundException {

        File DBFile = new File(DBPath);

        if(DBFile.exists()) {
          this.path = DBPath;
        }
        else {
            throw new FileNotFoundException("failed to connect the DB");
        }
    }

    @Override
    public boolean checkConnection() {

        Connection conn = null;

        try {
            // db parameters
            String url = String.format("jdbc:sqlite:%s", this.path);

            // create a connection to the database
            conn = DriverManager.getConnection(url);

        } catch (SQLException e) {

//            System.out.println(e.getMessage());
            return false;

        } finally {

            try {
                if (conn != null) {

                    conn.close();

                    return true;
                }

            } catch (SQLException ex) {

//                System.out.println(ex.getMessage());
                return false;
            }
        }

        return false;
    }

    @Override
    public Connection getConnection() {
        try {
            Class.forName("org.sqlite.JDBC");
            return DriverManager.getConnection(String.format("jdbc:sqlite:%s", this.path));

        } catch (SQLException e) {
            AMEDYSystem.systemLogger.warning("could't achieve connection");
            return null;

        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
            AMEDYSystem.systemLogger.warning("could't achieve connection");
            return null;
        }
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

        List<String> createStmt = new ArrayList<>();
        String createUsers = "CREATE TABLE \"Users\" (\n" +
                "\t\"userType\"\tTEXT,\n" +
                "\t\"username\"\tTEXT,\n" +
                "\t\"password\"\tTEXT,\n" +
                "\t\"name\"\tTEXT,\n" +
                "\t\"qualification\"\tTEXT,\n" +
                "\t\"birthday\"\tTEXT,\n" +
                "\t\"role\"\tTEXT,\n" +
                "\tPRIMARY KEY(\"username\")\n" +
                ")";
        String createAuthorization = "CREATE TABLE \"Authorization\" (\n" +
                "\t\"username\"\tTEXT,\n" +
                "\t\"permissionLevel\"\tINTEGER\n" +
                ")";

    }

    //    @Override
    public Object select(String type, List[] parameters) {
        return null;
    }

//    @Override
    public boolean insert(String tableName, List[] paramaters) {
        return false;
    }


}
