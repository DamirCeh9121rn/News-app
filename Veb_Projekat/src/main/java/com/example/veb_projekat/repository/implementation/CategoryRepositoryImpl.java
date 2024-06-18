package com.example.veb_projekat.repository.implementation;

import com.example.veb_projekat.entities.Category;
import com.example.veb_projekat.repository.CategoryRepository;
import com.example.veb_projekat.repository.MySqlAbstractRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryRepositoryImpl extends MySqlAbstractRepository implements CategoryRepository {
    @Override
    public List<Category> getAll(Integer page) {
        List<Category> categories = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM categorys LIMIT 10 OFFSET ?");
            preparedStatement.setInt(1, (page - 1) * 10);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                categories.add(new Category(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("description")
                ));
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return categories;
    }

    @Override
    public Category getById(Integer id) {
        Category category = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM categorys WHERE id=?");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                category = new Category(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("description")
                );
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }
        return category;
    }

    @Override
    public Category insert(Category category) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();
            String[] generatedColumns = {"id"};

            preparedStatement = connection.prepareStatement("INSERT INTO categorys (name, description) VALUES(?, ?)", generatedColumns);
            preparedStatement.setString(1, category.getName());
            preparedStatement.setString(2, category.getDescription());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                category.setId(resultSet.getInt(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
            this.closeResultSet(resultSet);
        }

        if(category.getId() != null)
            return category;
        return null;
    }

    @Override
    public Category update(Category category) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        int idx = 1;
        Map<String, Integer> indexes = new HashMap<>();
        StringBuilder sb = new StringBuilder("UPDATE categorys SET ");
        if(category.getName()!=null && !category.getName().isEmpty()) {
            sb.append(" name=?,");
            indexes.put("name", idx++);
        }
        if(category.getDescription()!=null && !category.getDescription().isEmpty()) {
            sb.append(" description=?,");
            indexes.put("description", idx++);
        }
        // Delete last comma sign
        sb.deleteCharAt(sb.length() - 1);
        sb.append(" WHERE id=?");

        indexes.put("id", idx);

        try {
            if (sb.toString().equals("UPDATE categorys SET WHERE id=?")) {
                throw new SQLException("Nothing to update...");
            }

            connection = this.newConnection();

            System.out.println("QUERY: " + sb);
            preparedStatement = connection.prepareStatement(sb.toString());

            if(sb.toString().contains("name=?")) { preparedStatement.setString(indexes.get("name"), category.getName()); }
            if(sb.toString().contains("description=?")) { preparedStatement.setString(indexes.get("description"), category.getDescription()); }
            preparedStatement.setInt(indexes.get("id"), category.getId());

            int status = preparedStatement.executeUpdate();
            if(status == 0){
                category = null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }

        return category;
    }

    @Override
    public Category delete(Integer id) {
        Category category = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("SELECT COUNT(*) AS total FROM news WHERE categoryID = ?");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            resultSet.next();
            int count = resultSet.getInt("total");
            System.out.println("COUNT: " + count);
            if (count > 0)
                throw new Exception();

            closeStatement(preparedStatement);
            preparedStatement = connection.prepareStatement("DELETE FROM categorys WHERE id = ?");
            preparedStatement.setInt(1, id);
            int status = preparedStatement.executeUpdate();

            if(status == 1){
                category = new Category(id);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return category;
    }
}
