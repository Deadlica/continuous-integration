package CIserver.app;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Class that handles downloading and extracting GitHub repositories
 */
public class GitHubRepositoryDownloader {
    private final OkHttpClient client = new OkHttpClient();
    public GitHubRepositoryDownloader() throws IOException {
        return;
    }

    /**
     * This method downloads and extracts a GitHub repository
     * @param commit the commit SHA value of the commit to be downloaded
     * @param owner name of the GitHub repository owner
     * @param repo name of the GitHub repository
     * @throws IOException if the response code from the HTTP request fails
     */
    public String download(String commit, String owner, String repo) throws IOException {
        String url = String.format("https://api.github.com/repos/%s/%s/zipball/%s", owner, repo, commit);
        String token = "Bearer " + System.getenv("GITHUB_TOKEN");
        String accept = "application/vnd.github+json";

        // Build HTTP request
        Request request = new Request.Builder()
                .url(url)
                .header("Accept", accept)
                .header("Authorization", token)
                .build();

        // Execute the call
        try(Response response = client.newCall(request).execute()) {
            if(!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            assert response.body() != null;
            // GitHub by convention names the repo by the first 7 chars of the commit SHA
            assert commit.length() > 7;
            String fileName = owner + "-" + repo + "-" + commit.substring(0, 7);

            // Download the repository as a zip file
            try(InputStream inputStream = response.body().byteStream();
                OutputStream outputStream = new FileOutputStream(fileName + ".zip")) {
                byte[] buffer = new byte[8192];
                int bytesRead;
                while((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                inputStream.close();
                outputStream.close();
                // Extract the zip file
                extract(fileName);
                // Delete the zip file
                File zipFile = new File(fileName + ".zip");
                if(!zipFile.delete()) {
                    throw new RuntimeException("Failed to delete " + fileName + ".zip");
                }
                return fileName;
            }
        }
    }

    /**
     * This method extracts a zip file
     * @param directoryName the name of the directory to extract the zip file into
     * @throws IOException if the file input stream fails to read the zip file
     */

    private void extract(String directoryName) {
        try {
            // Create directory to extract files into
            File destDir = new File(directoryName);
            if(!destDir.exists()) {
                destDir.mkdirs();
            }

            FileInputStream fis = new FileInputStream(directoryName + ".zip");
            ZipInputStream zis = new ZipInputStream(new BufferedInputStream(fis));

            // Extract files
            ZipEntry entry;
            while((entry = zis.getNextEntry()) != null) {
                String entryName = entry.getName();
                File entryFile = new File(directoryName, entryName);

                // Create directories if necessary
                if(entry.isDirectory()) {
                    entryFile.mkdirs();
                }
                else {
                    // Extract file
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    FileOutputStream fos = new FileOutputStream(entryFile);
                    BufferedOutputStream bos = new BufferedOutputStream(fos, buffer.length);
                    while((bytesRead = zis.read(buffer, 0, buffer.length)) != -1) {
                        bos.write(buffer, 0, bytesRead);
                    }
                    bos.close();
                }
                zis.closeEntry();
            }

            // Close streams
            zis.close();
            fis.close();

        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }
}
