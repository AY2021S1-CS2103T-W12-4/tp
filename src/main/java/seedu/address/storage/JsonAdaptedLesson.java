package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.model.group.Lesson;

/**
 * Jackson-friendly version of {@link Lesson}.
 */
class JsonAdaptedLesson {

    private final String name;

    /**
     * Constructs a {@code JsonAdaptedLesson} with the given {@code name}.
     */
    @JsonCreator
    public JsonAdaptedLesson(String name) {
        this.name = name;
    }

    /**
     * Converts a given {@code Lesson} into this Lesson for Jackson use.
     */
    public JsonAdaptedLesson(Lesson source) {
        name = source.getName();
    }

    @JsonValue
    public String getName() {
        return name;
    }
}
