package quizmos.command;

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
        Flashcard starredFlashcard = flashcards.getFlashcard(index);
        starredFlashcard.toggleStar();
        Ui.showStarredFlashcard();
        storage.writeToFile(flashcards);

    }
}
