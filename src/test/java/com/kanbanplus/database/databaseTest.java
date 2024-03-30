package com.kanbanplus.database;

import static org.junit.Assert.assertTrue;


import java.sql.Connection;

import org.junit.Test;

import com.kanbanplus.classes.KanbanBoard;


public class databaseTest 
{
    Connection connector = database.openConnection();
    KanbanBoard board = new KanbanBoard("#b1", "New Board");

    @Test
    void testDatabaseConnection()
    {   
        assertTrue(connector!=null);
    }

    @Test
    void testCheckPassword()
    {
     assertTrue(database.checkPassword(connector, "adrian_2099", "bookworm@1"));   
    }

    @Test
    void testGetID()
    {
        assertTrue(database.getID(connector, "adrian_2099")>0);
    }

    @Test
    void testStoreBoard()
    {
        database.storeBoard(connector, board, 1);
        KanbanBoard checkBoard = database.getBoard(connector, 1, board);
        assertTrue(board == checkBoard);
    }

    @Test
    void testSaveBoard()
    {
        
    }


}
