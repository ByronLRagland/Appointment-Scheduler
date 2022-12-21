package learn.scheduler.model;

public class Employee {

    private int employeeId;
    private String firstName;
    private String lastName;
    private String bio;
    private String imageUrl;

    public Employee()
    {

    }
    
    public Employee(int employeeId, String firstName, String lastName, String bio, String image_url) {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.bio = bio;
        this.imageUrl = image_url;
    }
    public int getEmployeeId() {
        return employeeId;
    }
    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getBio() {
        return bio;
    }
    public void setBio(String bio) {
        this.bio = bio;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String image_url) {
        this.imageUrl = image_url;
    }

    


}
