package entity_class;

public class MediaDetails {
    String imageLink;
    String videoLink;
    String datetime;
    int id_media;

    public MediaDetails(String imageLink, String videoLink, String datetime, int id_media) {
        this.imageLink = imageLink;
        this.videoLink = videoLink;
        this.datetime = datetime;
        this.id_media = id_media;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getVideoLink() {
        return videoLink;
    }

    public void setVideoLink(String videoLink) {
        this.videoLink = videoLink;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public int getId_media() {
        return id_media;
    }

    public void setId_media(int id_media) {
        this.id_media = id_media;
    }
}
