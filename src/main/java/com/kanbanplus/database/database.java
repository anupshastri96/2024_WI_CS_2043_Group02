package com.kanbanplus.database;

import com.kanbanplus.classes.Card;


import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import org.apache.commons.lang3.SerializationUtils;


public class database{
    public static void main(String[] args) throws SQLException {
        Connection connector  = openConnection();
        

        if(connector == null){
            System.out.println("Unable to Connect to the Database");
            System.exit(1);
        }
        while(!checkPassword(connector)){
            System.out.println("Oopsie Dasie");
        }
        Card card = new Card("1", "Test");

        storeCard(connector, card, 1);
        ArrayList<Card> cards = getCards(connector, 1); 
        Iterator<Card> iter = cards.iterator();
        while(iter.hasNext()){
            System.out.println(iter.next().getTitle());
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

    @SuppressWarnings("resource")
    public static boolean checkPassword(Connection connectionIn){  
        Scanner passInput = new Scanner(System.in);
        String passIn = passInput.nextLine();
        try{
            String password = checkUser("adrian_2099", connectionIn);
            if(password!=null){
                if(passIn.equals(password)){
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

    public static void storeCard (Connection connectionIn,Card card,int userID){
        String query  = "insert into cards values(?,?)";
        byte data[] = SerializationUtils.serialize(card);
        try{
            PreparedStatement statement = connectionIn.prepareStatement(query);
            statement.setInt(1, userID);
            statement.setBytes(2, data);
            statement.executeUpdate();
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }


    public static ArrayList<Card> getCards(Connection connectionIn,int userID){
        String query = "select card from cards where userID = ? ";
        ArrayList<Card> cards = new ArrayList<>() ;
        try{
            PreparedStatement statement = connectionIn.prepareStatement(query);
            statement.setInt(1, userID);
            ResultSet set = statement.executeQuery();
            while(set.next()){
                Card card = SerializationUtils.deserialize(set.getBytes(1));
                cards.add(card);
            } 
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        
        return cards;
        
    }
}
