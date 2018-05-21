import java.util.*;

/**
 * Maintains a list of Fitness Class objects
 * The list is initialised in order of start time
 * The methods allow objects to be added and deleted from the list
 * In addition an array can be returned in order of average attendance
 */
public class FitnessProgram {

	private final int maxNumOfClasses;
	private FitnessClass [] listOfFitnessClasses;
	private int numOfClassesInArray;
	
	/**
	 * Default constructor initialising the array of FitnessClass objects 
	 */
	public FitnessProgram () {
		maxNumOfClasses = 7; 
		listOfFitnessClasses = new FitnessClass [maxNumOfClasses];
		numOfClassesInArray = 0;
	}
	
	/**
	 * Gets a String with all information of one FitnessClass and adds them to the array according to the time. It splits the String at white spaces
	 * and creates an FitnessClass object. It inserts this object at the index of the array at: time of the class - 9 which means that entry X of the array contains
	 * the FitnessClass object with a starting time 9+X.
	 * @param lineClassesFile
	 */
	public void addClassesByTime (String lineClassesFile) {
		String oneClassAsArray [] = lineClassesFile.split("\\s+");
		int timeOfClass = Integer.parseInt(oneClassAsArray[3]);
		listOfFitnessClasses[(timeOfClass-9)] = new FitnessClass(lineClassesFile);
		numOfClassesInArray++; 
	}
	
	/**
	 * gets the number of FitnessClass objects stored in the fitnessClassArray array
	 * @return numOfClassesInArray
	 */
	public int getNumOfClassesInArray () {
		return this.numOfClassesInArray;
	}

	/**
	 * increases numOfClassesInArray by one.
	 */
	private void increaseNumOfClassesInArray () {
		this.numOfClassesInArray++;
	}

	/**
	 * decreases numOfClassesInArray by one.
	 */
	private void decreaseNumOfClassesInArray () {
		this.numOfClassesInArray--;
	}

	/**
	 * Populates the attendance lists for a given FitnessClass object in the array. It is given a String representing a single line
	 * of the Attendances.txt as a parameter.
	 * It assigns values of attendances for each week, using lines of the Attendance.txt file based on the ID given in the file.
	 * @param lineAttendanceFile
	 */
	public void setAttendancesOfFitnessClasses (String lineAttendanceFile) {
		String attendancesOfClass [] = lineAttendanceFile.split("\\s+");
		FitnessClass classInArray = this.getClassObject(this.getAttendancesOfClassInAttendanceFile(attendancesOfClass));
		classInArray.setAttendanceWeekOne(Integer.parseInt(attendancesOfClass[1]));
		classInArray.setAttendanceWeekTwo(Integer.parseInt(attendancesOfClass[2]));
		classInArray.setAttendanceWeekThree(Integer.parseInt(attendancesOfClass[3]));
		classInArray.setAttendanceWeekFour(Integer.parseInt(attendancesOfClass[4]));
		classInArray.setAttendanceWeekFive(Integer.parseInt(attendancesOfClass[5]));
	}

	/**
	 * Gets the attendancesOfClasses array and gets the String at index 0 (= id of the fitness class)
	 * @param attendancesOfClass
	 * @return idOfClassInFile
	 */
	private String getAttendancesOfClassInAttendanceFile (String[] attendancesOfClass) {
		String idOfClassInFile = attendancesOfClass[0];
		return idOfClassInFile; 
	}

	/**
	 *Gets the id of a class in the Attendance.txt file and returns the index of the FitnessClass object with the same Id.
	 * Returns a FitnessClass object with a given ID number in the array (or null if not present).
	 * @param idOfClassInFile
	 * @return listOfFitnessClasses[i]
	 */
	private FitnessClass getClassObject (String idOfClassInFile) {
		for (int i=0; i<maxNumOfClasses; i++) {
			if (listOfFitnessClasses[i] != null && listOfFitnessClasses[i].getClassId().equals(idOfClassInFile)) {
				    return listOfFitnessClasses[i];
			}
		} 
		return null;
	}

	/**
	 * Get the FitnessClass object at a given index
	 * @param index
	 * @return listOfFitnessClasses[index]
	 */
	public FitnessClass getFitnessClassAtIndex (int index) {
		return listOfFitnessClasses[index];
	}

	/**
	 * sorting the array of FitnessClass objects based on the average attendance of each class using the comparator initialised
	 * in the FitnessClass class
	 */
	public FitnessClass [] sortArrayByAttendance () {
		Arrays.sort(listOfFitnessClasses, FitnessClass.FitnessClassComparator);
		return listOfFitnessClasses;
	}

	/**
	 * calculate the overall average attendance of all fitness classes by summing the average attendance of each class and dividing it by the number
	 * of fitness classes in the array
	 * @return overallAvgAttendance
	 */
	public double getOverallAvgAttendance () {
		double attendanceAllClasses = 0.0;
		for (int i=0; i<this.getNumOfClassesInArray(); i++) {
				attendanceAllClasses += listOfFitnessClasses[i].averageAttendanceOfClass(); //adds all average attendances of all FitnessClass objects in the array
		}
		double overallAvgAttendance = attendanceAllClasses/ this.getNumOfClassesInArray(); //divides all average attendances by the number of objects in the array
		return overallAvgAttendance;
	}

	/**
	 * Gets a FitnessClass object based on a given time. Uses the time to get the index of the fitness class.
	 * Returns a FitnessClass starting at a particular time (or null if no such class exists)
	 * @param timeOfClass
	 * @return
	 */
	public FitnessClass getClassAtTime(int timeOfClass) {
		for (int i=0; i<maxNumOfClasses; i++) {
			if(listOfFitnessClasses[i] != null) {
				if (listOfFitnessClasses[i].getStartTime() == timeOfClass) { //checks which object in the array has the given startTime
					return listOfFitnessClasses[i];
				}
			}
		}
		return null;
	}

	/**
	 * Gets the first available time for a new fitness class.
	 * Returns first start time that is available for a class.
	 * @return firstAvailableTime
	 */
	 private int getFirstAvailableTime () {
	 	int firstAvailableTime = 9;
	 		for(int i=0; i<maxNumOfClasses;i++) {
				if (listOfFitnessClasses[i] != null && listOfFitnessClasses[i].getStartTime() <= firstAvailableTime) { //checks for the smallest startTime of a class
					firstAvailableTime++;
				}
		}
		return firstAvailableTime;
	 }

	/**
	 * checks if a given Id is the Id in a FitnessClass object in the FitnessProgram array.
	 * The method returns true when the Id already exists and false when the Id does not exists.
	 * @param idToCheck
	 * @return true
	 */
	private boolean checkForId (String idToCheck) {
		for(int i=0; i<maxNumOfClasses; i++) {
			if (listOfFitnessClasses[i] != null && listOfFitnessClasses[i].getClassId().equals(idToCheck)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Inserts a new FitnessClass object into the FitnessProgram array by first checking
	 * if the given ID already exists or not. It then initialises a new FitnessClass object
	 * and inserts it at the next available index that equals null (which means that there is
	 * no other object at that index and therefore no fitness class.
	 * @param newClassId
	 * @param newClassInfo
	 * @return true
	 */
	public boolean insertFitnessClass (String newClassId, String newClassInfo) {
		int timeOfNewFitnessClass = this.getFirstAvailableTime();
		if(!checkForId(newClassId)) { //checking if the class ID is not the same as the ID of one of the fitness classes in the array
			for(int i=0; i<maxNumOfClasses; i++) {
				if (listOfFitnessClasses[i] == null) { //to get a "free" spot in the array + to insert new class at the first available spot the first null space in the array is used
					String newFitnessClass = newClassInfo + " " + (timeOfNewFitnessClass); //building the String given to the FitnessClass object as an parameter
					listOfFitnessClasses[i] = new FitnessClass(newFitnessClass); //initialising a new FitnessClass object at the first index in the array that is null
					this.increaseNumOfClassesInArray();
					return true; //returning true if adding of the new fitness class was successful
				}
			}
		}
		return false; //returning false if the adding of the new fitness class was not successful
	}

	/**
	 * Deletes a FitnessClass object in the FitnessProgram array based on a given Id.
	 * It first checks if Id exists and gets the index of the FitnessClass object with the given Id.
	 * It then sets the space in the array at that index to null and therefore deletes the object from the array.
	 * @param classId
	 * @return true
	 */
	public boolean deleteClass (String classId) {
		if (checkForId(classId) == true) { //checking if the class ID is the same as the ID of one of the fitness classes in the array
			for(int i=0; i<maxNumOfClasses; i++) {
				if (listOfFitnessClasses[i] != null && listOfFitnessClasses[i].getClassId().equals(classId)) {
					listOfFitnessClasses[i] = null; //setting the FitnessClass object at index i to null if its ID equals the given ID
					this.decreaseNumOfClassesInArray(); //decreasing the number of classes in the array by one
					return true; //returning true if deletion was successful
				}
			}
		}
		return false; //returning false if deletion was unsuccessful
	}

	/**
	 * Creates a StringBuilder object and appends all information (Class ID, Name of Class, Name of Tutor, Start Time)
	 * of all FitnessClass objects of the FitnessProgram array.
	 * @return newFile.toString()
	 */
	public String getNewClassFile() {
		StringBuilder newFile = new StringBuilder();
		//getting the class ID, name of the class, name of the tutor as well as the starting time of each class then used in the ClassOut.txt file
		for (int i=0; i<this.maxNumOfClasses; i++) {
			if(listOfFitnessClasses[i] != null) {
				StringBuilder newLine = new StringBuilder();
				newLine.append(listOfFitnessClasses[i].getClassId());
				newLine.append(" ");
				newLine.append(listOfFitnessClasses[i].getNameOfClass());
				newLine.append(" ");
				newLine.append(listOfFitnessClasses[i].getNameOfTutor());
				newLine.append(" ");
				newLine.append(listOfFitnessClasses[i].getStartTime());
				newFile.append(newLine);
				newFile.append("\r\n");
			}
		}
		return newFile.toString();
	}

	/**
	 * Getter for the FitnessClass array.
	 * @return listOfFitnessClasses
	 */
	private FitnessClass[] getListOfFitnessClasses() {
		return listOfFitnessClasses;
	}

	/**
	 * Setter for the FitnessClass array.
	 * @param listOfFitnessClasses
	 */

	private void setListOfFitnessClasses(FitnessClass[] listOfFitnessClasses) {
		this.listOfFitnessClasses = listOfFitnessClasses;
	}

	/**
	 * Setter for the number of FitnessClass objects in the FitnessClass array.
	 * @param numOfClassesInArray
	 */
	private void setNumOfClassesInArray (int numOfClassesInArray) {
		this.numOfClassesInArray = numOfClassesInArray;
	}

	/**
	 * Method to make a deep copy of the FitnessClass array. It copies all objects in the array as well as their attendances for week one to five.
	 * This deep copy is used in the ReportFrame, where the deep copy is ordered by attendances and printed into the attendances report.
	 * @return fitnessProgramClone
	 */
	public FitnessProgram copy(){
		FitnessProgram fitnessProgramClone = new FitnessProgram();
		fitnessProgramClone.setListOfFitnessClasses(this.getListOfFitnessClasses().clone()); //cloning the original array
		fitnessProgramClone.setNumOfClassesInArray(this.getNumOfClassesInArray());
		for(int i=0; i<maxNumOfClasses; i++) {
			if (fitnessProgramClone.listOfFitnessClasses[i] != null) { //setting the attendances of the FitnessClass objects in the copied array to the attendances of the original
				fitnessProgramClone.listOfFitnessClasses[i].setAttendanceWeekOne(this.listOfFitnessClasses[i].getAttendanceWeekOne());
				fitnessProgramClone.listOfFitnessClasses[i].setAttendanceWeekTwo(this.listOfFitnessClasses[i].getAttendanceWeekTwo());
				fitnessProgramClone.listOfFitnessClasses[i].setAttendanceWeekThree(this.listOfFitnessClasses[i].getAttendanceWeekThree());
				fitnessProgramClone.listOfFitnessClasses[i].setAttendanceWeekFour(this.listOfFitnessClasses[i].getAttendanceWeekFour());
				fitnessProgramClone.listOfFitnessClasses[i].setAttendanceWeekFive(this.listOfFitnessClasses[i].getAttendanceWeekFive());
			}
		}
		return fitnessProgramClone;
	}
}
