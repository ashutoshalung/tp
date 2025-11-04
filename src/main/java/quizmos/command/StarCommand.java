package quizmos.command;

import quizmos.common.FlashcardMessages;
import quizmos.exception.QuizMosInputException;
import quizmos.flashcard.Flashcard;
import quizmos.flashcardlist.FlashcardList;
import quizmos.storage.Storage;
import quizmos.ui.Ui;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Command to star a flashcard by its index.
 * This command marks a flashcard as starred, adds it to the list of
 * starred flashcards in FlashcardList, displays the updated flashcard
 * to the user via the Ui, and persists the change in Storage.
 * Throws QuizMosInputException if the index is invalid or the flashcard
 * is already starred.
 * Logging is performed to track execution, errors, and persistence.
 */
public class StarCommand extends Command {
    private static final Logger logger = Logger.getLogger("quizmos");
    private final int index;

    /**
     * Constructs a StarCommand with the user-provided index.
     *
     * @param index the 1-based index of the flashcard to star (as String)
     *              internally converted to 0-based integer
     */
    public StarCommand(String index) {
        this.index = Integer.parseInt(index) - 1;
    }

    /**
     * Executes the StarCommand.
     *
     * @param flashcards the FlashcardList containing all flashcards; must not be null
     * @param storage    the Storage handler for persisting changes; must not be null
     * @throws QuizMosInputException if the index is out of range or flashcard is already starred
     * @throws Exception             if an unexpected error occurs during persistence
     */

    @Override
    public void execute(FlashcardList flashcards, Storage storage) throws Exception {
        // --- Assertions: sanity checks for developer ---
        assert flashcards != null : "FlashcardList must not be null";
        assert storage != null : "Storage must not be null";
        assert index >= 0 : "Index must be non-negative";

        logger.log(Level.INFO, "Executing StarCommand with index: " + index);

        // --- Validate index range ---
        if (index > flashcards.getSize() - 1 || index + 1 <=0) {
            logger.log(Level.WARNING, "Index out of range: " + index);
            throw new QuizMosInputException("Index is out of range!");
        }

        // --- Retrieve flashcard ---
        Flashcard flashcardToBeStarred = flashcards.getFlashcard(index);
        assert flashcardToBeStarred != null : "Retrieved flashcard should not be null";

        // --- Check if already starred ---
        if (flashcardToBeStarred.checkIsStarred()) {
            logger.log(Level.WARNING, "Flashcard already starred at index " + index);
            throw new QuizMosInputException("This flashcard is already starred!");
        }

        // --- Star and persist ---
        flashcardToBeStarred.toggleStar();
        flashcards.addStarredFlashcard(flashcardToBeStarred);
        String outputMessage = FlashcardMessages.showStarredFlashcard(flashcardToBeStarred);
        Ui.respond(outputMessage);
        logger.log(Level.INFO, "Starred flashcard: " + flashcardToBeStarred.toString());

        // --- Save changes ---
        storage.writeToFile(flashcards);
        logger.log(Level.INFO, "Updated storage after starring flashcard.");
    }
}
