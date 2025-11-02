package quizmos.storage;

import quizmos.flashcard.Flashcard;
import quizmos.flashcardlist.FlashcardList;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {
    private final String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
        ensureFilePathExists();
    }

    /**
     * Ensures that the file and its parent directories exist.
     * If not, creates them.
     */
    private void ensureFilePathExists() {
        try {
            File file = new File(filePath);
            File parentDir = file.getParentFile();
            if (!parentDir.exists()) {
                parentDir.mkdirs(); // create directories if missing
            }
            if (!file.exists()) {
                file.createNewFile(); // create the file if it doesn't exist
            }
        } catch (IOException e) {
            throw new RuntimeException("Error initializing storage file: " + e.getMessage(), e);
        }
    }

    /**
     * Loads flashcards from file into a list.
     * Lines must be in "question | answer" format, but extra spaces are ignored.
     *
     * @return list of flashcards
     * @throws FileNotFoundException if file is missing (should not happen)
     */
    public ArrayList<Flashcard> load() throws FileNotFoundException {
        ArrayList<Flashcard> listOfFlashcards = new ArrayList<>();
        File file = new File(filePath);

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String nextLine = scanner.nextLine();
                String line = nextLine.trim();
                if (line.isEmpty()) {
                    continue; // skip empty lines
                }

                // Split on " | " with optional surrounding spaces
                String[] parts = line.split("\\s*\\|\\s*");
                if (parts.length < 2) {
                    continue; // skip invalid lines
                }
                String question = parts[0].trim();
                String answer = parts[1].trim();
                boolean isStarred = false;
                if (parts.length >= 3 && parts[2].trim().equalsIgnoreCase("Starred")) {
                    isStarred = true;
                }

                Flashcard flashcard = new Flashcard(question, answer);
                if (isStarred) {
                    flashcard.toggleStar();
                }



                listOfFlashcards.add(flashcard);
            }
        }

        return listOfFlashcards;
    }

    /**
     * Writes all flashcards to file, overwriting existing content.
     *
     * @param flashcards FlashcardList to save
     * @throws IOException if writing fails
     */
    public void writeToFile(FlashcardList flashcards) throws IOException {
        try (FileWriter fw = new FileWriter(filePath, false)) { // overwrite
            for (Flashcard flashcard : flashcards) {
                fw.write(flashcard.toSaveFormat() + System.lineSeparator());
            }
        } catch (IOException e) {
            throw new IOException("Error writing flashcards to file: " + e.getMessage(), e);
        }
    }
}
