package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.group.Group;
import seedu.address.model.group.Lesson;
import seedu.address.model.group.Student;
import seedu.address.model.group.StudentInfo;
import seedu.address.model.person.Person;

/**
 * The API of the Model component.
 */
public interface Model {

    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;
    Predicate<Student> PREDICATE_SHOW_ALL_STUDENTS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /**
     * Returns the AddressBook
     */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasPerson(Person person);

    /**
     * Deletes the given person. The person must exist in the address book.
     */
    void deletePerson(Person target);

    /**
     * Adds the given person. {@code person} must not already exist in the address book.
     */
    void addPerson(Person person);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}. {@code target} must exist in the address
     * book. The person identity of {@code editedPerson} must not be the same as another existing person in the address
     * book.
     */
    void setPerson(Person target, Person editedPerson);

    /**
     * Returns an unmodifiable view of the filtered person list
     */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    // ========== Serenity ==========

    /**
     * Returns the user prefs' serenity file path.
     */
    Path getSerenityFilePath();

    /**
     * Sets the user prefs' serenity file path.
     */
    void setSerenityFilePath(Path serenityFilePath);

    /**
     * Replaces serenity data with the data in {@code serenity}.
     */
    void setSerenity(ReadOnlySerenity serenity);

    /**
     * Returns the Serenity
     */
    ReadOnlySerenity getSerenity();

    /**
     * Returns true if a group with the same identity as {@code group} exists in serenity.
     */
    boolean hasGroup(Group group);

    /**
     * Adds the given group. {@code group} must not already exist in serenity.
     */
    void addGroup(Group group);

    /**
     * Updates the filter of the filtered group list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredGroupList(Predicate<Group> predicate);

    /**
     * Updates the student list when changing to another group of interest.
     */
    void updateStudentList();

    /**
     * Updates the lesson list to filter when changing to another group of interest.
     */
    void updateLessonList();

    /**
     * Updates the filter of the filtered lesson list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredLessonList(Predicate<Lesson> predicate);

    /**
     * Updates the student info list to filter when changing to another lesson of interest.
     */
    void updateStudentInfoList();

    /**
     * Returns an unmodifiable view of the filtered group list.
     */
    ObservableList<Group> getFilteredGroupList();

    /**
     * Returns an unmodifiable view of the student list.
     */
    ObservableList<Student> getStudentList();

    /**
     * Returns an unmodifiable view of the lesson list.
     */
    ObservableList<Lesson> getLessonList();

    /**
     * Returns an unmodifiable view of the filtered lesson list.
     */
    ObservableList<Lesson> getFilteredLessonList();

    /**
     * Returns an unmodifiable view of the student info list
     */
    ObservableList<StudentInfo> getStudentInfoList();

}
