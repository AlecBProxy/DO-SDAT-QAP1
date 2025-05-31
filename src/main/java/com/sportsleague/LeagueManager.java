package com.sportsleague;

import java.util.ArrayList;
import java.util.List;

public class LeagueManager {
    private List<Team> teams;
    private List<Player> allPlayers;
    private int nextTeamId;
    private int nextPlayerId;

    public LeagueManager() {
        this.teams = new ArrayList<>();
        this.allPlayers = new ArrayList<>();
        this.nextTeamId = 1;
        this.nextPlayerId = 1;
    }

    public Team registerTeam(String teamName, String city) {
        for (Team existingTeam : teams) {
            if (existingTeam.getTeamName().equalsIgnoreCase(teamName)) {
                return null; // Team name already exists
            }
        }

        Team newTeam = new Team(nextTeamId++, teamName, city);
        teams.add(newTeam);
        return newTeam;
    }

    public Team findTeamById(int teamId) {
        for (Team team : teams) {
            if (team.getTeamId() == teamId) {
                return team;
            }
        }
        return null;
    }

    public Team findTeamByName(String teamName) {
        for (Team team : teams) {
            if (team.getTeamName().equalsIgnoreCase(teamName)) {
                return team;
            }
        }
        return null;
    }

    public Player registerPlayer(String firstName, String surname, String position) {
        Player newPlayer = new Player(nextPlayerId++, firstName, surname, position);
        allPlayers.add(newPlayer);
        return newPlayer;
    }

    public boolean assignPlayerToTeam(int playerId, int teamId) {
        Player player = findPlayerById(playerId);
        Team team = findTeamById(teamId);

        if (player == null || team == null) {
            return false;
        }

        return team.addPlayer(player);
    }

    public boolean removePlayerFromTeam(int playerId) {
        Player player = findPlayerById(playerId);
        if (player == null || !player.isAssignedToTeam()) {
            return false;
        }

        Team team = findTeamById(player.getTeamId());
        if (team != null) {
            return team.removePlayer(playerId);
        }
        return false;
    }

    public Player findPlayerById(int playerId) {
        for (Player player : allPlayers) {
            if (player.getPlayerId() == playerId) {
                return player;
            }
        }
        return null;
    }

    public List<Player> searchPlayersByName(String name) {
        List<Player> results = new ArrayList<>();
        String searchTerm = name.toLowerCase();

        for (Player player : allPlayers) {
            if (player.getFirstName().toLowerCase().contains(searchTerm) ||
                    player.getSurname().toLowerCase().contains(searchTerm)) {
                results.add(player);
            }
        }
        return results;
    }

    public List<Player> getUnassignedPlayers() {
        List<Player> unassigned = new ArrayList<>();
        for (Player player : allPlayers) {
            if (!player.isAssignedToTeam()) {
                unassigned.add(player);
            }
        }
        return unassigned;
    }

    public int getTotalTeams() {
        return teams.size();
    }

    public int getTotalPlayers() {
        return allPlayers.size();
    }

    public int getAssignedPlayers() {
        int count = 0;
        for (Player player : allPlayers) {
            if (player.isAssignedToTeam()) {
                count++;
            }
        }
        return count;
    }

    public List<Team> getAllTeams() {
        return new ArrayList<>(teams);
    }

    public List<Player> getAllPlayers() {
        return new ArrayList<>(allPlayers);
    }
}