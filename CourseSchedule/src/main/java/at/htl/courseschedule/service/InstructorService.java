package at.htl.courseschedule.service;

import at.htl.courseschedule.controller.AppointmentRepository;
import at.htl.courseschedule.controller.InstructorRepository;
import at.htl.courseschedule.entity.Appointment;
import at.htl.courseschedule.entity.Instructor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class InstructorService implements Service<Instructor> {

    private static InstructorService instance;
    private final ObservableList<Instructor> instructors;
    private final InstructorRepository instructorRepository;
    private InstructorService() {
        instructorRepository = new InstructorRepository();
        instructors = FXCollections.observableArrayList(instructorRepository.findAll());
    }

    public static InstructorService getInstance() {
        if (instance == null) {
            instance = new InstructorService();
        }
        return instance;
    }

    @Override
    public void add(Instructor entity) {
        instructorRepository.save(entity);
        updateInstructors();
    }

    @Override
    public void remove(Instructor entity) {
        instructorRepository.delete(entity);
        updateInstructors();
    }

    @Override
    public void update(Instructor entity) {
        instructorRepository.update(entity);
        updateInstructors();
    }

    private void updateInstructors() {
        instructors.clear();
        instructors.setAll(instructorRepository.findAll());
    }

    public ObservableList<Instructor> getInstructors() {
        return FXCollections.unmodifiableObservableList(instructors);
    }
}
