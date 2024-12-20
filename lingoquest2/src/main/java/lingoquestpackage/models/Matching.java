package lingoquestpackage.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

/**
 * Represents a matching question where the user is asked to match a word with its translation or definition.
 * This class extends the abstract Question class and implements specific behaviors for matching questions.
 */
public class Matching extends Question {
    String language;
    //private HashMap<Word,Word> answerMap;
    private ArrayList<Word> choices;
    private ArrayList<Word> shuffledChoices;
    private Word correctAnswer;
    private String userAnswer;
    private int coinValue = 100;
    private int pointValue = 100;
   
    /**
     * Constructs a Matching question with a specific language and a list of possible answer choices.
     * 
     * @param language The language for which the question is being created.
     * @param answerChoices A list of Word objects that are the possible choices for the question.
     */
    public Matching(String language, ArrayList<Word> answerChoices) {
        super(language);
        this.choices = answerChoices;
        
    }
    public void setCorrectAnswer(Word correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
   
    private ArrayList<Word> shuffle(ArrayList<Word> answerChoices) {
        shuffledChoices = new ArrayList<Word>();
        for(Word w : answerChoices) {
            shuffledChoices.add(w);
        }
        Collections.shuffle(shuffledChoices);
        shuffledChoices = shuffle(answerChoices);
        return shuffledChoices;
    }

    // made this for the front end so that the page can be
    // setup depending on the question type
    public String getType() {
        return "Matching";
    }

    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }

    public boolean isMatchCorrect(Word aWord, User user) {
        if(aWord.equals(userAnswer)) {
            user.getUserDictionary().getWordByUUID(aWord.getWordUUID()).wordPresented(true);
            return true;
        } else {
            user.getUserDictionary().getWordByUUID(aWord.getWordUUID()).wordPresented(false);
            return false;
        }
    }

   
    /**
     * Provides a formatted string representing the question and its choices.
     * 
     * @return A string that lists the word to match along with all possible choices.
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        //result.append("Match the English word " + words.get(answerIndex).getEnglishVersion() + " to one of the following words\n");
        //for(int i = 0; i < words.size();i++) {
        //    result.append(i+1).append(". ").append(words.get(i).getWordinLanguage()).append("\n");
        //}
        //Collections.shuffle(words);
        /*for(int i = 0; i < words.size();i++) {
            //result.append(i+5).append(". ").append(words.get(i).getEnglishVersion()).append("\n");
            
        }*/
        return result.toString();
    }
    /**
     * This method is supposed to determine the correctness of the user's overall interaction in a more complex scenario.
     * It is currently overridden to always return true, but should ideally contain logic to verify user answers.
     * 
     * @param user The user object to which the game progress is attributed.
     * @return Always returns true in the current implementation.
     */

    @Override
    public boolean isCorrect(User user) {
        if (userAnswer.toLowerCase().trim().equals(correctAnswer.getEnglishVersion().toLowerCase().trim())) {
            user.getUserDictionary().getWordByUUID(correctAnswer.getWordUUID()).wordPresented(true);
            user.addCoins(coinValue);
            user.getCurrentLanguage().increaseAnswerStreak();
            return true;
        } else {
            user.getUserDictionary().getWordByUUID(correctAnswer.getWordUUID()).wordPresented(false);
            return false;
        }
    }

    @Override
    public ArrayList<Word> getAnswerChoices() {
        // current intuition is to send these, might need to change - cade December 5
        return this.choices;
    }

    public ArrayList<Word> getShuffledChoices() {
        return this.shuffledChoices;
    }
   

    public Word getCorrectAnswer() {
        return null;
    }
}
