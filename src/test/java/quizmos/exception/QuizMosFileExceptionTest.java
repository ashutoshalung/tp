package quizmos.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class QuizMosFileExceptionTest {

    @Test
    void testConstructorStoresMessage() {
        String message = "File not found";
        QuizMosFileException exception = new QuizMosFileException(message);

        assertEquals(message, exception.getMessage());
        assertInstanceOf(QuizMosException.class, exception);
    }

    @Test
    void testConstructorWithNullMessage() {
        QuizMosFileException exception = new QuizMosFileException(null);
        assertEquals(null, exception.getMessage());
    }
}
