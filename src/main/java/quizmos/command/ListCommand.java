package quizmos.command;

import quizmos.flashcardlist.FlashcardList;
import quizmos.storage.Storage;

public class ListCommand extends Command {
    /**
     * Executes the list command.
     * Shows all tasks if the task list is not empty.
     * Throws Exception if the task list is empty.
     *
     * @param storage    Storage instance (not used for this command)
     * @param flashcards containing all flashcards
     */

    @Override
    public void execute(FlashcardList flashcards, Storage storage) throws Exception {
        flashcards.showList();
    }
}
