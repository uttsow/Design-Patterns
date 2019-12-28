package courseplanner.util;

import courseplanner.debug.Debug;
import java.util.Queue;
import java.util.LinkedList;




public class StateFunctions{

    public Queue initalizeQueue(char startClassIn, char endClassIn){
        Queue<String> courses = new LinkedList();
        // Debug.print("INITALIZING STATE QUEUE!" + startClassIn + " - " + endClassIn);
        for(char ch =startClassIn; ch <=endClassIn; ++ch){
            try{
                courses.add(String.valueOf(ch));
                // System.out.println(courses.poll());
            }catch (IllegalStateException e){
                e.printStackTrace();
                Debug.error("Couldnt populate State queue");
            }
        }
        Debug.print("STATE QUEUE INITALIZED!");
        return courses;
    }






}
