package team.serenity.logic.commands.studentinfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import team.serenity.model.group.Group;
import team.serenity.model.group.GroupLessonKey;
import team.serenity.model.group.UniqueGroupList;
import team.serenity.model.group.lesson.Lesson;
import team.serenity.model.group.lesson.UniqueLessonList;
import team.serenity.model.group.student.Student;
import team.serenity.model.group.studentinfo.StudentInfo;
import team.serenity.model.util.UniqueList;
import team.serenity.testutil.GroupBuilder;
import team.serenity.testutil.LessonBuilder;
import team.serenity.testutil.ModelStub;

class MarkAbsentCommandTest {

    @Test
    public void constructor_nullGroup_throwsNullPointerException() {

    }

    @Test
    public void execute_unmarkStudent_success() {
    }

    @Test
    public void execute_wrongName_throwsCommandException() {

    }

    @Test
    public void execute_wrongStudentId_throwsCommandException() {

    }

    @Test
    public void execute_missingStudentName_throwsCommandException() {

    }

    @Test
    public void execute_missingStudentId_throwsCommandException() {

    }

}

/**
 * A Model stub containing absent students
 */
class ModelStubWithStudentsPresent extends ModelStub {
    private Group uniqueGroup;
    private Lesson uniqueLesson;


    @Override
    public ObservableList<Group> getFilteredGroupList() {
        List<Group> grpList = new ArrayList<>();
        this.uniqueGroup = new GroupBuilder().build();
        grpList.add(uniqueGroup);
        UniqueList<Group> groupUniqueList = new UniqueGroupList();
        groupUniqueList.setElementsWithList(grpList);
        return groupUniqueList.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Lesson> getFilteredLessonList() {
        List<Lesson> lsnList = new ArrayList<>();
        this.uniqueLesson = new LessonBuilder().build();
        lsnList.add(uniqueLesson);
        UniqueList<Lesson> lessonUniqueList = new UniqueLessonList();
        lessonUniqueList.setElementsWithList(lsnList);
        return lessonUniqueList.asUnmodifiableObservableList();
    }

    @Override
    public UniqueList<StudentInfo> getListOfStudentsInfoFromGroupAndLesson(Group group, Lesson lesson) {
        GroupLessonKey key = new GroupLessonKey(group.getGroupName(), lesson.getLessonName());
        GroupLessonKey mapKey = new GroupLessonKey(uniqueGroup.getGroupName(), uniqueLesson.getLessonName());
        Map<GroupLessonKey, UniqueList<StudentInfo>> uniqueStudentInfoList = new HashMap<>();
        uniqueStudentInfoList.put(mapKey, uniqueLesson.getStudentsInfo());
        return uniqueStudentInfoList.get(key);
    }

    @Override
    public void updateStudentsInfoList() {
        return;
    }

    @Override
    public void updateLessonList() {
        return;
    }

}

/**
 * A Model stub containing an absent student
 */
class ModelStubWithIndexPresent extends ModelStub {
    private Group uniqueGroup;
    private Lesson uniqueLesson;

    @Override
    public ObservableList<Group> getFilteredGroupList() {
        List<Group> grpList = new ArrayList<>();
        this.uniqueGroup = new GroupBuilder().withName("G01")
                .withStudents(new Student("Aaron Tan", "A0123456U"))
                .withClasses("1-1").build();
        grpList.add(uniqueGroup);
        UniqueList<Group> groupUniqueList = new UniqueGroupList();
        groupUniqueList.setElementsWithList(grpList);
        return groupUniqueList.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Lesson> getFilteredLessonList() {
        List<Lesson> lsnList = new ArrayList<>();
        this.uniqueLesson = new LessonBuilder()
                .withName("1-1")
                .withStudentInfos(new StudentInfo(new Student("Aaron Tan", "A0123456U")))
                .build();
        lsnList.add(uniqueLesson);
        UniqueList<Lesson> lessonUniqueList = new UniqueLessonList();
        lessonUniqueList.setElementsWithList(lsnList);
        return lessonUniqueList.asUnmodifiableObservableList();
    }

    @Override
    public UniqueList<StudentInfo> getListOfStudentsInfoFromGroupAndLesson(Group group, Lesson lesson) {
        GroupLessonKey key = new GroupLessonKey(group.getGroupName(), lesson.getLessonName());
        GroupLessonKey mapKey = new GroupLessonKey(uniqueGroup.getGroupName(), uniqueLesson.getLessonName());
        Map<GroupLessonKey, UniqueList<StudentInfo>> uniqueStudentInfoList = new HashMap<>();
        uniqueStudentInfoList.put(mapKey, uniqueLesson.getStudentsInfo());
        return uniqueStudentInfoList.get(key);
    }

    @Override
    public void updateLessonList() {
        return;
    }

    @Override
    public void updateStudentsInfoList() {
        return;
    }

}

