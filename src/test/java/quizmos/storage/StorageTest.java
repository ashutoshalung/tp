package quizmos.storage;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import quizmos.exception.QuizMosFileException;
import quizmos.flashcard.Flashcard;
import quizmos.flashcardlist.FlashcardList;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StorageTest {

    private Path testDirPath;
    private Path testFilePath;
    private Storage testStorage;

    @BeforeEach
    void setUp() throws IOException, QuizMosFileException {
        testDirPath = Files.createTempDirectory("storageTest");
        testFilePath = testDirPath.resolve("flashcards.txt");

        testStorage = new Storage(testFilePath.toString());
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.walk(testDirPath)
                .sorted(Comparator.reverseOrder())
                .map(Path::toFile)
                .forEach(File::delete);
    }

    @Test
    void ensureFilePathExists_nonExistingFilePath_expectPathCreated() throws QuizMosFileException {
        String nonExistingFilePath = "./testFolder/testData.txt";
        new Storage(nonExistingFilePath);
        Path directory = Path.of("testFolder");
        Path file = Path.of("testFolder","testData.txt");
        assertTrue(Files.exists(directory));
        assertTrue(Files.exists(file));
    }

    @Test
    void load_fileEmpty_expectEmptyList() throws Exception {
        ArrayList<Flashcard> flashcards = testStorage.load();
        assertTrue(flashcards.isEmpty());
    }

    @Test
    void load_noSeparator_expectZeroParts() throws Exception {
        List<String> lines = Arrays.asList(
                "",
                "Invalid line without pipe"
        );
        Files.write(testFilePath, lines);

        List<Flashcard> testFlashCards = testStorage.load();

        assertEquals(0, testFlashCards.size());
    }

    @Test
    void load_oneSeparator_expectTwoParts() throws Exception {
        List<String> lines = Arrays.asList(
                "Question 1 | Answer 1 | ",
                "Question 2 | Answer 2 | Starred"
        );
        Files.write(testFilePath, lines);
        List<Flashcard> testFlashCards = testStorage.load();

        assertEquals(2, testFlashCards.size());
        assertEquals("Question 1", testFlashCards.get(0).getQuestion());
        assertEquals("Answer 1", testFlashCards.get(0).getAnswer());
        assertEquals("Question 2", testFlashCards.get(1).getQuestion());
        assertEquals("Answer 2", testFlashCards.get(1).getAnswer());
    }

    @Test
    void load_flashcardWithSpaces_expectTrimmedFlashcard() throws Exception {
        Files.write(testFilePath, Arrays.asList("   What is AI   |   Artificial Intelligence  |    "));

        List<Flashcard> loaded = testStorage.load();

        assertEquals(1, loaded.size());
        assertEquals("What is AI", loaded.get(0).getQuestion());
        assertEquals("Artificial Intelligence", loaded.get(0).getAnswer());
    }

    @Test
    void writeToFile_twoFlashcards_expectTwoLines() throws Exception {
        FlashcardList list = new FlashcardList();
        list.addFlashcard(new Flashcard("Q1", "A1"));
        list.addFlashcard(new Flashcard("Q2", "A2"));

        testStorage.writeToFile(list);

        List<String> lines = Files.readAllLines(testFilePath);
        assertEquals(2, lines.size());
        assertEquals("Q1 | A1 |", lines.get(0));
        assertEquals("Q2 | A2 |", lines.get(1));
    }

    @Test
    void writeToFile_oneFlashcard_expectOverwriting() throws Exception {
        Files.write(testFilePath, Arrays.asList("Old data line"));
        FlashcardList list = new FlashcardList();
        list.addFlashcard(new Flashcard("NewQ", "NewA"));

        testStorage.writeToFile(list);

        List<String> lines = Files.readAllLines(testFilePath);
        assertEquals(1, lines.size());
        assertEquals("NewQ | NewA |", lines.get(0));
    }
}
