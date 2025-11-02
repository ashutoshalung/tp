package quizmos.command;

import quizmos.common.FlashcardMessages;
import quizmos.common.Messages;
import quizmos.exception.QuizMosException;
import quizmos.flashcard.Flashcard;
import quizmos.flashcardlist.FlashcardList;
import quizmos.storage.Storage;
import quizmos.ui.Ui;

public class RemoveFlashcardCommand extends Command {
    int index = -1; // default index is false
    private boolean isValid = true;

    public RemoveFlashcardCommand(String arg) {
        try {
            int oneBasedIndex = Integer.parseInt(arg.trim());
            this.index = oneBasedIndex - 1; // convert to zero-based
        } catch (NumberFormatException | NullPointerException e) {
            this.isValid = false;
        }
    }

    @Override
    public void execute(FlashcardList flashcards, Storage storage) throws Exception {
        assert flashcards != null : "FlashcardList should not be null";

        if (!isValid || index < 0 || index >= flashcards.getSize()) {
            throw new QuizMosException(FlashcardMessages.invalidIndexMessage);
        }

        assert storage != null : "Storage should not be null";

        Flashcard deletedFlashcard = flashcards.getFlashcard(index);
        Ui.respond(FlashcardMessages.showFlashcardRemoved(deletedFlashcard));
        flashcards.removeFlashcard(index);
        storage.writeToFile(flashcards);

    }
}
