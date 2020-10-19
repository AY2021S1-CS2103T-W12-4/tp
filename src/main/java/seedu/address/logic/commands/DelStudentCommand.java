package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_GROUP_EMPTY;
import static seedu.address.commons.core.Messages.MESSAGE_STUDENT_EMPTY;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.group.Group;
import seedu.address.model.group.Student;

public class DelStudentCommand extends Command {

    public static final String COMMAND_WORD = "delstudent";
    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Removes a new Student from a specified tutorial group.\n"
        + "Parameters: "
        + PREFIX_GRP + "GRP "
        + PREFIX_STUDENT + "NAME "
        + PREFIX_ID + "STUDXENT_ID \n"
        + "Example: " + COMMAND_WORD + " " + PREFIX_GRP + "G04 " + PREFIX_STUDENT + "Ryan " + PREFIX_ID + "e1234567";

    public static final String MESSAGE_SUCCESS = "You removed %s (%s) from tutorial group %s";

    private final String studentName;
    private final String studentId;
    private final Predicate<Group> predicate;

    /**
     * Creates a DelStudentCommand to remove the specified {@code Student}
     * @param studentName Name of Student
     * @param studentId Id of Student
     * @param predicate Group predicate
     */
    public DelStudentCommand(String studentName, String studentId, Predicate<Group> predicate) {
        requireAllNonNull(studentName, studentId, predicate);
        this.studentName = studentName;
        this.studentId = studentId;
        this.predicate = predicate;
    }


    @Override
    public CommandResult execute(Model model) throws CommandException {
        Student student = new Student(studentName, studentId);
        model.updateFilteredGroupList(predicate);
        ObservableList<Group> groups = model.getFilteredGroupList();
        if (groups.isEmpty()) {
            //no such group
            throw new CommandException(MESSAGE_GROUP_EMPTY);
        }
        if (!groups.get(0).getStudents().contains(student)) {
            //student does not exist
            throw new CommandException(MESSAGE_STUDENT_EMPTY);
        } else {
            model.removeStudentFromGroup(student, predicate);
            model.updateFilteredGroupList(predicate);
            return new CommandResult(
                String.format(MESSAGE_SUCCESS, studentName, studentId, model.getFilteredGroupList().get(0).getName()));
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (obj instanceof DelStudentCommand) {
            DelStudentCommand other = (DelStudentCommand) obj;
            return studentName.equals(other.studentName) && studentId.equals(other.studentId)
                && predicate.equals(other.predicate);
        } else {
            return false;
        }
    }
}
