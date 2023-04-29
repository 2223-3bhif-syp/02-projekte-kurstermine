package at.htl.courseschedule.entity;

import java.time.LocalDateTime;

public class Appointment {
    private Long id;
    private LocalDateTime start;
    private Instructor instructor;
    private Course course;

    //region Constructors
    public Appointment() {
    }

    public Appointment(LocalDateTime start, Instructor instructor, Course course) {
        this.start = start;
        this.instructor = instructor;
        this.course = course;
    }
    //endregion

    //region Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
    //endregion
    @Override
    public String toString() {
        return "Appointment{" +
                "id=" + id +
                ", start=" + start +
                ", instructorId=" + instructor +
                ", courseId=" + course +
                '}';
    }
}
