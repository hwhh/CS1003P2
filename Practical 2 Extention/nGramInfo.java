package com.company;

import java.io.*;
import java.util.*;

public class nGramInfo {




    public class nGram implements Comparable<nGramInfo.nGram>{
        public String nGramString;
        public Integer occurrences;
        @Override
        public int compareTo(nGramInfo.nGram o) {
            return o.occurrences.compareTo(this.occurrences);
        }
    }



    public  List<nGramInfo.nGram> nGrams, wordGrams;
    public List<String> allWords;
    protected BufferedReader bufferedReader;
    protected nGramInfo.nGram nextNGram, newNGram;


     protected int nGram;
    private static final String UNDERSCORE = "_";
    private static final String SPACE = " ";


    public void findNGram(String word){
        //If the word input has more character in it than the specified n-gram value
        if (word.length()>nGram) {
            checkNGramOccurrences(word.substring(0, nGram),nGrams );
            findNGram(word.substring(1, word.length()));//Remove the first character then hand the result back to the function
        }
        else if(word.length() == nGram-1) {
            word += UNDERSCORE;
            checkNGramOccurrences(word, nGrams);
        }
        else if(word.length() == nGram) {
            checkNGramOccurrences(word, nGrams);
        }
    }



    public void findWordGram(List words){
        String nWordGram = "";
        //If the array size has more letters in than specified n-gram input
        if (words.size() >= nGram) {
            for (int i = 0; i < nGram; i++) {
                //Takes the first n letter from the array and add them to a string
                if (i==nGram-1)
                    nWordGram += words.get(i).toString();//Dob't add a space on to the end of the string
                else
                    nWordGram += words.get(i).toString() + SPACE;
            }
            checkNGramOccurrences(nWordGram, wordGrams);
            words.remove(0);//Remove the first word from the array
            }
        }


    //This is a universal function which checks for the occurrences of each n-gram
    public void checkNGramOccurrences(String inputNGram, List<nGramInfo.nGram> list) {

        //Loop through each object in the input list
        for (nGramInfo.nGram currentObject  : list) {
            /*If the current objects attribute 'nGramString' matches the input string
            the occurrences for that input string are updates*/
            if (currentObject.nGramString.equals(inputNGram)) {
                currentObject.occurrences++;
                return;
            }
        }
        //If the input string was not in the array a new object is created and the added to the array
        newNGram = new nGram();
        newNGram.nGramString = inputNGram;
        newNGram.occurrences=1;
        list.add(newNGram);

    }

    //Get the next line of the input file and add contents to array
    private void findNextNGram() throws IOException {
        String line = bufferedReader.readLine();
        //Check if there in a next line in the input file
        if (line != null) {
            //Add each individual word to an array and remove all characters other than lowercase letters
            String[]  words = line.replaceAll("[^a-z^A-Z ]", "").toLowerCase().split(" +");
            //Create a new temporary nGram object
            nextNGram = new nGram();
            for (String word : words) {
                //Loop for each word in the words array
                if (!word.equals("")){
                    //If the word in the array is not blank
                    findNGram(word);
                    allWords.add(word);
                }
            }

        }
        else {
            /*Because word-grams in theory could go over multiple lines, i first needed to read every word in the
            input file and add it to an array, then with all the words in the array i can look for word-grams
              */
            while (allWords.size()>nGram) {
                findWordGram(allWords);
            }
            //Sort both list arrays
            Collections.sort(nGrams);
            Collections.sort(wordGrams);
        }
    }

    //Constructor initiates the temporary nGram object
    public nGramInfo(String inputFileName, int nGram) throws FileNotFoundException {
        bufferedReader = new BufferedReader(new FileReader(inputFileName));
        nGrams = new ArrayList<> ();
        allWords = new ArrayList<> ();
        wordGrams = new ArrayList<> ();
        this.nGram = nGram;
    }

    //Check to see whether there are any more nGrams in the input file
    public boolean hasNextNGram() throws IOException {
        if (nextNGram == null)
            findNextNGram();
        return (nextNGram != null);
    }


    //Clear the nGram object
    public nGramInfo.nGram getNextNGram() {
        nGramInfo.nGram nGram = nextNGram;
        nextNGram = null;
        return nGram;
    }




}
