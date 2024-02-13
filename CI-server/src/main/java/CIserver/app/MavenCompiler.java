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
            String mvn;
            // Windows
            if(System.getProperty("os.name").toLowerCase().contains("win")) {
                mvn = "mvn.cmd";
            }
            // Linux / docker server
            else {
                mvn = "mvn";
            }

            String projectDirectory = findPomDirectory(project);

            Process process = Runtime.getRuntime().exec(new String[] {mvn, "-B", "-f", projectDirectory, "compile"});

            StringBuilder stringBuilder = new StringBuilder();
            InputStream inputStream = process.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            // Adding compile output
            while((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }

            process = Runtime.getRuntime().exec(new String[] {mvn, "-B", "-f", findPomDirectory(project), "test"});
            inputStream = process.getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            // Adding test output
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

    /**
     * This method finds the root directory of the Maven project
     * @param project name of the root directory of the project
     * @throws RuntimeException if the root directory of the Maven project is not found
     * @return the root directory of the Maven project
     */
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

    /**
     * This class searches for a file in a directory
     */
    private class FileSearcher extends SimpleFileVisitor<Path> {
        private Path pomDirectory;
        private String goal;
        FileSearcher(String goal) {
            this.goal = goal;
        }

        /**
         * This method visits a file
         * @param file the file to visit
         * @param attrs the file attributes
         * @return the result of the file visit
         */
        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            if (file.getFileName().toString().equals(goal)) {
                pomDirectory = file.getParent();
                return FileVisitResult.TERMINATE;
            }
            return FileVisitResult.CONTINUE;
        }

        /**
         * This method visits a file that failed to be visited
         * @param file the file that failed to be visited
         * @param exc the exception that caused the file visit to fail
         * @return the result of the file visit
         */
        @Override
        public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
            return FileVisitResult.CONTINUE;
        }


        /**
         * This method returns the root directory of the Maven project
         * @return the root directory of the Maven project
         */
        public Path getPomDirectory() {
            return pomDirectory;
        }
    }
}
