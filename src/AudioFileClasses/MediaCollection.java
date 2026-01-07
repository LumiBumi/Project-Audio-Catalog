package AudioFileClasses;

import java.util.ArrayList;
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
        items.add(item);
        duration += item.getDuration();
    }
}
