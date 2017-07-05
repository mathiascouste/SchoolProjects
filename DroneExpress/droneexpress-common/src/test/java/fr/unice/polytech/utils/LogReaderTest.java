package fr.unice.polytech.utils;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class LogReaderTest {

    private LogReader reader;
    private FileWriter file;
    private static final String path = "./test.temp";

    @Before
    public void setUp() throws Exception {
        reader = new LogReader(path);
        file = new FileWriter(path);
        file.write("hello");
        file.flush();
        file.close();
    }

    @After
    public void tearDown() throws Exception {
        reader = null;
        File f = new File(path);
        if (f.exists()) {
            f.delete();
        }
    }

    @Test
    public void teestRead() throws IOException {
        System.out.println(reader.read());
        assertTrue(reader.read().contains("hello"));
    }

    @Test
    public void testClean() throws IOException {
        reader.clean();
        assertTrue(reader.read().equals(""));
    }

}
