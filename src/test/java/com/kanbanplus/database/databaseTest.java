package com.kanbanplus.database;

import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
    public void testCheckPassword() throws Exception
    {
     assertTrue(database.checkPassword(connector, "adrian_2099", "bookworm@1"));   
    }

    @Test
    public void testGetID()
    {
        assertTrue(database.getID(connector, "adrian_2099")>0);
    }

    @Test
    public void testStoreBoard()
    {
        database.storeBoard(connector, board, 1);
        ArrayList<KanbanBoard> checkBoard = database.getBoards(connector, 1);
        Iterator<KanbanBoard> iter = checkBoard.iterator();
        while(iter.hasNext()) assertTrue(iter.next().getBoardId().equals(board.getBoardId()));
    }

    @Test
    public void testSaveBoard()
    {
        List<KanbanList> list = new ArrayList<KanbanList>();
        list.add(new KanbanList("#1", "test"));
        board.setLists(list);
        database.saveBoard(connector, board, 1);
        ArrayList<KanbanBoard> checkBoard = database.getBoards(connector, 1);
        Iterator<KanbanBoard> iter = checkBoard.iterator();
        while(iter.hasNext()) assertTrue(iter.next().getLists().get(0).getListId().equals(board.getLists().get(0).getListId()));
        
    }


}
