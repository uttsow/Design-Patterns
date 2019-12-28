package courseplanner.util;

import courseplanner.debug.Debug;
import java.util.ArrayList;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;


public class ErrorChecking{

    private String check;

    public ErrorChecking(String listIn){
        this.check = listIn;
    }

    /**
    This method clears all special character from the string
    CITATION: Stackoverflow -> check readme
    @return String
    */
    public String clearString(String sIn){
        return (sIn.replaceAll("[^a-zA-Z0-9-]", " "));
    }


    /**
    This methods checks if the courses are all valid. A-Z are the only offered courses. This checks if there are any duplicates or unknown courses given.
    @param startIndex start of the array.
    @param endIndexIn end of the array.
    */
    public void isValidCourses(int startIndexIn, int endIndexIn){
        Debug.print("STARTING VALID COURSE CHECK!");
        Map<String, Integer> courseList = new HashMap<>();

        //Got from Stackoverflow
        int value = 0;
        for(char ch = 'A'; ch <= 'Z'; ++ch){
            courseList.put(String.valueOf(ch), value++);
        }

        // courseList.entrySet().forEach(entry->{
        //     System.out.println(entry.getKey() + " " + entry.getValue());
        // });


            // Map<String, Integer> map = new HashMap<>();
        String[] splitSentence = check.split("\\s+");
        for(int j = startIndexIn; j < endIndexIn; j++){
            // Debug.print(splitSentence[j]);
            String course = splitSentence[j];
            if(!(courseList.containsKey(course))){
                Debug.error("The course " + course + " is not in the registar list!");
                throw new IllegalArgumentException("Course not found!");
            }
        }

        Debug.print("COURSE'S ARE ALL VALID");
    }

    /**
    This method checks if the arrayList contains any duplicate courses. throws out an error if duplicate course is found
    @param coursesIn list of all courses.
    */
    public void DuplicateCourseChecker(int startIndexIn, int endIndexIn){
        Map<String, Integer> map = new HashMap<>();
        String[] splitSentence = check.split("\\s+");
        for(int i = startIndexIn; i<endIndexIn; i++){
            String courseLetter = splitSentence[i].toUpperCase();
            if(map.containsKey(courseLetter)){
                throw new IllegalArgumentException("Duplicate course found!");
            }else{
                map.put(courseLetter, i);
            }
        }
    }


    /**
    This checks if the ID is a digit and if it's in range
    @return ERROR If not digit or out of range!
    (Citation: stackoverflow)
    */
    public void checkDigits(String inputIn, int startRangeIn, int endRangeIn){
        for(char c: inputIn.toCharArray()){
            if(!Character.isDigit(c)){
                throw new NumberFormatException(c + " Is not a number!");
            }
        }
        int aNum = Integer.parseInt(inputIn);
        if(aNum < startRangeIn || aNum > endRangeIn){
            throw new IllegalArgumentException("Number is out of range");
        }
    }


}
