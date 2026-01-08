package Storages;

import AudioFileClasses.Playlist;
import AudioFileClasses.MediaFile;
import Services.CatalogService;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PlaylistStorage {
    public static void save(List<Playlist> playlists, String file) throws IOException {
        try (PrintWriter pw = new PrintWriter(new FileWriter(file))) {
            for (Playlist p : playlists) {
                pw.print(p.toFile());
            }
        }
    }

    public static List<Playlist> load(String file) throws IOException {
        List<Playlist> list = new ArrayList<>();
        File f = new File(file);
        if (!f.exists()) {
            f.createNewFile();
            return list;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            Playlist current = null;

            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                if (line.startsWith("PLAYLIST")) {
                    //PLAYLIST|title
                    String[] parts = line.split("\\|");
                    if (parts.length > 1) {
                        current = new Playlist(parts[1]);
                        list.add(current);
                    }
                } else {
                    //ID
                    try {
                        int id = Integer.parseInt(line);
                        MediaFile item = CatalogService.getById(id);
                        if (item != null && current != null) {
                            current.add(item);
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Skipped ID (incorrect):" + line);
                    }
                }
            }
        }
        return list;
    }
}
