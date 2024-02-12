package CIserver.app;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * Class that compiles Java projects with Maven and reports if the compilation
 * and tests were successful.
 */
public class MavenCompiler {
    /**
     * This method compiles a Maven project
     * @param project name of the root directory of the project
     * @return boolean value to indicate if the compilation was successful
     */
    public String compile(String project) {
        String output = "";
        try {
            ProcessBuilder processBuilder;
            // Windows
            if(System.getProperty("os.name").toLowerCase().contains("win")) {
                processBuilder = new ProcessBuilder("cmd", "/c", "mvn", "package");
            }
            // Linux / docker server
            else {
                processBuilder = new ProcessBuilder("sudo", "mvn", "package");
            }

            File projectDirectory = new File(findPomDirectory(project));

            processBuilder.directory(projectDirectory);
            Process process = processBuilder.start();

            StringBuilder stringBuilder = new StringBuilder();
            InputStream inputStream = process.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }

            int exitCode = process.waitFor();
            if(exitCode != 0) {
                output += "Compilation process exited with code " + exitCode;
                output += "\n" + stringBuilder.toString();
            }
            else {
                output = stringBuilder.toString();
            }
        }
        catch(IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return output;
    }

    private String findPomDirectory(String project) {
        Path searchRoot = Paths.get(System.getProperty("user.dir") + File.separator + project);
        FileSearcher fileSearcher = new FileSearcher("pom.xml");
        try {
            Files.walkFileTree(searchRoot, fileSearcher);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return fileSearcher.getPomDirectory().toString();
    }

    private class FileSearcher extends SimpleFileVisitor<Path> {
        private Path pomDirectory;
        private String goal;
        FileSearcher(String goal) {
            this.goal = goal;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            if (file.getFileName().toString().equals(goal)) {
                pomDirectory = file.getParent();
                return FileVisitResult.TERMINATE;
            }
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
            return FileVisitResult.CONTINUE;
        }

        public Path getPomDirectory() {
            return pomDirectory;
        }
    }
}
