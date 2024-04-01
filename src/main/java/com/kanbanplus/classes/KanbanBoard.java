// KanbanBoard.java
package com.kanbanplus.classes;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class KanbanBoard implements Serializable {
    private String boardId;
    private String title;
    private List<KanbanList> lists;
    private List<User> users;

    public KanbanBoard(String boardId, String title) {
        this.boardId = boardId;
        this.title = title;
        lists = new ArrayList<KanbanList>(); 
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

    public void addToList(KanbanList listIn) {
        this.lists.add(listIn);
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    // Additional methods like adding or removing lists and users can be added here
}