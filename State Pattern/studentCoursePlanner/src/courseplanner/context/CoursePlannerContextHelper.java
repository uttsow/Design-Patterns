package courseplanner.context;

import courseplanner.context.CoursePlannerContext;
import courseplanner.debug.Debug;
import courseplanner.util.ErrorChecking;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;


import java.util.LinkedList;



public class CoursePlannerContextHelper{

    // Reference to the parent class
    private CoursePlannerContext context;
    private ErrorChecking e;

    //Queues for managing courses taken and waitlist as well as the size for each
    private Queue<String> courseTaking;
    private int courseQueueSize;
    private Queue<String> waitList;
    private int waitListSize;
    Queue<String> result;
    int resultQueueSize;

    //For constructor and input
    private String courses;
    private String id;

    //For checking student ID rand and max number of classes
    private final int IDSTART = 1000;
    private final int IDEND = 9999;
    private final int MAXCLASSES = 3;


    private boolean willGraduate;
    private boolean wontGraduate;


    /*
    Constructor
    */
    public CoursePlannerContextHelper(String coursesIn, CoursePlannerContext contextIn){
        this.courses = coursesIn;
        this.context =  contextIn;
        this.courseTaking = new LinkedList();
        this.courseQueueSize = 0;
        this.waitList = new LinkedList();
        this.waitListSize = 0;
        this.result = new LinkedList();
        this.resultQueueSize = 0;

        this.willGraduate = false;
        this.wontGraduate = false;

        this.e = new ErrorChecking(courses);
    }

    /**
    This method error checks the input string that was retrieved from the file. It checks for if the all the courses are valid, if theres duplicates, and the student ID range (1000-9999);
    @return;
    */
    void inputChecker(){
        Debug.print("ATTEMPTING TO CHECK INPUT FOR ERRORS!");
        //Step 1. Clear string of foreign characters
        String clearCourses = e.clearString(courses);

        // Step 2. Check for validity;
        String[] splitSentence = clearCourses.split("\\s+");
        e.isValidCourses(1, splitSentence.length);
        e.DuplicateCourseChecker(1,splitSentence.length);
        e.checkDigits(splitSentence[0],IDSTART,IDEND);

        Debug.print("INPUT IS VALID. READY TO COURSE PLAN!");

    }


    /**
    This method initalizes the Queues with the valid inputs and updates the queue size
    @return;
    */
    void init(){
        String clearCourses = e.clearString(courses);
        String[] splitSentence = clearCourses.split("\\s+");
        this.id = splitSentence[0];
        Arrays.sort(splitSentence);
        Debug.print("INITALIZING COURSE QUEUE!");
        for(int i = 1; i<splitSentence.length; i++){
            try{
            //    Debug.print(splitSentence[i]);
                courseTaking.add(splitSentence[i]);
                courseQueueSize++;
            }catch(IllegalStateException e){
                e.printStackTrace();
                Debug.error("Couldn't add courses to the Queue");
                System.exit(1);
            }
        }
        Debug.print("COURSE QUEUE COMPLETE!");

        //initalize the states
        Debug.print("INITALIZING ALL STATES");

        context.g1.initalize();
        context.g2.initalize();
        context.g3.initalize();
        context.g4.initalize();
        context.g5.initalize();

        Debug.print("ALL STATES INITALIZED!");

        // System.out.println("Queue size: " + courseQueueSize);
    }


    /**
    This method initiates taking a course. It removes the item from queue, determines if course can be taking or needs waiting listing
    */
    void takeCourse(){
        Debug.print("PROGRAM STARTED!");
        Debug.print("\n");

        //While loop until will or wont graudate occurs
        while(true){
            if((willGraduate) || (wontGraduate)){
                break;
            }

            // Step 1: Take a semester
            takeSemester();

        }

        // if(willGraduate){
        //     context.setGraduation(true);
        //
        //     System.out.println("I gradauteD!");
        //     System.out.println(context.getStateChange());
        //     System.out.println(context.getSemesterCount());
        //     while(resultQueueSize != 0){
        //         System.out.print(result.poll());
        //         resultQueueSize--;
        //     }
        // }else if(wontGraduate){
        //     System.out.println("Wont be graduatiing");
        // }

        // getOuput();
        Debug.print("PROGRAM SUCCESSFULLY COMPLETED!");
    }

    /**
    This is the main algorithim for taking courses. This method attemtps to take 3 semesters and sees if it will succeed. If it is able to take a course, the course is removed from the courseTaking queue and added to result Queue, else, it is added to the waitlist for reconfiguration. It also increments semester after every 3 classes taken.
    */
    private void takeSemester(){
        int count = 0;
        // Debug.print("ATTEMTPING TO COMPLETE SEMESTER!");
        while(count != MAXCLASSES){
            if(courseTaking.peek() == null){
                Debug.print("Course queue is empty!");
                break;
            }
            // tryTakingCourse(courseTaking.poll());
            String course = courseTaking.poll();
            boolean success = tryTakingCourse(course);
            courseQueueSize--;
            // System.out.println("QUEUE SIZE: " + courseQueueSize);
            if(courseQueueSize == 0 && count < 3 ){
                checkGroupReqs();
                if(willGraduate == false){
                    wontGraduate = true;
                    break;
                }
            }
            if(!success){
                waitList.add(course);
                waitListSize++;
                // System.out.println("Course added to wait list" + course);
            }else{
                result.add(course);
                resultQueueSize++;
                // Debug.print("ADDING TO RESULT" + course);
                checkGroupReqs();
                if(willGraduate){
                    break;
                }else{
                    count++;
                }
            }
        }

        if((!willGraduate) || (!wontGraduate)){
            //Semester completed
            context.incrementSemester();

            //Check if graduated or no graduated;
            checkGroupReqs();

            //Reconfigure Queue;
            reconfigQueue();

            //Reset prereq for states;
            resetPreReqs();
        }
    }

    /**
    This method reconfigures the queue by adding all of the remaining items of the course taking queue to the waitlist, then back into the courseTaking queue;
    @return;
    */
    private void reconfigQueue(){
        while(courseQueueSize != 0  || courseTaking.peek() != null){
            // Debug.print("{RECONFIG} Adding to wait " + courseTaking.peek());
            waitList.add(courseTaking.poll());
            courseQueueSize--;
            waitListSize++;
        }

        while(waitListSize != 0 || waitList.peek() != null){
            // Debug.print("{RECONFIG} Adding " + waitList.peek());
            courseTaking.add(waitList.poll());
            waitListSize--;
            courseQueueSize++;
        }
    }


    /**
    This methods attempts to call all the states classes to see which one will succeed in taking the course;
    @param course letter
    @return boolean succeed or fail
    */
    private boolean tryTakingCourse(String courseIn){
        boolean retVal = false;

        if(context.g1.takeCourse(courseIn)){
            // Debug.print("Took COurse from State 1");
            retVal = true;
        }else if(context.g2.takeCourse(courseIn)){
            // Debug.print("Took COurse from State 2");
            retVal = true;
        }else if(context.g3.takeCourse(courseIn)){
            // Debug.print("Took COurse from State 3");
            retVal = true;
        }else if(context.g4.takeCourse(courseIn)){
            // Debug.print("Took COurse from State 4");
            retVal = true;
        }else if(context.g5.takeCourse(courseIn)){
            // Debug.print("Took COurse from State 5");
            retVal = true;
        }else{
            retVal = false;
        }
        return retVal;
    }


    /**
    This method checks if all groups met min graduation requirment. if it did, it sets "willGraduate" to true
    */
    private void checkGroupReqs(){
        if((context.g1.getGroupReq()) && (context.g2.getGroupReq()) && (context.g3.getGroupReq()) && (context.g4.getGroupReq()) && (context.g5.getGroupReq())){
            willGraduate = true;
        }
    }

    /**
    This method resets all State classes preReq requirment
    */
    private void resetPreReqs(){
        context.g1.tookPre(false);
        context.g2.tookPre(false);
        context.g3.tookPre(false);
        context.g4.tookPre(false);
    }


    /**
    This method takes the output, Result queue, semster count, and state changes and creates a String that will be used for printing or writing out the output;
    @return output string
    */
    public String getOuput(){
        StringBuilder builder = new StringBuilder();
        builder.append(id);
        builder.append(": ");

        if(willGraduate){
            context.setGraduation(true);
            while(resultQueueSize != 0){
                builder.append(result.poll());

                builder.append(" ");
                resultQueueSize--;
            }
            builder.append("-- ");
            builder.append(context.getSemesterCount());
            builder.append(" ");
            builder.append(context.getStateChange());
        }else{
            builder.append("This student does not graduate! -- 0 ");
            builder.append(context.getStateChange());
        }
        // System.out.println(builder.toString());
        return builder.toString();
    }

}
