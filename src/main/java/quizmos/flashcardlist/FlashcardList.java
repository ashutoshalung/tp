package quizmos.flashcardlist;

import quizmos.common.FlashcardListMessages;
import quizmos.flashcard.Flashcard;

import java.util.ArrayList;
import java.util.Iterator;

public class FlashcardList implements Iterable<Flashcard> {
    private final ArrayList<Flashcard> flashcards;

    public FlashcardList() {
        this.flashcards = new ArrayList<Flashcard>();
    }

    public FlashcardList(ArrayList<Flashcard> flashcards) {
        this.flashcards = flashcards;
    }

    public void addFlashcard(Flashcard flashcard) {
        flashcards.add(flashcard);
    }

    public void removeFlashcard(int index) {
        flashcards.remove(index);
    }

    public Flashcard getFlashcard(int index) {
        return flashcards.get(index);
    }

    public int getSize() {
        return flashcards.size();
    }

    public ArrayList<Flashcard> getStarredFlashcards() {
        ArrayList<Flashcard> starredFlashcards = new ArrayList<>();
        for (Flashcard f : flashcards) {
            if (f.checkIsStarred()) {
                starredFlashcards.add(f);
            }
        }
        return starredFlashcards;
    }

    /**
     * @return a String that is list of all flashcards
     */
    public String toString() {
        if (this.getSize() == 0) {
            return FlashcardListMessages.EMPTY_LIST_MESSAGE;
        }
        StringBuilder sb = new StringBuilder();
        int count = 1;

        int total = flashcards.size();

        for (Flashcard f : flashcards) {
            String item = count + ". " + f.toString();
            if (count != total) {
                item += "\n";
            }
            sb.append(item);
            count++;
        }
        return sb.toString();
    }

    @Override
    public Iterator<Flashcard> iterator() {
        return flashcards.iterator();
    }
}
