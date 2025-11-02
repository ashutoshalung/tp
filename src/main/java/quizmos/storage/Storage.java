package quizmos.storage;

import quizmos.exception.QuizMosFileException;
import quizmos.exception.QuizMosInputException;
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

    public Storage(String filePath) throws QuizMosFileException {
        this.filePath = filePath;
        ensureFilePathExists();
    }

    /**
     * Ensures that the file and its parent directories exist.
     * If not, creates them.
     */
    private void ensureFilePathExists() throws QuizMosFileException {
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
            throw new QuizMosFileException("Error loading tasks from file: " + e.getMessage());
        }
    }

    /**
     * Loads flashcards from file into a list.
     * Lines must be in "question | answer" format, but extra spaces are ignored.
     *
     * @return list of flashcards
     * @throws FileNotFoundException if file is missing (should not happen)
     */
    public ArrayList<Flashcard> load() throws QuizMosFileException {
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
                Flashcard flashcard = new Flashcard(question, answer);
                if (parts.length >= 3) {
                    String starMark = parts[2].trim();
                    if (starMark.equalsIgnoreCase("Starred")) {
                        flashcard.toggleStar();
                    }
                }
                listOfFlashcards.add(flashcard);
            }
            return listOfFlashcards;
        } catch (FileNotFoundException e) {
            throw new QuizMosFileException(e.getMessage());
        }
    }

    /**
     * Writes all flashcards to file, overwriting existing content.
     *
     * @param flashcards FlashcardList to save
     * @throws IOException if writing fails
     */
    public void writeToFile(FlashcardList flashcards) throws QuizMosInputException {
        try (FileWriter fw = new FileWriter(filePath, false)) { // overwrite
            for (Flashcard flashcard : flashcards) {
                fw.write(flashcard.toSaveFormat() + System.lineSeparator());
            }
        } catch (IOException e) {
            throw new QuizMosInputException("Error writing flashcards to file: " + e.getMessage());
        }
    }
}
