import java.sql.*;
import java.util.*;

/**
 * The class implements methods of the Zoo database interface.
 *
 * All methods of the class receive a Connection object through which all
 * communication to the database should be performed. Note: the
 * Connection object should not be closed by any method.
 *
 * Also, no method should throw any exceptions. In particular, in case
 * an error occurs in the database, then the method should print an
 * error message and call System.exit(-1);
 */

public class ZooApplication {

    private Connection connection;

    /*
     * Constructor
     */
    public ZooApplication(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection()
    {
        return connection;
    }

    /**
     * getMemberStatusCount: This method has a string argument called theMemberStatus, and
     * returns the number of Keepers whose keeperLevel equals theMemberStatus.
     * A value of theMemberStatus that’s not ‘L’, ‘M’ or ‘H’ (corresponding to Low, Medium, and
     * High) is an error.
     */

    public Integer getMemberStatusCount(String theMemberStatus) throws SQLException
    {
        Integer result = 0;
        // your code here
        System.out.println( "get member  mstat= " + theMemberStatus );
	if (!( theMemberStatus.equals("L") || theMemberStatus.equals("M") || theMemberStatus.equals("H"))){
		System.out.println("get member status invalid entry");
		System.exit(-1);
		}  // check constraint are applicable
//	System.out.println("test2");
 
	Statement stat1 = connection.createStatement();
	PreparedStatement stat2 = connection.prepareStatement("SELECT COUNT(*) FROM Members  WHERE memberStatus LIKE '" + theMemberStatus+ "'" );
	ResultSet result2 = stat2.executeQuery();	
//	System.out.println("test1");

	while (result2.next()){
		result = result2.getInt("count");
		System.out.println("result= " + result);
		}
//	System.out.println("end of getmemstatus");
        // end of your code
        //connection.close();
        return result;	
    }


    /**
     * updateMemberAddress:  Sometimes a member changes address.  The updateMemberAddress method
     * has two arguments, an integer argument theMemberID, and a string argument,
     * newMemberAddress.  For the tuple in the Members table (if any) whose memberID equals
     * theMemberID, updateMemberAddress should update the address to be newMemberAddress.
     * (Note that there might not be any tuples whose memberID matches theMemberID.)
     *
     * updateMemberAddress should return the number of tuples that were updated, which will
     * always be 0 or 1.
     */

    public int updateMemberAddress(int theMemberID, String newMemberAddress)
    {
        // your code here; return 0 appears for now to allow this skeleton to compile.
        
	System.out.println( " mID= " + theMemberID + " nMemAddy= " + newMemberAddress );
	//PreparedStatement stat2 = connection.prepareStatement("UPDATE members SET address = '"+ newMemberAddress+ "'  WHERE memberID = " + theMemberID +";" );
        //int result2 = stat2.executeUpdate();
//	Statement stat1 = connection.createStatement();
        int result2=0;
	try { 

	
	PreparedStatement stat2 = connection.prepareStatement("UPDATE members SET address = '"+ newMemberAddress+ "'  WHERE "+ theMemberID+ "= memberID;");
         result2 = stat2.executeUpdate();
     // System.out.println("test1");


		}

		catch(SQLException e){ 
			e.printStackTrace();}
		System.out.println("result= "+ result2);
		return result2;

        
        // end of your code
    }


    /**
     * increaseSomeKeeperSalaries: This method has an integer parameter, maxIncreaseAmount.
     * It invokes a stored function increaseSomeKeeperSalariesFunction that you will need to
     * implement and store in the database according to the description in Section 5.
     * increaseSomeKeeperSalariesFunction should have the same parameter, maxIncreaseAmount.
     * A value of maxIncreaseAmount that’s not positive is an error.
     *
     * The Keepers table has a keeperSalary attribute, which gives the salary (in dollars and
     * cents) for each keepers.  increaseSomeKeeperSalariesFunction will increase the salary
     * for some (but not necessarily all) keepers; Section 5 explains which keepers should have
     * their salaries increased, and also tells you how much they should be increased.
     * The increaseSomeKeeperSalaries method should return the same integer result that the
     * increaseSomeKeeperSalariesFunction stored function returns.
     *
     * The increaseSomeKeeperSalaries method must only invoke the stored function
     * increaseSomeKeeperSalariesFunction, which does all of the assignment work; do not
     * implement the increaseSomeKeeperSalaries method using a bunch of SQL statements through
     * JDBC.
     */

    public int increaseSomeKeeperSalaries (int maxIncreaseAmount)
    {
        // There's nothing special about the name storedFunctionResult
        int storedFunctionResult = 0;

        // your code here
	
		storedFunctionResult= increaseSomeKeeperSalariesFunction(maxIncreaseAmount);

        // end of your code
        return storedFunctionResult;

    }

	public int increaseSomeKeeperSalariesFunction (int  maxIncreaseAmount){
	
	int result2=0;
	int total_increase =0;// must ne less than maxIncreaseAmount
// if null, keeper salary will not increase
	String level= new String ("A");
	int num= 10;	
	//System.out.println("raw raw");

try{
		//process A
	PreparedStatement stat1 = connection.prepareStatement("SELECT COUNT(*) FROM Keepers WHERE keeperlevel ='A' AND keeperSalary IS NOT NULL;");
	ResultSet result1 = stat1.executeQuery();
	Integer result = 0;
	result1.next();
	result= result1.getInt("count");
	System.out.println("result= "+ result*num);
	
while ( (result*num)>=maxIncreaseAmount) result--; //check max constraints

	if ((result*num)<maxIncreaseAmount){	
		total_increase=total_increase+(result*num);
		System.out.println("total increase= "+ total_increase);
		PreparedStatement stat2 = connection.prepareStatement("UPDATE keepers SET KeeperSalary = KeeperSalary+10 WHERE KeeperLevel IN (SELECT KeeperLevel FROM Keepers WHERE KeeperLevel='A' AND Keepersalary IS NOT NULL  ORDER BY hireDate ASC FOR UPDATE );");
	         result2 = stat2.executeUpdate();

}
		//process B
	PreparedStatement statB = connection.prepareStatement("SELECT COUNT(*) FROM Keepers WHERE keeperlevel ='B' AND keeperSalary IS NOT NULL;");
        result1 = statB.executeQuery();
        result1.next();
        result= result1.getInt("count");
        //System.out.println("result= "+ result*num);

	num= 20;
        if ((result*num)<maxIncreaseAmount){
                total_increase=total_increase+(result*num);
                System.out.println("total increase= "+ total_increase);
                PreparedStatement statB1 = connection.prepareStatement("UPDATE keepers SET KeeperSalary = KeeperSalary+20 WHERE KeeperLevel IN (SELECT KeeperLevel FROM Keepers WHERE KeeperLevel='B' AND Keepersalary IS NOT NULL  ORDER BY hireDate ASC FOR UPDATE );");
                 result2 = statB1.executeUpdate();
} // end if

	//Process C
	PreparedStatement statC = connection.prepareStatement("SELECT COUNT(*) FROM Keepers WHERE keeperlevel ='C' AND keeperSalary IS NOT NULL;");
        result1 = statC.executeQuery();
        result1.next();
        result= result1.getInt("count");
        

        num= 30;
        if ((result*num)<maxIncreaseAmount){
                total_increase= total_increase+(result*num);
                System.out.println("total increase= "+ total_increase);
                PreparedStatement statC1 = connection.prepareStatement("UPDATE keepers SET KeeperSalary = KeeperSalary+20 WHERE KeeperLevel IN (SELECT KeeperLevel FROM Keepers WHERE KeeperLevel='B' AND Keepersalary IS NOT NULL  ORDER BY hireDate ASC FOR UPDATE );");
                 result2 = statC1.executeUpdate();

	
	}// end if
}
  catch(SQLException e){ 
                        e.printStackTrace();}
          System.out.println("total increase= "+ total_increase);
	return total_increase;		
	}

};



