package com.apps.repository;

import com.apps.domain.Application;
import com.apps.domain.User;
import org.apache.catalina.core.AprLifecycleListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;

@Repository
public class UserRepository {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private Object User;

    public ArrayList<User> getAllUsers() {
        ArrayList<User> resultList = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/appstore_db", "postgres", "root")) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM users");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setUser_login(resultSet.getString("user_login"));
                user.setUser_password(resultSet.getString("user_password"));
                user.setEmail(resultSet.getString("email"));
                user.setFirst_name(resultSet.getString("first_name"));
                user.setLast_name(resultSet.getString("last_name"));
                user.setCreated(resultSet.getDate("created"));
                user.setEdited(resultSet.getDate("edited"));
                user.set_deleted(resultSet.getBoolean("is_deleted"));
                resultList.add((com.apps.domain.User) User);
            }
        } catch (SQLException e) {
            System.out.println("getAllUsers ERROR");
        }
        return resultList;
    }

    public static User getUserById(int id) {
        User user = new User();
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/appstore_db", "postgres", "root")) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE id=?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            resultSet.next();
            user.setId(resultSet.getInt("id"));
            user.setUser_login(resultSet.getString("user_login"));
            user.setUser_password(resultSet.getString("user_password"));
            user.setEmail(resultSet.getString("email"));
            user.setFirst_name(resultSet.getString("first_name"));
            user.setLast_name(resultSet.getString("last_name"));
            user.setCreated(resultSet.getDate("created"));
            user.setEdited(resultSet.getDate("edited"));
            user.set_deleted(resultSet.getBoolean("is_deleted"));
        } catch (SQLException e) {
            System.out.println("getUserById ERROR");
        }
        return user;
    }

    public boolean createUser(String user_login, String user_password, String email, String firstName, String lastName) throws SQLException {
        int result = 0;
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/appstore_db", "postgres", "root")) {

            PreparedStatement statement = connection.prepareStatement("INSERT INTO users (id, user_login, user_password, email, first_name, last_name, created, edited, is_deleted) " +
                    "VALUES (DEFAULT, ?, ?, ?, ?, ?, ?, ?, DEFAULT,)");
            statement.setString(1, user_login);
            statement.setString(2, user_password);
            statement.setString(3, email);
            statement.setString(4, firstName);
            statement.setString(5, lastName);
            statement.setDate(6, new Date((new java.util.Date()).getTime()));
            statement.setDate(7, new Date((new java.util.Date()).getTime()));

            result = statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("createUser ERROR");
        }
        return result == 1;
    }

    public boolean updateUser(int id, String login, String password, String email, String first_name, String last_name, boolean edited) {
        int result = 0;
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/appstore_db", "postgres", "root")) {

            PreparedStatement statement = connection.prepareStatement("UPDATE users SET user_login=?, user_password=?, email=?, first_name=?, last_name=?, edited=? WHERE id=?");
            statement.setString(1, login);
            statement.setString(2, password);
            statement.setString(3, email);
            statement.setString(4, first_name);
            statement.setString(5, last_name);
            statement.setDate(6, new Date((new java.util.Date()).getTime()));
            statement.setInt(7, id);

            result = statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("updateUser ERROR");
        }
        return result == 1;
    }

    public boolean deleteUser(int id) {
        int result = 0;
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/appstore_db", "postgres", "root")) {

            PreparedStatement statement = connection.prepareStatement("UPDATE users SET is_deleted=true WHERE id=?");
            statement.setInt(1, id);
            result = statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("deleteUser ERROR");
        }
        return result == 1;
    }

    public ArrayList<Application> getApplicationsForSingleUser(int id) throws SQLException {
        ArrayList<Application> applicationsList = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/appstore_db", "postgres", "root")) {
            PreparedStatement statement = connection.prepareStatement("SELECT a.id, a.app_name, a.app_category, a.rating, a.description, a.app_year, a.price FROM l_applications_users JOIN applications as a ON l_applications_users.applications_id = a.id WHERE l_applications_users.user_id=?");
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Application application = new Application();
                application.setId(resultSet.getInt("id"));
                application.setApp_name(resultSet.getString("app_name"));
                application.setApp_category(resultSet.getString("app_category"));
                application.setRating(resultSet.getDouble("rating"));
                application.setDescription(resultSet.getString("description"));
                application.setApp_year(resultSet.getInt("app_year"));
                application.setPrice(resultSet.getDouble("price"));
                applicationsList.add(application);
            }
        } catch (SQLException e) {
            System.out.println("getApplicationsForSingleUser ERROR");
        }
        return applicationsList;
    }

    public boolean addApplicationToUser(int userId, int applicationId) {
        int result = 0;
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/appstore_db", "postgres", "root")) {

            PreparedStatement statement = connection.prepareStatement("INSERT INTO l_applications_users (id, user_id, applications_id) " + "VALUES (DEFAULT, ?, ?)");
            statement.setInt(1, userId);
            statement.setInt(2, applicationId);

            result = statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("addApplicationToUser ERROR");
        }
        return result == 1;
    }

    public boolean deleteApplicationFromUser(int userId, int applicationId) { //TODO: DELETE
        int result = 0;
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/appstore_db", "postgres", "root")) {

            PreparedStatement statement = connection.prepareStatement("DELETE FROM l_applications_users (id, user_id, applications_id) " + "VALUES (DEFAULT, ?, ?)");
            statement.setInt(1, userId);
            statement.setInt(2, applicationId);

            result = statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("deleteApplicationFromUser ERROR");
        }
        return result == 1;
    }
}
