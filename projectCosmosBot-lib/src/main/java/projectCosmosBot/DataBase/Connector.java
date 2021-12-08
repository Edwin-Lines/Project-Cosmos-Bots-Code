package projectCosmosBot.DataBase;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
// jdbc:mariadb://localhost:3306/DB?user=root&password="
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Connector {
    private Connection conn = null;
    private Properties DBurl;
    private Properties DBCredentials;
    String urlString;

    public Connector() {
        this.conn=null;
        this.DBurl = new Properties();
        this.DBCredentials = new Properties();
    }
    
    private void getPropertiesDBURL(String dbPropertiesproperties){
        try {
            InputStream input = new FileInputStream(dbPropertiesproperties);
            this.DBurl.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void getPropertiesCredentials(String dbCredentialsProperties){
        try {
            InputStream input = new FileInputStream(dbCredentialsProperties);
            this.DBCredentials.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public Connection getConnection() {
        try {
            String DBurltoString="";
            String DBCredentialstoString="";
            getPropertiesDBURL("dbinfo.properties");
            getPropertiesCredentials("credentials.properties");
            DBurltoString = getDBurlStringConnection(this.DBurl);
            DBCredentialstoString = getDBCredentials(this.DBCredentials);
            this.conn = DriverManager.getConnection(DBurltoString+DBCredentialstoString);
            System.out.println("Success Connection \n");
        } catch (SQLException ex) {
            // handle errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return conn;
    }
    
    private String getDBurlStringConnection(Properties DBurl){
        String urlStringConnection="";
        urlStringConnection = DBurl.get("db.connector") + ":"+
                              DBurl.get("db.dms")+"://"+
                              DBurl.get("db.serverName")+":"+
                              DBurl.get("db.portNumber")+"/"+
                              DBurl.get("db.name")+"?";
        return urlStringConnection;
    }
    
    private String getDBCredentials(Properties DBCredentials){
        String CredentialsStringConnection="";
        CredentialsStringConnection = "user=" + DBCredentials.get("user") +
                                      "&password="+ DBCredentials.get("password");
        return CredentialsStringConnection;
    }

    public boolean closeConnection(){
        boolean flag = true;
        try {
            this.conn.close();
            System.out.println("Closed connection \n");
        } catch (SQLException ex) {
            flag = false;
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return flag;
    }

}
