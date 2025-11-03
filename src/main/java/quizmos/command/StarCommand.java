package quizmos.command;


import quizmos.exception.QuizMosInputException;
import quizmos.flashcard.Flashcard;
import quizmos.flashcardlist.FlashcardList;
import quizmos.storage.Storage;
import quizmos.ui.Ui;

import java.io.IOException;

/**
 *
 */
public class StarCommand extends Command{
    private final int index;

    /**
     * @param index
     */
    public StarCommand(String index) {
        this.index = Integer.parseInt(index)-1;
    }

    /**
     * @param flashcards
     * @param storage
     * @throws IOException
     */
    @Override
    public void execute(FlashcardList flashcards, Storage storage) throws Exception {
        if (index > flashcards.getSize() -1) {
            throw new QuizMosInputException("Index is out of range!");
        }
        Flashcard starredFlashcard = flashcards.getFlashcard(index);
        if (starredFlashcard.checkIsStarred()) {
            throw new QuizMosInputException("This flashcard is already starred!");
        } else {
            starredFlashcard.toggleStar();
            flashcards.addStarredFlashcard(starredFlashcard);
            Ui.showStarredFlashcard(starredFlashcard);
            storage.writeToFile(flashcards);
        }

    }
}
