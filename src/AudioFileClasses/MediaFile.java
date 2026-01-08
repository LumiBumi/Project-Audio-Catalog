package AudioFileClasses;

public class MediaFile {
    private int id;
    private String title;
    private Genre genre;
    private int duration;
    private AudioType type;
    private String author;
    private int year;
    private String album;

    public MediaFile(int id, String title, Genre genre, int duration, AudioType type,
    String author, int year, String album) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.duration = duration;
        this.type = type;
        this.author = author;
        this.year = year;
        if(album == null) album = "single";
        else{
            album = album.trim();
            if (album.isEmpty()||album.equalsIgnoreCase("none")) {
                album = "single";
            }
        }
        this.album = album;
    }

    public int getId() {return id;}
    public String getTitle() {return title;}
    public Genre getGenre() {return genre;}
    public int getDuration() {return duration;}
    public AudioType getType() {return type;}
    public String getAuthor() {return author;}
    public int getYear() {return year;}
    public String getAlbum() {return album;}

    public static String formatDuration(int totalSeconds) {
        if (totalSeconds <= 0) return "00:00";

        long days = totalSeconds / 86400;
        long remaining = totalSeconds % 86400;
        long hours = remaining / 3600;
        remaining %= 3600;
        long minutes = remaining / 60;
        long seconds = remaining % 60;

        if (days > 0) {
            return String.format("%d days, %02d:%02d:%02d", days, hours, minutes, seconds);
        }
        else if (hours > 0) {
            return String.format("%02d:%02d:%02d", hours, minutes, seconds);
        }
        else {
            return String.format("%02d:%02d", minutes, seconds);
        }
    }

    @Override
    public String toString() {
        return String.format("[ID: %d] %s | Title: %s | Author: %s | Album: %s | %s | %d | %s",
                id, type, title, author, album, genre, year, formatDuration(duration));
    }

    public String toFile(){
        return id + "|" + type + "|" + title + "|" + author + "|" + album + "|" +
                duration + "|" + genre + "|" + year;
    }

    public static MediaFile fromFile(String line) {
        String[] p = line.split("\\|");

        //ID|TYPE|TITLE|AUTHOR|ALBUM|DURATION|GENRE|YEAR
        return new MediaFile(
                Integer.parseInt(p[0]),
                p[2],
                Genre.valueOf(p[6]),
                Integer.parseInt(p[5]),
                AudioType.valueOf(p[1]),
                p[3],
                Integer.parseInt(p[7]),
                p[4]
        );
    }
}
