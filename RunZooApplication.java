import java.sql.*;
import java.io.*;
import java.util.*;

/**
 * A class that connects to PostgreSQL and disconnects.
 * You will need to change your credentials below, to match the usename and password of your account
 * in the PostgreSQL server.
 * The name of your database in the server is the same as your username.
 * You are asked to include code that tests the methods of the ZooApplication class
 * in a similar manner to the sample RunFilmsApplication.java program.
*/


public class RunZooApplication
{
    public static void main(String[] args) {
    	
    	Connection connection = null;
    	try {
    	    //Register the driver
    		Class.forName("org.postgresql.Driver"); 
    	    // Make the connection.
            // You will need to fill in your real username (twice) and password for your
            // Postgres account in the arguments of the getConnection method below.
            connection = DriverManager.getConnection(
                                                     "jdbc:postgresql://cse182-db.lt.ucsc.edu/airparke",
                                                     "airparke",
                                                     "3141");
            
            if (connection != null)
                System.out.println("Connected to the database!");

            /* Include your code below to test the methods of the ZooApplication class.
             * The sample code in RunFilmsApplication.java should be useful.
             * That code tests other methods for a different database schema.
             * Your code below: */
            	
	//Test 1 getMemberStatusCount 
		ZooApplication zapp = new ZooApplication(connection);
		System.out.println ("created new ZooAPP!");	
		String mStat = new String ("H");
		int result = zapp.getMemberStatusCount(mStat);
		System.out.println( " /* \n* Output of getMemberStatusCount \n *when the parameter theMemberStatus is ‘H’. \n <" + result + "> \n*  */");
//problem, not reutning to main method

	
	//Test 2 updateMemberAddress
	
		int  mID = 1006;
		String mAddy = new String ("200 Rocky Road");
		//zapp.updateMemberAddress(mID,mAddy);
		System.out.println("/* \n* Output of updateMemberAddress when theMemberID is 1006\n * and newMemberAddress is ‘200 Rocky Road’\n<" + zapp.updateMemberAddress(mID,mAddy) +">  \n*/");
	//System.out.println("end of commands!");
		//updateMemberAddress
		//increaseSomeKeeperSalaries


	//Test 2 B
		mID=1011;
		mAddy= "300 Rocky Road";
		System.out.println("/* \n* Output of updateMemberAddress when theMemberID is 1011\n * and newMemberAddress is ‘300 Rocky Road’\n<" + zapp.updateMemberAddress(mID,mAddy) +">  \n*/");

            //Test 3
            int max= 451;
		System.out.println(" Max result= "+ zapp.increaseSomeKeeperSalaries(max) );
		max= 132;
		System.out.println(" Max result= "+ zapp.increaseSomeKeeperSalaries(max) );
		connection.close(); //closing connection
            /*******************
            * Your code ends here */
            
    	}
    	catch (SQLException | ClassNotFoundException e) {
    		System.out.println("Error while connecting to database: " + e);
    		e.printStackTrace();
    	}
    	finally {
    		if (connection != null) {
    			// Closing Connection
    			try {
					connection.close();
				} catch (SQLException e) {
					System.out.println("Failed to close connection: " + e);
					e.printStackTrace();
				}
    		}
    	}
    }
}
