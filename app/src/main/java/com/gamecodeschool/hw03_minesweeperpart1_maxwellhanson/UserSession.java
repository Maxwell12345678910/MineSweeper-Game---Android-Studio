package com.gamecodeschool.hw03_minesweeperpart1_maxwellhanson;
public class UserSession {
    private String username;
    private int score;

    public UserSession(String username, int score) {
        this.username = username;
        this.score = score;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
