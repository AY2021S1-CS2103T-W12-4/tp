package team.serenity.logic.parser;

import static team.serenity.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static team.serenity.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import team.serenity.logic.commands.AddGrpCommand;
import team.serenity.logic.commands.AddLsnCommand;
import team.serenity.logic.commands.AddQnCommand;
import team.serenity.logic.commands.AddScoreCommand;
import team.serenity.logic.commands.AddStudentCommand;
import team.serenity.logic.commands.Command;
import team.serenity.logic.commands.DelGrpCommand;
import team.serenity.logic.commands.DelLsnCommand;
import team.serenity.logic.commands.DelQnCommand;
import team.serenity.logic.commands.DelStudentCommand;
import team.serenity.logic.commands.ExitCommand;
import team.serenity.logic.commands.HelpCommand;
import team.serenity.logic.commands.MarkAbsentCommand;
import team.serenity.logic.commands.MarkPresentCommand;
import team.serenity.logic.commands.ViewGrpCommand;
import team.serenity.logic.commands.ViewLsnCommand;
import team.serenity.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class SerenityParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT =
        Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case AddGrpCommand.COMMAND_WORD:
            return new AddGrpCommandParser().parse(arguments);

        case AddLsnCommand.COMMAND_WORD:
            return new AddLsnCommandParser().parse(arguments);

        case AddQnCommand.COMMAND_WORD:
            return new AddQnCommandParser().parse(arguments);

        case AddScoreCommand.COMMAND_WORD:
            return new AddScoreCommandParser().parse(arguments);

        case AddStudentCommand.COMMAND_WORD:
            return new AddStudentCommandParser().parse(arguments);

        case DelGrpCommand.COMMAND_WORD:
            return new DelGrpCommandParser().parse(arguments);

        case DelLsnCommand.COMMAND_WORD:
            return new DelLsnCommandParser().parse(arguments);

        case DelQnCommand.COMMAND_WORD:
            return new DelQnCommandParser().parse(arguments);

        case DelStudentCommand.COMMAND_WORD:
            return new DelStudentCommandParser().parse(arguments);

        case MarkAbsentCommand.COMMAND_WORD:
            return new MarkAbsentCommandParser().parse(arguments);

        case MarkPresentCommand.COMMAND_WORD:
            return new MarkPresentCommandParser().parse(arguments);

        case ViewGrpCommand.COMMAND_WORD:
            return new ViewGrpCommandParser().parse(arguments);

        case ViewLsnCommand.COMMAND_WORD:
            return new ViewLsnCommandParser().parse(arguments);

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }
}