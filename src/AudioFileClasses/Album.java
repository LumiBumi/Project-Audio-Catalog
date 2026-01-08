package AudioFileClasses;

public class Album extends MediaCollection {
    private String artist;

    public Album(String title, String artist) {
        super(title);
        this.artist = artist;
    }

    public String getArtist() {return artist;}

    @Override
    public String toString() {
        String timeString = MediaFile.formatDuration(duration);

        StringBuilder sb = new StringBuilder("Album: " + title + " (" + artist + ") - Total: " + timeString + "\n");
        for (MediaFile s : items) {
            sb.append("  ").append(s).append("\n");
        }
        return sb.toString();
    }
}
