package makeprojectlogs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Scanner;

public class MakeProjectLogs {

    private static String SourceFileLocation;
    private static String DestinationFileLocation;
    private static String[] pathnames;
    private static ArrayList<String> StudentNames = new ArrayList<String>();
    private static int studentFileIndexInDir;

    public static void main(String[] args) {
        System.out.println("Hi future Julie :) "
                + "\nIn a folder put a text document with all of the student names called 'StudentNames', and in the same file put other files you want to copy (any filetype)"
                + "\nLast time you used the Current Project Log folder to put these into."
                + "\nThis program will make a new folder for each of the students and copy the files into it with the file name and their name appended.\n");
        getFileLocation();
        //This gets a list of the files in the sourceFileLocation
        File f = new File(SourceFileLocation);
        pathnames = f.list();

        if (getStudentNames()) {
            makeFolders();
            copyFile();
        }

    }

    //Type in fodler name of where they are stored
    public static void getFileLocation() {
        Scanner input = new Scanner(System.in);
        System.out.println("Copy and paste in source file location for project log you want to copy");
        SourceFileLocation = input.nextLine();

        //append name of file to sourceFileLocation
        System.out.println("\nCopy and paste in destination file location for destination folder");
        DestinationFileLocation = input.nextLine();
    }

    public static void makeFolders() {
        for (int i = 0; i < StudentNames.size(); i++) {
            File theDir = new File(DestinationFileLocation + "/" + StudentNames.get(i));
            if (!theDir.exists()) {
                theDir.mkdirs();
            }
        }
    }

    public static void copyFile() {
        //For each file in the source folder
        for (int j = 0; j < pathnames.length; j++) {

            //If the file is not the studentNames file
            if (studentFileIndexInDir != j) {

                try {
                    //loops through student array and copys the file, when pasting creates the file with the students name
                    for (int i = 0; i < StudentNames.size(); i++) {

                        //copy from from source file, call file StudentName + Filename +.doc
                        Files.copy(Paths.get(SourceFileLocation + "\\" + pathnames[j]), Paths.get(DestinationFileLocation + "\\" +StudentNames.get(i)+"\\"+ StudentNames.get(i) + " " + pathnames[j]), StandardCopyOption.COPY_ATTRIBUTES);
                        System.out.println("file created for " + StudentNames.get(i));
                    }
                    System.out.println(StudentNames.size() + "file(s) created");

                } catch (Exception e) {
                    System.out.println(e);
                }

            }

        }
    }

    //If studentNames file does not exist it returns false, otherwise returns true
    public static boolean getStudentNames() {

        //Find file with studentNames in the name of it
        studentFileIndexInDir = -1;
        for (int i = 0; i < pathnames.length; i++) {
            if (pathnames[i].contains("StudentNames")) {
                studentFileIndexInDir = i;
            }
        }

        if (studentFileIndexInDir != -1) {
            //Reading items from a file and loading items into an arraylist
            try {
                BufferedReader re = new BufferedReader(new FileReader(SourceFileLocation + "\\" + pathnames[studentFileIndexInDir]));
                String inputLine;
                while ((inputLine = re.readLine()) != null) {
                    StudentNames.add(inputLine); //add item to array list
                }
            } catch (Exception ex) {
                System.out.println("Error: " + ex);
            }
            return true;
        } else {
            System.out.println("No StudentNames file found");
            return false;
        }
    }
}
