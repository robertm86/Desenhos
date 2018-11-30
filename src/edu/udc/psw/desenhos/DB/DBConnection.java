package edu.udc.psw.desenhos.DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	private static Connection connection = null; // gerencia a conex�o
    private static int connectionCount = 0;
    
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    
    // As informa��es das seguintes strings n�o devem ser declaradas no c�digo, em uma aplica��o em produ��o
    private static final String DATABASE_URL = "jdbc:mysql://localhost/desenhos?useSSL=false";
    private static String USR = "root";
    private static String PASS = "root";
    
    public static Connection getConnection()
    {
    	if(connection == null)
    	{
		    try 
		    {
		       Class.forName( JDBC_DRIVER ); // carrega classe de driver do banco de dados
		
		       // estabelece conex�o com o banco de dados
		       connection = DriverManager.getConnection( DATABASE_URL, USR, PASS );
		    }
		    catch (SQLException sqlException)                                
		    {                                                                  
		       sqlException.printStackTrace();
		       System.exit( 1 );                                               
		    }
		    catch (ClassNotFoundException classNotFound)                     
		    {                                                                  
		       classNotFound.printStackTrace();            
		       System.exit( 1 );                                               
		    }
    	}
    	connectionCount++;
    	return connection;
    }
    
    public static boolean isConnected()
    {
    	if(connection == null)
    		return false;
    	return true;
    }
    
    public static void closeConnection() throws IllegalStateException
    {
    	if(connectionCount == 0) 
    		throw new IllegalStateException("Not Connected to Database");
    	connectionCount--;
    	if(connectionCount == 0)
    	{
	        try                                                        
	        {                                                          
	           connection.close();                                     
	        }
	        catch ( Exception exception )                              
	        {                                                          
	           exception.printStackTrace();                            
	           System.exit( 1 );                                       
	        }
    	}
    }
}
