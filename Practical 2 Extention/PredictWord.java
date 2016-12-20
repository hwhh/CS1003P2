package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;


public class PredictWord{


    public class word implements Comparable<PredictWord.word>{
        private String word;
        private Integer score;

        @Override
        public int compareTo(PredictWord.word o) {
            return o.score.compareTo(this.score);
        }
    }


    private ArrayList<PredictWord.word> allWords, possibleWords, top5;
    private word nextWord;
    private BufferedReader bufferedReader;
    public String inputNGram;

    //Constructor initiates the temporary nextWord object
    public PredictWord(BufferedReader bufferedReader, String inputNGram) {
        this.bufferedReader = bufferedReader;
        nextWord = new word();
        this.inputNGram = inputNGram;
        possibleWords= new ArrayList<>();
        allWords= new ArrayList<>();
    }


    public void findNGram (){
        boolean found = false;
        possibleWords.clear();
        for (PredictWord.word word : allWords) {
            /*if (word.word.contains(inputNGram)) {
                word.score=word.score/50;
                possibleWords.add(word);
                found=true;
            }*/
            //If there is an object in the array who's attribute 'word' starts with the n-gram input
            if(word.word.startsWith(inputNGram)){
                //Add the found word object to the possible words array
                possibleWords.add(word);
                found=true;
            }
        }
        //If an similar word to the input n-gram has been found
        if (found) {
            //Sorry the possible words array
            Collections.sort(possibleWords);
            //If there are more than 5 similar words found, only display the top 5
            if (possibleWords.size() > 5)
                top5 = new ArrayList<>(possibleWords.subList(0, 5));
            else
                top5 = new ArrayList<>(possibleWords);

            System.out.println("The predicted words based on the input '"+inputNGram+"'(in order of relevance) are:");
            for (PredictWord.word possibleWord : top5) {
                System.out.println(possibleWord.word);
            }
        }
        else if (!found)
            System.out.println("No predictions found");
    }



    //Get the next line of the input file and add contents to array
    public void read () throws IOException {
        String line;
        //Loop until the end of the file is reached
        while ((line = bufferedReader.readLine()) != null) {
            //If the line is not blank
            if (!line.trim().equals("")) {
                //Add all the words and scores in the file to appropriate array and remove unwanted characters
                String[] words = line.replaceAll("[^a-z^A-Z ]", "").split("\\s+");
                String[] scores = line.replaceAll("[^0-9]", "").split("\\s+");
                for (String word : words) {
                    nextWord.word = word;
                }
                for (String score : scores) {
                    nextWord.score=Integer.parseInt(score);
                }
                //Add the 'word' object to the array which has the word and corresponding score
                allWords.add(nextWord);
                //create a new object
                nextWord=new word();
            }
        }
        findNGram();
    }




}