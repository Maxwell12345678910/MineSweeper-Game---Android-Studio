package com.gamecodeschool.hw03_minesweeperpart1_maxwellhanson;

public class MineCell {
    private boolean hasMine;
    private boolean flagged;
    private boolean revealed;

    public MineCell() {
        this.hasMine = false;
        this.flagged = false;
        this.revealed = false;
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
}

