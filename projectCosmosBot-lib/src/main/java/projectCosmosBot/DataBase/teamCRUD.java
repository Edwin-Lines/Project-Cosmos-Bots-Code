package projectCosmosBot.DataBase;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import projectCosmosBot.model.*;

public class teamCRUD {
	Connector connector;
    ResultSet resultsetquery;
    
    public teamCRUD(){
    	
    }
    
    public void retrieveAccountsTable(String tableName){
        try{
            connector = new Connector();
            Statement statement = connector.getConnection().createStatement();
            resultsetquery = statement.executeQuery("SELECT * FROM "+tableName);
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
    
    public void addTeamMemeber(Account account,String tableName){
        try{
            connector = new Connector();
            Statement statement = connector.getConnection().createStatement();
            statement.executeUpdate("INSERT INTO "+ tableName + "(rol,username,password)"
            						+ " VALUES ('"+ account.getRole()
                                    + "','" + account.getUser()
                                    + "','" + account.getPassword()
                                    + "')");
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    public ResultSet getresultsetQuery(){
        return resultsetquery;
    }
    
    public boolean closeTeamCRUD(){
        return connector.closeConnection();
    }
    
}
