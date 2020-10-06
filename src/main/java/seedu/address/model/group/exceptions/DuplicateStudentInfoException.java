package seedu.address.model.group.exceptions;

/**
 * Signals that the operation will result in duplicate Student Info
 * (Student Info are considered duplicates if they have the same student identity).
 */
public class DuplicateStudentInfoException extends RuntimeException {

    public DuplicateStudentInfoException() {
        super("Operation would result in duplicate Student Info!");
    }

}
