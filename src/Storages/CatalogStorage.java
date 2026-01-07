package Storages;

import AudioFileClasses.MediaFile;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CatalogStorage {

    public static void save(List<MediaFile> catalog, String fileName) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            for (MediaFile item : catalog) {
                writer.println(item.toFile());
            }
        }
    }

    public static List<MediaFile> load(String fileName) throws IOException {
        List<MediaFile> list = new ArrayList<>();
        File file = new File(fileName);

        if (!file.exists()) {
            file.createNewFile();
            return list;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = reader.readLine()) != null) {

                if (!line.trim().isEmpty()) {
                    try {
                        MediaFile item = MediaFile.fromFile(line);
                        list.add(item);
                    } catch (Exception e) {
                        System.out.println("Skipped line (corrupted):" + line);
                    }
                }
            }
        }

        return list;
    }
}
