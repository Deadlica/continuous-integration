package CIserver.app;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class GitHubRepositoryDownloader {
    private final OkHttpClient client = new OkHttpClient();
    private final String zipName = "repository.zip";
    public GitHubRepositoryDownloader() throws IOException {
        return;
    }

    public void download(String commit, String owner, String repo) throws IOException, NullPointerException {
        String url = String.format("https://api.github.com/repos/%s/%s/zipball/%s", owner, repo, commit);
        String token = "Bearer " + System.getenv("GITHUB_TOKEN");
        String accept = "application/vnd.github+json";
        Request request = new Request.Builder()
                .url(url)
                .header("Accept", accept)
                .header("Authorization", token)
                .build();

        try(Response response = client.newCall(request).execute()) {
            if(!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            assert response.body() != null;
            try(InputStream inputStream = response.body().byteStream();
                OutputStream outputStream = new FileOutputStream(zipName)) {
                byte[] buffer = new byte[8192];
                int bytesRead;
                while((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            }
        }
    }
}
