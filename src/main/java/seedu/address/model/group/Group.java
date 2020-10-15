package seedu.address.model.group;

import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;
import java.util.Set;

import javafx.collections.ObservableList;
import seedu.address.commons.util.CsvUtil;

/**
 * Represents a tutorial group in serenity. Guarantees: details are present and not null, field values are validated,
 * immutable.
 */
public class Group {

    public static final String GROUP_NAME_CONSTRAINT = "Group name cannot be empty";

    private String name;
    private UniqueStudentList students;
    private UniqueLessonList lessons;

    /**
     * Constructs a {@code Group}
     *
     * @param name     A valid name.
     * @param filePath A valid filePath.
     */
    public Group(String name, Path filePath) {
        requireAllNonNull(name, filePath);
        checkArgument(isValidGroupName(name), GROUP_NAME_CONSTRAINT);
        CsvUtil util = new CsvUtil(filePath);
        Set<StudentInfo> studentsInfo = util.readStudentsInfoFromCsv(util.readStudentsFromCsv());
        this.name = name;
        students = new UniqueStudentList();
        students.setStudents(new ArrayList<>(util.readStudentsFromCsv()));
        lessons = new UniqueLessonList();
        lessons.setLessons(new ArrayList<>(util.readLessonsFromCsv(studentsInfo)));
    }

    /**
     * Constructs a {@code Group}.
     *
     * @param name     A valid name.
     * @param students A list of students.
     */
    public Group(String name, UniqueStudentList students) {
        requireAllNonNull(name, students);
        this.name = name;
        this.students = students;
        this.lessons = new UniqueLessonList();
    }

    /**
     * Constructs a {@code Group}.
     *
     * @param name     A valid name.
     * @param students A list of students.
     * @param lessons  A list of tutorial lessons.
     */

    public Group(String name, UniqueStudentList students, UniqueLessonList lessons) {
        requireAllNonNull(name, students, lessons);
        this.name = name;
        this.students = students;
        this.lessons = lessons;
    }

    public static boolean isValidGroupName(String s) {
        return s.length() > 0;
    }

    public String getName() {
        return name;
    }

    public UniqueStudentList getStudents() {
        return students;
    }

    public ObservableList<Student> getStudentsAsUnmodifiableObservableList() {
        return students.asUnmodifiableObservableList();
    }

    public ObservableList<Lesson> getLessonsAsUnmodifiableObservableList() {
        return lessons.asUnmodifiableObservableList();
    }

    public UniqueLessonList getLessons() {
        return lessons;
    }

    public UniqueLessonList getSortedLessons() {
        lessons.sort(Comparator.comparing(Lesson::getName));
        return lessons;
    }

    /**
     * Adds a Student to a Group
     *
     * @param student Student to be added
     */
    public void addStudentToGroup(Student student) {
        addToStudentList(student);
        addToStudentListInLessons(student);
    }

    /**
     * Removes a Student from the Group.
     *
     * @param student Student to be added
     */
    public void removeStudentFromGroup(Student student) {
        removeStudentFromStudentListInLessons(student);
    }


    private void addToStudentList(Student student) {
        students.add(student);
    }


    private void addToStudentListInLessons(Student student) {
        for (Lesson lesson : lessons) {
            StudentInfo newStudent = new StudentInfo(student);
            UniqueStudentInfoList studentInfos = lesson.getStudentsInfo();
            studentInfos.add(newStudent);
            Lesson updatedLesson = new Lesson(lesson.getName(), studentInfos);
            lessons.setLesson(lesson, updatedLesson);
        }
    }

    private void removeStudentFromStudentListInLessons(Student student) {
        for (Lesson lesson : lessons) {
            StudentInfo newStudent = new StudentInfo(student);
            UniqueStudentInfoList studentInfos = lesson.getStudentsInfo();
            studentInfos.remove(newStudent);
            Lesson updatedLesson = new Lesson(lesson.getName(), studentInfos);
            lessons.setLesson(lesson, updatedLesson);
        }
    }

    /**
     * Returns true if both groups of the same name have at least one other identity field that is the same. This
     * defines a weaker notion of equality between two groups.
     */
    public boolean isSameGroup(Group otherGroup) {
        if (otherGroup == this) {
            return true;
        }

        return otherGroup != null
            && otherGroup.getName().equals(getName())
            && otherGroup.getStudents().equals(getStudents())
            && otherGroup.getLessons().equals(getLessons());
    }

    /**
     * Returns true if both groups have the same identity and data fields. This defines a stronger notion of equality
     * between two groups.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Group)) {
            return false;
        }

        Group otherGroup = (Group) other;
        return otherGroup.getName().equals(getName())
            && otherGroup.getStudents().equals(getStudents())
            && otherGroup.getLessons().equals(getLessons());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, students, lessons);
    }

    @Override
    public String toString() {
        return String.format("Tutorial group %s", name);
    }


}
