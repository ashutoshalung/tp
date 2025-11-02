package quizmos.command;

import quizmos.flashcard.Flashcard;
import quizmos.flashcardlist.FlashcardList;
import quizmos.storage.Storage;
import quizmos.ui.Ui;

import java.util.ArrayList;

public class GetStarCommand extends Command {
    @Override
    public void execute(FlashcardList flashcards, Storage storage) {
        String starredFlashcardsList = flashcards.getStarredFlashcards();
        Ui.showStarredFlashcardsList(starredFlashcardsList);

    }
}
