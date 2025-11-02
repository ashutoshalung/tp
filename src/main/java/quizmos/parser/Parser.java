package quizmos.parser;

import quizmos.command.AddFlashcardCommand;
import quizmos.command.Command;
import quizmos.command.ExitCommand;
import quizmos.command.GetStarredCommand;
import quizmos.command.HelpCommand;
import quizmos.command.InvalidCommand;
import quizmos.command.ListCommand;
import quizmos.command.RemoveFlashcardCommand;
import quizmos.command.ReviewCommand;
import quizmos.command.SearchFlashcardCommand;
import quizmos.command.StarCommand;
import quizmos.exception.QuizMosInputException;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Parser {
    private static final Logger logger = Logger.getLogger(Parser.class.getName());

    /**
     * Parse user's raw command and return the Command object
     *
     * @param command raw command from user
     * @return suitable Command object
     */
    public static Command parseCommand(String command) throws QuizMosInputException {
        String trimmed = command.trim();
        String[] parts = trimmed.split(" ", 2);
        String commandKeyword = parts[0].toLowerCase();

        logger.log(Level.INFO, "parsed keyword: " +  commandKeyword);

        try {
            return switch (commandKeyword) {
            case "help" -> new HelpCommand();
            case "exit" -> new ExitCommand();
            case "list" -> new ListCommand();
            case "add" -> new AddFlashcardCommand(parts);
            case "delete" -> new RemoveFlashcardCommand(parts[1].trim());
            case "review" -> new ReviewCommand(command);
            case "search" -> new SearchFlashcardCommand(parts[1].trim());
            case "star" -> new StarCommand(parts[1].trim());
            case "getstarred" -> new GetStarredCommand();
            default -> new InvalidCommand();
            };
        } catch (ArrayIndexOutOfBoundsException e) {
            logger.log(Level.WARNING, "parser got exception: " + e.getMessage());
            return new InvalidCommand();
        }
    }
}
