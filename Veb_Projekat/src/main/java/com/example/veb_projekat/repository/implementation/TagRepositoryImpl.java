package com.example.veb_projekat.repository.implementation;

import com.example.veb_projekat.entities.Tag;
import com.example.veb_projekat.repository.MySqlAbstractRepository;
import com.example.veb_projekat.repository.TagRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TagRepositoryImpl extends MySqlAbstractRepository implements TagRepository {

    @Override
    public Tag insert(Tag tag) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();
            String[] generatedColumns = {"id"};

            preparedStatement = connection.prepareStatement("INSERT INTO tags (key_word) VALUES (?)", generatedColumns);
            preparedStatement.setString(1, tag.getKeyword());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();

            if(resultSet.next()){
                tag.setId(resultSet.getInt(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
            this.closeResultSet(resultSet);
        }

        return tag.getId() == null ? null : tag;
    }

    @Override
    public Tag findByKeyword(String keyword) {
        Tag tag = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = this.newConnection();

            // by default mySql in case insensitive. To make it case sensitive 'BINARY' clause is needed
            preparedStatement = connection.prepareStatement("SELECT * FROM tags WHERE BINARY key_word = ?");
            preparedStatement.setString(1, keyword);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                tag = new Tag();
                tag.setId(resultSet.getInt("id"));
                tag.setKeyword(resultSet.getString("key_word"));
            }

        } catch (Exception e){
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return tag;
    }

    @Override
    public List<Tag> getAll() {
        List<Tag> tags = new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = this.newConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM tags");

            while(resultSet.next()){
                tags.add(new Tag(
                        resultSet.getInt("id"),
                        resultSet.getString("key_word")
                ));
            }

        } catch (Exception e){
            e.printStackTrace();
        } finally {
            this.closeStatement(statement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return tags;
    }
}
