package quizmos.command;

import quizmos.common.FlashcardMessages;
import quizmos.exception.QuizMosInputException;
import quizmos.flashcard.Flashcard;
import quizmos.flashcardlist.FlashcardList;
import quizmos.storage.Storage;
import quizmos.ui.Ui;

import java.util.logging.Level;
import java.util.logging.Logger;

public class StarCommand extends Command {
    private static final Logger logger = Logger.getLogger("quizmos");
    private final int index;

    public StarCommand(String index) {
        this.index = Integer.parseInt(index) - 1;
    }

    @Override
    public void execute(FlashcardList flashcards, Storage storage) throws Exception {
        // --- Assertions: sanity checks for developer ---
        assert flashcards != null : "FlashcardList must not be null";
        assert storage != null : "Storage must not be null";
        assert index >= 0 : "Index must be non-negative";

        logger.log(Level.INFO, "Executing StarCommand with index: " + index);

        // --- Validate index range ---
        if (index > flashcards.getSize()-1) {
            logger.log(Level.WARNING, "Index out of range: " + index);
            throw new QuizMosInputException("Index is out of range!");
        }

        // --- Retrieve flashcard ---
        Flashcard starredFlashcard = flashcards.getFlashcard(index);
        assert starredFlashcard != null : "Retrieved flashcard should not be null";

        // --- Check if already starred ---
        if (starredFlashcard.checkIsStarred()) {
            logger.log(Level.WARNING, "Flashcard already starred at index " + index);
            throw new QuizMosInputException("This flashcard is already starred!");
        }

        // --- Star and persist ---
        starredFlashcard.toggleStar();
        flashcards.addStarredFlashcard(starredFlashcard);
        Ui.respond(FlashcardMessages.showStarredFlashcard(starredFlashcard));
        logger.log(Level.INFO, "Starred flashcard: " + starredFlashcard.toString());

        // --- Save changes ---
        storage.writeToFile(flashcards);
        logger.log(Level.INFO, "Updated storage after starring flashcard.");
    }
}
