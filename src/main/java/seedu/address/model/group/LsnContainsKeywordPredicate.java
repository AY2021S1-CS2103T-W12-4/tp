package seedu.address.model.group;

import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Lesson} matches the keyword given.
 */
public class LsnContainsKeywordPredicate implements Predicate<Lesson> {

    private final String keyword;

    public LsnContainsKeywordPredicate(String string) {
        this.keyword = string;
    }

    @Override
    public boolean test(Lesson lesson) {
        return StringUtil.containsWordIgnoreCase(lesson.getName(), keyword);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LsnContainsKeywordPredicate // instanceof handles nulls
                && keyword.equals(((LsnContainsKeywordPredicate) other).keyword)); // state check
    }
}
