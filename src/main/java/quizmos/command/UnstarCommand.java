package quizmos.command;

import quizmos.exception.QuizMosInputException;
import quizmos.flashcard.Flashcard;
import quizmos.flashcardlist.FlashcardList;
import quizmos.storage.Storage;
import quizmos.ui.Ui;

import java.io.IOException;

public class UnstarCommand extends Command {
    private final int index;

    /**
     * @param index
     */
    public UnstarCommand(String index) {
        this.index = Integer.parseInt(index) - 1;
    }

    /**
     * @param flashcards
     * @param storage
     * @throws IOException
     */
    @Override
    public void execute(FlashcardList flashcards, Storage storage) throws QuizMosInputException {
        Flashcard unstarredFlashcard = flashcards.getFlashcard(index);
        if (!unstarredFlashcard.checkIsStarred()) {
            Ui.showUnstarredFlashcard();
        } else {
            unstarredFlashcard.toggleStar();
            flashcards.removeStarredFlashcard(unstarredFlashcard);
            Ui.showUnstarredFlashcard(unstarredFlashcard);
            storage.writeToFile(flashcards);

        }
    }

}

