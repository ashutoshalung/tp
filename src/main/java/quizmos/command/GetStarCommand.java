package quizmos.command;

import quizmos.QuizMos;
import quizmos.flashcardlist.FlashcardList;
import quizmos.storage.Storage;
import quizmos.ui.Ui;
import quizmos.exception.QuizMosInputException;

import java.util.logging.Level;
import java.util.logging.Logger;

public class GetStarCommand extends Command {
    private static final Logger logger = Logger.getLogger(QuizMos.class.getName());

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

        // --- Check for empty list and throw exception if needed ---
        if (starredFlashcardsList.isEmpty()) {
            logger.log(Level.WARNING, "No starred flashcards found.");
            throw new QuizMosInputException("You have no starred flashcards.");
        }

        // --- Display to user ---
        Ui.showStarredFlashcardsList(starredFlashcardsList);
        logger.log(Level.INFO, "Displayed starred flashcards to user: " + starredFlashcardsList);
    }
}
