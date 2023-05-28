package at.htl.courseschedule.service;

import at.htl.courseschedule.controller.AppointmentRepository;
import at.htl.courseschedule.entity.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AppointmentService implements Service<Appointment> {
    private static AppointmentService instance;
    private final ObservableList<Appointment> appointments;
    private final AppointmentRepository appointmentRepository;

    private AppointmentService() {
        appointmentRepository = new AppointmentRepository();
        appointments = FXCollections.observableArrayList(appointmentRepository.findAll());
    }

    public static AppointmentService getInstance() {
        if (instance == null) {
            instance = new AppointmentService();
        }

        return instance;
    }

    @Override
    public void add(Appointment appointment) {
        appointmentRepository.insert(appointment);
        appointments.add(appointment);
    }

    @Override
    public void remove(Appointment appointment) {
        appointmentRepository.delete(appointment);
        appointments.remove(appointment);
    }

    @Override
    public void update(Appointment appointment) {
        appointmentRepository.update(appointment);
    }

    public ObservableList<Appointment> getAppointments() {
        return appointments;
    }
}
