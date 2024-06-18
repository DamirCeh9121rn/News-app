package com.example.veb_projekat.repository.implementation;

import com.example.veb_projekat.entities.Comment;
import com.example.veb_projekat.repository.CommentRepository;
import com.example.veb_projekat.repository.MySqlAbstractRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommentRepositoryImpl extends MySqlAbstractRepository implements CommentRepository {
    @Override
    public Comment insert(Comment comment) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();
            String[] generatedColumns = {"id"};

            preparedStatement = connection.prepareStatement("INSERT INTO comments (newsID, author, content, createdAt, `like`, dislike) VALUES(?, ?, ?, ?, ?, ?)", generatedColumns);
            preparedStatement.setInt(1, comment.getNewsId());
            preparedStatement.setString(2, comment.getAuthor());
            preparedStatement.setString(3, comment.getContent());
            preparedStatement.setLong(4, comment.getCreatedAt());
            preparedStatement.setInt(5, 0);
            preparedStatement.setInt(6,  0);
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();

            if(resultSet.next()){
                comment.setId(resultSet.getInt(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
            this.closeResultSet(resultSet);
        }
        if(comment.getId() != null )
            return comment;

        return null;
    }

    @Override
    public Comment update(Comment comment) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        int idx = 1;
        Map<String, Integer> indexes = new HashMap<>();
        StringBuilder sb = new StringBuilder("UPDATE comments SET ");
        if(comment.getAuthor()!=null && !comment.getAuthor().isEmpty()) {
            sb.append(" author=?,");
            indexes.put("author", idx++);
        }
        if(comment.getContent()!=null && !comment.getContent().isEmpty()) {
            sb.append(" content=?,");
            indexes.put("content", idx++);
        }

        // Delete last comma sign
        sb.deleteCharAt(sb.length() - 1);
        sb.append(" WHERE id=?");
        indexes.put("id", idx);
        try {
            if(sb.toString().equals( "UPDATE comments SET WHERE id=?")){
                throw new SQLException("Nothing to update...");
            }
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement(sb.toString());

            if(sb.toString().contains("author=?")) { preparedStatement.setString(indexes.get("author"), comment.getAuthor()); }
            if(sb.toString().contains("content=?")) { preparedStatement.setString(indexes.get("content"), comment.getContent()); }
            preparedStatement.setInt(indexes.get("id"), comment.getId());

            int status = preparedStatement.executeUpdate();
            if(status == 0){
                comment = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }

        return comment;
    }

    @Override
    public Comment delete(Integer id) {
        Comment comment = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("DELETE FROM comments WHERE id = ?");
            preparedStatement.setInt(1, id);

            int status = preparedStatement.executeUpdate();
            if(status == 1){
                comment = new Comment(id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }

        return comment;
    }

    @Override
    public List<Comment> findByNewsId(Integer newsId) {
        List<Comment> comments = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM comments WHERE newsID = ? ORDER BY createdAt DESC");
            preparedStatement.setInt(1, newsId);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                comments.add(new Comment(
                        resultSet.getInt("id"),
                        resultSet.getInt("newsID"),
                        resultSet.getString("author"),
                        resultSet.getString("content"),
                        resultSet.getLong("createdAt"),
                        resultSet.getInt("like"),
                        resultSet.getInt("dislike")
                ));
            }

        } catch (Exception e){
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return comments;
    }

    @Override
    public void addCommentLike(Integer commentID) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection =this.newConnection();
            preparedStatement = connection.prepareStatement("UPDATE comments SET `like` = `like` + 1 WHERE id = ?");
            preparedStatement.setInt(1, commentID);
            int status = preparedStatement.executeUpdate();

        } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        this.closeStatement(preparedStatement);
        this.closeConnection(connection);
    }

    }

    @Override
    public void addCommentDislike(Integer commentID) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection =this.newConnection();
            preparedStatement = connection.prepareStatement("UPDATE comments SET dislike = dislike + 1 WHERE id = ?");
            preparedStatement.setInt(1, commentID);
            int status = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }
    }
}
