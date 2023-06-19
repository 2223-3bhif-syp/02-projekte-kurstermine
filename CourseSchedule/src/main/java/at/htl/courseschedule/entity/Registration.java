package at.htl.courseschedule.entity;

public class Registration {
    private Long id;
    private Participant participant;
    private Appointment appointment;

    //region Constructors
    public Registration() {
    }

    public Registration(Participant participant, Appointment appointment) {
        this.participant = participant;
        this.appointment = appointment;
    }
    //endregion

    //region Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Participant getParticipant() {
        return participant;
    }

    public void setParticipant(Participant participant) {
        this.participant = participant;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }
    //endregion

    @Override
    public String toString() {
        return "Registration{" +
                "id=" + id +
                ", participant=" + participant +
                ", appointment=" + appointment +
                '}';
    }
}
