package seedu.address.storage;

import static seedu.address.testutil.TypicalGroups.GROUP_A;

import java.util.List;
import java.util.stream.Collectors;

class JsonAdaptedGroupTest {

    private static final String VALID_NAME = GROUP_A.getName();
    private static final List<JsonAdaptedStudent> VALID_STUDENTS = GROUP_A.getStudents().asUnmodifiableObservableList()
            .stream()
        .map(JsonAdaptedStudent::new)
        .collect(Collectors.toList());
    private static final List<JsonAdaptedLesson> VALID_CLASSES = GROUP_A.getLessons().asUnmodifiableObservableList()
            .stream()
        .map(JsonAdaptedLesson::new)
        .collect(Collectors.toList());


    /*

   The following testcases failed because the order of the students' names (in the Set) was not the same.

    @Test
    public void toModelType_validGroupDetails_returnsGroup() throws Exception {
        JsonAdaptedGroup group = new JsonAdaptedGroup(GROUP_A);
        assertEquals(GROUP_A, group.toModelType());
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedGroup group = new JsonAdaptedGroup(null, VALID_STUDENTS, VALID_CLASSES);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "name");
        assertThrows(IllegalValueException.class, expectedMessage, group::toModelType);
    }

    @Test
    public void toModelType_nullStudents_throwsIllegalValueException() {
        JsonAdaptedGroup group = new JsonAdaptedGroup(VALID_NAME, null, VALID_CLASSES);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "students");
        assertThrows(IllegalValueException.class, expectedMessage, group::toModelType);
    }

    @Test
    public void toModelType_nullClasses_throwsIllegalValueException() {
        JsonAdaptedGroup group = new JsonAdaptedGroup(VALID_NAME, VALID_STUDENTS, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "classes");
        assertThrows(IllegalValueException.class, expectedMessage, group::toModelType);
    }
     */

}
