package loadbalancer.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import loadbalancer.debug.Debug;



public class ErrorChecking{

    //ArrayList of inputs to check for error
    private ArrayList<String> check;

    //This enum contains the list of all possible operations
    private enum Ops{
        SERVICE_OP__ADD_SERVICE,
        SERVICE_OP__REMOVE_SERVICE,
        SERVICE_OP__ADD_INSTANCE,
        SERVICE_OP__REMOVE_INSTANCE,
        CLUSTER_OP__SCALE_UP,
        CLUSTER_OP__SCALE_DOWN,
        REQUEST

    }

    /**
    Constructor
    */
    public ErrorChecking(ArrayList<String> listIn){
        this.check = listIn;

    }

    /**
    This method clears all special character from the string
    CITATION: Stackoverflow -> check readme
    @param String to clear
    @return String[] string array of the new cleared string
    */
    public String[] clearString(String sIn){
        return (sIn.split("\\s*(\\s|,)\\s*"));
    }


    /**
    This method checks if all the operations given in the input file are correct operations
    @return;
    */
    public void checkCorrectOperations(){
        if(check.size() == 0 || check.get(0).equals("")){
            throw new IllegalArgumentException("File was empty! Give valid input");
        }
        Debug.print("CHECKING FOR CORRECT OPERATIONS GIVEN!");
        Ops op;
        for(int i = 0; i < check.size(); i++){
            String[] operants = clearString(check.get(i));
            // System.out.println(Arrays.toString(operants));
            try{
                op = Ops.valueOf(operants[0]);
                // System.out.println(operants[0]);
                checkCorrectArgumets(operants);
            }catch (IllegalArgumentException e){
                Debug.error("Invalid operations given. Check Operations!");
                e.printStackTrace();
            }
        }

        Debug.print("ALL OPERATIONS CORRECT!!!");
    }

    /**
    This method checks if correc arguments are given for each operation
    */
    public void checkCorrectArgumets(String[] operantsIn){
        // System.out.println(operantsIn.length);

        switch(operantsIn[0]){

            case "SERVICE_OP__ADD_SERVICE":
                if(operantsIn.length < 4){
                    throw new IllegalArgumentException("{SERVICE_OP__ADD_SERVICE} invalid number of arguments");

                }
                break;
            case "SERVICE_OP__REMOVE_SERVICE":
                if(operantsIn.length < 2 || operantsIn.length >2){
                    System.out.println(operantsIn.length);
                    throw new IllegalArgumentException("{SERVICE_OP__REMOVE_SERVICE} invalid number of arguments");
                }
                break;
            case "SERVICE_OP__ADD_INSTANCE":
                if((operantsIn.length < 3) || (operantsIn.length > 3)){
                    throw new IllegalArgumentException("{SERVICE_OP__ADD_INSTANCE} invalid number of arguments");
                }
                break;
            case "SERVICE_OP__REMOVE_INSTANCE":
                if(operantsIn.length <3 || operantsIn.length > 3){
                    throw new IllegalArgumentException("{SERVICE_OP__REMOVE_INSTANCE}invalid numebr of arguments");
                }
                break;
            case "CLUSTER_OP__SCALE_UP":
                if(operantsIn.length < 2 || operantsIn.length > 2){
                    throw new IllegalArgumentException("{CLUSTER_OP__SCALE_UP} invalid number of arguments");
                }
                break;
            case "CLUSTER_OP__SCALE_DOWN":
                if(operantsIn.length < 2 || operantsIn.length > 2){
                    throw new IllegalArgumentException("{CLUSTER_OP__SCALE_DOWN} invalid number of arguments");
                }
            case "REQUEST":
                if(operantsIn.length < 2 || operantsIn.length > 2){
                    throw new IllegalArgumentException("{REQUEST} invalid number of arguments");
                }
                break;
            default :
            break;
        }
    }


}
