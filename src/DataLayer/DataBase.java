package DataLayer;

import BuisinessLayer.LogicalOperations.User;

import java.sql.Connection;
import java.util.Collection;


public interface DataBase {


    /***
     * Check DB availability.
     *
     * @return True if the DB is available, False otherwise.
     */
    boolean checkConnection();


    /***
     * Get connection from the database.
     *
     * @return Connection, null if connection attempt fail.
     */
    Connection getConnection();


    /***
     * Object containing a Colection with all required element
     *
     * @param type which element are required
     *
     * @return Collection with the relevant element according to the type given.
     */
    Collection select(String type);


    /**
     * Object containing a Collection with all required elements by the 2 Keys.
     * @param type - where to select.
     * @param firstKey - the first key we search with.
     * @param secondKey - the second key we search with.
     * @return Collection of objects if found some. else - return an empty Collection.
     */
    Collection select(String type, String firstKey, String secondKey);

    /**
     * the method return the User with the given user name.
     * @param userName - the username inserted by the user.
     * @return User - an instance of the user by the user name.
     */
    User selectUser(String userName);

    /***
     * Select by key.
     *
     * @param type what to select.
     * @param key what is the key.
     *
     * @return Object as asked if key exists, empty list if could't find.
     */
    Collection select(String type, String key);
//    Object select(String type, List[] parameters ); //OLD Version


    /***
     * Add new content to an existing struct.
     *
     * @param entity the entity that need to be added.
     * @param indicator if need to pe more specific them given a entity, this will be the place for metadata.
     *
     * @return true if succeeded, false otherwise.
     */
    boolean add(Object entity, String indicator);


    /***
     * Add new content to an existing struct.
     *
     * @param entity the entity that need to be added.
     *
     * @return true if succeeded, false otherwise.
     */
    boolean add(Object entity);

    /***
     * Add new content to an existing struct by 2 entities.
     *
     * @param firstEntity the entity that need to be added.
     * @param secondEntity the second entity that need to be added.
     * @return true if succeeded, false otherwise.
     */
    boolean add(Object firstEntity, Object secondEntity);


    /***
     * Create a new Struct (table, list...)
     *
     * @param obj list of Params need to be store.
     *
     * @return true if succeeded, false otherwise.
     */
    boolean addNew(Object obj);

    /***
     * remove content to an existing struct.
     *
     * @param entity the entity that need to be removed.
     * @param indicator if need to pe more specific them given a entity, this will be the place for metadata.
     *
     * @return true if succeeded, false otherwise.
     */
    boolean remove(Object entity, String indicator);

    /***
     * remove content to an existing struct by both entities.
     *
     * @param firstEntity the first entity that need to be removed.
     * @param secondEntity the second entity that need to be removed.
     * @return true if succeeded, false otherwise.
     */
    boolean remove(Object firstEntity, Object secondEntity);

    /***
     * Each class that implement database interface need to implement
     * this function to initial all DB structs.
     */
    void initialDBStruct();
}
