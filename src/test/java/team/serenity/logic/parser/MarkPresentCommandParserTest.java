package team.serenity.logic.parser;

import org.junit.jupiter.api.Test;

import team.serenity.logic.parser.attendance.MarkPresentCommandParser;

class MarkPresentCommandParserTest {

    private MarkPresentCommandParser parser = new MarkPresentCommandParser();

    @Test
    public void execute_missingStudentName_throwsParseException() {

    }

    @Test
    public void execute_missingStudentId_throwsCommandException() {

    }

    @Test
    public void execute_invalidParameter_throwsCommandException() {

    }

    @Test
    public void parse_validArgs_returnsMarkPresentCommand() {

    }

    @Test
    public void parse_invalidArgs_throwsParseException() {

    }
}
