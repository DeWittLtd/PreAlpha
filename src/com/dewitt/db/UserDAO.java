package com.dewitt.db;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    private DataSource dataSource;

    private static UserDAO instance;

    private static final String SQL_CREATE = "INSERT INTO users VALUES (?,?,?,?,?)";
    private static final String SQL_GET = "SELECT * FROM users WHERE login=?";
    private static final String SQL_UPDATE = "UPDATE users SET latitude=?, longitude=?, bearing=? WHERE login=?";

    public static UserDAO getInstance(){
        if (instance == null)
            instance = new UserDAO();

        return instance;
    }

    private UserDAO(){
        try{
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            dataSource = (DataSource) envContext.lookup("jdbc/alpha");
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    private Connection getConnection(){
        Connection con = null;
        try{
            con = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }


    public void create(User user){
        PreparedStatement statement;

        try (Connection con = getConnection()){
            statement = con.prepareStatement(SQL_CREATE);

            int k = 1;
            statement.setInt(k++, user.getId());
            statement.setString(k++, user.getLogin());
            statement.setFloat(k++, user.getLatitude());
            statement.setFloat(k++, user.getLongitude());
            statement.setFloat(k, user.getBearing());

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User get(String login){
        PreparedStatement statement;
        ResultSet rs;

        try(Connection con = getConnection()) {
            statement = con.prepareStatement(SQL_GET);
            statement.setString(1, login);

            rs = statement.executeQuery();

            if (rs.next())
                return extractUser(rs);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private User extractUser(ResultSet rs) {
        User user = new User();
        try {
            user.setId(rs.getInt("id"));
            user.setLogin(rs.getString("login"));
            user.setLatitude(rs.getFloat("latitude"));
            user.setLongitude(rs.getFloat("longitude"));
            user.setBearing(rs.getInt("bearing"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public void update(User user){
        PreparedStatement statement;

        try (Connection con = getConnection()){
            statement = con.prepareStatement(SQL_UPDATE);
            statement.setFloat(1, user.getLatitude());
            statement.setFloat(2, user.getLongitude());
            statement.setInt(3, user.getBearing());
            statement.setString(4, user.getLogin());

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(User user){
        // TODO delete user
    }

}
