package AudioFileClasses;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Playlist {
    private String title;
    private int duration;
    private List<MediaFile> playlist = new ArrayList<>();

    public Playlist(String title) {
        this.title = title;
        this.duration = 0;
    }

    public String getTitle() {return title;}
    public int getDuration() {return duration;}
    public List<MediaFile> getMediaFiles() {return playlist;}

    public void addMediaFile(MediaFile mediaFile) {
        duration += mediaFile.getDuration();
        playlist.add(mediaFile);
    }

    public boolean removeByID(int id) {
        MediaFile toRemove = null;

        for (MediaFile mediaFile : playlist) {
            if (mediaFile.getId() == id) {
                toRemove = mediaFile;
                break;
            }
        }

        if (toRemove != null) {
            duration -= toRemove.getDuration();
            playlist.remove(toRemove);
            return true;
        }
        return false;
    }

    public void sortByTitle() {
        playlist.sort(new Comparator<MediaFile>() {
            @Override
            public int compare(MediaFile item1, MediaFile item2) {
                return item1.getTitle().compareToIgnoreCase(item2.getTitle());
            }
        });
    }

    @Override
    public String toString() {
        return title + " - " + duration + "s" + " (" + playlist.size() + " items)";
    }

    public String toFile() {
        StringBuilder sb = new StringBuilder("PLAYLIST|" + title + "\n");
        for (MediaFile i : playlist) sb.append(i.getId()).append("\n");
        return sb.toString();
    }
}
