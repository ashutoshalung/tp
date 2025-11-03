package quizmos.ui;

import java.util.Scanner;

import quizmos.common.FlashcardMessages;
import quizmos.common.Messages;
import quizmos.flashcard.Flashcard;

public class Ui {
    public static boolean isTestMode = false;
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_RESET = "\u001B[0m";

    private static final Scanner in = new Scanner(System.in);

    /**
     * Reads a line of input from the user.
     *
     * @return The user's input as a string.
     */
    public static String readCommand() {
        return in.nextLine();
    } 

    /**
     * Announce the message with UI format
     * @param message error message
     */
    public static void respond(String message) {
        System.out.println(Messages.separator);
        System.out.println(message);
        System.out.println(Messages.separator);
    }

    public static void printMessage(String message) {
        System.out.println(message);
        System.out.flush();
    }

    public static void printPrompt(String message) {
        System.out.print(message);
        System.out.flush();
    }

    public static void printError(String message) {
        String errorMessage;
        if (isTestMode) {
            errorMessage = "? ERROR: " + message;
        } else {
            errorMessage = ANSI_RED + "❌ ERROR: " + message + ANSI_RESET;
        }
        System.out.println(errorMessage);
        System.out.flush();
    }

    public static void respondError(String message) {
        Ui.printSeparator();
        Ui.printError(message);
        Ui.printSeparator();
    }

    public static void printSeparator() {
        System.out.println(Messages.separator);
    }

    public static void showFlashcardAdded(Flashcard flashcard) {
        String response = FlashcardMessages.addedFlashcardMessage + flashcard;
        respond(response);
    }

    public static void showFlashcardRemoved(Flashcard flashcard) {
        String response = FlashcardMessages.removedFlashcardMessage + flashcard;
        respond(response);
    }

    public static void showStarredFlashcardsList(String fullStarredFlashcardString) {
        respond(fullStarredFlashcardString);
    }

    public static void showStarredFlashcard(Flashcard flashcard) {
        respond(Messages.starredFlashcardMessage + flashcard.toString());
    }

    public static void showUnstarredFlashcard(Flashcard unstarredFlashcard) {
        respond(Messages.unStarredFlashcardMessage + unstarredFlashcard.toString());
    }
}
