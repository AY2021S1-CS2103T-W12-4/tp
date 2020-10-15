package seedu.address.model.group;

import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import javafx.collections.ObservableList;

/**
 * Represents a tutorial class in serenity. Guarantees: details are present and not null, field values are validated,
 * immutable.
 */
public class Lesson {

    public static final String LESSON_NAME_CONSTRAINT = "Class name cannot be empty";
    public static final String STUDENT_INFO_CONSTRAINT = "Student information cannot be empty";

    private final String name;
    private final UniqueStudentInfoList studentsInfo;
    private final UniqueQuestionList questionList;

    /**
     * Constructs a {@code Lesson}.
     *
     * @param name A valid name.
     * @param studentsInfo A valid list of studentInfo.
     */
    public Lesson(String name, UniqueStudentInfoList studentsInfo) {
        requireAllNonNull(name, studentsInfo);
        checkArgument(isValidName(name), LESSON_NAME_CONSTRAINT);
        checkArgument(isValidStudentInfo(studentsInfo), STUDENT_INFO_CONSTRAINT);
        this.name = name;
        this.studentsInfo = studentsInfo;
        this.questionList = new UniqueQuestionList();
    }

    /**
     * Constructs a {@code Lesson}.
     *
     * @param name A valid name.
     * @param studentsInfo A valid list of studentInfo.
     * @param questionList A list of questions.
     */
    public Lesson(String name, UniqueStudentInfoList studentsInfo, UniqueQuestionList questionList) {
        requireAllNonNull(name, studentsInfo);
        checkArgument(isValidName(name), LESSON_NAME_CONSTRAINT);
        checkArgument(isValidStudentInfo(studentsInfo), STUDENT_INFO_CONSTRAINT);
        this.name = name;
        this.studentsInfo = studentsInfo;
        this.questionList = questionList;
    }

    /**
     * Constructs a {@code Lesson}.
     * @param name
     * @param students
     */
    public Lesson(String name, UniqueStudentList students) {
        requireAllNonNull(name, students);
        checkArgument(isValidName(name), LESSON_NAME_CONSTRAINT);
        this.name = name;
        this.studentsInfo = this.generateStudentInfo(students.asUnmodifiableObservableList());
        this.questionList = new UniqueQuestionList();
    }

    private UniqueStudentInfoList generateStudentInfo(ObservableList<Student> students) {
        UniqueStudentInfoList studentInfo = new UniqueStudentInfoList();
        for (Student s : students) {
            studentInfo.add(new StudentInfo(s, new Participation(), new Attendance()));
        }
        return studentInfo;
    }

    boolean isValidName(String name) {
        return name.length() > 0;
    }

    public String getName() {
        return name;
    }

    boolean isValidStudentInfo(UniqueStudentInfoList studentsInfo) {
        return studentsInfo.size() > 0;
    }

    public UniqueStudentInfoList getStudentsInfo() {
        return studentsInfo;
    }

    public UniqueQuestionList getQuestionList() {
        return questionList;
    }

    public ObservableList<StudentInfo> getStudentsInfoAsUnmodifiableObservableList() {
        return studentsInfo.asUnmodifiableObservableList();
    }

    public boolean isSame(Lesson otherLsn) {
        return otherLsn.getName().equals(getName());
    }

    public ObservableList<Question> getQuestionListAsUnmodifiableObservableList() {
        return questionList.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof Lesson)) {
            return false;
        }

        Lesson otherClass = (Lesson) obj;
        return otherClass.getName().equals(getName())
            && otherClass.getStudentsInfo().equals(getStudentsInfo())
            && otherClass.getQuestionList().equals(getQuestionList());
    }

    @Override
    public String toString() {
        return String.format("Lesson %s", name);
    }
}

