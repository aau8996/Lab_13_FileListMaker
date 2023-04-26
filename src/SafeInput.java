import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.ArrayList;
public class SafeInput {
    /**
     *
     * @param pipe a Scanner opened to read from System.in
     * @param prompt prompt for the user
     * @return a String response that is not zero length
     */
    public static String getNonZeroLenString(Scanner pipe, String prompt)
    {
        String retString = ""; // Set this to zero length. Loop runs until it isn’t
        do
        {
            System.out.print("\n" +prompt + ": "); // show prompt add space
            retString = pipe.nextLine();
        }while(retString.length() == 0);
        return retString;
    }

    public static int getInt(Scanner pipe, String prompt)
    {
        int retInt = 0;
        boolean repeat  = true;
        while (repeat) {
            try{
                System.out.print("\n" +prompt + ": ");
                while (!pipe.hasNext()){
                    String input = pipe.next();
                }
                retInt = pipe.nextInt();
                repeat = false;
            }catch (Exception ex){
                repeat = true;
            }
            pipe.nextLine();
        }
        return retInt;
    }

    public static double getDouble(Scanner pipe, String prompt)
    {
        double retDouble = 0;
        boolean repeat  = true;
        while (repeat) {
            try{
                System.out.print("\n" +prompt + ": ");
                while (!pipe.hasNext()){
                    String input = pipe.next();
                }
                retDouble = pipe.nextDouble();
                repeat = false;
            }catch (Exception ex){
                repeat = true;
            }
            pipe.nextLine();
        }
        return retDouble;
    }

    public static int getRangedInt(Scanner pipe, String prompt, int low, int high)
    {

        int retInt = 0;
        boolean repeat  = true;
        while (repeat) {
            try{
                System.out.print("\n" +prompt+ "(in range " + low + "-" + high + "): ");
                while (!pipe.hasNext()){
                    String input = pipe.next();
                }
                retInt = pipe.nextInt();
                if(retInt >= low && retInt <= high){
                    repeat = false;
                }
            }catch (Exception ex){
                repeat = true;
            }
            pipe.nextLine();
        }
        return retInt;
    }

    public static double getRangedDouble(Scanner pipe, String prompt, double low, double high)
    {
        double retDouble = 0;
        boolean repeat  = true;
        while (repeat) {
            try{
                System.out.print("\n" +prompt+ "(in range " + low + "-" + high + "): ");
                while (!pipe.hasNext()){
                    String input = pipe.next();
                }
                retDouble = pipe.nextDouble();
                if(retDouble >= low && retDouble <= high){
                    repeat = false;
                }
            }catch (Exception ex){
                repeat = true;
            }
            pipe.nextLine();
        }
        return retDouble;
    }

    public static boolean getYNConfirm(Scanner pipe, String prompt){
        String input = "";
        do{
            System.out.print("\n" +prompt + ": ");
            input = pipe.next();
            if(input.length() == 1){
                if(input.toLowerCase().equals("y")){
                    return true;
                }
                if(input.toLowerCase() .equals("n")){
                    return false;
                }
            }
            pipe.nextLine();
        }while(true);
    }

    public static String getRegExString(Scanner pipe, String prompt, String regExString){
        String input = "";
        Pattern regEx = Pattern.compile(regExString);
        do{
            System.out.print("\n" +prompt + " (that matches the following Regex: " + regExString + "): ");
            input = pipe.next();
            if(input.matches(regExString)){
                return input;
            }
            pipe.nextLine();
        }while(true);
    }

    public static void prettyHeader(String msg){
        int numPadding = (54 - msg.length()) / 2;
        for (int i = 0; i < 60; i++) {
            System.out.print("*");
        }
        System.out.println();
        System.out.print("***");
        for (int i = 0; i < numPadding; i++) {
            System.out.print(" ");
        }
        System.out.print(msg);
        for (int i = 0; i < numPadding; i++) {
            System.out.print(" ");
        }
        if(msg.length() % 2 == 1){
            System.out.print(" ");
        }
        System.out.print("***");
        System.out.println();
        for (int i = 0; i < 60; i++) {
            System.out.print("*");
        }
    }

    public static String getValidOption(Scanner pipe, String prompt, ArrayList<String> options){
        String input = "";
        do{
            System.out.print("\n" +prompt + ": ");
            input = pipe.next();
            if(options.contains(input)){
                return input;
            }
            pipe.nextLine();
        }while(true);
    }
}
