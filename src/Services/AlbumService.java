package Services;

import AudioFileClasses.Album;
import AudioFileClasses.MediaFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlbumService {
    private List<Album> albums = new ArrayList<Album>();

    public List<Album> getAlbums() { return albums; }

    public void createAlbums() {
        albums.clear();

        List<MediaFile> catalog = CatalogService.getCatalog();

        Map<String, Album> mapOfAlbums = new HashMap<>();

        for (MediaFile song : catalog) {
            String albumName = song.getAlbum();

            if (albumName == null) continue;

            String cleanName = albumName.trim();

            if (cleanName.equals("single")) {
                continue;
            }

            if (!mapOfAlbums.containsKey(cleanName)) {
                Album newAlbum = new Album(cleanName, song.getAuthor());
                mapOfAlbums.put(cleanName, newAlbum);
                albums.add(newAlbum);
            }

            mapOfAlbums.get(cleanName).add(song);
        }
    }
}
