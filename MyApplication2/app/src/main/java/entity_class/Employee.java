package entity_class;

public class Employee {
    private int id;
    private String name;
    private String phone_num;
    private byte[] avatar;
    private int role;


    public Employee(int id, String name, String phone_num, byte[] image){
        this.id = id;
        this.name = name;
        this.phone_num = phone_num;
        this.avatar = image;
    }

    public Employee() {
    }


    // GET/SET method
    public byte[] getAvatar() {
        return avatar;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone_num() {
        return phone_num;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone_num(String phone_num) {
        this.phone_num = phone_num;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}
