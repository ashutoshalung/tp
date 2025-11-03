package quizmos.storage;
import quizmos.exception.QuizMosFileException;
import quizmos.exception.QuizMosInputException;
import quizmos.flashcard.Flashcard;
import quizmos.flashcardlist.FlashcardList;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Storage {
    private static final Logger logger = Logger.getLogger("quizmos");
    private final String filePath;

    /**
     * Constructs a Storage object for the given file path.
     * Ensures the file exists and is ready for read/write.
     */
    public Storage(String filePath) throws QuizMosFileException {
        assert filePath != null && !filePath.trim().isEmpty() : "File path must not be null or empty";

        this.filePath = filePath;
        logger.log(Level.INFO, "Initializing storage for file path: {0}", filePath);
        ensureFilePathExists();
    }

    /**
     * Ensures that the file and its parent directories exist.
     * Creates them if missing.
     */
    private void ensureFilePathExists() throws QuizMosFileException {
        try {
            File file = new File(filePath);
            File parentDir = file.getParentFile();

            if (parentDir != null && !parentDir.exists()) {
                boolean dirCreated = parentDir.mkdirs();
                logger.log(Level.INFO, "Created parent directories: {0}", dirCreated);
            }

            if (!file.exists()) {
                boolean fileCreated = file.createNewFile();
                logger.log(Level.INFO, "Created new storage file: {0}", fileCreated);
            }

            assert file.exists() : "File creation failed unexpectedly";
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error ensuring file path exists: {0}", e.getMessage());
            throw new QuizMosFileException("Error initializing storage file: " + e.getMessage());
        }
    }

    /**
     * Loads flashcards from file into a list.
     * Each line must be in "question | answer | Starred" format (last part optional).
     */
    public ArrayList<Flashcard> load() throws QuizMosFileException {
        logger.log(Level.INFO, "Loading flashcards from file: {0}", filePath);
        ArrayList<Flashcard> listOfFlashcards = new ArrayList<>();
        File file = new File(filePath);

        // Pattern: question | answer | Starred (optional)
        Pattern pattern = Pattern.compile(
                "^\\s*(?<question>[^|]+?)\\s*\\|\\s*(?<answer>[^|]+?)\\s*(?:\\|\\s*(?<star>[^|]*))?\\s*$"
        );

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String nextLine = scanner.nextLine().trim();

                if (nextLine.isEmpty()) {
                    logger.log(Level.FINE, "Skipping empty line in file");
                    continue;
                }

                Matcher matcher = pattern.matcher(nextLine);
                if (!matcher.matches()) {
                    logger.log(Level.WARNING, "Skipping invalid line: {0}", nextLine);
                    continue;
                }

                String question = matcher.group("question").trim();
                String answer = matcher.group("answer").trim();
                String starGroup = matcher.group("star");

                Flashcard flashcard = new Flashcard(question, answer);
                if (starGroup != null && starGroup.equalsIgnoreCase("Starred")) {
                    flashcard.toggleStar();
                }

                listOfFlashcards.add(flashcard);
            }

            assert listOfFlashcards != null : "Flashcard list must not be null after load";
            logger.log(Level.INFO, "Successfully loaded {0} flashcards", listOfFlashcards.size());
            return listOfFlashcards;
        } catch (FileNotFoundException e) {
            logger.log(Level.SEVERE, "File not found during load: {0}", e.getMessage());
            throw new QuizMosFileException("Could not find flashcard file: " + e.getMessage());
        }
    }

    /**
     * Writes all flashcards to file, overwriting existing content.
     */
    public void writeToFile(FlashcardList flashcards) throws QuizMosInputException {
        assert flashcards != null : "Flashcard list must not be null before writing";

        logger.log(Level.INFO, "Writing flashcards to file: {0}", filePath);
        try (FileWriter fw = new FileWriter(filePath, false)) { // overwrite mode
            for (Flashcard flashcard : flashcards) {
                fw.write(flashcard.toSaveFormat() + System.lineSeparator());
            }
            logger.log(Level.INFO, "Successfully wrote {0} flashcards to file", flashcards.getSize());
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error writing flashcards: {0}", e.getMessage());
            throw new QuizMosInputException("Error saving flashcards: " + e.getMessage());
        }
    }
}
