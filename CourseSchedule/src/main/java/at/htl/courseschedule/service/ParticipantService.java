package at.htl.courseschedule.service;

import at.htl.courseschedule.controller.ParticipantRepository;
import at.htl.courseschedule.entity.Participant;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ParticipantService implements Service<Participant> {
    private static ParticipantService instance;

    private final ObservableList<Participant> participants;
    private final ParticipantRepository participantRepository;

    private ParticipantService() {
        participantRepository = new ParticipantRepository();
        participants = FXCollections.observableArrayList(participantRepository.findAll());
    }

    public static ParticipantService getInstance() {
        if (instance == null) {
            instance = new ParticipantService();
        }

        return instance;
    }

    @Override
    public void add(Participant participant) {
        participantRepository.insert(participant);
        updateParticipants();
    }

    @Override
    public void remove(Participant participant) {
        participantRepository.delete(participant);
        updateParticipants();
    }

    @Override
    public void update(Participant participant) {
        participantRepository.update(participant);
        updateParticipants();
    }

    private void updateParticipants(){
        participants.clear();
        participants.setAll(participantRepository.findAll());
    }

    public ObservableList<Participant> getParticipants() {
        return FXCollections.unmodifiableObservableList(participants);
    }
}
