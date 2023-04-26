import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

public class Main {
    public static void main(String[] args) {
        boolean quit = false;
        boolean dirty = false;
        String currentFile = null;
        ArrayList<String> userList = new ArrayList<String>();
        do{
            switch(getUserOption(currentFile)){
                case "A":
                    addOption(userList);
                    dirty = true;
                    break;
                case "D":
                    deleteOption(userList, currentFile);
                    dirty = true;
                    break;
                case "V":
                    printOption(userList, currentFile);
                    break;
                case "C":
                    clearOption(userList);
                    dirty = true;
                    break;
                case "O":
                    currentFile = openOption(dirty, userList, currentFile);
                    dirty = false;
                    break;
                case "S":
                    currentFile = saveOption(userList, currentFile);
                    dirty = false;
                    break;
                case "Q":
                    dirty = checkForSave(dirty, userList, currentFile);
                    quit = quitOption(dirty);
            }
        }while(!quit);
    }
    public static String getUserOption(String currentFile) {
        if(currentFile != null){
            System.out.println("Current file: " + currentFile);
        }
        System.out.println(" A – Add an item to the list \n D – Delete an item from the list \n V - View the list \n C - Clear the list \n O - Open a list file from disk (Dialog may appear in background) \n S - Save current list to disk \n Q – Quit the program");
        Scanner scan = new Scanner(System.in);
        String prompt = "Enter an option (A, D, V, C, O, S, Q)";
        ArrayList<String> validOptions = new ArrayList<String>(){{
            add("A");
            add("D");
            add("V");
            add("C");
            add("O");
            add("S");
            add("Q");
        }};
        String option = SafeInput.getValidOption(scan, prompt, validOptions);

        System.out.println(option);
        return option;
    }

    public static void addOption(ArrayList<String> list){
        Scanner scan = new Scanner(System.in);
        String prompt = "Enter the item you want to add";
        list.add(SafeInput.getNonZeroLenString(scan, prompt));
    }

    public static void  deleteOption(ArrayList<String> list, String currentFile){
        printOption(list, currentFile);

        if(list.size() > 0) {
            Scanner scan = new Scanner(System.in);
            String prompt = "Enter the integer of the item you want to delete";
            list.remove(SafeInput.getRangedInt(scan, prompt, 1, list.size()) - 1);
        }
    }

    public static void printOption(ArrayList<String> list, String currentFile){

        if(currentFile != null){
            System.out.println("Current file: " + currentFile);
        }
        if(list.size() == 0){
            System.out.println("The list is empty");
        }else{
            for(int i = 0; i < list.size(); i++){
                System.out.println((i + 1) + ") " + list.get(i));
            }
        }
        System.out.println();
    }

    public static void clearOption(ArrayList<String> list){
        list.clear();
    }

    public static String openOption(boolean dirty, ArrayList<String> list, String currentFile){
        if(dirty){
            checkForSave(dirty, list, currentFile);
        }
        clearOption(list);
        Scanner input = null;
        JFileChooser jfc = new JFileChooser();
        jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        jfc.showSaveDialog(null); // Makes dialog appear
        String path = jfc.getSelectedFile().getAbsolutePath(); // Gets path of selected file
        String fileName = jfc.getSelectedFile().getName(); // Gets name of selected file
        File inputFile = new File(path);
        if (inputFile.exists()){ // Makes sure that the file exists before continuing
            try{
                input = new Scanner(inputFile);
                // Defining buffered reader to define the chosen file
                BufferedReader br = new BufferedReader(new FileReader(inputFile));
                String line;
                while ((line = br.readLine()) != null) {
                    list.add(line);
                }
                currentFile = path;
                input.close();
            }catch(Exception ex){
                System.out.println("Error. File may not be found.");
            }
        }
        return currentFile;
    }

    public static String saveOption(ArrayList<String> list, String currentFile){
        String fileName = currentFile;
        JFileChooser jfc = new JFileChooser();
        if(fileName == null) {
            Scanner scan = new Scanner(System.in);
            String prompt = "What is the name of the .txt file that you want to save to?";
            fileName = SafeInput.getNonZeroLenString(scan, prompt) + ".txt";
            jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            jfc.showSaveDialog(null); // Makes dialog appear
            fileName = jfc.getSelectedFile() + "\\" + fileName;
        }

        try(FileWriter fw = new FileWriter(fileName)){
            for(int i = 0; i < list.size(); i++){
                fw.write(list.get(i) + "\n");
            }
            currentFile = fileName;
        }catch(Exception ex){
            System.out.println("Error. File may not be found.");
        }
        return currentFile;
    }

    public static boolean quitOption(boolean dirty){
        Scanner scan = new Scanner(System.in);
        String prompt = "Are you sure you want to quit? (Y/N)";
        return SafeInput.getYNConfirm(scan, prompt);
    }

    public static boolean checkForSave(boolean dirty, ArrayList<String> list, String currentFile){
        if(dirty) {
            Scanner scan = new Scanner(System.in);
            String prompt = "Do you want to save your list? Otherwise it will be abandoned. (Y/N)";
            if(SafeInput.getYNConfirm(scan, prompt)){
                saveOption(list, currentFile);
                return false;
            }
        }
        return dirty;
    }
}