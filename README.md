# Sports League Manager - Project Documentation

## Table of Contents
1. [Project Overview](#project-overview)
2. [How It Works](#how-it-works)
3. [Clean Code Practices](#clean-code-practices)
4. [Test Cases](#test-cases)
5. [Dependencies and Build Configuration](#dependencies-and-build-configuration)
6. [Maven Plugins Explanation](#maven-plugins-explanation)

---

## Project Overview

### What It Does
The Sports League Manager is a Java-based command-line application that allows users to manage sports teams and players. The system provides functionality for:

- **Team Management**: Register new teams with names and cities
- **Player Management**: Register players with personal information and positions
- **Assignment Operations**: Assign players to teams and remove them when needed
- **Data Retrieval**: View team rosters, search players, and generate league statistics
- **Validation**: Ensure teams don't exceed maximum capacity (15 players) and prevent duplicate assignments

### Core Architecture
The application follows a clean, object-oriented design with the following classes:

1. **`LeagueManager`**: Central business logic coordinator
2. **`Team`**: Represents individual teams and manages player rosters
3. **`Player`**: Represents individual players with team assignment tracking
4. **`LeagueManagerCLI`**: Command-line interface for user interaction

---

## How It Works

### System Flow
1. **Initialization**: The CLI creates a `LeagueManager` instance to handle all operations
2. **User Interaction**: A menu-driven interface guides users through available operations
3. **Data Management**: All data is stored in memory using `ArrayList` collections

### Key Features
- **Team Registration**: Prevents duplicate team names
- **Player Assignment**: Validates team capacity and player availability
- **Search Functionality**: Case-insensitive name searching
- **Statistics**: Real-time league statistics and player counts

---

## Clean Code Practices

### Example 1: Meaningful Method Names and Single Responsibility

```java
public boolean assignPlayerToTeam(int playerId, int teamId) {
    Player player = findPlayerById(playerId);
    Team team = findTeamById(teamId);
    
    if (player == null || team == null) {
        return false;
    }
    
    return team.addPlayer(player);
}
```

**Clean Code Principles Demonstrated:**
- **Descriptive Method Name**: `assignPlayerToTeam` clearly indicates the method's purpose
- **Single Responsibility**: The method does one thing - assigns a player to a team
- **Clear Boolean Return**: Returns `true` for success, `false` for failure

### Example 2: Consistent Naming Conventions and Encapsulation

```java
public class Player {
    private int playerId;
    private String firstName;
    private String surName;
    private String position;
    private int teamId;
    
    public boolean isAssignedToTeam() {
        return teamId != -1;
    }
    
    @Override
    public String toString() {
        return String.format("Player{id=%d, firstName='%s', surName='%s', position='%s', teamId=%d}",
                playerId, firstName, surName, position, teamId);
    }
}
```

**Clean Code Principles Demonstrated:**
- **Encapsulation**: All fields are private with public accessor methods
- **Consistent Naming**: Uses camelCase consistently throughout
- **Obvious intention**: `isAssignedToTeam()` clearly indicates what the method checks
- **String Formatting**: Uses `String.format()` for clean, readable string construction

### Example 3: Input Validation and Error Handling

```java
private void registerTeam() {
    System.out.println("\n--- Register New Team ---");
    System.out.print("Enter team name: ");
    String teamName = scanner.nextLine().trim();
    
    if (teamName.isEmpty()) {
        System.out.println("Team name cannot be empty!");
        return;
    }
    
    System.out.print("Enter city: ");
    String city = scanner.nextLine().trim();
    
    if (city.isEmpty()) {
        System.out.println("City cannot be empty!");
        return;
    }
    
    Team team = leagueManager.registerTeam(teamName, city);
    if (team != null) {
        System.out.println("Team registered successfully!");
        System.out.println(team);
    } else {
        System.out.println("Failed to register team. Team name might already exist.");
    }
}
```

**Clean Code Principles Demonstrated:**
- **Input Validation**: Checks for empty strings before processing
- **User Feedback**: Provides clear success/failure messages
- **Defensive Programming**: Handles both successful and failed registration scenarios

---

## Test Cases

### Unit Test Strategy
The project uses JUnit 5 for comprehensive testing of all business logic components. Here are the key test categories:

#### LeagueManager Test Cases
1. **Team Registration Tests**
   - `testRegisterTeam_Success()`: Validates successful team registration
   - `testRegisterTeam_DuplicateName()`: Ensures duplicate team names are rejected
   - `testFindTeamById()`: Tests team retrieval by ID
   - `testFindTeamByName()`: Tests case-insensitive team name search

2. **Player Management Tests**
   - `testRegisterPlayer()`: Validates player registration
   - `testFindPlayerById()`: Tests player retrieval
   - `testSearchPlayersByName()`: Tests partial name matching

3. **Assignment Logic Tests**
   - `testAssignPlayerToTeam_Success()`: Tests successful player assignment
   - `testAssignPlayerToTeam_PlayerAlreadyAssigned()`: Prevents double assignment
   - `testRemovePlayerFromTeam()`: Tests player removal functionality

#### Team Class Test Cases
1. **Roster Management Tests**
   - `testAddPlayer_Success()`: Validates player addition
   - `testAddPlayer_TeamFull()`: Tests maximum capacity enforcement
   - `testRemovePlayer()`: Tests player removal from roster

#### Player Class Test Cases
1. **State Management Tests**
   - `testPlayerCreation()`: Validates initial player state
   - `testTeamAssignment()`: Tests team assignment tracking

### Example Test Implementation
```java
@Test
void testAssignPlayerToTeam_Success() {
    // Arrange
    Team team = leagueManager.registerTeam("Eagles", "Philadelphia");
    Player player = leagueManager.registerPlayer("John", "Doe", "Forward");
    
    // Act
    boolean result = leagueManager.assignPlayerToTeam(player.getPlayerId(), team.getTeamId());
    
    // Assert
    assertTrue(result);
    assertTrue(player.isAssignedToTeam());
    assertEquals(team.getTeamId(), player.getTeamId());
    assertEquals(1, team.getPlayerCount());
}
```

---

## Dependencies and Build Configuration

### Project Dependencies

#### JUnit 5 Testing Framework
```xml
<dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter-api</artifactId>
    <version>5.10.0</version>
    <scope>test</scope>
</dependency>
<dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter-engine</artifactId>
    <version>5.10.0</version>
    <scope>test</scope>
</dependency>
```

**Source**: [Maven Central Repository](https://mvnrepository.com/artifact/org.junit.jupiter/junit-jupiter-api)

**Why We Need It**:
- `junit-jupiter-api`: Provides annotations (`@Test`, `@BeforeEach`, etc.) and assertion methods
- `junit-jupiter-engine`: Runtime engine that executes JUnit 5 tests
- `scope>test</scope>`: Dependencies only available during test compilation and execution

---

## Maven Plugins Explanation

The `<build>` section in Maven defines how the project should be compiled, tested, and executed. Without proper plugin configuration, Maven might use outdated defaults or lack necessary functionality.

### Plugin Breakdown

#### 1. Maven Compiler Plugin
```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-compiler-plugin</artifactId>
    <version>3.11.0</version>
    <configuration>
        <source>23</source>
        <target>23</target>
    </configuration>
</plugin>
```

**Purpose**: 
- Ensures compilation uses Java 23 features
- Without this, Maven might default to older Java versions
- Matches our `<properties>` section settings

#### 2. Maven Surefire Plugin
```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-surefire-plugin</artifactId>
    <version>3.1.2</version>
</plugin>
```

**Purpose**:
- Executes unit tests during `mvn test` phase
- Generates test reports and handles test failures properly
- **Critical for GitHub Actions**: Ensures tests run automatically in CI/CD pipeline

#### 3. Exec Maven Plugin
```xml
<plugin>
    <groupId>org.codehaus.mojo</groupId>
    <artifactId>exec-maven-plugin</artifactId>
    <version>3.1.0</version>
    <configuration>
        <mainClass>${exec.mainClass}</mainClass>
    </configuration>
</plugin>
```

**Purpose**:
- Enables running the main class with `mvn exec:java`
- Uses the `exec.mainClass` property we defined (`com.sportsleague.LeagueManagerCLI`)
- Provides a standardized way to execute the application
- **Essential for CLI applications**: Allows easy execution without manual classpath management

### Integration Benefits

#### For Development
```bash
mvn compile          # Compiles with Java 23
mvn test            # Runs JUnit 5 tests with proper reporting
mvn exec:java       # Launches the CLI application
```

#### For GitHub Actions
The plugins ensure that automated workflows can:
1. **Compile** the project with correct Java version
2. **Test** using JUnit 5 with proper test discovery
3. **Execute** the application for integration testing
4. **Report** test results and coverage metrics

#### Example GitHub Actions Integration
```yaml
- name: Run tests
  run: mvn test
  
- name: Run application
  run: mvn exec:java
```
