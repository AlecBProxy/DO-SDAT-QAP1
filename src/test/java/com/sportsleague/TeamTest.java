package com.sportsleague;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class TeamTest {

    private Team team;
    private Player player1;
    private Player player2;

    @BeforeEach
    public void setUp() {
        team = new Team(1, "Rockets", "Houston");
        player1 = new Player(1, "Jalen", "Green", "Shooting Guard");
        player2 = new Player(1, "Dillon", "Brooks", "Forward");
    }

    @Test
    public void testTeamCreation() {
        assertEquals(1, team.getTeamId());
        assertEquals("Rockets", team.getTeamName());
        assertEquals("Houston", team.getCity());
        assertEquals(0, team.getPlayerCount());
        assertFalse(team.isFull());
    }

    @Test
    public void testAddPlayerToTeam() {
        assertFalse(player1.isAssignedToTeam());
        assertEquals(0, team.getPlayerCount());

        assertTrue(team.addPlayer(player1));

        assertTrue(player1.isAssignedToTeam());
        assertEquals(1, team.getTeamId());
        assertEquals(1, team.getPlayerCount());

        assertTrue(team.getRoster().contains(player1));
    }

    @Test
    public void testCannotAddPlayerAlreadyAssigned() {
        assertTrue(team.addPlayer(player1));

        assertFalse(team.addPlayer(player1));
        assertEquals(1, team.getPlayerCount()); // Count shouldn't change
    }
}