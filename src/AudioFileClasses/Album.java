package AudioFileClasses;

import java.util.ArrayList;
import java.util.List;

public class Album {
    private String name;
    private String artist;
    private int duration;
    private List<MediaFile> album = new ArrayList<>();

    public Album(String name, String artist) {
        this.name = name;
        this.artist = artist;
    }

    public void addMediaFile(MediaFile mediaFile) {

        album.add(mediaFile);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Album: " + name + " (" + artist + ")\n");
        for (MediaFile s : album) sb.append("  ").append(s).append("\n");
        return sb.toString();
    }
}
