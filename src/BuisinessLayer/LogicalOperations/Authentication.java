package BuisinessLayer.LogicalOperations;

import DataLayer.DataBase;

import java.net.ConnectException;

public class Authentication {

    //Field
    private DataBase database;

    //Connections
    //None

    /***
     * Constructor.
     *
     * @param dataBase DataLayer.DataBase. database containing user data and permissions.
     * @throws ConnectException if can't reach the database.
     */
    public Authentication(DataBase dataBase) throws ConnectException {

        if(dataBase.checkConnection()) {
            this.database = dataBase;
        }
        else {
            throw new ConnectException("BuisinessLayer.LogicalOperations.Authentication could not connect to database");
        }
    }

    public DataBase getDB() {
        return this.database;
    }
}
