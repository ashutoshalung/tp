package quizmos.parser;

import org.junit.jupiter.api.Test;
import quizmos.command.Command;
import quizmos.command.ExitCommand;
import quizmos.command.HelpCommand;
import quizmos.command.AddFlashcardCommand;
import quizmos.command.RemoveFlashcardCommand;
import quizmos.command.SearchFlashcardCommand;
import quizmos.exception.QuizMosInputException;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

class ParserTest {
    @Test
    void parseCommand_validCommand_correctCommandType() throws QuizMosInputException {
        // help command
        Command helpCommand = Parser.parseCommand("help");
        assertInstanceOf(HelpCommand.class, helpCommand);
        assertFalse(helpCommand.getIsExit());

        Command untrimHelpCommand = Parser.parseCommand("   help   ");
        assertInstanceOf(HelpCommand.class, untrimHelpCommand);
        assertFalse(untrimHelpCommand.getIsExit());

        // exit command
        Command exitCommand = Parser.parseCommand("exit");
        assertInstanceOf(ExitCommand.class, exitCommand);
        assertTrue(exitCommand.getIsExit());

        Command untrimExitCommand = Parser.parseCommand("   exit   ");
        assertInstanceOf(ExitCommand.class, untrimExitCommand);
        assertTrue(untrimExitCommand.getIsExit());
    }

    @Test
    void parseCommand_validAddCommand_returnsAddFlashcardCommand() throws QuizMosInputException {
        Command command = Parser.parseCommand("add q/What is Java? a/A programming language");
        assertInstanceOf(AddFlashcardCommand.class, command);
    }

    @Test
    void parseCommand_addCommandMissingArgs_throwsQuizMosInputException() {
        // missing both q/ and a/
        assertThrows(QuizMosInputException.class, () -> Parser.parseCommand("add something random"));

        // missing a/
        assertThrows(QuizMosInputException.class, () -> Parser.parseCommand("add q/What is Java?"));

        // missing q/
        assertThrows(QuizMosInputException.class, () -> Parser.parseCommand("add a/A programming language"));

        // missing both q/ and a/, only "add" command
        assertThrows(QuizMosInputException.class, () -> Parser.parseCommand("add"));
    }

    @Test
    void parseCommand_validDeleteCommand_returnsRemoveFlashcardCommand() throws QuizMosInputException {
        Command command = Parser.parseCommand("delete 1");
        assertInstanceOf(RemoveFlashcardCommand.class, command);
    }

    @Test
    void parseCommand_deleteCommandMissingOrInvalidIndex_returnsRemoveFlashcardCommand() throws QuizMosInputException {
        // missing index
        assertThrows(QuizMosInputException.class, () -> Parser.parseCommand("delete"));

        // not a number
        assertThrows(QuizMosInputException.class, () -> Parser.parseCommand("delete abc"));

        // negative number
        assertThrows(QuizMosInputException.class, () -> Parser.parseCommand("delete -3"));

        // extra spaces
        assertThrows(QuizMosInputException.class, () -> Parser.parseCommand("delete       "));
    }

    @Test
    void parseCommand_validSearchCommand_returnsSearchFlashcardCommand() throws QuizMosInputException {
        Command command = Parser.parseCommand("search java");
        assertInstanceOf(SearchFlashcardCommand.class, command);

        Command spacedCommand = Parser.parseCommand("   search   oop   ");
        assertInstanceOf(SearchFlashcardCommand.class, spacedCommand);
    }

    @Test
    void parseCommand_searchCommandMissingKeyword_returnsInvalidCommand() {
        // no keyword
        assertThrows(QuizMosInputException.class, () -> Parser.parseCommand("search"));

        // only spaces after search
        assertThrows(QuizMosInputException.class, () -> Parser.parseCommand("search   "));
    }

    @Test
    void parseCommand_invalidCommand_throwQuizMosInputException() {
        // empty string
        assertThrows(QuizMosInputException.class, () -> Parser.parseCommand(""));

        // string only contains space
        assertThrows(QuizMosInputException.class, () -> Parser.parseCommand(" "));

        // invalid alphabet string
        assertThrows(QuizMosInputException.class, () -> Parser.parseCommand("abc"));

        // invalid numeric string
        assertThrows(QuizMosInputException.class, () -> Parser.parseCommand("123123123"));

        // invalid symbol string
        assertThrows(QuizMosInputException.class, () -> Parser.parseCommand("$@#$@%"));
    }
}
