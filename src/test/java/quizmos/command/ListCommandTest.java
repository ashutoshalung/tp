package quizmos.command;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import quizmos.common.FlashcardListMessages;
import quizmos.flashcard.Flashcard;
import quizmos.flashcardlist.FlashcardList;
import quizmos.storage.Storage;
import quizmos.common.Messages;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ListCommandTest {

    private ListCommand listCommand;
    private FlashcardList flashcardList;
    private Storage dummyStorage;
    private ByteArrayOutputStream outContent;
    private PrintStream originalOut;

    @BeforeEach
    void setUp() throws IOException {
        listCommand = new ListCommand();
        flashcardList = new FlashcardList();
        Path tempFile = Files.createTempFile("dummy", ".txt");
        dummyStorage = new Storage(tempFile.toString());

        // Redirect System.out to capture printed output
        outContent = new ByteArrayOutputStream();
        originalOut = System.out;
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    void execute_printsEmptyListMessageWhenNoFlashcards() throws Exception {
        listCommand.execute(flashcardList, dummyStorage);

        String output = outContent.toString().trim();

        // Check that the output contains separators and the empty list message
        assertTrue(output.contains(Messages.separator), "Output should include separator line");
        assertTrue(output.contains(FlashcardListMessages.EMPTY_LIST_MESSAGE),
                "Output should include empty list message");
    }

    @Test
    void execute_printsFlashcardsWhenListIsNotEmpty() throws Exception {
        flashcardList.addFlashcard(new Flashcard("What is AI?", "Artificial Intelligence"));
        flashcardList.addFlashcard(new Flashcard("What is ML?", "Machine Learning"));

        listCommand.execute(flashcardList, dummyStorage);

        String output = outContent.toString();

        // Each flashcard should appear in the output
        assertTrue(output.contains("Question: What is AI?"), "Output should contain first flashcard question");
        assertTrue(output.contains("Answer: Artificial Intelligence"), "Output should contain first flashcard answer");
        assertTrue(output.contains("Question: What is ML?"), "Output should contain second flashcard question");
        assertTrue(output.contains("Answer: Machine Learning"), "Output should contain second flashcard answer");

        // Output should include separators printed by FlashcardList.showList()
        assertTrue(output.contains(Messages.separator), "Output should include separator lines");
    }

    @Test
    void execute_doesNotThrowAnyException() {
        assertDoesNotThrow(() -> listCommand.execute(flashcardList, dummyStorage));
    }
}
