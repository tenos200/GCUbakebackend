import java.util.*;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;

public class test {

    public static void main(String[]args){


                Scanner in = new Scanner(System.in);

                System.out.println("Enter first name");
                String test = in.nextLine();
                System.out.println("Enter lastname");
                String test2 = in.nextLine();


                try{

                    //create my mysql database connection

                    String mysqlUrl = "jdbc:mysql://127.0.0.1:3306/GCUbake";
                    String username = "root";
                    String password = "";
                    // get a connection(Step 1)
                    Connection myConnection = DriverManager.getConnection(mysqlUrl,username,password);
                    //Create a statement(Step 2)
                    Statement myStatement = myConnection.createStatement();

                    //execute query(step 3)
                    String query = "insert into Customer (firstname, lastname)" + "values (?, ?)";

                    PreparedStatement preparedStmt = myConnection.prepareStatement(query);
                    preparedStmt.setString(1,test);
                    preparedStmt.setString(2,test2);
                    preparedStmt.execute();

                    myConnection.close();

                }
                catch (Exception e){

                    System.err.println("Got an exception");
                    System.err.println(e.getMessage());


                }



            }


        }








