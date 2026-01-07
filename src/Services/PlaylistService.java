package Services;

import AudioFileClasses.Playlist;

import java.util.ArrayList;
import java.util.List;

public class PlaylistService {
    private List<Playlist> playlists = new ArrayList<>();

    public List<Playlist> getPlaylist(){return playlists;}

    public boolean addPlaylist(Playlist playlist) {
        if(searchByTitle(playlist.getTitle())!=null){
            return false;
        }
        playlists.add(playlist);
        return true;
    }

    public boolean removePlaylist(Playlist playlist) {
        return playlists.remove(playlist);
    }

    public Playlist searchByTitle(String title) {
        String searchTitle = title.toLowerCase();

        for (Playlist item : playlists) {
            if (item.getTitle().toLowerCase().contains(searchTitle)) {
                return item;
            }
        }
        return null;
    }
}
