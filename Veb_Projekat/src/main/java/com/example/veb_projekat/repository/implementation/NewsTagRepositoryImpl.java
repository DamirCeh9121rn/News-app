package com.example.veb_projekat.repository.implementation;

import com.example.veb_projekat.entities.NewsTag;
import com.example.veb_projekat.repository.MySqlAbstractRepository;
import com.example.veb_projekat.repository.NewsTagRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NewsTagRepositoryImpl extends MySqlAbstractRepository implements NewsTagRepository {
    @Override
    public NewsTag insert(NewsTag newsTag) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();
            String[] generatedColumns = {"id"};

            preparedStatement = connection.prepareStatement("INSERT INTO news_tag (newsID, tagID) VALUES(?, ?)", generatedColumns);
            preparedStatement.setInt(1, newsTag.getNewsId());
            preparedStatement.setInt(2, newsTag.getTagId());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();

            if(resultSet.next()){
                newsTag.setId(resultSet.getInt(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
            this.closeResultSet(resultSet);
        }

        return newsTag;
    }

    @Override
    public void deleteByNewsId(Integer newsId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("DELETE FROM news_tag WHERE newsID = ?");
            preparedStatement.setInt(1, newsId);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }
    }

    @Override
    public List<NewsTag> findAllByNewsId(Integer newsId) {
        List<NewsTag> newsTags = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM news_tag WHERE newsID = ?");
            preparedStatement.setInt(1, newsId);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                newsTags.add(new NewsTag(
                        resultSet.getInt("id"),
                        resultSet.getInt("newsID"),
                        resultSet.getInt("tagID")
                ));
            }

        } catch (Exception e){
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return newsTags;
    }

    @Override
    public List<NewsTag> findAllByTagId(Integer tagId) {
        List<NewsTag> newsTags = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM news_tag WHERE tagID = ?");
            preparedStatement.setInt(1, tagId);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                newsTags.add(new NewsTag(
                        resultSet.getInt("id"),
                        resultSet.getInt("newsID"),
                        resultSet.getInt("tagID")
                ));
            }

        } catch (Exception e){
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return newsTags;
    }
}
