import java.util.*;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;

public class test {

    public static void main(String[]args){


								//Integrate this part with buttons from GUI so values are recieved
				
                Scanner in = new Scanner(System.in);

                System.out.println("Enter first name");
                String firstName = in.nextLine();
                
								System.out.println("Enter lastname");
                String lastName = in.nextLine();
                
								System.out.println("Insert postcode");
                String postCode = in.nextLine();
                
								System.out.println("Insert telephone number");
                int telno_user = in.nextInt();



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
                    String query = "insert into Customer (firstname, lastname, postcode, telno )" + "values (?, ?, ?, ?)";

                    PreparedStatement preparedStmt = myConnection.prepareStatement(query);
                    preparedStmt.setString(1,firstName);
                    preparedStmt.setString(2,lastName);
                    preparedStmt.setString(3,postCode);
                    preparedStmt.setInt(4,telno_user);
                    preparedStmt.execute();

                    myConnection.close();

                }
                catch (Exception e){

                    System.err.println("Got an exception");
                    System.err.println(e.getMessage());


                }



            }


        }








