package com.sportsleague;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {

    @Test
    public void testPlayerCreation() {
        Player player = new Player(1, "Jalen", "Green", "Shooting Guard");

        assertEquals(1, player.getPlayerId());
        assertEquals("Jalen", player.getFirstName());
        assertEquals("Green", player.getSurname());
        assertEquals("Shooting Guard", player.getPosition());
        assertEquals(-1, player.getTeamId());
        assertFalse(player.isAssignedToTeam());
    }

    @Test
    public void testPlayerTeamAssignment() {
        Player player = new Player(2, "Dillon", "Brooks", "Forward");

        // Initially not assigned
        assertFalse(player.isAssignedToTeam());

        // Assign to team
        player.setTeamId(10);
        assertEquals(10, player.getTeamId());
        assertTrue(player.isAssignedToTeam());
    }
}