//import com.sun.codemodel.internal.JOp;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.io.*;

/**
 * Defines a GUI that displays details of a FitnessProgram object
 * and contains buttons enabling access to the required functionality.
 */
public class SportsCentreGUI extends JFrame implements ActionListener {
	
	/** GUI JButtons */
	private JButton closeButton, attendanceButton;
	private JButton addButton, deleteButton;

	/** GUI JTextFields */
	private JTextField idIn, classIn, tutorIn;

	/** Display of class timetable */
	private JTextArea display;

	/** Display of attendance information */
	private ReportFrame report;

	/** Names of input text files */
	private final String classesInFile = "ClassesIn.txt";
	private final String classesOutFile = "ClassesOut.txt";
	private final String attendancesFile = "AttendancesIn.txt";
	
	/** Name of FitnessProgram object */
	private FitnessProgram newProgram;
	
	/**
	 * Constructor for AssEx3GUI class
	 */
	public SportsCentreGUI() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Boyd-Orr Sports Centre");
		setSize(700, 300);
		display = new JTextArea();
		display.setFont(new Font("Courier", Font.PLAIN, 14));
		add(display, BorderLayout.CENTER);
		layoutTop();
		layoutBottom();
		this.initLadiesDay();
		this.initAttendances();
		this.updateDisplay();
	}

	/**
	 * Creates the FitnessProgram list ordered by start time
	 * using data from the file ClassesIn.txt
	 */
	public void initLadiesDay() {
		FileReader filereader;
		Scanner scanner;
		newProgram = new FitnessProgram(); 
		
		try {
			filereader = new FileReader(classesInFile); //reading lines from ClassesIn.txt
			scanner = new Scanner (filereader);
			
			while (scanner.hasNextLine()) {
			String lineClassesFile = scanner.nextLine(); 
			newProgram.addClassesByTime(lineClassesFile); //calling the addClassesByTime method with one line from the file as a String as the parameter
			}
			
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "No file with classes found", "file error", JOptionPane.ERROR_MESSAGE); //error message when file was not found
			e.printStackTrace();
		}
	}

	/**
	 * Initialises the attendances using data
	 * from the file AttendancesIn.txt
	 */
	public void initAttendances() {
	    // your code here
		FileReader filereader;
		Scanner scanner;
		try {
			filereader = new FileReader(attendancesFile);
			scanner = new Scanner (filereader);
			
			while (scanner.hasNextLine()) {
			String lineAttendanceFile = scanner.nextLine();  //reading lines from AttendancesIn.txt
			newProgram.setAttendancesOfFitnessClasses(lineAttendanceFile); //calling the setAttendancesOfFitnessClasses with one line of the file as a String as the parameter.
			}
			
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "No file with attendances found", "file error", JOptionPane.ERROR_MESSAGE); //error message when file was not found
			e.printStackTrace();
		}
	}

	/**
	 * Instantiates timetable display and adds it to GUI
	 */
	public void updateDisplay() {
		StringBuilder timeTable = new StringBuilder(); 
		//rows of times. First row of the text area in the GUI.
		timeTable.append(String.format("%-13.10s", "9-10"));
		timeTable.append(String.format("%-13.10s", "10-11"));
		timeTable.append(String.format("%-13.10s", "11-12"));
		timeTable.append(String.format("%-13.10s", "12-13"));
		timeTable.append(String.format("%-13.10s", "13-14"));
		timeTable.append(String.format("%-13.10s", "14-15"));
		timeTable.append(String.format("%-13.10s", "15-16"));
		timeTable.append("\r\n");
		//getting every class in the array by time
		for (int i=9; i<16; i++) {
			if (newProgram.getClassAtTime(i) != null) {
				for (int j = 9; j < 16; j++) {
					if (newProgram.getClassAtTime(i).getStartTime() == j) {
						timeTable.append(String.format("%-13.10s", newProgram.getClassAtTime(i).getNameOfClass())); //appending it to the StringBuilder by time
					}
				}
			} else {
				timeTable.append(String.format("%-13.10s", "Available")); //if there is no object at an index, there is not class at the time. So it is printed "Available"
			}
			}
		timeTable.append("\r\n");
		//get name of tutors for each course in the FitnessProgram object + append it to the timeTable 
		for (int i=9; i<16; i++) {
			if (newProgram.getClassAtTime(i) != null) {
				for (int j = 9; j < 16; j++) {
					if (newProgram.getClassAtTime(i).getStartTime() == j) {
						timeTable.append(String.format("%-13.10s", newProgram.getClassAtTime(i).getNameOfTutor())); //appending it to the StringBuilder by time
					}
				}
			} else {
				timeTable.append(String.format("%-13.10s", ""));
			}
		}
		display.setText(timeTable.toString());  //change the StringBuilder into a String and set it as the text of the TextArea in the GUI 
	}

	/**
	 * adds buttons to top of GUI
	 */
	public void layoutTop() {
		JPanel top = new JPanel();
		closeButton = new JButton("Save and Exit"); 
		closeButton.addActionListener(this);
		top.add(closeButton);
		attendanceButton = new JButton("View Attendances");
		attendanceButton.addActionListener(this);
		top.add(attendanceButton);
		add(top, BorderLayout.NORTH);
	}

	/**
	 * adds labels, text fields and buttons to bottom of GUI
	 */
	public void layoutBottom() {
		// instantiate panel for bottom of display
		JPanel bottom = new JPanel(new GridLayout(3, 3));

		// add upper label, text field and button
		JLabel idLabel = new JLabel("Enter Class Id");
		bottom.add(idLabel);
		idIn = new JTextField();
		bottom.add(idIn);
		JPanel panel1 = new JPanel();
		addButton = new JButton("Add");
		addButton.addActionListener(this);
		panel1.add(addButton);
		bottom.add(panel1);

		// add middle label, text field and button
		JLabel nmeLabel = new JLabel("Enter Class Name");
		bottom.add(nmeLabel);
		classIn = new JTextField();
		bottom.add(classIn);
		JPanel panel2 = new JPanel();
		deleteButton = new JButton("Delete");
		deleteButton.addActionListener(this);
		panel2.add(deleteButton);
		bottom.add(panel2);

		// add lower label text field and button
		JLabel tutLabel = new JLabel("Enter Tutor Name");
		bottom.add(tutLabel);
		tutorIn = new JTextField();
		bottom.add(tutorIn);

		add(bottom, BorderLayout.SOUTH);
	}

	/**
	 * Processes adding a class
	 */
	public void processAdding() {
		String newClass = this.idIn.getText() + " " + this.classIn.getText() + " " + this.tutorIn.getText(); //checking if all input text fields contain content
		if (this.idIn.getText().equals("") || this.classIn.getText().equals("") || this.tutorIn.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "at least one input field is empty", "input error", JOptionPane.ERROR_MESSAGE);
		}
		else if (newProgram.getNumOfClassesInArray() == 7) { //checking of the number of classes in the array is seven (checking if list is already full)
			//error message when the number of classes in the array is already 7
			JOptionPane.showMessageDialog(null, "Sorry, the list is full!", "input error", JOptionPane.ERROR_MESSAGE);
		}
		else if (!newProgram.insertFitnessClass(this.idIn.getText(), newClass)) { //checking if the insertFitnessClass method returns false
			//error message when the insertFitnessClass method returns false which means that the ID put in already exists
			JOptionPane.showMessageDialog(null, "fitness class already exists", "input error", JOptionPane.ERROR_MESSAGE);
		}
		this.updateDisplay(); //updating the display if the insertFitnessClass method returned true
		//resetting the text field input
		this.idIn.setText("");
		this.classIn.setText("");
		this.tutorIn.setText("");
	}

	/**
	 * Processes deleting a class
	 */
	public void processDeletion() {
		if (this.idIn.getText().equals("")) { //checking if ID text field is empty
			JOptionPane.showMessageDialog(null, "class ID field is empty", "input error", JOptionPane.ERROR_MESSAGE);
		}
		else if(!newProgram.deleteClass(this.idIn.getText())) { //checking if the deleteClass method returns false
			JOptionPane.showMessageDialog(null, "fitness class doesn't exists", "input error", JOptionPane.ERROR_MESSAGE);
		}
		this.updateDisplay(); //updating the display if the deleteClass method returned true
		//resetting the text field input
		this.idIn.setText("");
		this.classIn.setText("");
		this.tutorIn.setText("");
	}

	/**
	 * Instantiates a new window and displays the attendance report
	 */
	public void displayReport() {
	    report = new ReportFrame(newProgram.copy()); //creating a new ReportFrame object giving a deep copy of the FitnessProgram object as a parameter
	    report.setVisible(true);
	    report.buildAttendanceReport();
	}

	/**
	 * Writes lines to file representing class name, 
	 * tutor and start time and then exits from the program
	 */
	public void processSaveAndClose() {
		try (FileWriter newOutput = new FileWriter(classesOutFile)) { //creating a new output file (ClassesOut.txt)
			newOutput.write(newProgram.getNewClassFile());
			newOutput.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Process button clicks.
	 * @param ae the ActionEvent
	 */
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == this.attendanceButton) {
			this.displayReport();
		} else if (ae.getSource() == this.addButton) {
			this.processAdding();
		} else if (ae.getSource() == this.deleteButton) {
			this.processDeletion();
		} else if (ae.getSource() == closeButton) {
			this.processSaveAndClose();
			System.exit(0);
		}
	}
}
