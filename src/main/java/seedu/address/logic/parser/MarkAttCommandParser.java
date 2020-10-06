package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT;

import java.util.Optional;
import java.util.stream.Stream;

import seedu.address.logic.commands.MarkAttCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.group.Student;

/**
 * Parses input arguments and creates a new MarkAttCommand object.
 * Current support:
 * markatt Name grp/GROUP lsn/LESSON
 * markatt all grp/GROUP lsn/LESSON
 *
 */
public class MarkAttCommandParser implements Parser<MarkAttCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the MarkAttCommand and
     * returns a MarkAttCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public MarkAttCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer
                        .tokenize(args, PREFIX_STUDENT, PREFIX_ID);

        String studentName;
        String studentNumber;
        Optional<Student> student;
        Optional<String> keyToAll = Optional.ofNullable(argMultimap.getPreamble());

        if (argMultimap.getValue(PREFIX_STUDENT).isPresent() && argMultimap.getValue(PREFIX_ID).isPresent()) {

            // If single student specified, get student
            studentName = SerenityParserUtil.parseStudent(argMultimap.getValue(PREFIX_STUDENT).get());
            studentNumber = SerenityParserUtil.parseStudent(argMultimap.getValue(PREFIX_ID).get());
            student = Optional.ofNullable(new Student(studentName, studentNumber));

            return new MarkAttCommand(student.get());

        } else if (keyToAll.get() == "all") {

            // mark attendance of all students
            return new MarkAttCommand();

        } else {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkAttCommand.MESSAGE_USAGE));
        }

    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given {@code
     * ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
