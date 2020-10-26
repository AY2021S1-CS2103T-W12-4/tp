package team.serenity.model.group;

/**
 * Wraps a Group and a Lesson to form a key for a Hashmap.
 */
public class GroupLessonKey {
    private final Group group;
    private final Lesson lesson;

    /**
     * Instantiates a GroupLessonKey object.
     * @param group
     * @param lesson
     */
    public GroupLessonKey(Group group, Lesson lesson) {
        this.group = group;
        this.lesson = lesson;
    }

    /**
     * Generates a hashcode for the object.
     * Since Groups and Lessons are unique,
     * concatenating the group and lesson, then hashing it would give a good hash function.
     * @return hash code
     */
    @Override
    public int hashCode() {
        String groupName = this.group.getName();
        String lessonName = this.lesson.getName();
        String cocatenated = groupName + lessonName;
        return cocatenated.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this
                || (obj instanceof GroupLessonKey
                && this.group.equals(((GroupLessonKey) obj).group)
                && this.lesson.equals(((GroupLessonKey) obj).lesson));
    }
}
