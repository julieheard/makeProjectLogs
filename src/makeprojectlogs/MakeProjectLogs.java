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
        System.out.println("Hi "
                + "\nIn a folder put a text document with all of the student names in, call it 'StudentNames.txt', and in the same file put other files you want to copy (any filetype)."
                + "\nYou will need to know the source of where the files are currently stored, and will need to make a new folder to put all of the new files in (the destination folder).\n"
                + "\nIn your destination folder, this program will make a new folder for each of the student names in the text file and will copy the other files from the source folder into it as well as change the file names to the student names.\n");
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
        System.out.println("Copy and paste in here source file location for files and textfile of studentNames you want to copy");
        SourceFileLocation = input.nextLine();

        //append name of file to sourceFileLocation
        System.out.println("Copy and paste in here destination file location for where you want your files created");
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
                    
                } catch (Exception e) {
                    System.out.println(e);
                }

            }

        }
        System.out.println(StudentNames.size() + "file(s) created");
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
