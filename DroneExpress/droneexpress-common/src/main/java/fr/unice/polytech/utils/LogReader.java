package fr.unice.polytech.utils;

import java.io.*;


public class LogReader {

    private static final String pathLog = System.getProperty("user.home") + "/DroneExpress.log";
    private String path;

    public LogReader() {
        path = pathLog;
    }

    public LogReader(String path) {
        this.path = path;
    }

    public String read() throws IOException {
        StringBuilder builder = new StringBuilder();
        String line;
        BufferedReader reader;

        try {
            reader = new BufferedReader(new FileReader(path));
        } catch (FileNotFoundException e) {
            return null;
        }

        while ((line = reader.readLine()) != null) {
            builder.append(line);
            builder.append(System.getProperty("line.separator"));
        }

        reader.close();

        return builder.toString();
    }

    public void clean() throws IOException {
        File log = new File(path);

        if (log.exists()) {
            new FileWriter(log, false);
        }
    }

}
