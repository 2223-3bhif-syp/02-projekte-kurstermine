package at.htl.courseschedule.service;

import at.htl.courseschedule.controller.ParticipantRepository;
import at.htl.courseschedule.entity.Participant;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ParticipantService {
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

    public void addParticipant(Participant participant) {
        participantRepository.insert(participant);
        participants.add(participant);
    }

    public void removeParticipant(Participant participant) {
        participantRepository.delete(participant);
        participants.remove(participant);
    }

    public void updateParticipant(Participant participant) {
        participantRepository.update(participant);
    }

    public ObservableList<Participant> getParticipants() {
        return FXCollections.unmodifiableObservableList(participants);
    }
}
