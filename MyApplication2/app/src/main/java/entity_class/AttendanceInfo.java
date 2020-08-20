package entity_class;

public class AttendanceInfo {
    private int id_employee;
    private String datetime;
    private String linkImage;
    private String linkVideo;


    public AttendanceInfo(int id_employee, String datetime, String linkImage, String linkVideo) {
        this.id_employee = id_employee;
        this.datetime = datetime;
        this.linkImage = linkImage;
        this.linkVideo = linkVideo;
    }

    public int getId_employee() {
        return id_employee;
    }

    public void setId_employee(int id_employee) {
        this.id_employee = id_employee;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getLinkImage() {
        return linkImage;
    }

    public void setLinkImage(String linkImage) {
        this.linkImage = linkImage;
    }

    public String getLinkVideo() {
        return linkVideo;
    }

    public void setLinkVideo(String linkVideo) {
        this.linkVideo = linkVideo;
    }


}
