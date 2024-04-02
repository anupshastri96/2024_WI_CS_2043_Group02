// KanbanList.java
package com.kanbanplus.classes;
import java.io.Serializable;
import java.util.List;


public class KanbanList implements Serializable{
    private String listId;
    private String title;
    private List<Card> cards;
    private static final long serialVersionUID = 2931086971234997193L;

    public KanbanList(String listId, String title) {
        this.listId = listId;
        this.title = title;
    }

    public String getListId() {
        return listId;
    }

    public void setListId(String listId) {
        this.listId = listId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    // Additional methods like adding or removing cards can be added here
    public void addCard(Card card) {
        cards.add(card);
    }

    public void removeCard(Card card) {
        cards.remove(card);
    }

    
}
