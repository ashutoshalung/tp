package quizmos.command;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

class HelpCommandTest {
    @Test
    void helpCommand_constructor_isExitSetToFalse(){
        HelpCommand helpCommand = new HelpCommand();
        assertFalse(helpCommand.getIsExit());
    }

    @Test
    void execute_shouldCallUiRespond() {
        HelpCommand command = new HelpCommand();
        command.execute(null, null);
    }
}
