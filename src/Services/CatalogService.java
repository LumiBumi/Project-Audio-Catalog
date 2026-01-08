package Services;

import AudioFileClasses.AudioType;
import AudioFileClasses.Genre;
import AudioFileClasses.MediaFile;

import java.util.*;

public class CatalogService {
    private static List<MediaFile> catalog = new ArrayList<>();

    public static List<MediaFile> getCatalog() {return catalog;}

    public static void createAndAdd(String title, Genre genre, int duration,
                                    AudioType type, String author, int year, String album) {
        if(isDuplicate(title, author, album)){
            System.out.println("This media already exists in the catalog!");
            System.out.println("   (" + title + " by " + author + " in " +  album + ")");
            return;
        }
        int newId = getNextId();
        MediaFile newItem = new MediaFile(newId, title, genre, duration, type, author, year, album);
        catalog.add(newItem);
    }

    public static void add(MediaFile mediaFile) {catalog.add(mediaFile);}

    private static int getNextId() {
        int maxId = 0;
        for (MediaFile item : catalog) {
            if (item.getId() > maxId) {
                maxId = item.getId();
            }
        }
        return maxId + 1;
    }

    public static boolean remove(int id){
        return catalog.removeIf(i -> i.getId() == id);
    }

    public static MediaFile getById(int id){
        for (MediaFile item : catalog) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }

    private static boolean isDuplicate(String title, String author, String album) {
        if (album == null || album.trim().isEmpty() || album.equalsIgnoreCase("none")) {
            album = "single";
        } else {
            album = album.trim();
        }
        for (MediaFile item : catalog) {
            if (item == null) {
                continue;
            }
            if (item.getTitle() == null || item.getAuthor() == null) {
                continue;
            }
            boolean titleMatch = item.getTitle().equalsIgnoreCase(title);
            boolean authorMatch = item.getAuthor().equalsIgnoreCase(author);
            boolean albumMatch = item.getAlbum().equalsIgnoreCase(album);

            if (titleMatch && authorMatch && albumMatch) {
                return true;
            }
        }
        return false;
    }

    public static List<MediaFile> searchByTitle(String title) {
        List<MediaFile> list = new ArrayList<>();
        String searchTitle = title.toLowerCase();

        for (MediaFile item : catalog) {
            if (item.getTitle().toLowerCase().contains(searchTitle)) {
                list.add(item);
            }
        }
        return list;
    }

    public static List<MediaFile> searchByAuthor(String author) {
        List<MediaFile> list = new ArrayList<>();
        String searchAuthor = author.toLowerCase();

        for (MediaFile item : catalog) {
            if (item.getAuthor().toLowerCase().contains(searchAuthor)) {
                list.add(item);
            }
        }
        return list;
    }

    public static List<MediaFile> searchByGenre(String genre) {
        List<MediaFile> list = new ArrayList<>();
        String searchGenre = genre.toLowerCase();
        for (MediaFile item : catalog) {
            if (item.getGenre().toString().toLowerCase().contains(searchGenre)) {
                list.add(item);
            }
        }
        return list;
    }

    public static List<MediaFile> searchByYear(int year) {
        List<MediaFile> list = new ArrayList<>();
        for (MediaFile item : catalog) {
            if (item.getYear() == year) {
                list.add(item);
            }
        }
        return list;
    }

    public static List<MediaFile> searchByTitleAndAuthor(String title, String author) {
        List<MediaFile> list = new ArrayList<>();
        String searchTitle = title.toLowerCase();
        String searchAuthor = author.toLowerCase();
        for (MediaFile item : catalog) {
            if(item.getTitle().toLowerCase().contains(searchTitle) ||
                    item.getAuthor().toLowerCase().contains(searchAuthor)) {
                list.add(item);
            }
        }
        return list;
    }

    public static void sortByTitle() {
        catalog.sort(new Comparator<MediaFile>() {
            @Override
            public int compare(MediaFile item1, MediaFile item2) {
                return item1.getTitle().compareToIgnoreCase(item2.getTitle());
            }
        });
    }

    public static void sortByNewest() {
        catalog.sort(new Comparator<MediaFile>() {
            @Override
            public int compare(MediaFile item1, MediaFile item2) {
                return Integer.compare(item2.getId(), item1.getId());
            }
        });
    }

    public static void sortByOldest() {
        catalog.sort(new Comparator<MediaFile>() {
            @Override
            public int compare(MediaFile item1, MediaFile item2) {
                return Integer.compare(item1.getId(), item2.getId());
            }
        });
    }
}
