package com.kanbanplus.database;

import java.sql.*;

public class database {
    public static void main(String[] args) throws SQLException{
        Connection connector  = openConnection();

        if(connector == null){
            System.out.println("Unable to Connect to the Database");
            System.exit(1);
        }
        
        String password = getPassword("adrian_2099", connector);
        
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

    public static String getPassword(String userIn, Connection connectorIn) throws SQLException{
        String usersQuery = "select password from users where username = ?;";

        PreparedStatement userStatement = connectorIn.prepareStatement(usersQuery);

        userStatement.setString(1,userIn);

        ResultSet usersResult = userStatement.executeQuery();

        if(!usersResult.next()) return null;

        else return usersResult.getString(1);
    }
    private static boolean checkUser(String userIn){
        boolean check = userIn == null?false:true;
        return check;
    }
}
