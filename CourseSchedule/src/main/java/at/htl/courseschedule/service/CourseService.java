package at.htl.courseschedule.service;

import at.htl.courseschedule.controller.CourseRepository;
import at.htl.courseschedule.controller.ParticipantRepository;
import at.htl.courseschedule.entity.Course;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Optional;

public class CourseService implements Service<Course> {
    private static CourseService instance;
    private final ObservableList<Course> courses;
    private final CourseRepository courseRepository;

    private CourseService() {
        courseRepository = new CourseRepository();
        courses = FXCollections.observableArrayList(courseRepository.findAll());
    }

    public static CourseService getInstance() {
        if (instance == null) {
            instance = new CourseService();
        }

        return instance;
    }

    @Override
    public void add(Course course) {
        courseRepository.save(course);
        updateCourses();
    }

    @Override
    public void remove(Course course) {
        courseRepository.delete(course);
        updateCourses();
    }

    @Override
    public void update(Course course) {
        courseRepository.update(course);
        updateCourses();
    }

    private void updateCourses(){
        courses.clear();
        courses.setAll(courseRepository.findAll());
    }

    public ObservableList<Course> getCourses() {
        return FXCollections.unmodifiableObservableList(courses);
    }
}