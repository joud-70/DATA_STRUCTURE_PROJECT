package ridesharingsystemproject;

public class DateTime implements IDateTime {

    private final int year;
    private final int month;
    private final int day;
    private final int hour;
    private final int minute;

    // Constructor
    public DateTime(int year, int month, int day, int hour, int minute) {

        if (month < 1 || month > 12) {
            throw new IllegalArgumentException("Invalid month");
        }

        if (day < 1 || day > 31) {
            throw new IllegalArgumentException("Invalid day");
        }

        if (hour < 0 || hour > 23) {
            throw new IllegalArgumentException("Invalid hour");
        }

        if (minute < 0 || minute > 59) {
            throw new IllegalArgumentException("Invalid minute");
        }

        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
    }

    @Override
    public int getYear() {
        return year;
    }

    @Override
    public int getMonth() {
        return month;
    }

    @Override
    public int getDay() {
        return day;
    }

    @Override
    public int getHour() {
        return hour;
    }

    @Override
    public int getMinute() {
        return minute;
    }

    @Override
    public String format() {
        return String.format("%02d/%02d/%04d %02d:%02d",
                month, day, year, hour, minute);
    }

    @Override
    public int compareTo(IDateTime other) {

        int result = Integer.compare(this.year, other.getYear());
        if (result != 0) {
            return result;
        }

        result = Integer.compare(this.month, other.getMonth());
        if (result != 0) {
            return result;
        }

        result = Integer.compare(this.day, other.getDay());
        if (result != 0) {
            return result;
        }

        result = Integer.compare(this.hour, other.getHour());
        if (result != 0) {
            return result;
        }

        return Integer.compare(this.minute, other.getMinute());
    }

    @Override
    public String toString() {
        return format();
    }
}
