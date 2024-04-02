package com.kanbanplus.database;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;

import org.junit.Test;

import com.kanbanplus.classes.KanbanBoard;
import com.kanbanplus.classes.KanbanList;


public class databaseTest 
{
    Connection connector = database.openConnection();
    KanbanBoard board = new KanbanBoard("#b1", "New Board");

    @Test
    public void testDatabaseConnection()
    {   
        assertTrue(connector!=null);
    }

    @Test
    public void testCorrectPassword() throws Exception
    {
     assertTrue(database.checkPassword(connector, "adrian_2099", "bookworm@1"));   
    }

    @Test
    public void testFalsePassword() throws Exception
    {
     assertFalse(database.checkPassword(connector, "adrian_2099", "bookworm@2"));   
    }

    @Test
    public void testGetID()
    {
        assertTrue(database.getID(connector, "adrian_2099")>0);
    }

    public void testWrongID()
    {
        assertTrue(database.getID(connector, "adrian_2097")>0);
    }

    @Test
    public void testStoreBoard()
    {
        database.storeBoard(connector, board, 1);
        KanbanBoard checkBoard = database.getBoard(connector,database.getID(connector, "adrian_2099"));
        assertTrue(checkBoard.getBoardId().equals(board.getBoardId()));
    }

    @Test
    public void testSaveBoard()
    {
        board.addToList(new KanbanList("#1", "test"));
        database.saveBoard(connector, board, 1);
        KanbanBoard checkBoard = database.getBoard(connector,database.getID(connector, "adrian_2099"));
        assertTrue(checkBoard.getLists().get(0).getListId().equals(board.getLists().get(0).getListId()));
        
    }


}
