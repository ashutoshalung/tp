package quizmos.common;

public class FlashcardListMessages {

    public static final String EMPTY_LIST_MESSAGE = "Your flashcard list is empty!";
    public static final String EMPTY_STARRED_LIST = "You have no starred flashcards.";
    public static final String STARRED_LIST_MESSAGE = "These are your starred flashcards: \n";

    public static String showStarredFlashcardsList(String fullStarredFlashcardString) {
        return STARRED_LIST_MESSAGE + fullStarredFlashcardString;
    }
}
