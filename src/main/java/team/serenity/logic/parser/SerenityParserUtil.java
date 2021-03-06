package team.serenity.logic.parser;

import static java.util.Objects.requireNonNull;
import static team.serenity.commons.core.Messages.MESSAGE_INVALID_FILE_NON_XLSX;
import static team.serenity.commons.core.Messages.MESSAGE_INVALID_FILE_PATH;
import static team.serenity.commons.core.Messages.MESSAGE_INVALID_INDEX;

import org.apache.poi.openxml4j.exceptions.InvalidOperationException;

import team.serenity.commons.core.Messages;
import team.serenity.commons.core.index.Index;
import team.serenity.commons.util.XlsxUtil;
import team.serenity.logic.parser.exceptions.ParseException;
import team.serenity.model.group.GroupName;
import team.serenity.model.group.lesson.LessonName;
import team.serenity.model.group.question.Description;
import team.serenity.model.group.student.Student;
import team.serenity.model.group.student.StudentName;
import team.serenity.model.group.student.StudentNumber;
import team.serenity.model.group.studentinfo.Participation;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class SerenityParserUtil {

    /**
     * Parses a {@code String groupName} into a {@code GroupName}. Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code groupName} is invalid.
     */
    public static GroupName parseGroupName(String groupName) throws ParseException {
        requireNonNull(groupName);
        String trimmedGroupName = groupName.trim().toUpperCase();
        if (trimmedGroupName.isEmpty()) {
            throw new ParseException(GroupName.MESSAGE_GROUP_NAME_EMPTY);
        } else if (trimmedGroupName.split(" ").length > 1) {
            throw new ParseException(GroupName.MESSAGE_GROUP_NAME_MULTIPLE);
        } else if (!GroupName.isValidName(trimmedGroupName)) {
            throw new ParseException(GroupName.MESSAGE_CONSTRAINTS);
        }
        return new GroupName(trimmedGroupName);
    }

    /**
     * Parses a {@code String filePath} into a {@code XlsxUtil} object. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the given {@code filePath} is invalid.
     */
    public static XlsxUtil parseFilePath(String filePath) throws ParseException {
        requireNonNull(filePath);
        String trimmedFilePath = filePath.trim();
        if (trimmedFilePath.isEmpty()) {
            throw new ParseException(Messages.MESSAGE_FILE_PATH_EMPTY);
        } else if (!filePath.trim().endsWith(".xlsx")) {
            throw new ParseException(MESSAGE_INVALID_FILE_NON_XLSX);
        }
        try {
            return new XlsxUtil(trimmedFilePath);
        } catch (InvalidOperationException e) {
            throw new ParseException(MESSAGE_INVALID_FILE_PATH);
        }
    }

    /**
     * Parses a {@code String lessonName} into a {@code LessonName}. Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code lessonName} is invalid.
     */
    public static LessonName parseLessonName(String lessonName) throws ParseException {
        requireNonNull(lessonName);
        String trimmedLessonName = lessonName.trim();
        if (trimmedLessonName.isEmpty()) {
            throw new ParseException(LessonName.MESSAGE_LESSON_NAME_EMPTY);
        } else if (!LessonName.isValidName(trimmedLessonName)) {
            throw new ParseException(LessonName.MESSAGE_CONSTRAINTS);
        }
        return new LessonName(trimmedLessonName);
    }

    /**
     * Parses a {@code String studentName} into a {@code String}. Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code student} is invalid.
     */
    public static StudentName parseStudentName(String studentName) throws ParseException {
        requireNonNull(studentName);
        String trimmedName = studentName.trim();
        if (trimmedName.isEmpty()) {
            throw new ParseException(StudentName.MESSAGE_STUDENT_NAME_EMPTY);
        } else if (!Student.isValidName(trimmedName)) {
            throw new ParseException(StudentName.MESSAGE_CONSTRAINTS);
        }
        return new StudentName(trimmedName);
    }

    /**
     * Parses a {@code String studentId} into a {@code String}. Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code studentId} is invalid.
     */
    public static StudentNumber parseStudentNumber(String studentId) throws ParseException {
        requireNonNull(studentId);
        String trimmedId = studentId.trim();
        if (trimmedId.isEmpty()) {
            throw new ParseException(StudentNumber.MESSAGE_STUDENT_NUMBER_EMPTY);
        } else if (!Student.isValidStudentId(trimmedId)) {
            throw new ParseException(StudentNumber.MESSAGE_CONSTRAINTS);
        }
        return new StudentNumber(trimmedId);
    }

    /**
     * Parses {@code String inputScore} into an {@code int} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the specified score is invalid.
     */
    public static int parseScore(String inputScore) throws ParseException {
        requireNonNull(inputScore);
        String trimmedScore = inputScore.trim();
        int score = Integer.parseInt(trimmedScore);
        if (score < 0 || score > 5) {
            throw new ParseException(Participation.MESSAGE_CONSTRAINTS);
        }
        return score;
    }

    /**
     * Parses a {@code String questionDescription} into a {@code String}. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @return a Description object with the given {@code questionDescription}.
     * @throws ParseException if the given {@code questionDescription} is invalid.
     */
    public static Description parseDescription(String questionDescription) throws ParseException {
        requireNonNull(questionDescription);
        String trimmedQuestionDescription = questionDescription.trim();
        if (!Description.isValidDescription(trimmedQuestionDescription)) {
            throw new ParseException(Description.MESSAGE_CONSTRAINTS);
        }
        return new Description(trimmedQuestionDescription);
    }

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        requireNonNull(oneBasedIndex);
        String trimmedIndex = oneBasedIndex.trim();
        int index = Integer.parseInt(trimmedIndex);
        if (index < 1) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(index);
    }
}
