package CIserver.app;

import org.apache.commons.io.FileUtils;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * BuildThread class that allows building of git repo to run after webhook receives response
 */
public class BuildThread extends Thread {
    private final Runnable compilation;

    /**
     * Creates a runnable object that parses the requestBody,
     * downloads the repo from the parsed commit SHA.
     * After downloading it compiles the project and run unit tests.
     * @param requestBody JSON data with information of the git commit,
     *                    repository name, repository owner
     */
    public BuildThread(String requestBody) {
        this.compilation = () -> {
            // Parsing JSON data
            JSONObject jsonObject = new JSONObject(requestBody);
            JSONObject repository = jsonObject.getJSONObject("repository");
            String commit = jsonObject.getString("after");
            String repositoryName = repository.getString("name");
            String owner = repository.getJSONObject("owner").getString("name");
            String repo_url = String.format("https://api.github.com/repos/%s/%s", owner, repositoryName);

            //Set all statuses to pending
            StatusUpdater statusChanger = new StatusUpdater(repo_url);
            statusChanger.ChangeStatus("Download", "pending", "", commit);
            statusChanger.ChangeStatus("Compilation", "pending", "", commit);
            statusChanger.ChangeStatus("Tests", "pending", "", commit);


            // Download GitHub Repository
            GitHubRepositoryDownloader gitHubDownloader = null;
            String repo;
            try {
                gitHubDownloader = new GitHubRepositoryDownloader();
                repo = gitHubDownloader.download(commit, owner, repositoryName);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if(repo.isEmpty()) {
                statusChanger.ChangeStatus("Download", "failure", "Failed to download repository", commit); //Change commit status to signal that download failed
                throw new RuntimeException("Failed to download repository " + repositoryName);
            } else {
                statusChanger.ChangeStatus("Download", "success", "Repository downloaded", commit); //Change commit status to signal that download is complete
            }

            // Compiles maven project
            MavenCompiler mavenCompiler = new MavenCompiler();
            String output = mavenCompiler.compile(repo);
            if(output.isEmpty()) {
                throw new RuntimeException("Failed to compile repository " + repositoryName);
            }

            // Update commit status according to output
            updateCommitStatus(output, commit, statusChanger);

            Date date = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                SQLhandler sql = new SQLhandler();
                sql.insertEntry(commit, dateFormat.format(date), output);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }


            // Deletes the downloaded repo (to save storage)
            String repoPath = System.getProperty("user.dir") + File.separator + repo;
            try {
                FileUtils.deleteDirectory(new File(repoPath));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            File file = new File(repoPath);
            if(file.exists()) {
                output = "Failed to delete repository from server: " + repo;
            }};
    }


    // Run the thread
    @Override
    public void run() {
                compilation.run();
    }

    /*
    * Method for parsing the output log of a compilation to see if there was a compilation
    * error or if any test failed
    * 
    * @param output  The string output of the compilation to be parsed
    */
    public static void updateCommitStatus(String output, String commit_id, StatusUpdater statusChanger) {
        String[] lines = output.split("\n");
        for (String line : lines) {
            if (line.startsWith("[ERROR]")) {
                // Compilation error handeled here
                if(line.contains("COMPILATION ERROR")){
                    statusChanger.ChangeStatus("Compilation", "failure", "There were compilation errors", commit_id);
                    return;
                }
                // Test failed handeled here
                else{
                    statusChanger.ChangeStatus("Test", "failure", "At least one test failed", commit_id);
                    return;
                }
            }
        }
        statusChanger.ChangeStatus("Compilation", "success", "Compiled without errors", commit_id);
        statusChanger.ChangeStatus("Tests", "success", "All tests passed", commit_id);
    }
    
}