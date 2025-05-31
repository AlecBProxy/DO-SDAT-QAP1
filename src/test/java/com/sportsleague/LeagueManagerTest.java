package com.sportsleague;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

public class LeagueManagerTest {

    private LeagueManager league;

    @BeforeEach
    public void setUp() {
        league = new LeagueManager();
    }

    @Test
    public void testTeamRegistration() {
        Team team1 = league.registerTeam("Hornets", "Charlotte");
        assertNotNull(team1);
        assertEquals(1, team1.getTeamId());
        assertEquals("Hornets", team1.getTeamName());
        assertEquals("Charlotte", team1.getCity());
        assertEquals(1, league.getTotalTeams());

        Team team2 = league.registerTeam("Nuggets", "Denver");
        assertNotNull(team2);
        assertEquals(2, team2.getTeamId());
        assertEquals(2, league.getTotalTeams());

        Team duplicateTeam = league.registerTeam("Hornets", "Charlotte");
        assertNull(duplicateTeam);
        assertEquals(2, league.getTotalTeams()); // Count shouldn't change
    }

    @Test
    public void testPlayerRegistrationAndAssignment() {
        Team team = league.registerTeam("Raptors", "Toronto");
        Player player1 = league.registerPlayer("Kyle", "Lowry", "Point Guard");
        Player player2 = league.registerPlayer("DeMar", "DeRozan", "Shooting Guard");

        assertNotNull(player1);
        assertNotNull(player2);
        assertEquals(1, player1.getPlayerId());
        assertEquals(2, player2.getPlayerId());
        assertEquals(2, league.getTotalPlayers());
        assertEquals(0, league.getAssignedPlayers());
        assertFalse(player1.isAssignedToTeam());

        // Test successful player assignment
        assertTrue(league.assignPlayerToTeam(player1.getPlayerId(), team.getTeamId()));
        assertTrue(player1.isAssignedToTeam());
        assertEquals(team.getTeamId(), player1.getTeamId());
        assertEquals(1, league.getAssignedPlayers());
        assertEquals(1, team.getPlayerCount());

        // Test assigning player to non-existent team
        assertFalse(league.assignPlayerToTeam(player2.getPlayerId(), 999));
        assertFalse(player2.isAssignedToTeam());

        // Test assigning non-existent player
        assertFalse(league.assignPlayerToTeam(999, team.getTeamId()));
    }

    @Test
    public void testPlayerRemovalFromTeam() {
        Team team = league.registerTeam("Celtics", "Boston");
        Player player = league.registerPlayer("Bill", "Russell", "Center");

        boolean assignResult = league.assignPlayerToTeam(player.getPlayerId(), team.getTeamId());

        assertTrue(player.isAssignedToTeam());
        assertEquals(1, team.getPlayerCount());
        assertEquals(1, league.getAssignedPlayers());

        boolean removeResult = league.removePlayerFromTeam(player.getPlayerId());

        assertTrue(removeResult);
    }

    @Test
    public void testSearchFunctionality() {
        Player player1 = league.registerPlayer("Michael", "Jordan", "Shooting Guard");
        Player player2 = league.registerPlayer("Magic", "Johnson", "Point Guard");
        Player player3 = league.registerPlayer("Larry", "Bird", "Small Forward");

        List<Player> results = league.searchPlayersByName("Michael");
        assertEquals(1, results.size());
        assertTrue(results.contains(player1));

        results = league.searchPlayersByName("Johnson");
        assertEquals(1, results.size());
        assertTrue(results.contains(player2));

        //Testing of partial search
        results = league.searchPlayersByName("Joh");
        assertEquals(1, results.size());
        assertTrue(results.contains(player2));

        results = league.searchPlayersByName("BIRD");
        assertEquals(1, results.size());
        assertTrue(results.contains(player3));

        results = league.searchPlayersByName("Olajuwon");
        assertEquals(0, results.size());
    }

    @Test
    public void testUnassignedPlayersTracking() {
        Team team = league.registerTeam("Lakers", "Los Angeles");
        Player player1 = league.registerPlayer("Kobe", "Bryant", "Shooting Guard");
        Player player2 = league.registerPlayer("Shaquille", "O'Neal", "Center");
        Player player3 = league.registerPlayer("Magic", "Johnson", "Point Guard");

        List<Player> unassigned = league.getUnassignedPlayers();
        assertEquals(3, unassigned.size());
        assertTrue(unassigned.contains(player1));
        assertTrue(unassigned.contains(player2));
        assertTrue(unassigned.contains(player3));

        league.assignPlayerToTeam(player1.getPlayerId(), team.getTeamId());
        unassigned = league.getUnassignedPlayers();
        assertEquals(2, unassigned.size());
        assertFalse(unassigned.contains(player1));
        assertTrue(unassigned.contains(player2));
        assertTrue(unassigned.contains(player3));

        league.assignPlayerToTeam(player2.getPlayerId(), team.getTeamId());
        league.assignPlayerToTeam(player3.getPlayerId(), team.getTeamId());
        unassigned = league.getUnassignedPlayers();
        assertEquals(0, unassigned.size());
    }

    @Test
    public void testLeagueStatistics() {
        assertEquals(0, league.getTotalTeams());
        assertEquals(0, league.getTotalPlayers());
        assertEquals(0, league.getAssignedPlayers());

        Team team1 = league.registerTeam("Bulls", "Chicago");
        Team team2 = league.registerTeam("Heat", "Miami");
        Player player1 = league.registerPlayer("Michael", "Jordan", "Shooting Guard");
        Player player2 = league.registerPlayer("Scottie", "Pippen", "Small Forward");
        Player player3 = league.registerPlayer("Dennis", "Rodman", "Power Forward");

        assertEquals(2, league.getTotalTeams());
        assertEquals(3, league.getTotalPlayers());
        assertEquals(0, league.getAssignedPlayers());

        league.assignPlayerToTeam(player1.getPlayerId(), team1.getTeamId());
        league.assignPlayerToTeam(player2.getPlayerId(), team1.getTeamId());

        assertEquals(2, league.getTotalTeams());
        assertEquals(3, league.getTotalPlayers());
        assertEquals(2, league.getAssignedPlayers());
    }
}