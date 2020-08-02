package com.aps6.greenmail.DAO;

import com.aps6.greenmail.connection.ConnectionFactory;
import com.aps6.greenmail.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    private final Connection connection;

    public UserDAO() {
        this.connection = new ConnectionFactory().getConnection();
    }

    public String create(User user) {
        String query = "insert into Users (username, password) values (?,?)";

        if(UserExists(user)){
            return "User already exists.";
        }

        try {
            PreparedStatement statement = this.connection.prepareStatement(query);
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getPassword());
            statement.execute();
            statement.close();
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
        return "User created successfully!";

    }

    public String readOne(User testUser) {
        String query = "select * from Users where username = ? and password = ?";
        String accessStatus;

        try {

            PreparedStatement statement = this.connection.prepareStatement(query);
            statement.setString(1, testUser.getUserName());
            statement.setString(2, testUser.getPassword());
            ResultSet resultSet = statement.executeQuery();

            User actualUser = new User();
            actualUser.setId(resultSet.getLong("id"));
            actualUser.setUserName(resultSet.getString("username"));
            actualUser.setPassword(resultSet.getString("password"));

            accessStatus = authentication(actualUser, testUser);

            resultSet.close();
            statement.close();
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }

        return accessStatus;
    }

    public boolean UserExists(User user) {
        String query = "select * from Users where username = ? and password = ?";

        try {
            PreparedStatement statement = this.connection.prepareStatement(query);
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getPassword());
            ResultSet resultSet = statement.executeQuery();

            if(!resultSet.next()) {
                resultSet.close();
                statement.close();
                return false;
            }

            resultSet.close();
            statement.close();
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }

        return true;
    }

    public String authentication(User actualUser, User testUser) throws SQLException {
        if (actualUser.getUserName().equals(testUser.getUserName())
                && actualUser.getPassword().equals(testUser.getPassword())) {

            return "Access Granted!";
        } else {
            return "Access denied, wrong password.";
        }
    }

    public String updateOne(User user, String newPassword) {
        if (!UserExists(user)) {
            return "User not found, try signing up!";
        }

        String query = "update Users set password = ? where username = ? and password = ?";

        try {
            PreparedStatement statement = this.connection.prepareStatement(query);
            statement.setString(1, newPassword);
            statement.setString(2, user.getUserName());
            statement.setString(3, user.getPassword());

            statement.execute();
            statement.close();
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }

        return "Updated successfully";
    }
}