package CIserver.app;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This class contains tests for the {@link Build} class, ensuring that the getters and setters for
 * commit ID, build date, and build log messages work as expected.
 */
public class BuildTests {

    private Build build;

    /**
     * Tests the retrieval of the commit ID from a {@link Build} instance.
     */
    @Test
    public void testGetCommit() {
        build = new Build("abc123", "2023-02-15", "Build successful");
        String expected = "abc123 1";
        String actual = build.getCommit();
        assertEquals(expected, actual);
    }

    /**
     * Tests the setting of a new commit ID in a {@link Build} instance.
     */
    @Test
    public void testSetCommit(){
        build = new Build("abc123", "2023-02-15", "Build successful");
        String expected = "fff111";
        build.setCommit(expected);
        String actual = build.getCommit();
        assertEquals(expected, actual);
    }

    /**
     * Tests the retrieval of the build date from a {@link Build} instance.
     */
    @Test
    public void testGetDate() {
        build = new Build("abc123", "2023-02-15", "Build successful");
        String expected = "2023-02-15";
        String actual = build.getDate();
        assertEquals(expected, actual);
    }

    /**
     * Tests the setting of a new build date in a {@link Build} instance.
     */
    @Test
    public void testSetDate(){
        build = new Build("abc123", "2023-02-15", "Build successful");
        String expected = "2021-02-15";
        build.setDate(expected);
        String actual = build.getDate();
        assertEquals(expected, actual);
    }

    /**
     * Tests the retrieval of the build log message from a {@link Build} instance.
     */
    @Test
    public void testGetLog() {
        build = new Build("abc123", "2023-02-15", "Build successful");
        String expected = "Build successful";
        String actual = build.getLog();
        assertEquals(expected, actual);
    }

    /**
     * Tests the setting of a new build log message in a {@link Build} instance.
     */
    @Test
    public void testSetLog(){
        build = new Build("abc123", "2023-02-15", "Build successful");
        String expected = "Build Failed";
        build.setLog(expected);
        String actual = build.getLog();
        assertEquals(expected, actual);
    }
}