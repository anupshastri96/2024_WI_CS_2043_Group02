package com.kanbanplus.classes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Card implements Serializable{
    private String cardId;
    private String title;
    private String description;
    private PriorityLevel priority;
    private List<Label> labels;
    private List<User> assignees;
    private List<Attachment> attachments;

    public Card(String cardId, String title) {
        this.cardId = cardId;
        this.title = title;
        this.labels = new ArrayList<>();
        this.assignees = new ArrayList<>();
        this.attachments = new ArrayList<>();
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PriorityLevel getPriority() {
        return priority;
    }

    public void setPriority(PriorityLevel priority) {
        this.priority = priority;
    }

    public List<Label> getLabels() {
        return labels;
    }

    public void setLabels(List<Label> labels) {
        this.labels = labels;
    }

    public List<User> getAssignees() {
        return assignees;
    }

    public void setAssignees(List<User> assignees) {
        this.assignees = assignees;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    // Methods to add and remove labels, assignees, and attachments can be added here
}