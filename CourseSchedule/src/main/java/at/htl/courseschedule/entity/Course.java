package at.htl.courseschedule.entity;

public class Course {
    private Long id;
    private String name;
    private String description;
    private int minutesPerAppointment;
    private int amountOfAppointments;

    //region Constructors
    public Course() {

    }

    public Course(String name, String description, int minutesPerAppointment, int amountOfAppointments) {
        this.name = name;
        this.description = description;
        this.minutesPerAppointment = minutesPerAppointment;
        this.amountOfAppointments = amountOfAppointments;
    }
    //endregion

    //region Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMinutesPerAppointment() {
        return minutesPerAppointment;
    }

    public void setMinutesPerAppointment(int minutesPerAppointment) {
        this.minutesPerAppointment = minutesPerAppointment;
    }

    public int getAmountOfAppointments() {
        return amountOfAppointments;
    }

    public void setAmountOfAppointments(int amountOfAppointments) {
        this.amountOfAppointments = amountOfAppointments;
    }
    //endregion
    @Override
    public String toString() {
        return name;
    }
}
