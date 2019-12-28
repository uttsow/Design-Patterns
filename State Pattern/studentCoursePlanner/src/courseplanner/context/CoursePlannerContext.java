package courseplanner.context;

import courseplanner.state.CoursePlannerStateI;
import courseplanner.debug.Debug;
import courseplanner.state.GroupOne;
import courseplanner.state.GroupTwo;
import courseplanner.state.GroupThree;
import courseplanner.state.GroupFour;
import courseplanner.state.GroupFive;

public class CoursePlannerContext{

    //References to all the states
    CoursePlannerStateI g1;
    CoursePlannerStateI g2;
    CoursePlannerStateI g3;
    CoursePlannerStateI g4;
    CoursePlannerStateI g5;

    int semester;
    int stateChange;
    boolean graduate;

    //Helper class for parsing micromanaging conditions and data structures
    private CoursePlannerContextHelper helper;

    //Too keep track of the current state
    CoursePlannerStateI currState;

    //input from parsing the file
    private String courses;

    /*
    Constructor
    */
    public CoursePlannerContext(String coursesIn){
        this.courses = coursesIn;
        this.g1 = new GroupOne(this);
        this.g2 = new GroupTwo(this);
        this.g3 = new GroupThree(this);
        this.g4 = new GroupFour(this);
        this.g5 = new GroupFive(this);
        this.helper = new CoursePlannerContextHelper(coursesIn, this);
        this.semester = 0;
        this.stateChange = 0;
        this.graduate = false;

        this.currState = g1;
    }

    /**
    This method calls the helper function to check if the input is valid
    @return
    */
    public void checkInput(){
        helper.inputChecker();
    }


    /**
    This method initalizes the the process of course planning and attempts to create a outcome
    @return
    */
    public void initalize(){
        helper.init();
    }

    /**
    Main method to start program from driver
    */
    public void determineOutcome(){
        takeCourse();
    }

    /**
    This method calls the helper method to take a course
    */
    private void takeCourse(){
        helper.takeCourse();
    }

    /**
    This method increments the number of semester
    */
    void incrementSemester(){
        semester += 1;
    }

    /**
    getter for semester count
    @return number of semester
    */
    public int getSemesterCount(){
        return semester;
    }


    /**
    Setter for if student is graduating or Not
    @param true false value
    */
    void setGraduation(boolean in){
        graduate = in;
    }

    /**
    Getter for if graduating or not
    */
    public boolean getGraduation(){
        return graduate;
    }


    /**
    Setter for updating current state
    @param stateIn, the new state of the context class
    @return;
    */
    public void setState(CoursePlannerStateI stateIn){
        this.currState = stateIn;
        incrementState();
    }


    /**
    State change incrementer
    */
    void incrementState(){
        stateChange += 1;
        // System.out.println("State change counter: " + stateChange);
    }

    /**
    Getter for state change;
    @return state change number
    */
    public int getStateChange(){
        return stateChange;
    }


    /**
    Getter for getting current state
    @return CoursePlannerStateI
    */
    public CoursePlannerStateI getState(){
        return this.currState;
    }


    /**
    Public getter for CourseContextHelper
    @return CoursePlannerContextHelper
    */
    public CoursePlannerContextHelper getHelper(){
        return helper;
    }


    /**
    Getter  for state
    @return g1 state
    */
    public CoursePlannerStateI getG1(){
        return g1;
    }

    /**
    Getter  for state
    @return g2 state
    */
    public CoursePlannerStateI getG2(){
        return g2;
    }

    /**
    Getter  for state
    @return g2 state
    */
    public CoursePlannerStateI getG3(){
        return g3;
    }

    /**
    Getter  for state
    @return g4 state
    */
    public CoursePlannerStateI getG4(){
        return g4;
    }

    /**
    Getter  for state
    @return g5 state
    */
    public CoursePlannerStateI getG5(){
        return g5;
    }

    /**
    Calls the getOutput function in Helper class to get the output based of the input
    @return output string
    */
    public String getOutput(){
        return helper.getOuput();
    }

}
