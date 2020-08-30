package entity_class;

public class MediaDetails {
    String imageLink;
    String videoLink;
    String datetime;
    int id_media;
    int id_employee;

    public MediaDetails(String imageLink, String videoLink, String datetime, int id_media, int id_employee) {
        this.imageLink = imageLink;
        this.videoLink = videoLink;
        this.datetime = datetime;
        this.id_media = id_media;
        this.id_employee = id_employee;
    }

    public int getId_employee() {
        return id_employee;
    }

    public void setId_employee(int id_employee) {
        this.id_employee = id_employee;
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
