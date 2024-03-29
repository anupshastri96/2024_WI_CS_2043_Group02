// KanbanBoard.java
package com.kanbanplus.classes;
import java.util.List;

import com.kanbanplus.database.KanbanList;

public class KanbanBoard {
    private String boardId;
    private String title;
    private List<KanbanList> lists;
    private List<User> users;

    public KanbanBoard(String boardId, String title) {
        this.boardId = boardId;
        this.title = title;
    }

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<KanbanList> getLists() {
        return lists;
    }

    public void setLists(List<KanbanList> lists) {
        this.lists = lists;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    // Additional methods like adding or removing lists and users can be added here
}