package io.proxylabs.spawning_pool.database;

import io.proxylabs.spawning_pool.models.FcmToken;
import io.proxylabs.spawning_pool.models.LootTableItem;
import io.proxylabs.spawning_pool.models.Unit;
import io.proxylabs.spawning_pool.models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.Int;

/**
 * Created by dbland on 4/17/17.
 */
public class DbService {

    private static final String PROPERTIES_DB_USER = "dbuser";
    private static final String PROPERTIES_DB_PASSWORD = "dbpassword";
    private static final String PROPERTIES_JDBC_URL = "jdbc_url";
    private static final String TABLE_UNIT_TEMPLATES = "unit_templates";
    private static final String TABLE_USER_AUTH_FCMTOKEN = "user_auth_fcmtoken";
    private static final String TABLE_UNITS = "units_unit";
    private static final String TABLE_USERS = "user_auth_user";

    private static final String USER_ID = "id";
    private static final String USER_NAME = "name";
    private static final String USER_BAG_ID = "bag_id";
    private static final String LOOT_TABLE_UNIT_ID = "unit_id";
    private static final String LOOT_TABLE_UNIT_DROP_WEIGHT = "unit_drop_weight";
    private static final String UNIT_TEMPLATES_ID = "id";
    private static final String UNIT_TEMPLATES_NAME = "name";
    private static final String UNIT_TEMPLATES_TYPE = "type";
    private static final String FCMTOKEN_ID = "id";
    private static final String FCMTOKEN_USER_ID = "user_id";
    private static final String FCMTOKEN_BODY = "body";

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
        String query = String.format("select * from %s;", TABLE_USERS);
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
            } catch (SQLException e) {
                //TODO Log and handle
            }
        }
        close();
        return allUsers;
    }

    public ArrayList<LootTableItem> retrieveLootTable(String table){
        String query = "select * from " + table + ";";
        ArrayList<LootTableItem> allLootTableItems = new ArrayList<LootTableItem>();
        Statement statement = null;
        ResultSet resultSet = null;
        if (connect()){
            try{
                statement = connection.createStatement();
                resultSet = statement.executeQuery(query);

                if (resultSet != null){
                    while (resultSet.next()){
                        allLootTableItems.add(new LootTableItem(resultSet.getInt(LOOT_TABLE_UNIT_ID), resultSet.getInt(LOOT_TABLE_UNIT_DROP_WEIGHT)));
                    }
                }

            } catch (SQLException e){
                //TODO Log and handle
            }
        }
        close();
        return allLootTableItems;
    }

    public Unit getUnitFromId(int id){
        String query = String.format("select * from %s where id = %s;", TABLE_UNIT_TEMPLATES, id);
        System.out.println("Getting Unit, query: " + query);
        Statement statement = null;
        ResultSet resultSet = null;
        if (connect()){
            try{
                statement = connection.createStatement();
                resultSet = statement.executeQuery(query);

                if (resultSet != null){
                    while (resultSet.next()) {
                        return new Unit(resultSet.getInt(UNIT_TEMPLATES_ID), resultSet.getString(UNIT_TEMPLATES_NAME), resultSet.getString(UNIT_TEMPLATES_TYPE));
                    }
                }

            } catch (SQLException e){
                //TODO Log and handle
            }

        }
        return null;
    }

    public FcmToken getTokenFromUser(User user){
        String query = String.format("select * from %s where user_id = %s;", TABLE_USER_AUTH_FCMTOKEN, user.getId());
        System.out.println("Getting token, query: " + query);
        Statement statement = null;
        ResultSet resultSet = null;
        if (connect()){
            try{
                statement = connection.createStatement();
                resultSet = statement.executeQuery(query);

                if (resultSet != null){
                    while (resultSet.next()) {
                        return new FcmToken(resultSet.getInt(FCMTOKEN_ID), resultSet.getString(FCMTOKEN_BODY), resultSet.getInt(FCMTOKEN_USER_ID));
                    }
                }

            } catch (SQLException e){
                //TODO Log and handle
            }

        }
        return null;
    }

    public void saveUnit(Unit unit, User user){
        String query = String.format("insert into `%s` (`name`, `bag_id`) VALUES ('%s', '%s');", TABLE_UNITS, unit.getName(), user.getBag_id());
        System.out.println("Saving unit, query: " + query);
        Statement statement = null;
        if (connect()){
            try{
                statement = connection.createStatement();
                statement.execute(query);
            } catch (SQLException e){
                //TODO Log and handle
            }
        }
    }
}
