package com.sportsleague;

public class Player {
    private int playerId;
    private String firstName;
    private String surName;
    private String position;
    private int teamId;

    public Player(int playerId, String firstName, String surName, String position) {
        this.playerId = playerId;
        this.firstName = firstName;
        this.surName = surName;
        this.position = position;
        this.teamId = -1; // -1 means not currently assigned to a team
    }

    // Getters and Setters
    public int getPlayerId() {
        return playerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSurname() {
        return surName;
    }

    public String getPosition() {
        return position;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public boolean isAssignedToTeam() {
        return teamId != -1;
    }

    @Override
    public String toString() {
        return String.format("Player{id=%d, firstName='%s', surName='%s', position='%s', teamId=%d}",
                playerId, firstName, surName, position, teamId);
    }
}