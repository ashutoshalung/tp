package quizmos;

import quizmos.command.Command;
import quizmos.common.Messages;
import quizmos.flashcardlist.FlashcardList;
import quizmos.storage.Storage;
import quizmos.parser.Parser;
import quizmos.ui.Ui;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class QuizMos {
    private static final String APP_LOGGER_NAME = "quizmos";
    private static final Logger logger = Logger.getLogger(QuizMos.class.getName());

    private FlashcardList flashcards;
    private Storage storage;



    public QuizMos() {
        try {
            setupLogging();
            this.flashcards = new FlashcardList();
            this.storage = new Storage("data/QuizMos.txt");
        } catch (Exception e) {
            Ui.respondError(e.getMessage());
        }
        try {
            flashcards = new FlashcardList(storage.load());
        } catch (Exception e) {
            Ui.respondError(e.getMessage());
            flashcards = new FlashcardList();
        }
    }

    private void setupLogging() {
        Logger appLogger = Logger.getLogger(APP_LOGGER_NAME);
        appLogger.setUseParentHandlers(false);
        try {
            FileHandler fileHandler = new FileHandler("quizmos.log", true);
            fileHandler.setLevel(Level.INFO);
            fileHandler.setFormatter(new SimpleFormatter());
            appLogger.addHandler(fileHandler);
            logger.log(Level.INFO, "quizmos.log initialized successfully");
        } catch (IOException e) {
            logger.log(Level.SEVERE, "quizmos.log initialization failed");
        }
    }

    /**
     *
     */
    public void run() {
        Ui.respond(Messages.greeting);
        boolean isExit = false;

        logger.log(Level.INFO, "quizmos main loop started");
        while (!isExit){
            try {
                String command = Ui.readCommand();
                logger.log(Level.INFO, "quizmos got user's command: " + command);
                Command c = Parser.parseCommand(command);
                logger.log(Level.INFO, "quizmos parsed user's command");
                c.execute(flashcards, storage);
                isExit = c.getIsExit();
            } catch (Exception e) {
                logger.log(Level.WARNING, "quizmos got exception: " + e.getMessage());
                Ui.respondError(e.getMessage());
            }
        }
        logger.log(Level.INFO, "quizmos main loop ended");
        System.exit(0);
    }

    public static void main(String[] args) {
        if (args.length > 0 && args[0].equals("--test")) {
            Ui.isTestMode = true;
        }
        new QuizMos().run();
    }
}
