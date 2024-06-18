package com.example.veb_projekat.repository.implementation;

import com.example.veb_projekat.entities.Comment;
import com.example.veb_projekat.entities.News;
import com.example.veb_projekat.entities.NewsTag;
import com.example.veb_projekat.entities.Tag;
import com.example.veb_projekat.repository.*;

import javax.inject.Inject;
import java.sql.*;
import java.util.*;

public class NewsRepositoryImpl extends MySqlAbstractRepository implements NewsRepository {

    @Inject
    private TagRepository tagRepository;
    @Inject
    private NewsTagRepository newsTagRepository;
    @Inject
    private CommentRepository commentRepository;


    @Override
    public News insert(News news) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = this.newConnection();
            String[] generatedColumns = {"id"};

            preparedStatement = connection.prepareStatement("INSERT INTO news (categoryID, title, content, author, createdAt, visitis, tags, `like`, dislike) VALUES(?, ?, ?, ?, ?, ?, ?,?,?)", generatedColumns);
            preparedStatement.setInt(1, news.getCategoryId());
            preparedStatement.setString(2, news.getTitle());
            preparedStatement.setString(3, news.getContent());
            preparedStatement.setString(4, news.getAuthor());
            preparedStatement.setLong(5, news.getCreatedAt());
            preparedStatement.setInt(6, news.getVisits());
            preparedStatement.setString(7,  removeDuplicates(news.getTags()));
            preparedStatement.setInt(8, 0);
            preparedStatement.setInt(9,  0);
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();

            if(resultSet.next()){
                news.setId(resultSet.getInt(1));

                if(news.getTags()!=null && !news.getTags().isEmpty()){
                    String[] keywords = tagsToArray(news.getTags());
                    for(String keyword : keywords){
                        Tag tag = tagRepository.findByKeyword(keyword);
                        if(tag == null){
                            tag = tagRepository.insert(new Tag(keyword));
                        }
                        newsTagRepository.insert(new NewsTag(news.getId(), tag.getId()));
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
            this.closeResultSet(resultSet);
        }

        if(news.getId()!= null)
            return news;
        return  null;
    }

    @Override
    public News update(News news) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        int idx = 1;
        Map<String, Integer> indexes = new HashMap<>();
        StringBuilder sb = new StringBuilder("UPDATE news SET ");
        if(news.getCategoryId() != null) {
            sb.append(" categoryID=?,");
            indexes.put("categoryID", idx++);
        }
        if(news.getTitle()!=null && !news.getTitle().isEmpty()) {
            sb.append(" title=?,");
            indexes.put("title", idx++);
        }
        if(news.getContent()!=null && !news.getContent().isEmpty()) {
            sb.append(" content=?,");
            indexes.put("content", idx++);
        }
        if(news.getAuthor()!=null && !news.getAuthor().isEmpty()) {
            sb.append(" author=?,");
            indexes.put("author", idx++);
        }
        if(news.getTags()!=null && !news.getTags().isEmpty()) {
            sb.append(" tags=?,");
            indexes.put("tags", idx++);
        }

        // Delete last comma sign
        sb.deleteCharAt(sb.length() - 1);
        sb.append(" WHERE id=?");
        indexes.put("id", idx);
        try {
            if(sb.toString().equals( "UPDATE news SET WHERE id=?")){
                throw new SQLException("Nothing to update...");
            }


            connection = this.newConnection();

            System.out.println("QUERY: " + sb);
            preparedStatement = connection.prepareStatement(sb.toString());

            if(sb.toString().contains("categoryID=?")) { preparedStatement.setInt(indexes.get("categoryID"), news.getCategoryId()); }
            if(sb.toString().contains("title=?")) { preparedStatement.setString(indexes.get("title"), news.getTitle()); }
            if(sb.toString().contains("content=?")) { preparedStatement.setString(indexes.get("content"), news.getContent()); }
            if(sb.toString().contains("author=?")) { preparedStatement.setString(indexes.get("author"), news.getAuthor()); }
            if(sb.toString().contains("tags=?")) { preparedStatement.setString(indexes.get("tags"), removeDuplicates(news.getTags())); }
            preparedStatement.setInt(indexes.get("id"), news.getId());

            int status = preparedStatement.executeUpdate();
            if(status == 1){
                if(news.getTags()!=null && !news.getTags().isEmpty()){

                    newsTagRepository.deleteByNewsId(news.getId());
                    String[] keywords = news.getTags().split(",");

                    for(String keyword : keywords){
                        Tag tag = tagRepository.findByKeyword(keyword);
                        if(tag == null){
                            tag = tagRepository.insert(new Tag(keyword));
                        }
                        newsTagRepository.insert(new NewsTag(news.getId(), tag.getId()));
                    }
                }
            }
            else{
                news = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }

        return news;
    }

    @Override
    public News newsVisited(Integer newsId) {
        News news = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("UPDATE news SET visitis = visitis + 1 WHERE id = ?");
            preparedStatement.setInt(1, newsId);
            int status = preparedStatement.executeUpdate();

            if(status == 1){
                preparedStatement.close();
                preparedStatement = connection.prepareStatement("SELECT * FROM news WHERE id = ?");
                preparedStatement.setInt(1, newsId);

                resultSet = preparedStatement.executeQuery();
                if(resultSet.next()) {
                    news = new News(
                            resultSet.getInt("id"),
                            resultSet.getInt("categoryID"),
                            resultSet.getString("title"),
                            resultSet.getString("content"),
                            resultSet.getString("author"),
                            resultSet.getInt("like"),
                            resultSet.getInt("dislike"),
                            resultSet.getLong("createdAt"),
                            resultSet.getInt("visitis"),
                            resultSet.getString("tags")
                    );
                    List<Comment> comments = commentRepository.findByNewsId(newsId);
                    news.setComments(comments);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
            this.closeResultSet(resultSet);
        }

        return news;
    }

    @Override
    public void addNewsLike(Integer newsId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("UPDATE news SET `like` = `like` + 1 WHERE id = ?;");
            preparedStatement.setInt(1, newsId);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }

    }

    @Override
    public void addNewsDisike(Integer newsId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("UPDATE news SET dislike = dislike + 1 WHERE id = ?;");
            preparedStatement.setInt(1, newsId);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }
    }

    @Override
    public News delete(Integer id) {
        News news = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("DELETE FROM news WHERE id = ?");
            preparedStatement.setInt(1, id);
            int status = preparedStatement.executeUpdate();
            System.out.println(status);
            if(status == 1) {
                news = new News(id);
                newsTagRepository.deleteByNewsId(id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }

        return news;
    }

    @Override
    public List<News> mostRead() {
        List<News> news = new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = this.newConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM news ORDER BY visitis DESC LIMIT 10");

            while(resultSet.next()){
                news.add(new News(
                        resultSet.getInt("id"),
                        resultSet.getInt("categoryID"),
                        resultSet.getString("title"),
                        resultSet.getString("content"),
                        resultSet.getString("author"),
                        resultSet.getInt("like"),
                        resultSet.getInt("dislike"),
                        resultSet.getLong("createdAt"),
                        resultSet.getInt("visitis"),
                        resultSet.getString("tags")
                ));
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            this.closeStatement(statement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return news;
    }

    @Override
    public List<News> findAllByCategory(Integer categoryId, Integer page) {
        List<News> news = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM news WHERE categoryID = ? ORDER BY createdAt DESC LIMIT 10 OFFSET ?");
            preparedStatement.setInt(1, categoryId);
            preparedStatement.setInt(2, (page - 1) * 10);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                news.add(new News(
                        resultSet.getInt("id"),
                        resultSet.getInt("categoryID"),
                        resultSet.getString("title"),
                        resultSet.getString("content"),
                        resultSet.getString("author"),
                        resultSet.getInt("like"),
                        resultSet.getInt("dislike"),
                        resultSet.getLong("createdAt"),
                        resultSet.getInt("visitis"),
                        resultSet.getString("tags")
                ));
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return news;

    }

    @Override
    public List<News> findAllByTag(String tagName, Integer page) {
        List<News> news = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM news WHERE tags LIKE ? OR tags LIKE ? OR tags LIKE ? OR tags LIKE ? ORDER BY createdAt DESC LIMIT 10 OFFSET ?");
            String pattern1 = tagName + ",%";
            String pattern2 = "%,"+ tagName + ",%";
            String pattern3 = "%," + tagName;
            String pattern4 = tagName ;
            preparedStatement.setString(1, pattern1);
            preparedStatement.setString(2, pattern2);
            preparedStatement.setString(3, pattern3);
            preparedStatement.setString(4, pattern4);
            preparedStatement.setInt(5, (page - 1) * 10);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                news.add(new News(
                        resultSet.getInt("id"),
                        resultSet.getInt("categoryID"),
                        resultSet.getString("title"),
                        resultSet.getString("content"),
                        resultSet.getString("author"),
                        resultSet.getInt("like"),
                        resultSet.getInt("dislike"),
                        resultSet.getLong("createdAt"),
                        resultSet.getInt("visitis"),
                        resultSet.getString("tags")
                ));
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return news;
    }

    @Override
    public News findById(Integer id) {
        News news = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM news WHERE id = ?");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                news = new News();
                news.setId(resultSet.getInt("id"));
                news.setCategoryId(resultSet.getInt("categoryID"));
                news.setTitle(resultSet.getString("title"));
                news.setContent(resultSet.getString("content"));
                news.setAuthor(resultSet.getString("author"));
                news.setCreatedAt(resultSet.getLong("createdAt"));
                news.setLike(resultSet.getInt("like"));
                news.setDislike(resultSet.getInt("dislike"));
                news.setVisits(resultSet.getInt("visitis"));
                news.setTags(resultSet.getString("tags"));
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return news;
    }

    @Override
    public List<News> findNewsByWord(String word, Integer page) {
        List<News> news = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM news WHERE title LIKE ? OR news.content LIKE ? ORDER BY createdAt DESC LIMIT 10 OFFSET ?");
            String pattern1 = "%"+ word + "%";
            String pattern2 = "%"+ word + "%";
            preparedStatement.setString(1, pattern1);
            preparedStatement.setString(2, pattern2);
            preparedStatement.setInt(3, (page - 1) * 10);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                news.add(new News(
                        resultSet.getInt("id"),
                        resultSet.getInt("categoryID"),
                        resultSet.getString("title"),
                        resultSet.getString("content"),
                        resultSet.getString("author"),
                        resultSet.getInt("like"),
                        resultSet.getInt("dislike"),
                        resultSet.getLong("createdAt"),
                        resultSet.getInt("visitis"),
                        resultSet.getString("tags")
                ));
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return news;
    }

    @Override
    public List<News> findAll(int page) {
        List<News> news = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM news ORDER BY createdAt DESC LIMIT 10 OFFSET ?");
            preparedStatement.setInt(1, (page - 1) * 10);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                news.add(new News(
                        resultSet.getInt("id"),
                        resultSet.getInt("categoryID"),
                        resultSet.getString("title"),
                        resultSet.getString("content"),
                        resultSet.getString("author"),
                        resultSet.getInt("like"),
                        resultSet.getInt("dislike"),
                        resultSet.getLong("createdAt"),
                        resultSet.getInt("visitis"),
                        resultSet.getString("tags")
                ));
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return news;
    }


    private String removeDuplicates(String tag){


        String[] tags = Arrays.stream(tag.split("[,#.|;:-]")).map(String::trim).toArray(String[]::new);
        Set<String> set = new HashSet<>(Arrays.asList(tags));
        //System.out.println(set.toString() + " " +tag);
        StringBuilder sb = new StringBuilder();
        for (String s : set) {
            int i = 0;
            sb.append(s);
            if(i != set.size() - 1){
                sb.append(",");
            }
        }
        System.out.println(sb);
        return sb.toString();
    }

    private String[] tagsToArray(String tag){
        String[] tags = Arrays.stream(tag.split("[,#.|;:-]")).map(String::trim).toArray(String[]::new);
        Set<String> set = new HashSet<>(Arrays.asList(tags));

        return set.toArray(new String[0]);

    }

}
