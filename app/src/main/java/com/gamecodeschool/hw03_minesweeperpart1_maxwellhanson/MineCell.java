package com.gamecodeschool.hw03_minesweeperpart1_maxwellhanson;

public class MineCell {
    private boolean hasMine;
    private boolean flagged;
    private boolean revealed;

    private String position;

    public MineCell() {
        this.hasMine = false;
        this.flagged = false;
        this.revealed = false;
        this.setPosition("Unset position");
    }

    public boolean hasMine() {
        return hasMine;
    }

    public void setMine(boolean hasMine) {
        this.hasMine = hasMine;
    }

    public boolean isFlagged() {
        return flagged;
    }

    public void setFlagged(boolean flagged) {
        this.flagged = flagged;
    }

    public boolean isRevealed() {
        return revealed;
    }

    public void setRevealed(boolean revealed) {
        this.revealed = revealed;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}

