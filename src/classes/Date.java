package classes;
/**
 * This class implements the Date object.
 * The Date class represents a date on the calendar in the form M/D/YYYY.
 * Here, a Date can be compared, printed, and validated.
 *
 * @author Aaman Gafur, Jai Patel
 */
public class Date implements Comparable<Date>{
    public static final int QUADRENNIAL = 4;
    public static final int CENTENNIAL = 100;
    public static final int QUATERCENTENNIAL = 400;

    private int year;
    private int month;
    private int day;


    /**
     * Default constructor for a date object
     */
    public Date() {
        this.year = 2000;
        this.month = 1;
        this.day = 1;
    }

    /**
     * Parameterized Constructor with 3 parameters.
     * Overloaded constructor for Date.
     *
     * @param month the month of the date
     * @param day the day of the date
     * @param year the year of the date
     */
    public Date(int month, int day, int year) {
        this.month = month;
        this.day = day;
        this.year = year;
    }


    /**
     * Copy constructor to clone a Date object.
     * @param other the Date object to copy
     */
    public Date(Date other) {
        this.month = other.month;
        this.day = other.day;
        this.year = other.year;
    }


//GETTER METHODS

    /**
     * Getter for year.
     * @return the year of this date.
     */
    public int getYear() {
        return year;
    }

    /**
     * Getter for month.
     * @return the month of this date.
     */
    public int getMonth() {
        return month;
    }

    /**
     * Getter for day.
     * @return the day of this date.
     */
    public int getDay() {
        return day;
    }

    /**
     * Checks whether a date is equal to another date.
     * The dates are equal if year, month, and day all match.
     *
     * @param obj the object to compare with this date
     * @return true if obj is a Date with the
     * same year, month, and day and returns false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Date) {
            Date date = (Date) obj;
            return this.year == date.year && this.month == date.month && this.day == date.day;
        }
        return false;
    }

    /**
     * Compare two Date objects.
     * The comparison is done by year, then by month, then day.
     * @param other the Date object to compare with this date
     * @return return 1 if this date is later than the other date, return -1 if it is earlier
     *  and return 0 if they are the same date
     */
    @Override
    public int compareTo(Date other) {
        if (this.year > other.year) {
            return 1;
        }
        if (this.year < other.year) {
            return -1;
        }
        if (this.month > other.month) {
            return 1;
        }
        if (this.month < other.month) {
            return -1;
        }
        if (this.day > other.day) {
            return 1;
        }
        if (this.day < other.day) {
            return -1;
        }
        return 0;
    }

    /**
     * Return this date as a string in M/D/YYYY format.
     * @return the string representation of this date.
     */
    @Override
    public String toString() {
        return month + "/" + day + "/" + year;
    }


    /**
     * Checks if the given year is a leap year.
     *
     * @param year The year to check.
     * @return true if it is a leap year, false otherwise.
     */

    private boolean leapYear(int year) {
        return (year % QUATERCENTENNIAL == 0) || (year % QUADRENNIAL == 0) && (year % CENTENNIAL != 0);
    }

    /**
     * Validates if the date is a real calendar date.
     * Checks the month, day, and year using leap year rulings.
     *
     * @return true if the date is valid, false otherwise.
     */    public boolean isValid() {
        if (this.month < 1 || this.month > 12) {
            return false;
        }
        if (this.day < 1 || this.day > 31) {
            return false;
        }
        int[] days = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        if  (month == 2 && leapYear(year)) {
            days[1] = 29;
        }

        return day > 0 && day <= days[month-1];

    }


    public static void main(String[] args) {
        // isValid tests
        System.out.println(new Date(2, 29, 2024).isValid()); // true (leap year)
        System.out.println(new Date(2, 29, 2019).isValid()); // false
        System.out.println(new Date(6, 31, 2020).isValid()); // false
        System.out.println(new Date(0, 10, 2020).isValid()); // false
        System.out.println(new Date(12, 31, 2025).isValid()); // true

        // compareTo tests
        Date a = new Date(1, 1, 2020);
        Date b = new Date(1, 1, 2021);
        System.out.println(a.compareTo(b)); // -1
        System.out.println(b.compareTo(a)); // 1
        System.out.println(a.compareTo(new Date(1, 1, 2020))); // 0
    }








}

