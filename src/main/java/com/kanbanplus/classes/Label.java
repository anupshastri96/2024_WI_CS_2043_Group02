package com.kanbanplus.classes;
public class Label {
    private String labelId;
    private String title;
    private String colour;

    public Label(String labelId, String title, String colour) {
        this.labelId = labelId;
        this.title = title;
        this.colour = colour;
    }

    public String getLabelId() {
        return labelId;
    }

    public void setLabelId(String labelId) {
        this.labelId = labelId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }
}