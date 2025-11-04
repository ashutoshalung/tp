# Raahim Mukhtar - Project Portfolio Page

---

## Overview

My contributions centered on building the core flashcard management system. I implemented the `Flashcard` and `FlashcardList` classes, which form the foundation of the application’s data model, and developed the `AddFlashcardCommand`, `DeleteFlashcardCommand`, and `SearchFlashcardCommand` features to allow users to manage their flashcards seamlessly. Additionally, I integrated these features with the `Storage` and `Ui` components to ensure reliable saving and display of flashcards. I also created comprehensive tests, including JUnit tests and UI-based validation, to verify functionality and ensure robust error handling across all flashcard-related commands.

--- 

## Summary of Contributions

### **Code Contributed**
- [View my code on tP Code Dashboard](https://nus-cs2113-ay2526s1.github.io/tp-dashboard/?search=mukhtarcal&breakdown=true&sort=groupTitle%20dsc&sortWithin=title&since=2025-09-19T00%3A00%3A00&timeframe=commit&mergegroup=&groupSelect=groupByRepos&checkedFileTypes=docs~functional-code~test-code~other&filteredFileName=) 

---

## **Enhancements Implemented**

#### **1. Add Flashcard Feature**
- Implemented the core logic for adding new flashcards through the `AddFlashcardCommand` class.
- Integrated input parsing with validation checks (ensuring both question and answer fields are present and properly ordered).
- Updated the `Storage` class to persist added flashcards to the save file.
- Added corresponding JUnit and text-based UI tests to ensure full feature coverage.

#### **2. Delete Flashcard Feature**
- Developed the `RemoveFlashcardCommand` to allow users to delete flashcards by index.
- Implemented defensive error handling for invalid inputs (e.g., out-of-range indices, non-integer inputs).
- Enhanced the UI feedback to clearly display which flashcard was removed.
- Wrote multiple JUnit tests and text-based UI tests verifying expected behavior and edge cases.

#### **3. Search Flashcard Feature**
- Designed and implemented the `SearchCommand`, which filters flashcards based on a user-provided keyword.
- Built logic for case-insensitive matching and formatted UI output to show matched results clearly.
- Wrote comprehensive tests to ensure correctness and reliability of search results.

#### **4. Core Flashcard System**
- Implemented the foundational `Flashcard` and `FlashcardList` classes.
- Designed `FlashcardList` to support operations such as adding, deleting, and searching efficiently.
- Ensured clean integration between `FlashcardList`, commands, and persistent storage.
- Collaborated with teammates to align data structures with other system components.

---

## **Contributions to the User Guide (UG)**
- Created the template and draft one of the UG
- Added and edited sections explaining:
    - `add q/<QUESTION> a/<ANSWER>` command
    - `delete <INDEX>` command
    - `search <KEYPHRASE>` command
- Wrote usage examples and error message explanations for each feature.
- Improved command descriptions for clarity and consistency in formatting.

---

## **Contributions to the Developer Guide (DG)**

### **Major Additions**
- Created **Main Component Class Diagram** (`QuizMos`) and explanation.
- Created **Model Component Class Diagram** (`Flashcard`, `FlashcardList`) and explanation.
- Created **Commons Component Class Diagram** (`QuizMosException`, `Messages`, ect) and explanation.
- Authored sections and added **sequence diagrams** for:
  - `AddFlashcardCommand`
  - `RemoveFlashcardCommand`
  - `SearchCommand`

### **Team-Wide DG Contributions**
- Drafted the DG template
- Standardized the layout and markdown structure of the DG.
- Wrote component descriptions for:
  - **Model Component**
  - **Main Component**
  - **Commons Component**
- Authored the section on Instructions for Manual Testing
- Authored the section on User Stories
- Helped unify diagram styles (colors, titles, and relationships).

---

## **Contributions to the User Guide (UG)**
- Drafted the UG template and wrote sections for:
  - `add q/<QUESTION> a/<ANSWER>`
  - `delete <INDEX>`
  - `search <KEYWORD>`
- Added examples, command syntax, and error message explanations.
- Refined formatting for consistency across UG sections.

---

## **Contributions to Team-Based Tasks**
- Reviewed pull requests and maintained consistency in naming conventions and Javadoc style.
- Resolved merge conflicts involving shared components (`Command`, `Parser`, `Storage`).
- Assisted teammates in debugging `Storage` integration with commands.
- Participated in team discussions regarding architecture and component division.

---

## **Review and Mentoring Contributions**
- Reviewed PRs related to `Storage`, `ReviewCommand`, error handling, and many more.
- Provided structured feedback for testing and exception handling.
- Helped identify and fix UI–Logic integration bugs.
- Reviewed DG and UG consistently and gave pointers to team members on where we could refine.
- Located bugs across most features and UG and DG by manual testing.

---

## **Overall Impact**
I contributed to both the **functionality** and **documentation foundation** of the project. Beyond developing the flashcard system, I ensured the Developer Guide reflected a clear architectural vision, high-quality diagrams, and thorough explanations. My work bridged the technical and design aspects of the team’s submission, ensuring it met both functionality and documentation excellence standards.

---