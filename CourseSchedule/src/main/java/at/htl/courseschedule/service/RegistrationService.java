package at.htl.courseschedule.service;

import at.htl.courseschedule.controller.AppointmentRepository;
import at.htl.courseschedule.controller.RegistrationRepository;
import at.htl.courseschedule.entity.Appointment;
import at.htl.courseschedule.entity.Registration;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class RegistrationService implements Service<Registration>{

    private static RegistrationService instance;
    private final ObservableList<Registration> registrations;
    private final RegistrationRepository registrationRepository;

    private RegistrationService() {
        registrationRepository = new RegistrationRepository();
        registrations = FXCollections.observableArrayList(registrationRepository.findAll());
    }

    public static RegistrationService getInstance() {
        if (instance == null) {
            instance = new RegistrationService();
        }

        return instance;
    }


    @Override
    public void add(Registration entity) {
        registrationRepository.save(entity);
        updateRegistrations();
    }

    @Override
    public void remove(Registration entity) {
        registrationRepository.delete(entity);
        updateRegistrations();
    }

    @Override
    public void update(Registration entity) {
        registrationRepository.update(entity);
        updateRegistrations();
    }

    private void updateRegistrations() {
        registrations.clear();
        registrations.setAll(registrationRepository.findAll());
    }

    public ObservableList<Registration> getRegistrations() {
        return FXCollections.unmodifiableObservableList(registrations);
    }
}
