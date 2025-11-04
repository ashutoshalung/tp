package quizmos.flashcardlist;

import quizmos.common.FlashcardListMessages;
import quizmos.flashcard.Flashcard;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FlashcardList implements Iterable<Flashcard> {
    private static final Logger logger = Logger.getLogger(FlashcardList.class.getName());

    private final ArrayList<Flashcard> flashcards;
    private ArrayList<Flashcard> starredFlashcards = new ArrayList<Flashcard>();


    public FlashcardList() {
        this.flashcards = new ArrayList<Flashcard>();
        logger.log(Level.INFO, "Initialized empty FlashcardList.");
        initialiseStarredFlashcards();
    }

    public FlashcardList(ArrayList<Flashcard> flashcards) {
        this.flashcards = flashcards;
        logger.log(Level.INFO, "Initialized FlashcardList with {0} flashcards.", flashcards.size());
        initialiseStarredFlashcards();
    }

    public ArrayList<Flashcard> getFlashcards() {
        return flashcards;
    }

    public void addFlashcard(Flashcard flashcard) {
        logger.log(Level.INFO, "Added flashcard: {0}", flashcard.toString());
        flashcards.add(flashcard);
    }

    public void removeFlashcard(int index) {
        Flashcard removed = flashcards.remove(index);
        logger.log(Level.INFO, "Removed flashcard: {0}", removed.toString());
    }

    public Flashcard getFlashcard(int index) {
        Flashcard f = flashcards.get(index);
        logger.log(Level.FINE, "Retrieved flashcard: {0}", f.toString());
        return f;
    }

    public int getSize() {
        return flashcards.size();
    }

    public ArrayList<Flashcard> getStarredFlashcards() {
        return starredFlashcards;
    }

    public void initialiseStarredFlashcards() {
        for (Flashcard f : flashcards) {
            if (f.checkIsStarred()) {
                starredFlashcards.add(f);
            }
        }
        logger.log(Level.INFO, "Initialized starred flashcards list with {0} entries.", starredFlashcards.size());
    }

    public String getStarredFlashcardsString() {
        if (starredFlashcards.size() == 0) {
            return FlashcardListMessages.EMPTY_STARRED_LIST;
        }
        StringBuilder sb = new StringBuilder();
        int count = 1;
        for (Flashcard f : flashcards) {
            if (f.checkIsStarred()) {
                String item = count + ". " + f + "\n";
                sb.append(item);

            }
            count++;
        }
        // remove trailing newline
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 1);
        }
        return sb.toString();

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

    public void addStarredFlashcard(Flashcard starredFlashcard) {
        starredFlashcards.add(starredFlashcard);
        logger.log(Level.INFO, "Added to starred list: {0}", starredFlashcard.toString());
    }

    public void removeStarredFlashcard(Flashcard unstarredFlashcard) {
        if (unstarredFlashcard.checkIsStarred()) {
            starredFlashcards.remove(unstarredFlashcard);
        }
    }
}
