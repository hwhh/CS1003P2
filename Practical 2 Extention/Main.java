package com.company;

import java.io.*;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final String CSV_SEPARATOR = ",";
    private static final String QUIT = "quit";
    private static final String YES = "y";

    //Universal method to set up the print witters
    public static PrintWriter printWriter(String fileName, String format) throws FileNotFoundException, UnsupportedEncodingException {
        return new PrintWriter(fileName, format);
    }

    //Function to allow user to iput an integer
    public static int getInputInt()  {
        int input = 0;
        Scanner scanIn = new Scanner(System.in);
        //Loop that breaks when the user enter an integer between 2 and 9
        do {
            try {
                System.out.println("Please enter an integer >=2 <10: ");
                input = scanIn.nextInt();
            }catch (InputMismatchException e){//Throws exception is user enter anything other than an integer
                //System.out.println("Please enter an integer >=2 and <10: ");
                scanIn.next();
            }
        }while(input<2 || input>10);
        return input;//Rerun the input value when its valid
    }

    //Function to allow user to input a string
    public static String getInputString()  {
        String input;
        Scanner scanIn = new Scanner(System.in);
        do{
            input = scanIn.nextLine();
        }while (input.matches(".*\\d+.*"));
        return input.toLowerCase();//Convert the input string to lower case

    }


    public static void generaOutputFile(PrintWriter printWriter, List<nGramInfo.nGram> list){
        //Loop through each object in the given list
        for (nGramInfo.nGram currentObject  : list){
            printWriter.print(currentObject.nGramString);
            printWriter.print(CSV_SEPARATOR);//Add a coma to signify different coloumn
            printWriter.print(currentObject.occurrences);
            printWriter.println();
        }
        printWriter.close();

    }


    public static void main(String[] args) {
        try {
            //Create new nGramInfo object which handel's reading in file and extracting the correct data
            nGramInfo nGramInfo = new nGramInfo(args[0], getInputInt());
            //Create the new print witters which populate the output files
            PrintWriter nGramWriter = printWriter(args[1], args[4]);
            PrintWriter wordGramWritter = printWriter(args[2], args[4]);
            System.out.println("Processing");
            //Loop for as long as there is the required data in the input file
            while (nGramInfo.hasNextNGram()) {
                //Create a new object for each experiment which contains the data for each n-gram in the input file
                nGramInfo.nGram nGram = nGramInfo.getNextNGram();
            }
            System.out.println("Generating wordGram and nGram CSV files...");
            //Create the output files with the data created based on the input file
            generaOutputFile(nGramWriter, nGramInfo.nGrams);
            generaOutputFile(wordGramWritter, nGramInfo.wordGrams);
            System.out.println("Done.");
                                        
            //Allow the user to choose whether to use the predictive text functionality
            System.out.println("Enter Y to predict words based of input n-gram");
            if (getInputString().toLowerCase().equals(YES)) {
                //Create new predictWord object which uses a dictionary to find most likely words based on input
                PredictWord predictWord = new PredictWord(new BufferedReader(new FileReader(args[3])), "");
               //Keep requesting input until input is 'quit'
                String input="";
                do{
                    System.out.println();
                    System.out.println("Enter quit to end program or otherwise enter an n-gram:");

                    input = getInputString();
                    if(!input.equals(QUIT)) {
                        predictWord.inputNGram = input;
                        predictWord.read();
                    }
                } while (!input.equals(QUIT));
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }catch (IndexOutOfBoundsException e){
            System.out.println("Program arguments are incorrect...");
        }
    }
}
