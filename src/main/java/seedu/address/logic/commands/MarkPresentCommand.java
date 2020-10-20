package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT;

import javafx.collections.ObservableList;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.group.Attendance;
import seedu.address.model.group.Lesson;
import seedu.address.model.group.Student;
import seedu.address.model.group.StudentInfo;
import seedu.address.model.group.UniqueStudentInfoList;

/**
 * Marks the attendance of a class or a student in the class.
 */
public class MarkPresentCommand extends Command {

    public static final String COMMAND_WORD = "markpresent";
    public static final String MESSAGE_SUCCESS = "%s: \nAttendance: present";
    public static final String MESSAGE_ALL_SUCCESS = "Attendance of all students marked present!";
    public static final String MESSAGE_STUDENT_NOT_FOUND =
            "%s is not found, please ensure the name & student id is correct";
    public static final String MESSAGE_NOT_IN_LESSON = "Currently not in any lesson. Please enter a lesson.";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks the attendance of all students / a student in a class. \n"
            + "Parameters: "
            + "all or "
            + PREFIX_STUDENT + " NAME" + " " + PREFIX_ID + " STUDENT_NUMBER\n"
            + "Example: " + COMMAND_WORD + " " + "all\n"
            + "or " + COMMAND_WORD + " "
            + PREFIX_STUDENT + " Aaron Tan" + " " + PREFIX_ID + " e0123456";

    private Student toMarkPresent;
    private boolean isWholeClass;
    private boolean isCorrectStudent;


    /**
     * Creates an MarkPresentCommand to mark all {@code Student} present
     */
    public MarkPresentCommand() {
        // Mark all students present
        isWholeClass = true;
    }

    /**
     * Creates an MarkPresentCommand to mark the specified {@code Student} present
     */
    public MarkPresentCommand(Student student) {
        requireNonNull(student);
        isWholeClass = false;
        // Specified student to mark present
        toMarkPresent = student;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        try {
            Lesson uniqueLesson = model.getFilteredLessonList().get(0);
            UniqueStudentInfoList uniqueStudentInfoList = uniqueLesson.getStudentsInfo();
            ObservableList<StudentInfo> studentsInfo = uniqueStudentInfoList.asUnmodifiableObservableList();

            if (!isWholeClass) {

                // Mark single student attendance
                for (int i = 0; i < studentsInfo.size(); i++) {
                    StudentInfo studentInfo = studentsInfo.get(i);
                    isCorrectStudent = studentInfo.containsStudent(toMarkPresent);
                    if (isCorrectStudent) {
                        Attendance update = studentInfo.getAttendance().setNewAttendance(true);
                        StudentInfo updatedStudentInfo = studentInfo.updateAttendance(update);
                        uniqueStudentInfoList.setStudentInfo(studentInfo, updatedStudentInfo);
                        model.updateLessonList();
                        model.updateStudentInfoList();
                        break;
                    }
                }

                if (!isCorrectStudent) {
                    throw new CommandException(String.format(MESSAGE_STUDENT_NOT_FOUND, toMarkPresent));
                }
                return new CommandResult(String.format(MESSAGE_SUCCESS, toMarkPresent));
            }

            // Mark whole class attendance
            for (StudentInfo each : studentsInfo) {
                Attendance update = each.getAttendance().setNewAttendance(true);
                StudentInfo updatedStudentInfo = each.updateAttendance(update);
                uniqueStudentInfoList.setStudentInfo(each, updatedStudentInfo);
                model.updateLessonList();
                model.updateStudentInfoList();
            }

            return new CommandResult(String.format(MESSAGE_ALL_SUCCESS));

        } catch (Exception e) {
            if (e instanceof CommandException) {
                throw e;
            } else {
                throw new CommandException(MESSAGE_NOT_IN_LESSON);
            }
        }
    }
}