package seedu.address.model.group;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalStudent.JAMES;
import static seedu.address.testutil.TypicalStudent.JOHN;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;


public class LessonTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Lesson(null, null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        UniqueStudentInfoList studentsInfo = new UniqueStudentInfoList();
        StudentInfo studentInfo = new StudentInfo(JOHN, new Participation(), new Attendance());
        studentsInfo.add(studentInfo);
        assertThrows(IllegalArgumentException.class, () -> new Lesson(invalidName, studentsInfo));
    }

    @Test
    public void constructor_emptyClass_throwsIllegalArgumentException() {
        String name = "1-1";
        Set<Student> students = new HashSet<>();
        assertThrows(IllegalArgumentException.class, () -> new Lesson(name, new UniqueStudentInfoList()));
    }

    @Test
    public void equals() {
        UniqueStudentInfoList studentsInfo = new UniqueStudentInfoList();
        StudentInfo studentInfo = new StudentInfo(JOHN);
        studentsInfo.add(studentInfo);
        Lesson oneOne = new Lesson("1-1", studentsInfo);
        Lesson oneOneClone = new Lesson("1-1", studentsInfo);
        Lesson classTwoOne = new Lesson("2-1", studentsInfo); //same students, different name
        UniqueStudentInfoList newStudentsInfo = new UniqueStudentInfoList();
        newStudentsInfo.add(new StudentInfo(JOHN));
        newStudentsInfo.add(new StudentInfo(JAMES));
        Lesson oneOneWithTwoStudents = new Lesson("1-1", newStudentsInfo);
        Lesson twoOneWithTwoStudents = new Lesson("2-1", newStudentsInfo);
        assertTrue(oneOneClone.equals(oneOne)); //same
        assertFalse(oneOneWithTwoStudents.equals(oneOne)); //same class name, different students
        assertFalse(classTwoOne.equals(oneOne)); //different class name, same students
        assertFalse(twoOneWithTwoStudents.equals(oneOne)); //different class name, different students
    }
}
