package com.kanbanplus.database;

import com.kanbanplus.classes.KanbanBoard;
import java.sql.*;
import java.util.ArrayList;
import org.apache.commons.lang3.SerializationUtils;

public class database{


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
    private static String checkUser(String userIn, Connection connectorIn){
        try{
            String usersQuery = "select password from users where username = ?;";

            PreparedStatement userStatement = connectorIn.prepareStatement(usersQuery);

            userStatement.setString(1,userIn);

            ResultSet usersResult = userStatement.executeQuery();

            if(!usersResult.next()) return null;

            else return usersResult.getString(1);
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
            return null;
        }
        
    }

    //To check if the password is correct or incorrect

    public static boolean checkPassword(Connection connectorIn,String userIn,String passIn) throws Exception{  
        String pass = checkUser(userIn, connectorIn);
        if(pass!=null){
            if(passIn.equals(pass)){
                return true;
            }
            else throw new Exception("Password Incorrect ");
        }
        else throw new Exception("Username Invalid");
    }

    //To retrieve the user ID of user that is logged in
    public static int getID(Connection connectorIn,String userIn){
        int userID = 0;
        try{
            String usersQuery = "select userID from users where username = ?;";
            PreparedStatement userStatement = connectorIn.prepareStatement(usersQuery);

            userStatement.setString(1,userIn);

            ResultSet usersResult = userStatement.executeQuery();
            
            while(usersResult.next()) userID=usersResult.getInt(1);

            return userID;
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
            return userID;
        }
    }

    //To save the user's work done on the board
    public static void saveBoard (Connection connectorIn,KanbanBoard board,int userID){
        String query  = "update boards set board = ? where userID = ?";
        //Convert Object to bytes
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
        //Convert Object to bytes
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
    public static ArrayList<KanbanBoard> getBoards(Connection connectorIn,int userID){
        String query = "select board from boards where userID = ?";
        ArrayList<KanbanBoard> boards = new ArrayList<KanbanBoard>() ;
        try{
            PreparedStatement statement = connectorIn.prepareStatement(query);
            statement.setInt(1, userID);
            ResultSet set = statement.executeQuery();
            //Converting bytes back to object and adding them to the arraylist
            while(set.next()) boards.add((KanbanBoard)SerializationUtils.deserialize(set.getBytes(1)));
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        
        return boards;
        
    }
}
