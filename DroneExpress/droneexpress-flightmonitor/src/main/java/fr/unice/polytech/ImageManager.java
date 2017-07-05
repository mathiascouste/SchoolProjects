package fr.unice.polytech;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.imageio.ImageIO;


public final class ImageManager {

    private static Map<String, ImageManager> staticMap = new HashMap<String, ImageManager>();
    private Map<String, String> pathingMap;
    private Map<String, Image> imageMap;

    private ImageManager() {
        this.pathingMap = new HashMap<String, String>();
        this.imageMap = new HashMap<String, Image>();
    }

    public static ImageManager create(String name) {
        ImageManager iL = new ImageManager();
        staticMap.put(name, iL);
        return iL;
    }

    public static ImageManager get(String name) {
        return staticMap.get(name);
    }

    public static void delete(String name) {
        staticMap.remove(name);
    }

    public void addPath(String name, String path) {
        this.pathingMap.put(name, path);
    }

    public void loadImages() throws IOException {
        for (Entry<String, String> entries : this.pathingMap.entrySet()) {
            this.imageMap.put(entries.getKey(), ImageIO.read(new File(entries.getValue())));
        }
    }

    public Image getImage(String imgName) {
        return this.imageMap.get(imgName);
    }
}