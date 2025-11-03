package quizmos.ui;

import java.util.Scanner;
import quizmos.common.Messages;

public class Ui {
    public static boolean isTestMode = false;
    public static java.util.function.Supplier<String> mockedCommandSupplier = null;
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_RESET = "\u001B[0m";

    private static final Scanner in = new Scanner(System.in);


    /**
     * Reads a line of input from the user.
     *
     * @return The user's input as a string.
     */
    public static String readCommand() {
        if (mockedCommandSupplier != null) {
            return mockedCommandSupplier.get();
        }
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


}
