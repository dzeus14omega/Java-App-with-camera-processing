package entity_class;

public class EmployeeCheckinResult {
    private int id;
    private String datetime;
    private String employee_name;
    private String videoLink;
    private String imageLink;
    private byte[] avatar;

    public EmployeeCheckinResult(int id, String datetime, String employee_name, String videoLink, String imageLink, byte[] avatar) {
        this.id = id;
        this.datetime = datetime;
        this.employee_name = employee_name;
        this.videoLink = videoLink;
        this.imageLink = imageLink;
        this.avatar = avatar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getEmployee_name() {
        return employee_name;
    }

    public void setEmployee_name(String employee_name) {
        this.employee_name = employee_name;
    }

    public String getVideoLink() {
        return videoLink;
    }

    public void setVideoLink(String videoLink) {
        this.videoLink = videoLink;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }
}
