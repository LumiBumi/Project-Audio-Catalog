package AudioFileClasses;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Playlist extends MediaCollection{

    public Playlist(String title) {
        super(title);
    }

    public boolean removeByID(int id) {
        MediaFile toRemove = null;

        for (MediaFile mediaFile : items) {
            if (mediaFile.getId() == id) {
                toRemove = mediaFile;
                break;
            }
        }

        if (toRemove != null) {
            duration -= toRemove.getDuration();
            items.remove(toRemove);
            return true;
        }
        return false;
    }

    public List<MediaFile> searchByTitleAndAuthor(String title, String author) {
        List<MediaFile> list = new ArrayList<>();
        String searchTitle = title.toLowerCase();
        String searchAuthor = author.toLowerCase();
        for (MediaFile item : items) {
            if(item.getTitle().toLowerCase().contains(searchTitle) ||
                    item.getAuthor().toLowerCase().contains(searchAuthor)) {
                list.add(item);
            }
        }
        return list;
    }

    @Override
    public String toString() {
        String timeString = MediaFile.formatDuration(duration);
        return title + " - " + timeString + " (" + items.size() + " items)";
    }

    public String toFile() {
        StringBuilder sb = new StringBuilder("PLAYLIST|" + title + "\n");
        for (MediaFile i : items) sb.append(i.getId()).append("\n");
        return sb.toString();
    }
}
