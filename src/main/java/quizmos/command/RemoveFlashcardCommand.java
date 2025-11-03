package quizmos.command;

import quizmos.common.FlashcardMessages;
import quizmos.common.Messages;
import quizmos.exception.QuizMosInputException;
import quizmos.flashcard.Flashcard;
import quizmos.flashcardlist.FlashcardList;
import quizmos.storage.Storage;
import quizmos.ui.Ui;

public class RemoveFlashcardCommand extends Command {
    int index = -1; // default index is false
    private boolean isValid = true;

    public RemoveFlashcardCommand(String arg) throws QuizMosInputException {
        try {
            int oneBasedIndex = Integer.parseInt(arg.trim());
            this.index = oneBasedIndex - 1; // convert to zero-based
            if (this.index < 0) {
                throw new QuizMosInputException(FlashcardMessages.invalidIndexMessage);
            }
        } catch (QuizMosInputException e) {
            throw e;
        } catch (Exception e) {
            throw new QuizMosInputException(FlashcardMessages.invalidIndexMessage);
        }
    }

    @Override
    public void execute(FlashcardList flashcards, Storage storage) throws Exception {
        assert flashcards != null : "FlashcardList should not be null";

        if (!isValid || index < 0 || index >= flashcards.getSize()) {
            throw new QuizMosInputException(FlashcardMessages.invalidIndexMessage);
        }

        assert storage != null : "Storage should not be null";

        Flashcard deletedFlashcard = flashcards.getFlashcard(index);
        Ui.respond(FlashcardMessages.showFlashcardRemoved(deletedFlashcard));
        flashcards.removeFlashcard(index);
        flashcards.removeStarredFlashcard(deletedFlashcard);
        storage.writeToFile(flashcards);

    }
}
