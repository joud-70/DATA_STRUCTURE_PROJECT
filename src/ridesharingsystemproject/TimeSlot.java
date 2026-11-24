package ridesharingsystemproject;

public class TimeSlot implements ITimeSlot {

    private final IDateTime start;
    private final IDateTime end;

    // Creates a half-open interval [start, end); start must be before end
    public TimeSlot(IDateTime start, IDateTime end) {
        if (start == null || end == null || start.compareTo(end) >= 0) {
            throw new IllegalArgumentException("Start must be before end");
        }
        this.start = start;
        this.end = end;
    }

    @Override
    public IDateTime getStart() {
        return start;
    }

    @Override
    public IDateTime getEnd() {
        return end;
    }

    // -1: this ends at/before other starts, 1: this starts at/after other ends, 0: overlap
    @Override
    public int compareTo(ITimeSlot other) {
        if (this.end.compareTo(other.getStart()) <= 0) {
            return -1;
        }
        if (this.start.compareTo(other.getEnd()) >= 0) {
            return 1;
        }
        return 0;
    }

    @Override
    public String toString() {
        return "[" + start.format() + " - " + end.format() + ")";
    }
}
