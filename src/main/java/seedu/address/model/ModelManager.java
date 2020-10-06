package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.group.Group;
import seedu.address.model.group.Lesson;
import seedu.address.model.group.Student;
import seedu.address.model.group.StudentInfo;
import seedu.address.model.group.UniqueLessonList;
import seedu.address.model.group.UniqueStudentInfoList;
import seedu.address.model.group.UniqueStudentList;
import seedu.address.model.person.Person;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {

    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;

    private final Serenity serenity;
    private final FilteredList<Group> filteredGroups;
    private final ArrayObservableList<Student> students;
    private final ArrayObservableList<Lesson> lessons;
    private final FilteredList<Lesson> filteredLessons;
    private final ArrayObservableList<StudentInfo> studentsInfo;

    /**
     * Initializes a ModelManager with the given addressBook, userPrefs and serenity.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs,
                        ReadOnlySerenity serenity) {
        super();
        requireAllNonNull(addressBook, userPrefs, serenity);

        logger.fine("Initializing with address book: " + addressBook
                + " and user prefs " + userPrefs
                + " and serenity " + serenity);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);

        this.serenity = new Serenity(serenity);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        filteredGroups = new FilteredList<>(this.serenity.getGroupList());
        students = new ArrayObservableList<>(new UniqueStudentList().asUnmodifiableObservableList());
        lessons = new ArrayObservableList<>(new UniqueLessonList().asUnmodifiableObservableList());
        filteredLessons = new FilteredList<>(new UniqueLessonList().asUnmodifiableObservableList());
        studentsInfo = new ArrayObservableList<>(new UniqueStudentInfoList().asUnmodifiableObservableList());
    }

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);

        this.serenity = new Serenity();
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        filteredGroups = new FilteredList<>(this.serenity.getGroupList());
        students = new ArrayObservableList<>(new UniqueStudentList().asUnmodifiableObservableList());
        lessons = new ArrayObservableList<>(new UniqueLessonList().asUnmodifiableObservableList());
        filteredLessons = new FilteredList<>(new UniqueLessonList().asUnmodifiableObservableList());
        studentsInfo = new ArrayObservableList<>(new UniqueStudentInfoList().asUnmodifiableObservableList());
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs(), new Serenity());
    }

    // =========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    // =========== AddressBook ================================================================================

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return addressBook.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        addressBook.removePerson(target);
    }

    @Override
    public void addPerson(Person person) {
        addressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);
        addressBook.setPerson(target, editedPerson);
    }

    // =========== Serenity ================================================================================

    @Override
    public Path getSerenityFilePath() {
        return userPrefs.getSerenityFilePath();
    }

    @Override
    public void setSerenityFilePath(Path serenityFilePath) {
        requireNonNull(serenityFilePath);
        userPrefs.setSerenityFilePath(serenityFilePath);
    }

    @Override
    public void setSerenity(ReadOnlySerenity serenity) {
        this.serenity.resetData(serenity);
    }

    @Override
    public ReadOnlySerenity getSerenity() {
        return serenity;
    }

    @Override
    public boolean hasGroup(Group group) {
        requireNonNull(group);
        return serenity.hasGroup(group);
    }

    @Override
    public void addGroup(Group group) {
        requireNonNull(group);
        serenity.addGroup(group);
    }

    @Override
    public void updateFilteredGroupList(Predicate<Group> predicate) {
        requireAllNonNull(predicate);
        this.filteredGroups.setPredicate(predicate);
        updateStudentList();
        updateLessonList();
    }

    @Override
    public void updateStudentList() {
        if (!filteredGroups.isEmpty()) {
            this.students.setAll(this.filteredGroups.get(0).getStudentsAsUnmodifiableObservableList());
        }
    }

    @Override
    public void updateLessonList() {
        if (!filteredGroups.isEmpty()) {
            this.lessons.setAll(this.filteredGroups.get(0).getLessonsAsUnmodifiableObservableList());
        }
    }

    @Override
    public void updateFilteredLessonList(Predicate<Lesson> predicate) {
        requireAllNonNull(predicate);
        this.filteredLessons.setPredicate(predicate);
        updateStudentInfoList();
    }

    @Override
    public void updateStudentInfoList() {
        if (!filteredGroups.isEmpty() || !lessons.isEmpty()) {
            this.studentsInfo.setAll(this.lessons.get(0).getStudentsInfoAsUnmodifiableObservableList());
        }
    }

    @Override
    public ObservableList<Group> getFilteredGroupList() {
        return filteredGroups;
    }

    @Override
    public ObservableList<Student> getStudentList() {
        return students;
    }

    @Override
    public ObservableList<Lesson> getLessonList() {
        return lessons;
    }

    @Override
    public ObservableList<Lesson> getFilteredLessonList() {
        return lessons;
    }

    @Override
    public ObservableList<StudentInfo> getStudentInfoList() {
        return studentsInfo;
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of {@code
     * versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return addressBook.equals(other.addressBook)
            && userPrefs.equals(other.userPrefs)
            && filteredPersons.equals(other.filteredPersons);
    }

}
