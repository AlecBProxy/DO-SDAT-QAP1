package com.sportsleague;

import java.util.List;
import java.util.Scanner;

public class LeagueManagerCLI {
    private LeagueManager leagueManager;
    private Scanner scanner;

    public LeagueManagerCLI() {
        this.leagueManager = new LeagueManager();
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.println("Welcome to Sports League Manager!");
        System.out.println("=================================");

        while (true) {
            displayMainMenu();
            int choice = getIntInput();

            switch (choice) {
                case 1:
                    registerTeam();
                    break;
                case 2:
                    registerPlayer();
                    break;
                case 3:
                    assignPlayerToTeam();
                    break;
                case 4:
                    removePlayerFromTeam();
                    break;
                case 5:
                    viewAllTeams();
                    break;
                case 6:
                    viewAllPlayers();
                    break;
                case 7:
                    viewTeamRoster();
                    break;
                case 8:
                    searchPlayers();
                    break;
                case 9:
                    viewUnassignedPlayers();
                    break;
                case 10:
                    viewLeagueStats();
                    break;
                case 0:
                    System.out.println("Thank you for using Sports League Manager!");
                    return;
                default:
                    System.out.println("Error: Invalid choice. Please try again.");
            }

            System.out.println("\nPress Enter to continue...");
            scanner.nextLine();
        }
    }

    private void displayMainMenu() {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("SPORTS LEAGUE MANAGER - MAIN MENU");
        System.out.println("=".repeat(40));
        System.out.println("1.  Register Team");
        System.out.println("2.  Register Player");
        System.out.println("3.  Assign Player to Team");
        System.out.println("4.  Remove Player from Team");
        System.out.println("5.  View All Teams");
        System.out.println("6.  View All Players");
        System.out.println("7.  View Team Roster");
        System.out.println("8.  Search Players by Name");
        System.out.println("9.  View Unassigned Players");
        System.out.println("10. View League Statistics");
        System.out.println("0.  Exit");
        System.out.println("=".repeat(40));
        System.out.print("Please enter your choice: ");
    }

    private void registerTeam() {
        System.out.println("\n--- Register New Team ---");
        System.out.print("Enter team name: ");
        String teamName = scanner.nextLine().trim();

        if (teamName.isEmpty()) {
            System.out.println("Error: Team name cannot be empty!");
            return;
        }

        System.out.print("Enter city: ");
        String city = scanner.nextLine().trim();

        if (city.isEmpty()) {
            System.out.println("Error: City cannot be empty!");
            return;
        }

        Team team = leagueManager.registerTeam(teamName, city);
        if (team != null) {
            System.out.println("Team registered successfully!");
            System.out.println(team);
        } else {
            System.out.println("Error: Failed to register team. Team name already exists.");
        }
    }

    private void registerPlayer() {
        System.out.println("\n--- Register New Player ---");
        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine().trim();

        if (firstName.isEmpty()) {
            System.out.println("Error: First name cannot be empty!");
            return;
        }

        System.out.print("Enter surname: ");
        String surname = scanner.nextLine().trim();

        if (surname.isEmpty()) {
            System.out.println("Error: Surname cannot be empty!");
            return;
        }

        System.out.print("Enter position: ");
        String position = scanner.nextLine().trim();

        if (position.isEmpty()) {
            System.out.println("Error: Position cannot be empty!");
            return;
        }

        Player player = leagueManager.registerPlayer(firstName, surname, position);
        System.out.println("Player registered successfully!");
        System.out.println(player);
    }

    private void assignPlayerToTeam() {
        System.out.println("\n--- Assign Player to Team ---");

        // Show unassigned players
        List<Player> unassignedPlayers = leagueManager.getUnassignedPlayers();
        if (unassignedPlayers.isEmpty()) {
            System.out.println("No unassigned players available.");
            return;
        }

        System.out.println("Unassigned Players:");
        for (Player player : unassignedPlayers) {
            System.out.println(player);
        }

        System.out.print("Enter player ID: ");
        int playerId = getIntInput();

        // Show available teams
        List<Team> teams = leagueManager.getAllTeams();
        if (teams.isEmpty()) {
            System.out.println("No teams available. Please register a team first.");
            return;
        }

        System.out.println("\nAvailable Teams:");
        for (Team team : teams) {
            System.out.println(team);
        }

        System.out.print("Enter team ID: ");
        int teamId = getIntInput();

        if (leagueManager.assignPlayerToTeam(playerId, teamId)) {
            System.out.println("Player assigned to team successfully!");
        } else {
            System.out.println("Error: Failed to assign player to team. Check if player/team exists and team is not full.");
        }
    }

    private void removePlayerFromTeam() {
        System.out.println("\n--- Remove Player from Team ---");

        // Show assigned players
        List<Player> allPlayers = leagueManager.getAllPlayers();
        List<Player> assignedPlayers = allPlayers.stream()
                .filter(Player::isAssignedToTeam)
                .toList();

        if (assignedPlayers.isEmpty()) {
            System.out.println("No players are currently assigned to teams.");
            return;
        }

        System.out.println("Assigned Players:");
        for (Player player : assignedPlayers) {
            Team team = leagueManager.findTeamById(player.getTeamId());
            System.out.println(player + " (Team: " + team.getTeamName() + ")");
        }

        System.out.print("Enter player ID to remove: ");
        int playerId = getIntInput();

        if (leagueManager.removePlayerFromTeam(playerId)) {
            System.out.println("Player removed from team successfully!");
        } else {
            System.out.println("Failed to remove player from team. Player might not exist or not assigned to any team.");
        }
    }

    private void viewAllTeams() {
        System.out.println("\n--- All Teams ---");
        List<Team> teams = leagueManager.getAllTeams();

        if (teams.isEmpty()) {
            System.out.println("No teams registered yet.");
            return;
        }

        for (Team team : teams) {
            System.out.println(team);
        }
    }

    private void viewAllPlayers() {
        System.out.println("\n--- All Players ---");
        List<Player> players = leagueManager.getAllPlayers();

        if (players.isEmpty()) {
            System.out.println("No players registered yet.");
            return;
        }

        for (Player player : players) {
            if (player.isAssignedToTeam()) {
                Team team = leagueManager.findTeamById(player.getTeamId());
                System.out.println(player + " (Team: " + team.getTeamName() + ")");
            } else {
                System.out.println(player + " (Unassigned)");
            }
        }
    }

    private void viewTeamRoster() {
        System.out.println("\n--- View Team Roster ---");

        List<Team> teams = leagueManager.getAllTeams();
        if (teams.isEmpty()) {
            System.out.println("No teams available.");
            return;
        }

        System.out.println("Available Teams:");
        for (Team team : teams) {
            System.out.println(team);
        }

        System.out.print("Enter team ID: ");
        int teamId = getIntInput();

        Team team = leagueManager.findTeamById(teamId);
        if (team == null) {
            System.out.println("Team not found!");
            return;
        }

        System.out.println("\n--- " + team.getTeamName() + " Roster ---");
        List<Player> roster = team.getRoster();

        if (roster.isEmpty()) {
            System.out.println("No players in this team.");
        } else {
            for (Player player : roster) {
                System.out.println(player);
            }
        }
    }

    private void searchPlayers() {
        System.out.println("\n--- Search Players ---");
        System.out.print("Enter search term (first name or surname): ");
        String searchTerm = scanner.nextLine().trim();

        if (searchTerm.isEmpty()) {
            System.out.println("Search term cannot be empty!");
            return;
        }

        List<Player> results = leagueManager.searchPlayersByName(searchTerm);

        if (results.isEmpty()) {
            System.out.println("No players found matching '" + searchTerm + "'");
        } else {
            System.out.println("Search Results:");
            for (Player player : results) {
                if (player.isAssignedToTeam()) {
                    Team team = leagueManager.findTeamById(player.getTeamId());
                    System.out.println(player + " (Team: " + team.getTeamName() + ")");
                } else {
                    System.out.println(player + " (Unassigned)");
                }
            }
        }
    }

    private void viewUnassignedPlayers() {
        System.out.println("\n--- Unassigned Players ---");
        List<Player> unassigned = leagueManager.getUnassignedPlayers();

        if (unassigned.isEmpty()) {
            System.out.println("All players are assigned to teams.");
        } else {
            for (Player player : unassigned) {
                System.out.println(player);
            }
        }
    }

    private void viewLeagueStats() {
        System.out.println("\n--- League Statistics ---");
        System.out.println("Total Teams: " + leagueManager.getTotalTeams());
        System.out.println("Total Players: " + leagueManager.getTotalPlayers());
        System.out.println("Assigned Players: " + leagueManager.getAssignedPlayers());
        System.out.println("Unassigned Players: " + (leagueManager.getTotalPlayers() - leagueManager.getAssignedPlayers()));
    }

    private int getIntInput() {
        while (true) {
            try {
                int input = Integer.parseInt(scanner.nextLine().trim());
                return input;
            } catch (NumberFormatException e) {
                System.out.print("Please enter a valid number: ");
            }
        }
    }

    public static void main(String[] args) {
        LeagueManagerCLI cli = new LeagueManagerCLI();
        cli.start();
    }
}