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
public class ApplicationRepository {

    public static Application getApplicationById(int id) {
        Application application = new Application();
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/appstore_db", "postgres", "root")) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM applications WHERE id=?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            resultSet.next();
            application.setId(resultSet.getInt("id"));
            application.setApp_name(resultSet.getString("app_name"));
            application.setApp_category(resultSet.getString("app_category"));
            application.setRating(resultSet.getDouble("rating"));
            application.setDescription(resultSet.getString("description"));
            application.setApp_year(resultSet.getInt("app_year"));
            application.setPrice(resultSet.getDouble("price"));
        } catch (SQLException e) {
            System.out.println("getApplicationById ERROR");
        }
        return application;
    }

    public boolean createApplication(String app_name, String app_category, double rating, String description, int app_year, double price) throws SQLException {
        int result = 0;
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/appstore_db", "postgres", "root")) {

            PreparedStatement statement = connection.prepareStatement("INSERT INTO applications (id, app_name, app_category, rating, description, app_year, price) " +
                    "VALUES (DEFAULT, ?, ?, ?, ?, ?, ?, ?, ?)");
            statement.setString(1, app_name);
            statement.setString(2, app_category);
            statement.setDouble(3, rating);
            statement.setString(4, description);
            statement.setInt(5, app_year);
            statement.setDouble(6, price);

            result = statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("createApplication ERROR");
        }
        return result == 1;
    }

    public boolean updateApplication(int id, double rating, String description, double price) {
        int result = 0;
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/appstore_db", "postgres", "root")) {

            PreparedStatement statement = connection.prepareStatement("UPDATE applications SET rating=?, description=?, price=? WHERE id=?");
            statement.setDouble(1, rating);
            statement.setString(2, description);
            statement.setDouble(3, price);

            result = statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("updateApplication ERROR");
        }
        return result == 1;
    }
}
