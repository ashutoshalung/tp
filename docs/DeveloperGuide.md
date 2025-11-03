


# Developer Guide

---

## Table of Contents

1. [Acknowledgements](#acknowledgements)
2. [Design](#design)
   - [Architecture](#architecture)
   - [UI Component](#ui-component)
   - [Logic Component](#logic-component)
   - [Model Component](#model-component)
   - [Main Component](#main-component)
   - [Storage Component](#storage-component)
   - [Commons Component](#commons-component)
   - [Overall Architecture Diagram](#overall-architecture-diagram)
3. [Implementation](#implementation)
   - [Feature 1: Add Flashcard](#feature-1-add-flashcard)
   - [Feature 2: Delete Flashcard](#feature-2-delete-flashcard)
   - [Feature 3: Search Flashcard](#feature-3-search-flashcard)
   - [Feature 4: Review Flashcards](#feature-4-review-flashcards)
   - [Feature 5: Star Flashcard](#feature-5-star-flashcard)
   - [Feature 6: Unstar Flashcard](#feature-6-unstar-flashcard)
   - [Feature 7: Get Starred Flashcards](#feature-7-getstar-flashcard)
4. [Requirements](#requirements)
   - [Use Cases](#use-cases)
   - [Non-Functional Requirements (NFRs)](#non-functional-requirements-nfrs)
   - [Glossary](#glossary)
5. [Instructions for Manual Testing](#instructions-for-manual-testing)

---

## Acknowledgements

- [JUnit 5](https://junit.org/) - For unit testing.
- [Gradle](https://gradle.org/) - For build automation.
- [PlantUML](https://plantuml.com/) - For creating UML diagrams.

---

## Design

### Architecture

![Overall Architect](images/OverallArchitect.png "Overall Architect")

The **_Architecture Diagram_** given above explains the high-level design of the App.

- `Main`: Launch app and shut down
  - At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
  - At shut down, it shuts down the other components.
- `UI`: The UI of the App, in charge of print out information in format.
- `Logic`: The Command parser and executor.
- `Model`: Stores data of the App in memory.
- `Storage`: Reads data from, and writes data to, the hard disk.

The sections below give more details of each component

### UI Component

**Responsibilities:**
- Handles all user interactions via the console.
- Reads user input commands for processing by the `Logic` component.
- Displays formatted messages, prompts, separators, and errors.
- Provides a test mode for automated testing where ANSI colors are disabled.
- Interacts with `Flashcard` objects from the `Model` component to display data.

**Classes:**
- `Ui`: Provides methods to read user input, display messages, prompts, errors, and separators. Supports both normal and test modes for consistent UI behavior.
- `Messages`: Stores common string messages and separators used across the UI for consistent formatting and display.

![UI Component Class Diagram](images/UIComponent_ClassDiagram.png "UI Component Class Diagram")

### Logic Component


### Model Component

**Responsibilities:**
- Manages the in-memory representation of flashcards.
- Provides methods to add, delete, and search flashcards.

**Classes:**
- **`Flashcard`** — Represents a single flashcard containing a question and answer pair, with support for marking it as starred.
- **`FlashcardList`** — Manages a collection of flashcards, providing methods to add, remove, retrieve, and list flashcards, as well as filter starred ones.

![Model Component Class Diagram](images/ModelComponent_ClassDiagram.png "Model Component Class Diagram")

### Main Component

**Responsibilities:**
- Initializes the application.
- Coordinates between UI, Logic, and Storage components.

**Class:**
- **`QuizMos`** — The main entry point of the application. Handles setup, command parsing, and orchestrates the overall program flow.

![Main Component Class Diagram](images/MainComponent_ClassDiagram.png "Main Component Class Diagram")

### Storage Component
**Responsibilities:**
- Reads flashcard data from the storage file into memory.
- Writes updated flashcard data from memory back to the storage file.
- Ensures that the storage file and its parent directories exist, creating them if necessary.
- Handles file-related exceptions and logs operations for debugging purposes.
- Works with `Flashcard` and `FlashcardList` objects from the `Model` component.

**Classes:**
- `Storage`: Manages persistence of flashcards; reads from and writes to the storage file, ensures file existence, handles exceptions, and interacts with `Flashcard` and `FlashcardList`.

![Storage Component Class Diagram](images/StorageComponent_ClassDiagram.png "Storage Component Class Diagram")

### Commons Component

**Responsibilities:**
- Contains shared classes used by multiple components (e.g., Messages, Exceptions).

**Class:**
- **`QuizMosException`** — The base exception class for all application-specific errors.
- **`QuizMosFileException`** — Thrown when file read/write operations fail.
- **`QuizMosInputException`** — Thrown when user input is invalid or cannot be processed.
- **`QuizMosLogicException`** — Thrown when logical errors occur during command execution.
- **`Messages`** — Stores general UI and command-related messages.
- **`FlashcardMessages`** — Provides user-facing messages for flashcard-related operations.
- **`FlashcardListMessages`** — Contains messages related to the flashcard list state.
- **`ReviewMessages`** — Provides messages and prompts used during the flashcard review sessions.

![Commons Component Class Diagram](images/CommonsComponent_ClassDiagram.png "Commons Component Class Diagram")

---

## Implementation

This section describes some noteworthy details on how certain features are implemented.

### Feature 1: Add Flashcard

**Command:** `add q/<question> a/<answer>`

**Explanation:** 
- The `Parser` identifies the add command and creates a new `AddFlashcardCommand`.
- The `AddFlashcardCommand` parses the user input.
- Validates that both question and answer fields are valid.
- Adds a new flashcard to the `FlashcardList` and saves it via `Storage`.
- Calls the `Ui` to display the flashcard was added.

![OverallAddFlashcardFlow](images/AddFlashcardFeature_Overall.png)

### Feature 2: Delete Flashcard

**Command:** `delete <index>`

**Explanation:**
- The `Parser` identifies the delete command and creates a new `RemoveFlashcardCommand`.
- The `RemoveFlashcardCommand` parses the user input.
- Validates that the index is valid.
- Removes the flashcard and updates the `FlashcardList`.
- Calls the `Ui` to display the flashcard was removed.

![OverallRemoveFlashcardFlow](images/RemoveFlashcardFeature_Overall.png)

### Feature 3: Search Flashcard

**Command:** `search <keyphrase>`

**Explanation:**
- The `Parser` identifies the search command and creates a new `SearchFlashcardCommand`.
- The `SearchFlashcardCommand` parses the user input.
- Validates that the keyphrase is valid.
- Searches through all flashcards for matching keywords in question or answer.
- Displays a list of matches or an error if none are found.

![OverallSearchFlashcardFlow](images/SearchFlashcardFeature_Overall.png)

### Feature 4: Review Flashcards

**Command:** `review m/[FLIP  \| TF \| MCQ]`

**Explanation:**
- The `Parser` instantiates `ReviewCommand`, which immediately parses and validates the mode from the user's input.
- The `execute()` method first verifies the `FlashcardList` isn't empty. It then calls `setReviewMode()` to dynamically create and assign the correct review object (e.g., `MultipleChoiceReview`) for the review mode.
- The `reviewLoop()` iterates through the flashcards, performing three actions per card via the `reviewMode` object:
  1. Display: Shows the question (mode-specific formatting included).
  2. Input: Retrieves the user's answer (or breaks the loop if the input is `quit`). 
  3. Resolution: Checks the answer via `checkAnswer()` and updates the correct counter.
- Eventually, the `quitReview()` method displays the session summary, including total cards reviewed and final results (for non-flip modes).

![OverallReviewFlow](images/ReviewFeature_Overall.png)

- **Flip mode flow**
  - The App displays the question only.
  - The user is prompted (y/n) to reveal the answer.
  - `checkAnswer()` simply prints the answer if the user input is 'y'. No score tracking is done, as the user dictates correctness.
- **MCQ mode flow**
  - `displayQuestion()` first calls the `listOfChoices()` helper method. This method generates a list of four unique random indices (include the correct one).
  - The App tracks the correct answer's index internally.
  - `checkAnswer()` checks the user's input (1–4) against the internal correct index and updates the score.
- **TF mode flow**
  - `displayQuestion()` presents the flashcard's question paired with an answer that is randomly selected from the entire `FlashcardList`.
  - `currentAnswer` is set to `true` (default is `false`) if the random answer matches the correct answer.
  - The user inputs `t` or `f`. `checkAnswer()` compares the user's input with the pre-determined `currentAnswer` boolean to evaluate correctness and updates the score.
     
### Feature 5: Star Flashcard

**Command:** `star <index>`

**Explanation:**
- The `Parser` identifies the `star` command and creates a new `StarCommand` object.
- The `StarCommand` parses the user input and converts the provided index to a zero-based integer.
- Validates that the index is within the range of existing flashcards in the `FlashcardList`.
- Retrieves the corresponding `Flashcard` object using the validated index.
- Checks if the flashcard is already starred. If yes, an error is raised through `QuizMosInputException`.
- If not starred, the command toggles its starred status via `toggleStar()` and adds it to the list of starred flashcards.
- Displays a confirmation message using the `Ui` component, leveraging `FlashcardMessages` for consistent formatting.
- Persists the updated `FlashcardList` to storage using the `Storage` component.
- Logs key execution steps (validation, starring, saving) for debugging and traceability.

![OverallStarFlashcardFlow](images/StarFlashcardFeature_Overall.png "Star Flashcard Feature Overall Flow")

### Feature 6: Unstar Flashcard

**Command:** `unstar <index>`

**Explanation:**
- The `Parser` identifies the `unstar` command and creates a new `UnstarCommand` object.
- The `UnstarCommand` parses the user input and converts the provided index to a zero-based integer.
- Validates that the index is within the range of existing flashcards in the `FlashcardList`.
- Retrieves the corresponding `Flashcard` object using the validated index.
- Checks if the flashcard is already unstarred. If yes, an error is raised through `QuizMosInputException`.
- If starred, the command toggles its starred status via `toggleStar()` and removes it from the list of starred flashcards.
- Displays a confirmation message using the `Ui` component, leveraging `FlashcardMessages` for consistent formatting.
- Persists the updated `FlashcardList` to storage using the `Storage` component.
- Logs key execution steps (validation, unstarring, saving) for debugging and traceability.

![OverallUnstarFlashcardFlow](images/UnstarFlashcardFeature_Overall.png "Unstar Flashcard Feature Overall Flow")
### Feature 7: Get Starred Flashcards
**Command:** `getstar`

**Responsibilities / Explanation:**
- The `Parser` identifies the `getstar` command and creates a new `GetStarCommand` object.
- The `GetStarCommand` retrieves all starred flashcards from the `FlashcardList` using `getStarredFlashcardsString()`.
- Performs internal assertions to ensure the returned string is not `null`.
- Uses the `Ui` component to display the list of starred flashcards via `FlashcardListMessages.showStarredFlashcardsList()`.
- Does not modify the `FlashcardList` or `Storage`; this is a read-only command.
- Logs key execution steps for debugging and traceability.

**Classes:**
- `GetStarCommand`
- `FlashcardList`
- `Ui`
- `FlashcardListMessages`
- `Storage` (reference only, no writes)

![OverallGetStarFlow](images/GetStarFeature_Overall.png "GetStar Command Feature Overall Flow")

---

## Requirements

### Use Cases

### Non-Functional Requirements (NFRs)
- The display of the next question and answer resolution must occur within 500 milliseconds per card.
- The App must prevent the start of any review session if the `FlashcardList` is empty, throwing an appropriate exception.
- The application must prohibit starting MCQ review if the `FlashcardList` contains fewer than 4 flashcards.
- All review mode prompts must perform immediate input validation and provide clear, specific error messages for incorrect choices.
- The review session must guarantee a graceful exit upon receiving the `quit` command, regardless of the user's progress in the loop.

---

## Glossary
- **FLIP mode:** A basic mode where the user views the question and manually chooses whether or not to reveal the answer.
- **MCQ mode (Multi-choice questions):** A mode that presents the user with four answer choices (including the correct one), forcing them to select one number. Requires at least four flashcards in the list.
- **TF mode:** A mode where a flashcard's question is paired with a randomly selected answer (which may or may not be correct). The user must decide if the resulting statement is true or false.

---

## Instructions for Manual Testing

---
