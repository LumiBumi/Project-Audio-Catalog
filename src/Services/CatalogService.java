package Services;

import AudioFileClasses.MediaFile;

import java.util.*;

public class CatalogService {
    private List<MediaFile> catalog = new ArrayList<>();

    public List<MediaFile> getCatalog() { return catalog; }

    public void add(MediaFile item) { catalog.add(item); }

    public int getNextId() {
        int maxId = 0;
        for (MediaFile item : catalog) {
            if (item.getId() > maxId) {
                maxId = item.getId();
            }
        }
        return maxId + 1;
    }

    public boolean remove(int id){
        return catalog.removeIf(i -> i.getId() == id);
    }

    public MediaFile getById(int id){
        for (MediaFile item : catalog) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }

    public List<MediaFile> searchByTitle(String title) {
        List<MediaFile> list = new ArrayList<>();
        String searchTitle = title.toLowerCase();

        for (MediaFile item : catalog) {
            if (item.getTitle().toLowerCase().contains(searchTitle)) {
                list.add(item);
            }
        }
        return list;
    }

    public List<MediaFile> searchByAuthor(String author) {
        List<MediaFile> list = new ArrayList<>();
        String searchAuthor = author.toLowerCase();

        for (MediaFile item : catalog) {
            if (item.getAuthor().toLowerCase().contains(searchAuthor)) {
                list.add(item);
            }
        }
        return list;
    }

    public List<MediaFile> searchByGenre(String genre) {
        List<MediaFile> list = new ArrayList<>();
        String searchGenre = genre.toLowerCase();
        for (MediaFile item : catalog) {
            if (item.getGenre().toLowerCase().contains(searchGenre)) {
                list.add(item);
            }
        }
        return list;
    }

    public List<MediaFile> searchByYear(int year) {
        List<MediaFile> list = new ArrayList<>();
        for (MediaFile item : catalog) {
            if (item.getYear() == year) {
                list.add(item);
            }
        }
        return list;
    }

    public List<MediaFile> searchByTitleAndAuthor(String title, String author) {
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

    public void sortByTitle() {
        catalog.sort(new Comparator<MediaFile>() {
            @Override
            public int compare(MediaFile item1, MediaFile item2) {
                return item1.getTitle().compareToIgnoreCase(item2.getTitle());
            }
        });
    }
}
