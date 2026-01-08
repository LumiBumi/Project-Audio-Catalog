import AudioFileClasses.*;
import Services.*;
import Storages.*;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static final String CATALOG_FILE = "catalog.txt";
    private static final String PLAYLIST_FILE = "playlists.txt";

    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            List<MediaFile> loadedData = CatalogStorage.load(CATALOG_FILE);

            for (MediaFile item : loadedData) {
                CatalogService.add(item);
            }
            System.out.println("Catalog loaded: " + CatalogService.getCatalog().size() + " items.");
        } catch (IOException e) {
            System.out.println("Could not load catalog: " + e.getMessage());
        }

        try {
            List<Playlist> loadedPlaylists = PlaylistStorage.load(PLAYLIST_FILE);
            PlaylistService.getPlaylist().addAll(loadedPlaylists);
            System.out.println("Playlists loaded: " + loadedPlaylists.size());
        } catch (IOException e) {
            System.out.println("Could not load playlists: " + e.getMessage());
        }

        AlbumService.createAlbums();


        //Главното меню

        while (true) {
            System.out.println("\n___ MY AUDIO CATALOG ___");
            System.out.println("1. Show All Media");
            System.out.println("2. Add New Media");
            System.out.println("3. Remove Media");
            System.out.println("4. Search Media");
            System.out.println("5. Playlists Menu");
            System.out.println("6. Show Albums");
            System.out.println("7. Sort Catalog");
            System.out.println("0. Save & Exit");
            System.out.print("Choose option: ");

            String choice = sc.nextLine();

            switch (choice) {
                case "1" -> showCatalog();
                case "2" -> addNewItem();
                case "3" -> removeMedia();
                case "4" -> searchMenu();
                case "5" -> playlistMenu();
                case "6" -> showAlbums();
                case "7" -> sortCatalog();
                case "0" -> {
                    saveAndExit();
                    return;
                }
                default -> System.out.println("Invalid option! Try again");
            }
        }
    }

    //Методите за менюто

    private static void showCatalog() {
        System.out.println("\n    Current Catalog");
        List<MediaFile> list = CatalogService.getCatalog();
        if (list.isEmpty()) {
            System.out.println("Catalog is empty");
        } else {
            for (MediaFile item : list) {
                System.out.println(item);
            }
        }
    }

    private static void sortCatalog(){
        System.out.println("\n    Sorting Option");
        System.out.println("1. Sort By Title");
        System.out.println("2. Sort By Newest");
        System.out.println("3. Sort By Oldest");
        System.out.println("0. Exit");
        System.out.print("Choose option: ");
        String choice = sc.nextLine();

        if(choice.equals("0")){return;}

        switch (choice){
            case "1" -> {
                CatalogService.sortByTitle();
            }
            case "2" -> {
                CatalogService.sortByNewest();
            }
            case "3" -> {
                CatalogService.sortByOldest();
            }
            default ->  System.out.println("Invalid choice! Try again");
        }
        System.out.println("Catalog sorted");
        showCatalog();
    }

    private static void addNewItem() {
        System.out.println("\n    Add New Media");
        String title;
        String author;

        System.out.println("Type (SONG, AUDIOBOOK, PODCAST): ");
        String typeStr = sc.nextLine().toUpperCase();
        AudioType type;
        try {
            type = AudioType.valueOf(typeStr);
        } catch (IllegalArgumentException e) {
            type = AudioType.SONG;
            System.out.println("Invalid type. Set to SONG by default");
        }

        while(true){
            System.out.print("Title: ");
            title = sc.nextLine();
            if(title.trim().isEmpty()){
                System.out.println("Invalid title! Try again");
                continue;
            }
            break;
        }

        while(true){
            System.out.print("Author: ");
            author = sc.nextLine();
            if(author.trim().isEmpty()){
                System.out.println("Invalid author! Try again");
                continue;
            }
            break;
        }

        System.out.print("Album (type 'none' if single): ");
        String album = sc.nextLine();

        List<Genre> allowedGenres = new ArrayList<>();

        switch (type) {
            case SONG -> {
                allowedGenres.add(Genre.ROCK);
                allowedGenres.add(Genre.POP);
                allowedGenres.add(Genre.JAZZ);
                allowedGenres.add(Genre.CLASSICAL);
                allowedGenres.add(Genre.METAL);
                allowedGenres.add(Genre.HIP_HOP);
                allowedGenres.add(Genre.ELECTRONIC);
                allowedGenres.add(Genre.REGGAE);
                allowedGenres.add(Genre.BLUES);
                allowedGenres.add(Genre.COUNTRY);
                allowedGenres.add(Genre.RAP);
                allowedGenres.add(Genre.OTHER);
                System.out.println("Available Music Genres: Rock, Pop, Jazz, Metal, Hip_Hop, etc.");
            }

            case AUDIOBOOK, PODCAST -> {
                allowedGenres.add(Genre.FANTASY);
                allowedGenres.add(Genre.ROMANCE);
                allowedGenres.add(Genre.SCIFI);
                allowedGenres.add(Genre.HISTORY);
                allowedGenres.add(Genre.BIOGRAPHY);
                allowedGenres.add(Genre.THRILLER);
                allowedGenres.add(Genre.EDUCATION);
                allowedGenres.add(Genre.TECHNOLOGY);
                allowedGenres.add(Genre.OTHER);
                System.out.println("Available Categories: Fantasy, History, SciFi, Education, etc.");
            }
        }

        Genre genre = Genre.OTHER;

        while (true) {
            System.out.print("Genre: ");
            String input = sc.nextLine().toUpperCase().trim();

            try {
                Genre selectedGenre = Genre.valueOf(input);

                if (allowedGenres.contains(selectedGenre)) {
                    genre = selectedGenre;
                    break;
                } else {
                    System.out.println("Logic Error: You cannot select '" + selectedGenre + "' for a " + type);
                    System.out.println("Please select a valid genre from the list above.");
                }

            } catch (IllegalArgumentException e) {
                System.out.println("Invalid genre! Please check spelling.");
            }
        }

        int year = 0;
        while (true) {
            System.out.print("Year (1900 - 2026): ");
            String input = sc.nextLine();

            try {
                year = Integer.parseInt(input);

                if (year >= 1900 && year <= 2026) {
                    break;
                } else {
                    System.out.println("Invalid year! Please enter a year between 1900 and 2026");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number");
            }
        }

        int duration = 0;
        while (true) {
            System.out.print("Duration (in seconds): ");
            String input = sc.nextLine();

            try {
                duration = Integer.parseInt(input);

                if (duration > 30 && duration <= 86400) {
                    break;
                } else {
                    System.out.println("Invalid duration! Must be between 30 second and 86400 seconds (24h)");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number");
            }
        }

        CatalogService.createAndAdd(title, genre, duration, type, author, year, album);

        AlbumService.createAlbums();
    }

    private static void removeMedia() {
        System.out.println("\n    Remove Media");

        System.out.print("Enter Title or Author to remove: ");
        String input = sc.nextLine();

        List<MediaFile> matches = CatalogService.searchByTitleAndAuthor(input, input);
        int idToRemove = -1;

        if (matches.isEmpty()) {
            System.out.println("No media found matching '" + input + "'");
            return;
        }
        else if (matches.size() == 1) {
            MediaFile item = matches.getFirst();
            System.out.println("Found: " + item);
            System.out.print("Are you sure you want to delete it? (Yes/No): ");
            if (sc.nextLine().equalsIgnoreCase("yes")) {
                idToRemove = item.getId();
            } else {
                System.out.println("Operation cancelled");
                return;
            }
        }
        else {
            System.out.println("Multiple matches found:");
            for (MediaFile m : matches) System.out.println(m);

            System.out.print("Enter ID to delete: ");
            try {
                int inputId = Integer.parseInt(sc.nextLine());
                for(MediaFile m : matches) {
                    if(m.getId() == inputId) {
                        idToRemove = inputId;
                        break;
                    }
                }
                if (idToRemove == -1) {
                    System.out.println("ID not found in the search results");
                    return;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid ID");
                return;
            }
        }

        if (idToRemove != -1) {
            boolean removed = CatalogService.remove(idToRemove);
            if (removed) {
                System.out.println("Item removed from Catalog");

                for (Playlist p : PlaylistService.getPlaylist()) {
                    p.removeByID(idToRemove);
                }

                AlbumService.createAlbums();
            } else {
                System.out.println("Error: Could not remove item");
            }
        }
    }

    private static void showAlbums() {
        System.out.println("\n    Albums Collection");
        List<Album> albums = AlbumService.getAlbums();

        if (albums.isEmpty()) {
            System.out.println("No albums found");
        } else {
            for (Album a : albums) {
                System.out.println(a);
            }
        }
    }

    private static void searchMenu() {
        System.out.println("\n    Search & Filter");
        System.out.println("1. Smart Search (Title/Author)");
        System.out.println("2. Filter by Genre");
        System.out.println("3. Filter by Year");
        System.out.print("Select: ");
        String ch = sc.nextLine();

        List<MediaFile> results = new ArrayList<>();

        switch (ch) {
            case "1" -> {
                System.out.print("Search: ");
                String query = sc.nextLine();
                results = CatalogService.searchByTitleAndAuthor(query, query);
            }
            case "2" -> {
                System.out.print("Enter Genre: ");
                String genre = sc.nextLine();
                results = CatalogService.searchByGenre(genre);
            }
            case "3" -> {
                System.out.print("Enter Year: ");
                try {
                    int year = Integer.parseInt(sc.nextLine());
                    results = CatalogService.searchByYear(year);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid year");
                }
            }
            default -> System.out.println("Invalid option");
        }

        if (results != null && !results.isEmpty()) {
            System.out.println("Found " + results.size() + " items:");
            for (MediaFile m : results) System.out.println(m);
        } else {
            System.out.println("No results found");
        }
    }

    private static void playlistMenu() {
        while (true) {
            System.out.println("\n    Playlists");
            System.out.println("1. Create New Playlist");
            System.out.println("2. Show All Playlists");
            System.out.println("3. Open/Manage Playlist");
            System.out.println("0. Back");
            System.out.print("Choice: ");
            String ch = sc.nextLine();

            if (ch.equals("0")) break;

            switch (ch) {
                case "1" -> {
                    System.out.print("Playlist Name: ");
                    String name = sc.nextLine();
                    if (PlaylistService.addPlaylist(new Playlist(name))) {
                        System.out.println("Playlist created");
                    } else {
                        System.out.println("Playlist with this name already exists");
                    }
                }
                case "2" -> {
                    for (Playlist p : PlaylistService.getPlaylist()) {
                        System.out.println(p);
                    }
                }
                case "3" -> managePlaylist();
            }
        }
    }

    private static void managePlaylist() {
        System.out.print("Enter Playlist Name to open: ");
        String name = sc.nextLine();
        Playlist p = PlaylistService.searchByTitle(name);

        if (p == null) {
            System.out.println("Playlist not found");
            return;
        }

        boolean playlistExists = true;
        while (playlistExists) {
            System.out.println("\n    Managing: " + p.getTitle());
            System.out.println("1. Show media");
            System.out.println("2. Add media (Smart Search)");
            System.out.println("3. Remove media from Playlist");
            System.out.println("4. Sort by Title");
            System.out.println("5. Search inside Playlist");
            System.out.println("6. Remove Playlist");
            System.out.println("0. Back");
            System.out.print("Choice: ");
            String ch = sc.nextLine();

            if (ch.equals("0")) break;

            switch (ch) {
                case "1" -> {
                    if (p.getItems().isEmpty()) System.out.println("Playlist is empty");
                    else for (MediaFile m : p.getItems()) System.out.println(m);
                }
                case "2" -> {
                    System.out.print("Enter Song Title or Author: ");
                    String input = sc.nextLine();

                    List<MediaFile> matches = CatalogService.searchByTitleAndAuthor(input, input);

                    if (matches.isEmpty()) {
                        System.out.println("No matches found for '" + input + "'");
                    }
                    else if (matches.size() == 1) {
                        MediaFile item = matches.getFirst();
                        System.out.println(item);
                        System.out.println("Is this the media that you wanted to add? (Yes/No):");
                        if(sc.nextLine().equalsIgnoreCase("yes")){
                            p.add(item);
                        }
                        else{
                            System.out.println("Operation cancelled");
                            return;
                        }

                        System.out.println("Found exact match! Added: " + item.getTitle());
                    }
                    else {
                        System.out.println("Multiple matches found:");
                        for (MediaFile m : matches) {
                            System.out.println(m);
                        }

                        System.out.print("Enter the ID of the media you want to add from the list above: ");
                        try {
                            int id = Integer.parseInt(sc.nextLine());

                            MediaFile selected = null;
                            for(MediaFile m : matches) {
                                if(m.getId() == id) {
                                    selected = m;
                                    break;
                                }
                            }

                            if(selected != null) {
                                p.add(selected);
                                System.out.println("Added: " + selected.getTitle());
                            } else {
                                System.out.println("ID not found in the search results");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input format");
                        }
                    }
                }
                case "3" -> {
                    System.out.print("Enter Media Title or Author to remove: ");
                    String input = sc.nextLine();

                    List<MediaFile> matches = p.searchByTitleAndAuthor(input, input);

                    if (matches.isEmpty()) {
                            System.out.println("No matches found");
                    }
                    else if (matches.size() == 1) {
                        MediaFile item = matches.getFirst();
                        System.out.println(item);
                        System.out.println("Is this the  media that you wanted to remove? (Yes/No):");
                        if(sc.nextLine().equalsIgnoreCase("yes")){
                            if (p.removeByID(item.getId())) {
                                System.out.println("Removed: " + item.getTitle());
                            }
                        }
                        else {
                            System.out.println("Operation cancelled");
                            return;
                        }
                    }
                    else {
                        System.out.println("Multiple matches found in playlist:");
                        for (MediaFile m : matches) System.out.println(m);

                        System.out.print("Enter ID from list above to remove: ");
                        try {
                            int id = Integer.parseInt(sc.nextLine());
                            if (p.removeByID(id)) System.out.println("Removed");
                            else System.out.println("ID not found");
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid ID format");
                        }
                    }
                }
                case "4" -> {
                    p.sortByTitle();
                    System.out.println("Playlist sorted");
                }
                case "5" -> {
                    System.out.print("Search (Title/Author): ");
                    String query = sc.nextLine();
                    List<MediaFile> results = p.searchByTitleAndAuthor(query, query);

                    if (results.isEmpty()) {
                        System.out.println("No matches in this playlist");
                    } else {
                        System.out.println("Found " + results.size() + " songs:");
                        for (MediaFile m : results) System.out.println(m);
                    }
                }
                case "6" -> {
                    System.out.println("Are you sure? (Yes/No):");
                    if(sc.nextLine().equalsIgnoreCase("yes")){
                        playlistExists = !PlaylistService.removePlaylist(p);
                        if(!playlistExists) {
                            System.out.println("Playlist removed");
                        }
                        else{
                            System.out.println("Playlist couldn't be removed");
                        }
                    }
                    else{
                        System.out.println("Operation cancelled");
                        return;
                    }
                }
                default -> System.out.println("Invalid option");
            }
        }
    }

    private static void saveAndExit() {
        System.out.println("Saving data");
        try {
            CatalogStorage.save(CatalogService.getCatalog(), CATALOG_FILE);
            PlaylistStorage.save(PlaylistService.getPlaylist(), PLAYLIST_FILE);
            System.out.println("Data saved");
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }
}