package quizmos.command;

import quizmos.common.FlashcardListMessages;
import quizmos.flashcardlist.FlashcardList;
import quizmos.storage.Storage;
import quizmos.ui.Ui;
import quizmos.exception.QuizMosInputException;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Command to retrieve and display all starred flashcards.
 * This command reads the list of starred flashcards from the provided
 * FlashcardList and displays them to the user via the Ui class.
 * It does not modify the FlashcardList or Storage; it is a read-only command.
 * Logging is performed to track execution and display of starred flashcards.
 */

public class GetStarCommand extends Command {
    private static final Logger logger = Logger.getLogger("quizmos");

    /**
     * Executes the GetStarCommand.
     * @param flashcards the FlashcardList containing all flashcards; must not be null
     * @param storage    the Storage handler; must not be null (not used for persistence in this command)
     * @throws QuizMosInputException never thrown in current implementation; kept for method signature consistency
     */
    @Override
    public void execute(FlashcardList flashcards, Storage storage) throws QuizMosInputException {
        // --- Assertions: sanity checks for developer ---
        assert flashcards != null : "FlashcardList must not be null";
        assert storage != null : "Storage must not be null";

        logger.log(Level.INFO, "Executing GetStarCommand");

        // --- Retrieve starred flashcards ---
        String starredFlashcardsList = flashcards.getStarredFlashcardsString();

        // --- Assertions: internal consistency ---
        assert starredFlashcardsList != null : "getStarredFlashcardsString should not return null";

        // --- Display to user ---
        Ui.respond(FlashcardListMessages.showStarredFlashcardsList(starredFlashcardsList));
        logger.log(Level.INFO, "Displayed starred flashcards to user: " + starredFlashcardsList);
    }
}
