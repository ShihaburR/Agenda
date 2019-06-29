package com.Shihabur;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Main {
    //predefined file and Arraylist ready to be used
    static File filename = new File("notes.txt");
    static ArrayList<String> notes = new ArrayList<>();

    public static void menu() {
        Scanner input = new Scanner(System.in);
        System.out.println("-------------Main Menu------------");
        System.out.println("What would you like to do?");
        System.out.println("1) Write notes to save");
        System.out.println("2) Read notes you have saved");
        System.out.println("3) Arrange your notes");
        System.out.println("4) Remove a note");
        System.out.println("5) Exit");
        System.out.println();
        System.out.println("Please type the option's number");
        int option = input.nextInt();
        //menu options
        switch (option) {
            case 1:
                writeNotes();
                break;
            case 2:
                fileReader();
                menu();
                break;
            case 3:
                sortFile();
                break;
            case 4:
                deleteNote();
                break;
            case 5:
                System.exit(0);
        }
    }

    public static void writeNotes() {
        //user input
        Scanner input = new Scanner(System.in);
        System.out.println();
        System.out.println("Please enter the note you would like to store. Type STOP to return to Menu");
        String note = input.nextLine();
        //stop the recursion/catch mis-input
        if (note.equals("STOP")) {
            menu();
        } else if (note.equals("")) {
            System.out.println("Error please enter a note");
            writeNotes();
        } else {
            fileWriter(note);
            writeNotes();
        }
    }

    public static void fileWriter(String note) {
        try {
            //no recreation of file
            FileWriter fileWriter = new FileWriter(filename, true);
            fileWriter.write(note);
            //adds in new line
            fileWriter.write("\n");
            System.out.println("Note added");
            System.out.println();
            fileWriter.close();
        } catch (IOException ex) {
        }
    }

    public static void fileReader() {
        //clear notes to ensure no duplicates
        notes.clear();
        try {
            BufferedReader fileReader = new BufferedReader(new FileReader(filename));
            for (String line; (line = fileReader.readLine()) != null; ) {
                notes.add(line);
            }
            fileReader.close();
            getOutput(2);
        } catch (IOException ex) {
        }
    }

    //reusable code for filereader and sortfile.
    public static void getOutput(int option) {
        System.out.println();
        //display difference of notes if we want to sort our files.
        switch (option) {
            case 2:
                System.out.println("------Current Notes--------");
                break;
            case 3:
                System.out.println("------New Notes--------");
        }
        for (int i = 0; i < notes.size(); i++) {
            System.out.println(notes.get(i));
        }
        System.out.println();
    }


    public static void sortFile() {
        Scanner scan = new Scanner(System.in);
        fileReader();
        //sorts the arraylist without being case sensitive in alphabetical order
        Collections.sort(notes, String.CASE_INSENSITIVE_ORDER);
        //outputs the correct order
        getOutput(3);
        System.out.println("Save notes like this? Y/N");
        String answer = scan.nextLine();
        if (answer.equals("Y")) {
            rewriteNotes();
        }
        menu();
    }

    //will remake the file and replace it when altering the entire file e.g. sort/delete.
    public static void rewriteNotes() {
        try {
            FileWriter fileWriter = new FileWriter(filename);
            for (String note : notes) {
                fileWriter.write(note);
                fileWriter.write("\n");
                //adds in new line
            }
            fileWriter.close();
        } catch (IOException ex) {

        }
    }

    public static void deleteNote() {
        Scanner scan = new Scanner(System.in);
        System.out.println("What is the note you would like to delete?");
        fileReader();
        String answer = scan.nextLine();
        //Reads entire array list checking if it contains the note requested to delete
        for (String note : notes) {
            if (notes.contains(answer)) {
                notes.remove(answer);
            }
            rewriteNotes();
            getOutput(3);
            menu();

        }
    }


        //run the program
        public static void main (String[]args){
            menu();
        }
    }
