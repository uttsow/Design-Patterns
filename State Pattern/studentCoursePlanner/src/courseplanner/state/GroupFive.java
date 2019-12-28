package courseplanner.state;

import courseplanner.context.CoursePlannerContext;
import courseplanner.debug.Debug;
import courseplanner.state.CoursePlannerStateI;
import courseplanner.util.StateFunctions;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import courseplanner.context.CoursePlannerContext;
import courseplanner.util.StateFunctions;
import java.util.Queue;

public class GroupFive implements CoursePlannerStateI{

    //Reference to the context and helper
    CoursePlannerContext context;
    StateFunctions func;

    //Courses queue
    private HashMap<String, Integer> courses;

    //Counts number of courses taken
    private int courseCounter;

    private boolean groupReq;

    private final char START = 'Q';
    private final char END = 'Z';

    /*
    Constructor
    */
    public GroupFive(CoursePlannerContext contextIn){
        this.context = contextIn;
        this.func = new StateFunctions();
        this.courses = new HashMap<>();
        this.courseCounter = 0;
        this.groupReq = false;

    }

    /**
    This method initalizes the state with the correct courses corresponding with the group
    */
    public void initalize(){
        // courses = func.initalizeQueue(START, END);
        int value = 0;
        for(char ch = START; ch <= END; ++ch){
            courses.put(String.valueOf(ch), value++);
        }
    }

    /**
    This method attempts to take the course passed as an argument. It dequeues and returns true if successful else false;
    @param courseIn, the course letter
    @return true or false if it was able to be taken or not
    */
    public boolean takeCourse(String courseIn){
        // Debug.print("{STATE5}Attempting to take course from State 5: " + courseIn);
        if(courses.containsKey(courseIn)){
            // Debug.print("{STATE5}TOOK COURSE from State 5: " + courseIn);
            incrementCoursesTaken();
            return true;
        }

        return false;
    }

    /**
    This method increments course counter on successful takecourse and performs a state transition if number of courses taken in this state is larger than the current context state
    */
    public void incrementCoursesTaken(){
        // Debug.print("INCREMENTING STATE 5 Course Counter");
        courseCounter += 1;
        if(courseCounter > context.getState().getCourseTakenCounter()){
            // Debug.print("{STATE5} STATE CHANGE!");
            context.setState(context.getG5());
        }

        if(courseCounter == 2){
            setGroupReq(true);
        }
    }

    /**
    Getter for course taken counter
    @return course count
    */
    public int getCourseTakenCounter(){
        return this.courseCounter;
    }

    /**
    Setter for group requirment (if it meets the min 2 classes taken );
    @param boolean standing (true or false)
    */
    public void setGroupReq(boolean standingIn){
        Debug.print("{STATE5} Group req fufilled!");
        this.groupReq = standingIn;
    }

    /**
    getter for group requirment
    @return group requirment standing
    */
    public boolean getGroupReq(){
        return this.groupReq;
    }


    /**
    Setter for pre reqs but pre reqs dont matter for state 5
    @param took (true or false)
    */
    public void tookPre(boolean tookIn){

    }

    /**
    Getter for pre reqs
    */
    public boolean getPreReq(){
        return false;
    }






}
