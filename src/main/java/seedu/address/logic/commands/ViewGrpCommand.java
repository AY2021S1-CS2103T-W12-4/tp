package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRP;

import seedu.address.commons.core.Messages;
import seedu.address.model.Model;
import seedu.address.model.group.GrpContainsKeywordPredicate;

/**
 * Finds and lists all students and lessons in the group specifeied. Keyword matching is case insensitive.
 */
public class ViewGrpCommand extends Command {

    public static final String COMMAND_WORD = "viewgrp";
    public static final Object MESSAGE_USAGE = COMMAND_WORD
            + ": Finds all students who are part of the specified group (case-insensitive) "
            + "and displays them as a list with index numbers.\n"
            + "Parameters: GROUP \n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_GRP + " G04";

    private final GrpContainsKeywordPredicate predicate;

    public ViewGrpCommand(GrpContainsKeywordPredicate predicate) {
        this.predicate = predicate;
    }

    private String getMessage(Model model) {
        return model.getFilteredGroupList().isEmpty()
                ? Messages.MESSAGE_GROUP_EMPTY
                : String.format(Messages.MESSAGE_GROUP_LISTED_OVERVIEW, model.getFilteredGroupList().get(0).getName());
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredGroupList(predicate);
        return new CommandResult(this.getMessage(model));
    }
}
