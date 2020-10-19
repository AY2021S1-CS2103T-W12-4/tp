package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_DUPLICATE_STUDENT;
import static seedu.address.commons.core.Messages.MESSAGE_GROUP_EMPTY;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT;

import java.util.function.Predicate;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.group.Group;
import seedu.address.model.group.Student;

public class AddStudentCommand extends Command {

    public static final String COMMAND_WORD = "addstudent";
    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Adds a new Student to a specified tutorial group.\n"
        + "Parameters: "
        + PREFIX_GRP + "GRP "
        + PREFIX_STUDENT + "NAME "
        + PREFIX_ID + "Student ID\n"
        + "Example: " + COMMAND_WORD + " " + PREFIX_GRP + "G04 "
        + PREFIX_STUDENT + "Ryan " + PREFIX_ID + "e1234567";

    public static final String MESSAGE_SUCCESS = "You added %s (%s) to tutorial group %s";

    private final String studentName;
    private final String studentId;
    private final Predicate<Group> predicate;

    /**
     * Creates an AddStudentCommand to add the specified {@code Student}
     *
     * @param studentName
     * @param studentId
     * @param predicate
     */
    public AddStudentCommand(String studentName, String studentId, Predicate<Group> predicate) {
        requireAllNonNull(studentName, studentId, predicate);
        this.studentName = studentName;
        this.studentId = studentId;
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        Student student = new Student(studentName, studentId);
        model.updateFilteredGroupList(predicate);
        if (model.getFilteredGroupList().isEmpty()) {
            //no such group exists
            throw new CommandException(MESSAGE_GROUP_EMPTY);
        }
        Group targetGroup = model.getFilteredGroupList().get(0);
        if (targetGroup.getStudents().contains(student)) {
            throw new CommandException(MESSAGE_DUPLICATE_STUDENT);
        }
        model.addStudentToGroup(student, predicate);
        return new CommandResult(
            String.format(MESSAGE_SUCCESS, studentName, studentId, model.getFilteredGroupList().get(0).getName()));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (obj instanceof AddStudentCommand) {
            AddStudentCommand other = (AddStudentCommand) obj;
            return studentName.equals(other.studentName) && studentId.equals(other.studentId)
                && predicate.equals(other.predicate);
        } else {
            return false;
        }
    }
}
