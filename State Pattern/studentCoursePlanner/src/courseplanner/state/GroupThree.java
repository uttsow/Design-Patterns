package courseplanner.state;

import courseplanner.state.CoursePlannerStateI;
import courseplanner.context.CoursePlannerContext;
import courseplanner.util.StateFunctions;
import courseplanner.debug.Debug;
import java.util.Queue;
import java.util.LinkedList;

public class GroupThree implements CoursePlannerStateI{

    //Reference to the context and helper
    CoursePlannerContext context;
    StateFunctions func;

    //Courses queue
    private Queue<String> courses;

    //Counts number of courses taken
    private int courseCounter;

    private boolean groupReq;
    private boolean preReqTaking;

    private final char START = 'I';
    private final char END = 'L';

    /*
    Constructor
    */
    public GroupThree(CoursePlannerContext contextIn){
        this.context = contextIn;
        this.func = new StateFunctions();
        this.courses = new LinkedList();
        this.courseCounter = 0;
        this.groupReq = false;
        this.preReqTaking = false;
    }

    /**
    This method initalizes the state with the correct courses corresponding with the group
    */
    public void initalize(){
        courses = func.initalizeQueue(START, END);
    }

    /**
    This method attempts to take the course passed as an argument. It dequeues and returns true if successful else false;
    @param courseIn, the course letter
    @return true or false if it was able to be taken or not
    */
    public boolean takeCourse(String courseIn){
        // Debug.print("{STATE3}Attempting to take course from State 3: " + courseIn);
        String coursePeek = courses.peek();
        if(courseIn.equals(coursePeek) && (preReqTaking == false)){
            // Debug.print("{STATE3}TOOK COURSE from State 3: " + courses.peek());
            courses.poll();
            tookPre(true);
            incrementCoursesTaken();
            return true;
        }
        return false;
    }

    /**
    This method increments course counter on successful takecourse and performs a state transition if number of courses taken in this state is larger than the current context state
    */
    public void incrementCoursesTaken(){
        // Debug.print("INCREMENTING STATE 3 Course Counter");
        courseCounter += 1;
        if(courseCounter > context.getState().getCourseTakenCounter()){
            // Debug.print("{STATE3} STATE CHANGE!");
            context.setState(context.getG3());
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
        Debug.print("{STATE3} Group req fufilled!");
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
    Setter for pre req
    @param took (true or false)
    */
    public void tookPre(boolean tookIn){
        preReqTaking = tookIn;
    }

    /**
    Getter for pre req
    */
    public boolean getPreReq(){
        return preReqTaking;
    }
}
