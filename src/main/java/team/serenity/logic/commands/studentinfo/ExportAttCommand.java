package team.serenity.logic.commands.studentinfo;

import static java.util.Objects.requireNonNull;
import static team.serenity.logic.parser.CliSyntax.PREFIX_GRP;

import team.serenity.logic.commands.Command;
import team.serenity.logic.commands.CommandResult;
import team.serenity.logic.commands.exceptions.CommandException;
import team.serenity.model.Model;
import team.serenity.model.group.Group;
import team.serenity.model.group.GroupContainsKeywordPredicate;

/**
 * Exports the attendance sheet of a tutorial group as XLSX file.
 */
public class ExportAttCommand extends Command {

    public static final String COMMAND_WORD = "exportatt";
    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Exports the attendance sheet of the specified tutorial group as a new excel file.\n"
        + "Parameters: "
        + PREFIX_GRP + "GROUP_NAME\n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_GRP + "G01";

    public static final String MESSAGE_SUCCESS =
        "Attendance sheet of tutorial group %s has been exported as %s_attendance.xlsx";
    public static final String MESSAGE_GROUP_DOES_NOT_EXIST = "Specified Tutorial Group does not exist!";

    private final GroupContainsKeywordPredicate grpPredicate;

    /**
     * Creates an ExportAttCommand to add the specified {@code Group}.
     */
    public ExportAttCommand(GroupContainsKeywordPredicate target) {
        this.grpPredicate = target;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        model.updateFilteredGroupList(this.grpPredicate);

        if (model.getFilteredGroupList().isEmpty()) {
            throw new CommandException(MESSAGE_GROUP_DOES_NOT_EXIST);
        }
        Group toExport = model.getFilteredGroupList().get(0);
        model.exportAttendance(toExport);
        return new CommandResult(String.format(MESSAGE_SUCCESS,
            toExport.getGroupName().toString(), toExport.getGroupName().toString()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof ExportAttCommand // instanceof handles nulls
            && this.grpPredicate.equals(((ExportAttCommand) other).grpPredicate));
    }

}
