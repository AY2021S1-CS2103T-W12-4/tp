package seedu.address.model.group;

/**
 * Represents a {@code Student} attendance in a Class
 */
public class Attendance {

    private boolean isPresent;
    private boolean isFlagged;

    /**
     * Creates a default Attendance object that is not flagged and not present
     */
    public Attendance() {
        this.isFlagged = false;
        this.isPresent = false;
    }

    @Override
    public String toString() {
        return Boolean.toString(isPresent);
    }

    public boolean getAttendance() {
        return isPresent;
    }

    public Attendance setAttendance(boolean isPresent) {
        this.isPresent = isPresent;
        return this;
    }

    public boolean getFlagged() {
        return isFlagged;
    }

    @Override
    public boolean equals(Object obj) {
        Attendance other = (Attendance) obj;
        return other.getAttendance() == getAttendance() && other.getFlagged() == getFlagged();
    }
}
