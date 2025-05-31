package com.sportsleague;

import java.util.ArrayList;
import java.util.List;

public class Team {
    private int teamId;
    private String teamName;
    private String city;
    private List<Player> players;
    private static final int MAX_PLAYERS = 15;

    public Team(int teamId, String teamName, String city) {
        this.teamId = teamId;
        this.teamName = teamName;
        this.city = city;
        this.players = new ArrayList<>();
    }

    public boolean addPlayer(Player player) {
        if (players.size() >= MAX_PLAYERS) {
            return false; // Team is full
        }
        if (player.isAssignedToTeam()) {
            return false; // Player already assigned to a team
        }

        players.add(player);
        player.setTeamId(this.teamId);
        return true;
    }

    public boolean removePlayer(int playerId) {

        for (int i = 0; i < players.size(); i++) {
            Player currentPlayer = players.get(i);

            if (currentPlayer.getPlayerId() == playerId) {
                Player removedPlayer = players.remove(i);
                removedPlayer.setTeamId(-1);
                return true;
            }
        }
        return false;
    }

    public Player findPlayer(int playerId) {
        for (Player player : players) {
            if (player.getPlayerId() == playerId) {
                return player;
            }
        }
        return null;
    }

    public List<Player> getRoster() {
        return new ArrayList<>(players);
    }

    // Getters
    public int getTeamId() {
        return teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public String getCity() {
        return city;
    }

    public int getPlayerCount() {
        return players.size();
    }

    public boolean isFull() {
        return players.size() >= MAX_PLAYERS;
    }

    @Override
    public String toString() {
        return String.format("Team{id=%d, name='%s', city='%s', players=%d/%d}",
                teamId, teamName, city, players.size(), MAX_PLAYERS);
    }
}