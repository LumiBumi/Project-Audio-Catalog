package AudioFileClasses;

public class MediaFile {
    private int id;
    private String title;
    private String genre;
    private int duration;
    private AudioType type;
    private String author;
    private int year;
    private String album;

    public MediaFile(int id, String title, String genre, int duration, AudioType type,
    String author, int year, String album) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.duration = duration;
        this.type = type;
        this.author = author;
        this.year = year;
        this.album = album;
    }

    public int getId() {return id;}
    public String getTitle() {return title;}
    public String getGenre() {return genre;}
    public int getDuration() {return duration;}
    public AudioType getType() {return type;}
    public String getAuthor() {return author;}
    public int getYear() {return year;}
    public String getAlbum() {return album;}

    @Override
    public String toString() {
        return String.format("[ID: %d] %s | Title: %s | Author: %s | Album: %s | %s | %d | %ds",
                id, type, title, author, album, genre, year, duration);
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
                p[6],
                Integer.parseInt(p[5]),
                AudioType.valueOf(p[1]),
                p[3],
                Integer.parseInt(p[7]),
                p[4]
        );
    }
}
