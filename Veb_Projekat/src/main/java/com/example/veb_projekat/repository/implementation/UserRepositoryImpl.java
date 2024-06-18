package com.example.veb_projekat.repository.implementation;

import com.example.veb_projekat.entities.User;
import com.example.veb_projekat.repository.MySqlAbstractRepository;
import com.example.veb_projekat.repository.UserRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserRepositoryImpl extends MySqlAbstractRepository implements UserRepository {
    @Override
    public User insert(User user) throws Exception {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();
            String[] generatedColumns = {"id"};

            preparedStatement = connection.prepareStatement("INSERT INTO users (role, first_name, last_name, email, password, status) VALUES(?, ?, ?, ?, ?, ?)", generatedColumns);
            preparedStatement.setString(1, user.getRole());
            preparedStatement.setString(2, user.getFirstname());
            preparedStatement.setString(3, user.getLastname());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setString(5, user.getHashedPassword());
            preparedStatement.setBoolean(6, user.getStatus());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();

            if(resultSet.next()){
                user.setId(resultSet.getInt(1));
            }

        } catch (SQLException e) {
            if(e instanceof SQLIntegrityConstraintViolationException){
                if(e.getMessage().contains("'users.unique_email'")){
                    throw new Exception();
                }
            }
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
            this.closeResultSet(resultSet);
        }

        return user.getId() != null ? user : null;
    }

    @Override
    public User getById(Integer id) {
        User user = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE id=?");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                user = new User(
                        resultSet.getInt("id"),
                        resultSet.getString("role"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("email"),
                        resultSet.getBoolean("status")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
            this.closeResultSet(resultSet);
        }
        return user;
    }

    @Override
    public User update(User user) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        int idx = 1;
        Map<String, Integer> indexes = new HashMap<>();
        StringBuilder sb = new StringBuilder("UPDATE users SET ");
        if(user.getRole()!= null && !user.getRole().isEmpty()) {
            sb.append(" role=?,");
            indexes.put("role", idx++);
        }
        if(user.getFirstname()!= null && !user.getFirstname().isEmpty()) {
            sb.append(" first_name=?,");
            indexes.put("firstname", idx++);
        }
        if(user.getLastname()!= null && !user.getLastname().isEmpty()) {
            sb.append(" last_name=?,");
            indexes.put("lastname", idx++);
        }
        if(user.getEmail()!= null && !user.getEmail().isEmpty()) {
            sb.append(" email=?,");
            indexes.put("email", idx++);
        }
        if(user.getHashedPassword()!= null && !user.getHashedPassword().isEmpty()) {
            sb.append(" password=?,");
            indexes.put("password", idx++);
        }
        if(user.getStatus() != null) {
            sb.append(" status=?,");
            indexes.put("status", idx++);
        }

        // Delete last comma sign
        sb.deleteCharAt(sb.length() - 1);
        sb.append(" WHERE id=?");
        indexes.put("id", idx);
        try {
            if(sb.toString().equals( "UPDATE users SET WHERE id=?")){
                throw new SQLException("Nothing to update...");
            }

            connection = this.newConnection();

            System.out.println("QUERY: " + sb);
            preparedStatement = connection.prepareStatement(sb.toString());

            if(sb.toString().contains("role=?")) { preparedStatement.setString(indexes.get("role"), user.getRole()); }
            if(sb.toString().contains("first_name=?")) { preparedStatement.setString(indexes.get("firstname"), user.getFirstname()); }
            if(sb.toString().contains("last_name=?")) { preparedStatement.setString(indexes.get("lastname"), user.getLastname()); }
            if(sb.toString().contains("email=?")) { preparedStatement.setString(indexes.get("email"), user.getEmail()); }
            if(sb.toString().contains("password=?")) { preparedStatement.setString(indexes.get("password"), user.getHashedPassword()); }
            if(sb.toString().contains("status=?")) { preparedStatement.setBoolean(indexes.get("status"), user.getStatus()); }
            preparedStatement.setInt(indexes.get("id"), user.getId());

            int status = preparedStatement.executeUpdate();
            if(status == 0){
                user = null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }

        return user;
    }

    @Override
    public void statusActivation(Integer userId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("UPDATE users SET status=? WHERE id=?");
            preparedStatement.setBoolean(1, true);
            preparedStatement.setInt(2, userId);
            int status = preparedStatement.executeUpdate();
            System.out.println("STATUS: " + status);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }
    }

    @Override
    public void statusDeactivation(Integer userId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("UPDATE users SET status=? WHERE id=?");
            preparedStatement.setBoolean(1, false);
            preparedStatement.setInt(2, userId);
            int status = preparedStatement.executeUpdate();
            System.out.println("STATUS: " + status);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }
    }

    @Override
    public List<User> getAll(Integer page) {
        List<User> users = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM users LIMIT 10 OFFSET ?");
            preparedStatement.setInt(1, (page - 1) * 10);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                users.add(new User(
                        resultSet.getInt("id"),
                        resultSet.getString("role"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("email"),
                        resultSet.getBoolean("status")
                ));
            }

        } catch (Exception e){
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return users;
    }

    @Override
    public User findByEmail(String email) {

        User user = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE email = ?");
            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                user = new User();
                user.setId(resultSet.getInt("id"));
                user.setRole(resultSet.getString("role"));
                user.setFirstname(resultSet.getString("first_name"));
                user.setLastname(resultSet.getString("last_name"));
                user.setEmail(resultSet.getString("email"));
                user.setHashedPassword(resultSet.getString("password"));
                user.setStatus(resultSet.getBoolean("status"));
            }

        } catch (Exception e){
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return user;
    }
}
