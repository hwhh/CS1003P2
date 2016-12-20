package com.company;

import java.io.*;
import java.util.*;

public class TrigramInfo {



    public class Trigram implements Comparable<Trigram>{
        public String trigramString;
        public Integer occurrences;


        @Override
        public int compareTo(Trigram o) {
            return o.occurrences.compareTo(this.occurrences);
        }
    }



    public static List<Trigram> trigrams;
    protected BufferedReader bufferedReader;
    protected Trigram nextTrigram, newTriGram;
    private static final String UNDERSCORE = "_";


    /////////OLD CODE/////////////////
    /*public void findTrigram(String word){
        switch (word.length()) {
            case (0):
                break;
            case (1):
                checkOccurrences(word + "__");
                break;
            case (2):
                checkOccurrences(word + "_");
                break;
            case (3):
                checkOccurrences(word);
                break;
            default:
                checkOccurrences(word.substring(0, 3));
                findTrigram(word.substring(1 ,word.length()));
        }
    }
*/


    public void findTrigram(String word){
        if (word.length()>3) {
            checkOccurrences(word.substring(0, 3) );
            findTrigram(word.substring(1, word.length()));
        }
        else if (word.length()==3)
            checkOccurrences(word);
        else if(word.length() == 2) {
            word += UNDERSCORE;
            checkOccurrences(word);
        }
    }

    public void checkOccurrences(String inputTrigram) {
        for (Trigram trigram : trigrams) {
            if (trigram.trigramString.equals(inputTrigram)) {
                trigram.occurrences++;
                return;
            }
        }
        newTriGram = new Trigram();
        if (inputTrigram.length()<3)
            inputTrigram+="_";
        newTriGram.trigramString = inputTrigram;
        newTriGram.occurrences=1;
        trigrams.add(newTriGram);
    }




    private void findNextTrigram() throws IOException {
        String line = bufferedReader.readLine();
        if (line != null) {
            String[]  words = line.replaceAll("[^a-z^A-Z ]", "").toLowerCase().split("\\s+");
            nextTrigram = new Trigram();
            for (String word : words) {
                findTrigram(word);
            }

        }
        else
            Collections.sort(trigrams);
    }

    public TrigramInfo(String inputFileName) throws FileNotFoundException {
        bufferedReader = new BufferedReader(new FileReader(inputFileName));
        trigrams = new ArrayList<> ();
        ;
    }

    public boolean hasNextTriGram() throws IOException {
        if (nextTrigram == null)
            findNextTrigram();
        return (nextTrigram != null);
    }

    public Trigram getNextTrigram() {
        Trigram triGram = nextTrigram;
        nextTrigram = null;
        return triGram;
    }



}
