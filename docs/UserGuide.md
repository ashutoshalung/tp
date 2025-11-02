# Quizmos User Guide

```
 ___        _     __  __
/ _ \ _   _(_)___|  \/  | ___  ___
| | | | | | | |_  / |\/| |/ _ \/ __|
| |_| | |_| | |/ /| |  | | (_) \__ \
\__\_\\__,_|_/___|_|  |_|\___/|___/
```

---

## Introduction

**QuizMos** is a simple **command-line flashcard manager** that helps you create, review, and organize flashcards for effective studying and memorization.

It allows you to add, list, and remove flashcards—each containing a question and an answer—all directly from your terminal.

QuizMos automatically saves your flashcards to a file, ensuring your collection is preserved and ready every time you run the program.

---

## Quick Start 

1. Ensure you have Java `17` or above installed in your Computer.<br>
   **Mac users:** Ensure you have the precise JDK version prescribed [here](https://se-education.org/guides/tutorials/javaInstallationMac.html).
2. Download the latest `.jar` file from [here](https://github.com/AY2526S1-CS2113-T11-1/tp/releases).
3. Copy the file to the folder you want to use as the _home folder_ for your QuizMos.
4. Open a command terminal, cd into the folder you put the jar file in, and use the `java -jar quizmos.jar` command to run the application.<br>
    A CLI similar to the below should appear. Note how the App gives you the first step.
    ```bash
    ____________________________________________________________
    Hello, I'm QuizMos
      ___        _     __  __
     / _ \ _   _(_)___|  \/  | ___  ___
    | | | | | | | |_  / |\/| |/ _ \/ __|
    | |_| | |_| | |/ /| |  | | (_) \__ \
     \__\_\\__,_|_/___|_|  |_|\___/|___/
    Try command `help` for more information
    ____________________________________________________________
    ```
5. Type some commands to execute it. Some example commands you can try:
   - `help`: Shows all command formats.
   - `list`: Lists all flashcards.
   - `add q/1+1 a/2`: Add your first flashcard. With question is "1+1" and answer is "2".
   - `exit`: Exits the app
6. Refer to the [Usage](#usage) below for details of each command.

---

## Features 

- Quizmos supports creating basic flashcards with text based questions and answers.
- **Marking flashcards as important/starred** for easy review.
---

## Usage

For now, Quizmos only supports creating basic flashcards with text based questions and answers.

Notes about the command format:
- Words in `UPPERCASE` are the parameters to be supplied by the user.
- Items in square brackets are optional
- Parameters MUST be in order
- Extraneous parameters for commands that do not take in parameters (`help`, `list`, `exit`...) will be ignored.
- If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.

### View help: `help`
Shows a list of command

Format: `help`

### List all flashcards: `list`
Shows a list of flashcards

Format: `list`

### Add a flashcard: `add`
Add flashcard with a question and answer

Format: `add q/QUESTION a/ANSWER`

### Delete a flashcard: `delete`

Delete a flashcard with its index, can check by `list` command

Format: `delete INDEX`

### Search for flashcards: `search`
Search flashcards by keyword or keyphrase

Format: `search KEYPHRASE`

### Review: `review`

To begin the review:

Format: `review m/[FLIP | TF | MCQ]`
- the mode is case-insensitive
- `FLIP`: Simple flip mode
- `TF`: True/False mode
- `MCQ`: Multi-choices question mode

Example:
- `review m/FLIP`
- `review m/tf`
- `review m/mCq`

#### Simple flip mode
After each question showed, can select whether to show the answer (y/n), or quit the session

Format: `[Y | N | QUIT]`

#### True/false mode
After each question showed, type your answer (t/f) or quit the session

Format: `[T | F | QUIT]`
- `T` True
- `F` False

#### MCQ mode
This mode requires flashcard list has no less than 4 flashcards

After each question showed, type your answer (1/2/3/4) or quit the session

Format: `[1 | 2 | 3 | 4 | QUIT]`
- `1` Answer 1
- `2` Answer 2
- `3` Answer 3
- `4` Answer 4

### Star/flag flashcard: `star`
Star/flag a flashcard by its index
The starred flashcard is depicted in the stored file. 

Format: `star INDEX`



Example:
- `star 2`
- Output: `This flashcard is starred!`

### Exit the program: `exit`
Exit the program

Format: `exit`

---

## FAQ {not yet completed}

**Q**: {question} 

**A**: {your answer here}

---

## Command Summary

| Action | Format, Examples                | 
|--------|---------------------------------|
| Add    | `add q/QUESTION a/ANSWER`       |
| Delete | `delete INDEX`                  |
| Search | `search KEYPHRASE`              |
| Review | `review m/[FLIP  \| TF \| MCQ]` |
| Star   | `star INDEX`                    |
| List   | `list`                          |
| Exit   | `exit`                          |
| Help   | `help`                          |
