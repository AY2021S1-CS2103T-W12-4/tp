package seedu.address.model.group;

import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Group} matches the keyword given.
 */
public class GrpContainsKeywordPredicate implements Predicate<Group> {

    private final String keyword;

    public GrpContainsKeywordPredicate(String string) {
        this.keyword = string;
    }

    @Override
    public boolean test(Group group) {
        return StringUtil.containsWordIgnoreCase(group.getName(), keyword);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof GrpContainsKeywordPredicate // instanceof handles nulls
                && keyword.equals(((GrpContainsKeywordPredicate) other).keyword)); // state check
    }
}
