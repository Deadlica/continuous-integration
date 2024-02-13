package CIserver.app;

import java.io.IOException;

public class StatusUpdater {
    private static String baseURL;

    /*
     * Create an object
     * @param URL of the repo
     */
    public StatusUpdater(String url){
        baseURL = url + "/statuses/"; //Should be taken as parameter
    }

    /*
     * Method for updating the GitHub status of a specific commit
     * 
     * @param context  The context to be set for the commit status 
     * @param state The result of the test
     * @param description A short description of what is tested
     * @param commit_id  The commit_id for the commit status that should be updated
     */
    public int ChangeStatus(String context, String state, String description, String commit_id) {
        try {
            // Check so state matches predefined states for GitHub commits
            if(state != "success" && state != "failure" && state != "pending" && state != "error")
                throw new IllegalArgumentException("state has to be either success, failure, pending, or error");
            // If context or commit_id is null something has gone wrong
            if(context == null || commit_id == null)
                throw new IllegalArgumentException("Context and commit_id cannot be null");
            
            String url = baseURL + commit_id;
            
            // Build the data
            StringBuilder msgBuilder = new StringBuilder();
            msgBuilder.append("{\"state\":\"").append(state).append("\",\"description\":\"").append(description).append("\",\"context\":\"").append(context).append("\"}");    
            String msg = msgBuilder.toString();

            // Build the process
            ProcessBuilder processBuilder = new ProcessBuilder(
                    "curl",
                    "-X", "POST",
                    "-H", "Authorization: token " + System.getenv("GITHUB_TOKEN") ,
                    "-H", "Accept: application/vnd.github.v3+json",
                    "-d", msg,
                    url
            );

            Process process = processBuilder.start();
            int exitCode = process.waitFor();
            return exitCode;

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return 0; // We don't want the server to stop 
        }
    }
}