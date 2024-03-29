package com.kanbanplus.database;

import com.kanbanplus.classes.KanbanBoard;
import java.sql.*;
import java.util.Scanner;
import org.apache.commons.lang3.SerializationUtils;

public class database{
    public static void main(String[] args) throws SQLException {
        Connection connector  = openConnection();
        

        if(connector == null){
            System.out.println("Unable to Connect to the Database");
            System.exit(1);
        }
        while(!checkPassword(connector,"adrian_2099"))return;
    }

    //To open a secured connection to the database
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

    //To check if the user exists or not
    private static String checkUser(String userIn, Connection connectorIn) throws SQLException{
        String usersQuery = "select password from users where username = ?;";

        PreparedStatement userStatement = connectorIn.prepareStatement(usersQuery);

        userStatement.setString(1,userIn);

        ResultSet usersResult = userStatement.executeQuery();

        if(!usersResult.next()) return null;

        else return usersResult.getString(1);
    }

    //To check if the password is correct or incorrect
    @SuppressWarnings("resource")
    public static boolean checkPassword(Connection connectorIn,String userIn){  
        Scanner passInput = new Scanner(System.in);
        String passIn = passInput.nextLine();
        try{
            String encodedpass = checkUser(userIn, connectorIn);
            if(encodedpass!=null){
                String decodedpass = jwt.decodeToken(encodedpass);
                if(passIn.equals(decodedpass)){
                    passInput.close();
                    return true;
                }
                else throw new Exception("Password Incorrect ");
            }
            else throw new Exception("Username Invalid");
            
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    //To retrieve the user ID of user that is logged in
    public static int getID(Connection connectorIn,String userIn) throws SQLException{
        String usersQuery = "select userID from users where username = ?;";

        PreparedStatement userStatement = connectorIn.prepareStatement(usersQuery);

        userStatement.setString(1,userIn);

        ResultSet usersResult = userStatement.executeQuery();
        
        return usersResult.getInt(1);
    }

    //To save the user's work done on the board
    public static void saveBoard (Connection connectorIn,KanbanBoard board,int userID){
        String query  = "update boards set board = ? where userID = ?";
        byte data[] = SerializationUtils.serialize(board);
        try{
            PreparedStatement statement = connectorIn.prepareStatement(query);
            statement.setBytes(1, data);
            statement.setInt(2, userID);
            statement.executeUpdate();
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    //To store user's board for the first time
    public static void storeBoard (Connection connectorIn,KanbanBoard board,int userID){
        String query  = "insert into boards values(?,?)";
        byte data[] = SerializationUtils.serialize(board);
        try{
            PreparedStatement statement = connectorIn.prepareStatement(query);
            statement.setInt(1, userID);
            statement.setBytes(2, data);
            statement.executeUpdate();
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    //To retrieve the board from the database
    public static KanbanBoard getBoard(Connection connectorIn,int userID){
        String query = "select board from boards where userID = ? ";
        KanbanBoard board = null ;
        try{
            PreparedStatement statement = connectorIn.prepareStatement(query);
            statement.setInt(1, userID);
            ResultSet set = statement.executeQuery();
            while(set.next()) board = SerializationUtils.deserialize(set.getBytes(1));
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        
        return board;
        
    }
}
