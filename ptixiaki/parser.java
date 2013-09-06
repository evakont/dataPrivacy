package ptixiaki;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.sql.*;
/**
 *
 * @author eva
 */
public class parser {


    public static int values[][] = new int [10000][2];

    static Connection con;
    public parser(Connection conn){
	con = conn;
    }
   /**
    * sindeete stin vasi kai pairnei tis plirofories gia to
    * istogramma apo to table Hist kai tis bazei ston
    * pinaka values gia na tis xrisimopoiisei sto histog kai
    * na zografisei to istogramma
    */
    public void GetData(String name)throws SQLException{
        Statement statement = (Statement) con.createStatement();
        int i = 0;
        String SelectQuery = "SELECT val, pl FROM Hist WHERE name = "+ name +";";
        try{
	    ResultSet result = statement.executeQuery(SelectQuery);
            while(result.next()){
                values[i][0] = result.getInt(1);
                values[i][1] = result.getInt(2);
                i++;
            }
	    statement.close();
	}catch(Exception E){
            System.out.println("get data "+E.getMessage());
        }
    }
    
}