package team.serenity.logic.commands.student;

import static team.serenity.commons.core.Messages.MESSAGE_GROUP_EMPTY;
import static team.serenity.commons.core.Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX;
import static team.serenity.commons.core.Messages.MESSAGE_STUDENT_EMPTY;
import static team.serenity.commons.util.CollectionUtil.requireAllNonNull;
import static team.serenity.logic.parser.CliSyntax.PREFIX_GRP;
import static team.serenity.logic.parser.CliSyntax.PREFIX_MATRIC;
import static team.serenity.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.Optional;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import team.serenity.commons.core.index.Index;
import team.serenity.logic.commands.Command;
import team.serenity.logic.commands.CommandResult;
import team.serenity.logic.commands.exceptions.CommandException;
import team.serenity.model.Model;
import team.serenity.model.group.Group;
import team.serenity.model.group.student.Student;
import team.serenity.model.group.student.StudentName;
import team.serenity.model.group.student.StudentNumber;
import team.serenity.model.util.UniqueList;

public class DelStudentCommand extends Command {

    public static final String COMMAND_WORD = "delstudent";
    public static final String MESSAGE_SUCCESS = "You removed %s (%s) from tutorial group %s";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Deletes an existing student from the specified tutorial group. \n"
        + "Parameters (2 methods):\n"
        + "1. " + PREFIX_GRP + "GROUP_NAME " + PREFIX_NAME + "STUDENT_NAME " + PREFIX_MATRIC + "STUDENT_NUMBER\n"
        + "2. INDEX (must be a positive integer) " + PREFIX_GRP + "GROUP_NAME "
        + "Examples:\n"
        + "1. " + COMMAND_WORD + " " + PREFIX_GRP + "G01 " + PREFIX_NAME + "Aaron Tan " + PREFIX_MATRIC + "A0123456A\n"
        + "2. " + COMMAND_WORD + " 1 " + PREFIX_GRP + "G01";

    private Optional<StudentName> studentName;
    private Optional<StudentNumber> studentNumber;
    private Optional<Student> toDelete;
    private Optional<Index> index;
    private boolean isByIndex;
    private final Predicate<Group> predicate;

    /**
     * Creates a DelStudentCommand to remove the specified {@code Student}.
     * @param studentName Name of Student
     * @param studentNumber Id of Student
     * @param predicate Group predicate
     */
    public DelStudentCommand(StudentName studentName, StudentNumber studentNumber, Predicate<Group> predicate) {
        requireAllNonNull(studentName, studentNumber, predicate);
        this.studentName = Optional.ofNullable(studentName);
        this.studentNumber = Optional.ofNullable(studentNumber);
        this.predicate = predicate;
        this.index = Optional.empty();
        this.isByIndex = false;
    }

    /**
     * Creates a DelStudentCommand to remove the specified {@code Student} by index.
     * @param index Index of student in the list
     * @param predicate Group predicate
     */
    public DelStudentCommand(Index index, Predicate<Group> predicate) {
        requireAllNonNull(index, predicate);
        this.index = Optional.ofNullable(index);
        this.predicate = predicate;
        this.isByIndex = true;
        this.studentNumber = Optional.empty();
        this.studentName = Optional.empty();
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        model.updateFilteredGroupList(this.predicate);
        ObservableList<Group> groups = model.getFilteredGroupList();

        if (groups.isEmpty()) {
            //no such group
            throw new CommandException(MESSAGE_GROUP_EMPTY);
        }

        UniqueList<Student> uniqueStudentList = groups.get(0).getStudents();

        if (!isByIndex) {
            toDelete = Optional.ofNullable(new Student(this.studentName.get(), this.studentNumber.get()));
            if (!groups.get(0).getStudents().contains(toDelete.get())) {
                //student does not exist
                throw new CommandException(MESSAGE_STUDENT_EMPTY);
            }
        } else {
            if (index.get().getZeroBased() >= uniqueStudentList.size()) {
                throw new CommandException(
                        String.format(MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX, index.get().getOneBased()));
            }

            toDelete = Optional.ofNullable(uniqueStudentList.getList().get(index.get().getZeroBased()));
            if (!uniqueStudentList.contains(toDelete.get())) {
                //student does not exist
                throw new CommandException(MESSAGE_STUDENT_EMPTY);
            }
        }

        model.deleteStudentFromGroup(toDelete.get(), this.predicate);
        model.updateFilteredGroupList(this.predicate);
        return new CommandResult(
            String.format(MESSAGE_SUCCESS, toDelete.get().getStudentName(),
                toDelete.get().getStudentNo(),
                model.getFilteredGroupList().get(0).getGroupName()),
            CommandResult.UiAction.REFRESH_TABLE
        );
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof DelStudentCommand)) {
            return false;
        }

        DelStudentCommand other = (DelStudentCommand) obj;
        return this.studentName.equals(other.studentName)
                && this.studentNumber.equals(other.studentNumber)
                && this.index.equals(other.index)
                && this.predicate.equals(other.predicate);
    }

}
