package CIserver.app;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLhandler {
    // JDBC URL with file path
    static final String JDBC_URL = "jdbc:sqlite:buildHistory.db";
    static Connection conn = null;

    /*
    * Instantiate a SQLhandler that opens a connection to the file buildHistory.db. 
    * If the file does not exist, it creates a file named buildHistory.db
    * If the table BuildHistory does not exist in the file, create it.
    */
    public SQLhandler() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS BuildHistory (commit_id VARCHAR(255) PRIMARY KEY, date TIMESTAMP, logs TEXT)";
        try {
            // Open a connection
            conn = DriverManager.getConnection(JDBC_URL);

            // Create the BuildHistory table if it doesn't exist
            try (Statement stmt = conn.createStatement()) {
                stmt.executeUpdate(createTableSQL);
            }

        } catch (SQLException e) {
            // Handle errors for JDBC
            e.printStackTrace();
        }
    }

    /*
     * Method to insert a new build history entry into the database
     *
     * @param commitId  The commit ID of the build history entry
     * @param buildDate The build date of the build history entry
     * @param buildLogs The build logs of the build history entry
     * @throws SQLException If an SQL exception occurs
     */
    public static void insertEntry(String commitId, String buildDate, String buildLogs) throws SQLException {
        String insertSQL = "INSERT INTO BuildHistory (commit_id, date, logs) VALUES (?,?,?)";
        try (PreparedStatement preparedStatement = conn.prepareStatement(insertSQL)) {
            preparedStatement.setString(1, commitId);
            preparedStatement.setTimestamp(2, Timestamp.valueOf(buildDate));
            preparedStatement.setString(3, buildLogs);
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            System.out.println("commitID already exists in table \n");
        }
    }

    /*
     * Method to retrieve build history for a specific commit ID from the database.
     *
     * @param commitId The commit ID for which to retrieve build history
     * @return An array containing the build date and build logs for the given commit ID,
     *         or null if no build history is found for the commit ID
     * @throws SQLException If an SQL exception occurs
     */
    public static Build getEntry(String commitId) throws SQLException {
        String query = "SELECT * FROM BuildHistory WHERE commit_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, commitId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String buildDate = rs.getTimestamp("date").toString();
                    String buildLogs = rs.getString("logs");
                    return new Build(commitId, buildDate, buildLogs);
                } else {
                    return null;
                }
            }
        }
    }

    /*
     * Method to retrieve entire build history from the database.
     *
     * @return An array containing the build date and build logs for the given commit ID,
     *         or null if no build history is found for the commit ID
     * @throws SQLException If an SQL exception occurs
     */
    public static List<Build> getHistory() throws SQLException {
        List<Build> buildList = new ArrayList<>();
        String query = "SELECT * FROM BuildHistory";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String id = rs.getString("commit_id");
                    String date = rs.getTimestamp("date").toString();
                    String logs = rs.getString("logs");
                    Build build = new Build(id, date, logs);
                    buildList.add(build);
                }
                return buildList;
            }
        }
    }
}
