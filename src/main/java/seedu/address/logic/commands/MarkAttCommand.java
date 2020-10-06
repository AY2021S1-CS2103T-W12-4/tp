package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT;

import javafx.collections.ObservableList;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.group.Attendance;
import seedu.address.model.group.Student;
import seedu.address.model.group.StudentInfo;

/**
 * Marks the attendance of a class or a student in the class.
 */
public class MarkAttCommand extends Command {

    public static final String COMMAND_WORD = "markatt";
    public static final String MESSAGE_SUCCESS = "Attendance marked: %1$s";
    public static final String MESSAGE_ALL_SUCCESS = "Attendance of all students marked";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks the attendance of all students / a student in a class. \n"
            + "Parameters: "
            + "all or "
            + PREFIX_STUDENT + " NAME" + " " + PREFIX_ID + " STUDENT_NUMBER\n"
            + "Example: " + COMMAND_WORD + " " + "all\n"
            + "or " + COMMAND_WORD + " "
            + PREFIX_NAME + " John Doe" + " " + PREFIX_ID + " E0123456";

    private Student toMarkAtt;
    private boolean isWholeClass;


    /**
     * Creates an MarkAttCommand to mark all {@code Student} present
     */
    public MarkAttCommand() {
        // Mark all students present
        isWholeClass = true;
    }

    /**
     * Creates an MarkAttCommand to mark the specified {@code Student} present
     */
    public MarkAttCommand(Student student) {
        requireNonNull(student);
        isWholeClass = false;
        // Specified student to mark present
        toMarkAtt = student;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        ObservableList<StudentInfo> studentsInfo = model.getStudentInfoList();

        if (!isWholeClass) {

            // Mark single student attendance
            for (int i = 0; i < studentsInfo.size(); i++) {
                StudentInfo student = studentsInfo.get(i);
                boolean isCorrectStudent = studentsInfo.get(i).containsStudent(toMarkAtt);
                if (isCorrectStudent) {
                    Attendance update = student.getAttendance().setAttendance(true);
                    student.updateAttendance(update);
                }
            }
            return new CommandResult(String.format(MESSAGE_SUCCESS, toMarkAtt));
        }

        // Mark whole class attendance
        for (StudentInfo each: studentsInfo) {
            Attendance update = each.getAttendance().setAttendance(true);
            each.updateAttendance(update);
        }

        return new CommandResult(String.format(MESSAGE_ALL_SUCCESS));
    }
}
