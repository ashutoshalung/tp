package quizmos.common;

import quizmos.flashcard.Flashcard;

public class FlashcardMessages {
    public static String invalidAddCommand = "Invalid command! Try add q/<QUESTION> a/<ANSWER>";
    public static String addedFlashcardMessage = "Added this flashcard\n";
    public static String noMatchesMessage = "No matches found";
    public static String invalidIndexMessage = "Invalid index! Use a correct integer flashcard index!\n" +
            "Try `list` to see a list of valid flashcards";
    public static String removedFlashcardMessage = "Removed this flashcard\n";

    public static String showFlashcardAdded(Flashcard flashcard) {
        return addedFlashcardMessage + flashcard;
    }

    public static String showFlashcardRemoved(Flashcard flashcard) {
        return removedFlashcardMessage + flashcard;
    }

}
