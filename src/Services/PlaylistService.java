package Services;

import AudioFileClasses.MediaFile;
import AudioFileClasses.Playlist;

import java.util.ArrayList;
import java.util.List;

public class PlaylistService {
    private static List<Playlist> playlists = new ArrayList<>();

    public static List<Playlist> getPlaylist(){return playlists;}

    public static boolean addPlaylist(Playlist playlist) {
        if(searchByTitle(playlist.getTitle())!=null){
            return false;
        }
        playlists.add(playlist);
        return true;
    }

    public static boolean removePlaylist(Playlist playlist) {
        return playlists.remove(playlist);
    }

    public static Playlist searchByTitle(String title) {

        for (Playlist item : playlists) {
            if (item.getTitle().equalsIgnoreCase(title)) {
                return item;
            }
        }
        return null;
    }
}
