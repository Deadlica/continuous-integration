import java.sql.*;

public class SQLserver {
    // JDBC URL with file path
    static final String JDBC_URL = "jdbc:sqlite:CI_BuildHistory.db";
    static Connection conn = null;

    /*
    Instantiate the build history database if it does not already exist
    
    */
    public static void main(String[] args) {
        Statement stmt = null;

        try {
            // Open a connection
            conn = DriverManager.getConnection(JDBC_URL);

            // Create the BuildHistory table if it doesn't exist
            stmt = conn.createStatement();
            String createTableSQL = "CREATE TABLE IF NOT EXISTS BuildHistory (commit_id VARCHAR(255) PRIMARY KEY, build_date TIMESTAMP, build_logs VARCHAR(255))";
            stmt.executeUpdate(createTableSQL);

            // TEST REMOVE LATER
            insertBuildHistoryEntry("125","2020-02-05 04:05:02","Everything looks horrible");
            insertBuildHistoryEntry("126","2020-02-30 01:30:01","Everything looks bad!");
            String[] list = getBuildHistoryEntry("123");
            for(String elem : list)
                System.out.print(elem + " ");
            System.out.println();

        } catch (SQLException se) {
            // Handle errors for JDBC
            se.printStackTrace();
        } finally {
            // Close resources
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException se2) {} // nothing we can do
            try {
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            } // end finally try
        } // end try
    }

    /*
     * Method to insert a new build history entry into the database
     *
     * @param commitId  The commit ID of the build history entry
     * @param buildDate The build date of the build history entry
     * @param buildLogs The build logs of the build history entry
     * @throws SQLException If an SQL exception occurs
     */
    private static void insertBuildHistoryEntry(String commitId, String buildDate, String buildLogs) throws SQLException {
        PreparedStatement preparedStatement = null;
        try {
            String insertSQL = "INSERT INTO BuildHistory (commit_id, build_date, build_logs) VALUES (?,?,?)";
            preparedStatement = conn.prepareStatement(insertSQL);
            preparedStatement.setString(1, commitId);
            preparedStatement.setTimestamp(2, Timestamp.valueOf(buildDate));
            preparedStatement.setString(3, buildLogs);
            preparedStatement.executeUpdate();
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
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
    private static String[] getBuildHistoryEntry(String commitId) throws SQLException {
        PreparedStatement stmt = null;
        try {
            String query = "SELECT * FROM BuildHistory WHERE commit_id = ?";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, commitId);

            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                String buildDate = rs.getTimestamp("build_date").toString();
                String buildLogs = rs.getString("build_logs");
                return new String[]{commitId, buildDate, buildLogs};
            } else {
                return null;
            }
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }
}
