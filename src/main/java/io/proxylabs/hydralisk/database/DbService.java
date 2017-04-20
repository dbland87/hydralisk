package io.proxylabs.hydralisk.database;

import io.proxylabs.hydralisk.models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

/**
 * Created by dbland on 4/17/17.
 */
public class DbService {

    private static final String PROPERTIES_DB_USER = "dbuser";
    private static final String PROPERTIES_DB_PASSWORD = "dbpassword";
    private static final String PROPERTIES_JDBC_URL = "jdbc_url";
    private static final String USER_ID = "id";
    private static final String USER_NAME = "name";
    private static final String USER_BAG_ID = "bag_id";

    private static DbService dbService;
    private Properties properties;
    private Connection connection;

    private DbService(Properties properties){
        this.properties = properties;
    }

    public static DbService getInstance(Properties properties){
        if (dbService == null){
            dbService = new DbService(properties);
        }
        return dbService;
    }

    private boolean connect(){
        try {
            connection = DriverManager.getConnection(properties.getProperty(PROPERTIES_JDBC_URL), properties.getProperty(PROPERTIES_DB_USER), properties.getProperty(PROPERTIES_DB_PASSWORD));
        } catch (SQLException e) {
            //TODO Log this and handle
        }
        return connection != null;
    }

    private void close(){
        try{
            if (connection != null){
                connection.close();
            }
        } catch (SQLException e){

        }
    }

    public ArrayList<User> retrieveAllUsers(){
        String query = "select * from users";
        ArrayList<User> allUsers = new ArrayList<User>();
        Statement statement = null;
        ResultSet resultSet = null;
        if (connect()){
            try{
                statement = connection.createStatement();
                resultSet = statement.executeQuery(query);

                if (resultSet != null){
                    while (resultSet.next()){
                        allUsers.add(new User(resultSet.getInt(USER_ID), resultSet.getString(USER_NAME), resultSet.getInt(USER_BAG_ID)));
                    }
                }
            } catch (Exception e) {
                //TODO Log and handle
            }
        }
        close();
        return allUsers;
    }

}
