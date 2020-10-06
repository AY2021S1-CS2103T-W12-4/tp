package seedu.address.model.group;

import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import javafx.collections.ObservableList;

/**
 * Represents a tutorial class in serenity. Guarantees: details are present and not null, field values are validated,
 * immutable.
 */
public class Lesson {

    public static final String NAME_CONSTRAINT = "Class name cannot be empty";
    public static final String STUDENTS_INFO_CONSTRAINT = "Students information cannot be empty";

    private final String name;
    private final UniqueStudentInfoList studentsInfo;

    /**
     * Constructs a {@code Class}.
     *
     * @param name A valid name.
     */
    public Lesson(String name, UniqueStudentInfoList studentsInfo) {
        requireAllNonNull(name, studentsInfo);
        checkArgument(isValidName(name), NAME_CONSTRAINT);
        checkArgument(isValidStudentInfo(studentsInfo), STUDENTS_INFO_CONSTRAINT);
        this.name = name;
        this.studentsInfo = studentsInfo;
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

    public ObservableList<StudentInfo> getStudentsInfoAsUnmodifiableObservableList() {
        return studentsInfo.asUnmodifiableObservableList();
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
                && otherClass.getStudentsInfo().equals(getStudentsInfo());
    }
}

