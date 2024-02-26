package com.gamecodeschool.hw03_minesweeperpart1_maxwellhanson;

import java.util.List;

public class MineCell {
    private boolean hasMine;
    private boolean flagged;
    private boolean revealed;

    private String position;

    private List<String> neighbors;
    private List<String> armedNeighbors;
    private int armedNeighborsCount;


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

    public void setNeighbors(List<String> neighborPositions){
        this.neighbors = neighborPositions;
    }

    public List<String> getNeighbors(){
        return this.neighbors;
    }

    public void setArmedNeighbors(List<String> neighborArmedPositions){
        this.armedNeighbors = neighborArmedPositions;
        armedNeighborsCount = neighborArmedPositions.size();
    }

    public List<String> getArmedNeighbors(){
        return this.armedNeighbors;
    }

    public int getArmedNeighborsCount(){
        return this.armedNeighborsCount;
    }
}

