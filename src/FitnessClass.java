import java.util.Comparator;
import java.util.Locale;

/**
 * Defines an object representing a single fitness class
 */
public class FitnessClass implements Comparable<FitnessClass> {
    // initialising instance variables with given types 
    private final int weeksAttendanceRecorded = 5;
    private String classId, nameOfClass, nameOfTutor;
    private int startTime;
    private int attendanceWeekOne, attendanceWeekTwo, attendanceWeekThree, attendanceWeekFour, attendanceWeekFive;

    /**
     * Optional default constructor
     */
    public FitnessClass() {
        this.classId = "defaultID";
        this.nameOfClass = "defaultName";
        this.nameOfTutor = "defaultTutor";
    }

    /**
     * non-default constructor receiving a String of class information as a parameter. It splits the string at white spaces.
     *
     * @param classInformation
     */
    public FitnessClass(String classInformation) {
        String classInformationArray[] = classInformation.split("\\s+");
        this.classId = classInformationArray[0];
        this.nameOfClass = classInformationArray[1];
        this.nameOfTutor = classInformationArray[2];
        this.startTime = Integer.parseInt(classInformationArray[3]);
    }

    //accessor and mutator methods to enable all instance variables to be accessed and given values
    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getNameOfClass() {
        return nameOfClass;
    }

    public void setNameOfClass(String nameOfClass) {
        this.nameOfClass = nameOfClass;
    }

    public String getNameOfTutor() {
        return nameOfTutor;
    }

    public void setNameOfTutor(String nameOfTutor) {
        this.nameOfTutor = nameOfTutor;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getAttendanceWeekOne() {
        return attendanceWeekOne;
    }

    public void setAttendanceWeekOne(int attendanceWeekOne) {
        this.attendanceWeekOne = attendanceWeekOne;
    }

    public int getAttendanceWeekTwo() {
        return attendanceWeekTwo;
    }

    public void setAttendanceWeekTwo(int attendanceWeekTwo) {
        this.attendanceWeekTwo = attendanceWeekTwo;
    }

    public int getAttendanceWeekThree() {
        return attendanceWeekThree;
    }

    public void setAttendanceWeekThree(int attendanceWeekThree) {
        this.attendanceWeekThree = attendanceWeekThree;
    }

    public int getAttendanceWeekFour() {
        return attendanceWeekFour;
    }

    public void setAttendanceWeekFour(int attendanceWeekFour) {
        this.attendanceWeekFour = attendanceWeekFour;
    }

    public int getAttendanceWeekFive() {
        return attendanceWeekFive;
    }

    public void setAttendanceWeekFive(int attendanceWeekFive) {
        this.attendanceWeekFive = attendanceWeekFive;
    }

    public int getWeeksAttendanceRecorded() {
        return weeksAttendanceRecorded;
    }

    /**
     * Calculates the average attendance in class based on attendances of each week.
     * Returns the average attendance of a class.
     * @return averageAttendanceOfClass
     */
    public double averageAttendanceOfClass() {
        double averageAttendanceOfClass = ((double) this.getAttendanceWeekOne() + this.getAttendanceWeekTwo() + this.getAttendanceWeekThree() + this.getAttendanceWeekFour() + this.getAttendanceWeekFive()) / (double) this.getWeeksAttendanceRecorded();
        return averageAttendanceOfClass;
    }

    /**
     * Builds String that is printed into the attendance report file.
     * Returns a String formatted appropriately for the attendance report.
     * Equivalent to one line in the attendance report
     * @return createAttendanceReport
     */
    public String createAttendanceReport() {
        StringBuilder lineAttendanceReportBuilder = new StringBuilder();
        lineAttendanceReportBuilder.append(String.format("%-10.10s", this.getClassId()));
        lineAttendanceReportBuilder.append(String.format("%-15.10s", this.getNameOfClass()));
        lineAttendanceReportBuilder.append(String.format("%-15.20s", this.getNameOfTutor()));
        lineAttendanceReportBuilder.append(String.format("%5.5s", this.getAttendanceWeekOne()));
        lineAttendanceReportBuilder.append(String.format("%5.5s", this.getAttendanceWeekTwo()));
        lineAttendanceReportBuilder.append(String.format("%5.5s", this.getAttendanceWeekThree()));
        lineAttendanceReportBuilder.append(String.format("%5.5s", this.getAttendanceWeekFour()));
        lineAttendanceReportBuilder.append(String.format("%5.5s", this.getAttendanceWeekFive()));
        lineAttendanceReportBuilder.append(String.format(Locale.ENGLISH, "%15.2f", this.averageAttendanceOfClass()));
        String lineAttendanceReport = lineAttendanceReportBuilder.toString();
        return lineAttendanceReport;
    }

    /**
     * Checks the average attendance of two FitnessClass objects returning in integer value depending which average attendance of two objects is higher/ lower.
     * Compares two FitnessClass objects appropriately on average attendance.
     * It orders the objects based on their average attendance in a decreasing order.
     */
    public int compareTo(FitnessClass other) {
        if (this.averageAttendanceOfClass() > other.averageAttendanceOfClass()) {
            return -1;
        } else if (this.averageAttendanceOfClass() < other.averageAttendanceOfClass()) {
            return 1;
        }
        return 0;
    }

    /**
     * Comparator to compare two FitnessClass objects with one another using the compareTo method of this class
     * Compares two FitnessClass objects appropriately on average attendance.
     */
    public static Comparator<FitnessClass> FitnessClassComparator = new Comparator<FitnessClass>() {
        public int compare(FitnessClass fitnessClassOne, FitnessClass fitnessClassTwo) {
            //testing if fitnessClassOne or fitnessClassTwo are null in order to sort them at the end of the array
            if (fitnessClassOne == null) {
                return 1;
            }
            if (fitnessClassTwo == null) {
                return -1;
            }
            //otherwise the two objects are compared with each other
            return fitnessClassOne.compareTo(fitnessClassTwo);
        }
    };
}
