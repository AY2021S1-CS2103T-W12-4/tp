package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.group.Student;

public class SerenityParserUtil {

    /**
     * Parses a {@code String student} into a {@code String}. Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code student} is invalid.
     */
    public static String parseStudent(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Student.isValidString(trimmedName)) {
            throw new ParseException(Student.STUDENT_NAME_ERROR);
        }
        return trimmedName;
    }

    /**
     * Parses a {@code String id} into a {@code String}. Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code id} is invalid.
     */
    public static String parseStudentID(String id) throws ParseException {
        requireNonNull(id);
        String trimmedId = id.trim();
        if (!Student.isValidStudentNumber(trimmedId)) {
            throw new ParseException(Student.STUDENT_NUMBER_ERROR);
        }
        return trimmedId;
    }
}
