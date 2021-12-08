package projectCosmosBot.DataBase;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import projectCosmosBot.model.*;

public class botCRUD {
    
    Connector connector;
    ResultSet resultsetquery;

    public botCRUD() {
        
    }
    
    public void retrievePostTable(String tableName){
        try{
            connector = new Connector();
            Statement statement = connector.getConnection().createStatement();
            resultsetquery = statement.executeQuery("SELECT * FROM "+tableName);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    public void getPostByCriteria(String tableName,int criteria){
        try{
            connector = new Connector();
            Statement statement = connector.getConnection().createStatement();
            resultsetquery = statement.executeQuery("SELECT * FROM "+tableName+" WHERE status="+criteria);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    public void getPostById(String tableName,int iD){
        try{
            connector = new Connector();
            Statement statement = connector.getConnection().createStatement();
            resultsetquery = statement.executeQuery("SELECT * FROM "+tableName+" WHERE id='"+iD+"'");
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    public void deleteByIdentifier(int identificador, String tableName){
        try{
            connector = new Connector();
            Statement statement = connector.getConnection().createStatement();
            statement.executeQuery("DELETE FROM "+tableName+" WHERE id="+identificador);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    public void deleteRejectedPosts(String tableName){
        try{
            connector = new Connector();
            Statement statement = connector.getConnection().createStatement();
            statement.executeQuery("DELETE FROM "+tableName+" WHERE status="+-1);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    public void addNewPost(Post post,String tableName){
        try{
            connector = new Connector();
            Statement statement = connector.getConnection().createStatement();
            statement.executeUpdate("INSERT INTO "+ tableName + " (content,name,timesreviewed,score,status)"
            						+ " VALUES ('"+ post.getContent()
                                    + "','" + post.getName()
                                    + "','" + "0"
                                    + "','" + "0"
                                    + "','" + "0"
                                    + "')");
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    public void gradePostUpdate(Post post,String tableName,int identifier){
        try{
            connector = new Connector();
            Statement statement = connector.getConnection().createStatement();
            statement.executeUpdate("UPDATE "+ tableName 
                                    + " SET timesreviewed='" + post.getTimesReviewed()
                                    + "', score='" + String.valueOf(post.getScore())
                                    + "', status='" + String.valueOf(post.getStatus())
                                    + "' WHERE id='"+identifier+"'");
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    public ResultSet getresultsetQuery(){
        return resultsetquery;
    }
    
    public boolean closebotCRUD(){
        return connector.closeConnection();
    }
    
}
