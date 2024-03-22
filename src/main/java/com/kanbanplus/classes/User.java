public class User {
    private String userId;
    private String username;
    private List<Card> assignedCards;

    public User(String userId, String username, List<Card> assignedCards) {
        this.userId = userId;
        this.username = username;
        this.assignedCards = assignedCards;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Card> getAssignedCards() {
        return assignedCards;
    }

    public void setAssignedCards(List<Card> assignedCards) {
        this.assignedCards = assignedCards;
    }
}

