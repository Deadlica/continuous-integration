package CIserver.app;

/**
 * Placeholder class for information of a build
 */
public class Build {
    private String commit;
    private String date;
    private String log;

    /**
     * Default constructor
     */
    public Build() {

    }

    /**
     * Parameterized constructor
     * @param commit commit SHA of the build
     * @param date date when the build was executed
     * @param log output logs of the build
     */
    public Build(String commit, String date, String log) {
        this.commit = commit;
        this.date = date;
        this.log = log;
    }

    /**
     * Gets the commit value
     * @return the commit SHA
     */
    public String getCommit() {
        return commit;
    }

    /**
     * Sets the commit value
     * @param commit the new commit SHA to be set
     */
    public void setCommit(String commit) {
        this.commit = commit;
    }

    /**
     * Gets the date value
     * @return the date of the build
     */
    public String getDate() {
        return date;
    }

    /**
     * Sets the date value
     * @param date the new build date to be set
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Gets the build logs
     * @return the output logs of the build
     */
    public String getLog() {
        return log;
    }

    /**
     * Sets the log value
     * @param log the new build logs to be set
     */
    public void setLog(String log) {
        this.log = log;
    }
}
