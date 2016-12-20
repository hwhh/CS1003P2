package com.company;

import java.io.*;
import java.util.Scanner;

public class Main {

    private static final String CSV_SEPARATOR = ",";

    public static PrintWriter printWriter(String fileName, String format) throws FileNotFoundException, UnsupportedEncodingException {
        return new PrintWriter(fileName, format);
    }





    public static void main(String[] args) {
        try {

            TrigramInfo trieGramInfo = new TrigramInfo (args[0]);
            PrintWriter printWriter = printWriter(args[1], "UTF-8");
            System.out.println("Processing");
            while (trieGramInfo.hasNextTriGram()) {
                TrigramInfo.Trigram trieGram = trieGramInfo.getNextTrigram();
            }
            System.out.println("Generating wordGram and nGram CSV files...");
            for (TrigramInfo.Trigram trigram : TrigramInfo.trigrams) {
                printWriter.print(trigram.trigramString);
                printWriter.print(CSV_SEPARATOR);
                printWriter.print(trigram.occurrences);
                printWriter.println();
            }
            System.out.println("Done.");
        printWriter.close();
        catch (FileNotFoundException e) {
           System.out.println(e.getMessage());
       } catch (IOException e) {
           System.out.println(e.getMessage());
       }catch (IndexOutOfBoundsException e){
           System.out.println("Program arguments are incorrect...");
       } 
    }
}
