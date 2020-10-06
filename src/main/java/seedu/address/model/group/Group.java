package seedu.address.model.group;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;
import java.util.Set;

import javafx.collections.ObservableList;
import seedu.address.commons.util.CsvUtil;

/**
 * Represents a tutorial Group in serenity. Guarantees: details are present and not null, field values are validated,
 * immutable.
 */
public class Group {

    // Identity field
    private String name;

    // Data fields
    //private Set<Student> students;
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
        this.name = name;
        CsvUtil util = new CsvUtil(filePath);
        students = new UniqueStudentList();
        students.setStudents(new ArrayList<>(util.readStudentsFromCsv()));
        //todo: implement scores data
        Set<StudentInfo> studentsInfo = util.readStudentsInfoFromCsv(util.readStudentsFromCsv());
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
     * @param lessons  A list of tutorial classes.
     */

    public Group(String name, UniqueStudentList students, UniqueLessonList classes) {
        requireAllNonNull(name, students, classes);
        this.name = name;
        this.students = students;
        this.lessons = lessons;
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
        lessons.sort(new Comparator<Lesson>() {
            @Override
            public int compare(Lesson o1, Lesson o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
        return lessons;
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
        return name + "," + students.toString();
    }
}
