package AudioFileClasses;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public abstract class MediaCollection {
    protected String title;
    protected int duration;
    protected List<MediaFile> items = new ArrayList<>();

    public MediaCollection(String title) {
        this.title = title;
        this.duration = 0;
    }

    public void add(MediaFile item) {
        if (items.contains(item)) return;
        items.add(item);
        duration += item.getDuration();
    }

    public void sortByTitle() {
        items.sort(new Comparator<MediaFile>() {
            @Override
            public int compare(MediaFile item1, MediaFile item2) {
                return item1.getTitle().compareToIgnoreCase(item2.getTitle());
            }
        });
    }

    public String getTitle() {return title;}
    public int getDuration() {return duration;}
    public List<MediaFile> getItems() {return items;}
}
