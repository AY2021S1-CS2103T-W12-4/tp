package seedu.address.logic;

import java.nio.file.Path;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlySerenity;
import seedu.address.model.group.Group;
import seedu.address.model.group.Lesson;
import seedu.address.model.group.Question;
import seedu.address.model.group.Student;
import seedu.address.model.group.StudentInfo;
import seedu.address.model.person.Person;

/**
 * API of the Logic component.
 */
public interface Logic {

    /**
     * Executes the command and returns the result.
     *
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException   If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Returns the AddressBook.
     *
     * @see seedu.address.model.Model#getAddressBook()
     */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns an unmodifiable view of the filtered list of persons.
     */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the Serenity object.
     *
     * @see seedu.address.model.Model#getSerenity()
     */
    ReadOnlySerenity getSerenity();

    /**
     * Returns an unmodifiable view of the filtered list of groups.
     */
    ObservableList<Group> getFilteredGroupList();

    /**
     * Returns an unmodifiable view of the filtered list of students from a group.
     */
    ObservableList<Student> getStudentList();

    /**
     * Returns an unmodifiable view of the filtered list of lesson from a group.
     */
    ObservableList<Lesson> getLessonList();

    /**
     * Returns an unmodifiable view of the list of students info from a group-lesson.
     */
    ObservableList<StudentInfo> getStudentInfoList();

    /**
     * Returns an unmodifiable view of the list of questions from a group-lesson.
     */
    ObservableList<Question> getQuestionList();

    /**
     * Returns the user prefs' serenity file path.
     */
    Path getSerenityFilePath();
}
