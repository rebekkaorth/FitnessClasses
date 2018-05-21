import java.awt.*;
import java.util.Locale;
import javax.swing.*;

/**
 * Class to define window in which attendance report is displayed.
 */
public class ReportFrame extends JFrame {
	private FitnessProgram latestFitnessProgram;
	private JTextArea attendanceReport;
	private StringBuilder attendanceReportContent;

	/**
	 * Constructor with a FitnessProgram parameter used to initialise the FitnessProgram instance variable and
	 * add the JTextArea component to the window.
	 * @param latestFitnessProgram
	 * @throws HeadlessException
	 */
	public ReportFrame(FitnessProgram latestFitnessProgram) throws HeadlessException {
		super();
		setTitle("Boyd-Orr Sports Centre");
		setSize(700, 300);
		setLocation(800, 0);
		this.latestFitnessProgram = latestFitnessProgram;
		attendanceReportContent = new StringBuilder();
		attendanceReport = new JTextArea();
		attendanceReport.setFont(new Font("Courier", Font.PLAIN, 14));
		add(attendanceReport, BorderLayout.CENTER);
		attendanceReport.setEditable(false);
	}

	/**
	 *create first two lines of attendance report using a StringBuilder object
	 */
	private void attendanceReportDefaultSetting() {
		//first row of report = column headings
		attendanceReportContent.append(String.format("%-10.10s", "Id"));
		attendanceReportContent.append(String.format("%-15.10s", "Class"));
		attendanceReportContent.append(String.format("%-20.20s", "Tutor"));
		attendanceReportContent.append(String.format("%-20.20s", "Attendances"));
		attendanceReportContent.append(String.format("%-25.20s", "Average Attendance"));
		attendanceReportContent.append("\r\n");
		//line of = as line separator
		for (int i = 0; i < 83; i++) {
			attendanceReportContent.append("=");
		}
		attendanceReportContent.append("\r\n");
	}

	//build report for display in the JTextArea

	/**
	 *creating the attendance report by appending all FitnessClass object information to the StringBuilder.
	 * Builds the report for display on the JTextArea.
	 */
	public void buildAttendanceReport() {
		this.attendanceReportDefaultSetting();
		latestFitnessProgram.sortArrayByAttendance();
		for (int i = 0; i < latestFitnessProgram.getNumOfClassesInArray(); i++) {
			attendanceReportContent.append(latestFitnessProgram.getFitnessClassAtIndex(i).createAttendanceReport()); //creating the line for the report for every object in the array
			attendanceReportContent.append("\r\n");
		}
		attendanceReportContent.append("\r\n");
		attendanceReportContent.append(String.format("%60.20s", "Overall average: "));
		attendanceReportContent.append(String.format(Locale.ENGLISH, "%.2f", latestFitnessProgram.getOverallAvgAttendance())); //creating the line with the overall average attendance
		String reportContent = attendanceReportContent.toString();
		attendanceReport.setText(reportContent);
	}
}


