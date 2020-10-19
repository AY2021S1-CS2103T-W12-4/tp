package seedu.address.testutil;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.util.CsvUtil;
import seedu.address.model.group.Group;
import seedu.address.model.group.Lesson;
import seedu.address.model.group.Student;
import seedu.address.model.group.StudentInfo;
import seedu.address.model.group.UniqueLessonList;
import seedu.address.model.group.UniqueStudentInfoList;
import seedu.address.model.group.UniqueStudentList;
import seedu.address.model.util.UniqueList;

/**
 * A utility class to help with building Group objects.
 */
public class GroupBuilder {

    public static final String DEFAULT_NAME = "G04";
    public static final Set<Student> DEFAULT_STUDENTS = new HashSet<>(Arrays.asList(
        new Student("Aaron Tan", "e0123456"),
        new Student("Baron Wong", "e0654321"),
        new Student("Cherry Lee", "e0135791"),
        new Student("Dickson Low", "e0246810"),
        new Student("Eng Wee Kiat", "e0101010")

    ));
    public static final Set<Lesson> DEFAULT_CLASSES = new HashSet<>(Arrays.asList(
    ));

    private String name;
    private UniqueList<Student> students = new UniqueStudentList();
    private UniqueList<Lesson> lessons = new UniqueLessonList();

    /**
     * Creates a {@code GroupBuilder} with the default details.
     */
    public GroupBuilder() {
        name = DEFAULT_NAME;
        students.setElements(new ArrayList<>(DEFAULT_STUDENTS));
        lessons.setElements(new ArrayList<>(DEFAULT_CLASSES));
    }

    /**
     * Initializes the GroupBuilder with the data of {@code groupToCopy}.
     */
    public GroupBuilder(Group groupToCopy) {
        name = groupToCopy.getName();
        students = groupToCopy.getStudents();
        lessons = groupToCopy.getLessons();
    }

    /**
     * Initializes the GroupBuilder from the data inside the CSV file.
     */
    public GroupBuilder(String name, Path filePath) {
        this.name = name;
        students.setElements(new ArrayList<>(new CsvUtil(filePath).readStudentsFromCsv()));
        lessons.setElements(new ArrayList<>());
    }

    /**
     * Sets the {@code Name} of the {@code Group} that we are building.
     */
    public GroupBuilder withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Parses the {@code students} into a {@code Set<Student>} and set it to the {@code Group} that we are building.
     */
    public GroupBuilder withStudents(Student... students) {
        this.students.setElements(Arrays.asList(students));
        return this;
    }

    /**
     * Parses the {@code filePath} into a {@code Set<Student>} and set it to the {@code Group} that we are building.
     */
    public GroupBuilder withFilePath(Path filePath) {
        students.setElements(new ArrayList<>(new CsvUtil(filePath).readStudentsFromCsv()));
        return this;
    }

    /**
     * Creates and parses the {@code classes} into a {@code Set<Class>} and set it to the {@code Group} that we are
     * building.
     */
    public GroupBuilder withClasses(String... classes) {
        UniqueList<StudentInfo> studentsInfo = new UniqueStudentInfoList();
        for (Student student : students) {
            studentsInfo.add(new StudentInfo(student));
        }
        for (String className : classes) {
            this.lessons.add(new Lesson(className, studentsInfo));
        }
        return this;
    }

    public Group build() {
        return new Group(this.name, this.students, this.lessons);
    }

}
