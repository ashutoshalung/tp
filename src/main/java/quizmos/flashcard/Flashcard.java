package quizmos.flashcard;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Flashcard {
    private static final Logger logger = Logger.getLogger(Flashcard.class.getName());

    private String question;
    private String answer;
    private boolean isStarred;
    private String starMarker;

    public Flashcard(String question, String answer) {
        this.question = question;
        this.answer = answer;
        this.isStarred = false;
        this.starMarker = "";

        logger.log(Level.INFO, "Created new flashcard with question: \"{0}\"", question);
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public boolean checkIsStarred() {
        return isStarred;
    }

    public void toggleStar() {
        this.isStarred = !this.isStarred;

        if (this.starMarker.equals("")) {
            this.starMarker = " Starred";

        } else {
            this.starMarker = "";
        }
    }


    public String toSaveFormat() {
        return getQuestion() + " | " + getAnswer() + " |" + starMarker;
    }

    public String toString() {
        return "Question: " + getQuestion() + " | " + "Answer: " + getAnswer() + " |" + starMarker;
    }
}
