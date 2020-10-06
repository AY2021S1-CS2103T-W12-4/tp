package seedu.address.model.group;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.group.exceptions.DuplicateGroupException;
import seedu.address.model.group.exceptions.DuplicateStudentInfoException;
import seedu.address.model.group.exceptions.StudentInfoNotFoundException;

/**
 * A list of Students Info that enforces uniqueness between its elements and does not allow nulls.
 * A Student Info is considered unique by comparing using {@code StudentInfo#equal(Object)}.
 */
public class UniqueStudentInfoList implements Iterable<StudentInfo> {

    private final ObservableList<StudentInfo> internalList = FXCollections.observableArrayList();
    private final ObservableList<StudentInfo> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent student info as the given argument.
     */
    public boolean contains(StudentInfo toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::equals);
    }

    /**
     * Adds a student info to the list. The student info must not already exist in the list.
     */
    public void add(StudentInfo toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateStudentInfoException();
        }
        internalList.add(toAdd);
    }

    /**
     * Removes the equivalent student info from the list. The student info must exist in the list.
     */
    public void remove(StudentInfo toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new StudentInfoNotFoundException();
        }
    }

    public int size() {
        return internalList.size();
    }

    /**
     * Replaces the student info {@code target} in the list with {@code editedStudentInfo}.
     * {@code target} must exist in the list.
     * The identity of {@code editedStudentInfo} must not be the same as another existing student info in the list.
     */
    public void setStudentInfo(StudentInfo target, StudentInfo editedStudentInfo) {
        requireAllNonNull(target, editedStudentInfo);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new StudentInfoNotFoundException();
        }

        if (!target.equals(editedStudentInfo) && contains(editedStudentInfo)) {
            throw new DuplicateStudentInfoException();
        }

        internalList.set(index, editedStudentInfo);
    }

    public void setStudentInfo(UniqueStudentInfoList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code studentsInfo}.
     * {@code studentsInfo} must not contain duplicate students info.
     */
    public void setStudentInfo(List<StudentInfo> studentsInfo) {
        requireAllNonNull(studentsInfo);
        if (!studentsInfoAreUnique(studentsInfo)) {
            throw new DuplicateGroupException();
        }

        internalList.setAll(studentsInfo);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<StudentInfo> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<StudentInfo> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueStudentInfoList // instanceof handles nulls
                && internalList.equals(((UniqueStudentInfoList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code studentsInfo} contains only unique students info.
     */
    private boolean studentsInfoAreUnique(List<StudentInfo> studentsInfo) {
        for (int i = 0; i < studentsInfo.size() - 1; i++) {
            for (int j = i + 1; j < studentsInfo.size(); j++) {
                if (studentsInfo.get(i).equals(studentsInfo.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

}
