package com.kanbanplus.database;

import java.sql.*;
import java.util.Scanner;

public class database {
    public static void main(String[] args) throws SQLException{
        Connection connector  = openConnection();
        Scanner passInput;
        

        if(connector == null){
            System.out.println("Unable to Connect to the Database");
            System.exit(1);
        }
        while(true){
             passInput = new Scanner(System.in);
            String passIn = passInput.nextLine();
            try{
                String password = checkUser("adrian_2099", connector);
                if(password!=null){
                    if(passIn.equals(password)){
                        passInput.close();
                        break;
                    }
                    else throw new Exception("Password Incorrect ");
                }
                else throw new Exception("Username Invalid");
                
            }
            catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
        
        
    }


    public static Connection openConnection(){
        final String url = "jdbc:mysql://cs1103.cs.unb.ca:3306/c63e3";
        final String user = "c63e3";
        final String password  = "HE6IgSJ6";

        Connection connect  = null;

        try{
            connect = DriverManager.getConnection(url, user, password);
        }
        catch(Exception e){
            System.out.println("Could not establish connection "+e.getMessage());
        }
        return connect;
    }

    public static String checkUser(String userIn, Connection connectorIn) throws SQLException{
        String usersQuery = "select password from users where username = ?;";

        PreparedStatement userStatement = connectorIn.prepareStatement(usersQuery);

        userStatement.setString(1,userIn);

        ResultSet usersResult = userStatement.executeQuery();

        if(!usersResult.next()) return null;

        else return usersResult.getString(1);
    }
}
