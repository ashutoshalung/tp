# Ashutosh Alung – Project Portfolio Page

---

## **Overview**

My contributions centered on **engineering the persistence and enhancement layer** of the QuizMos system — connecting user-facing commands with durable, maintainable, and extensible backend support.

I implemented the complete **Starring System** (`star`, `unstar`, `getstar` commands) and **re-engineered the Storage component**, both of which are fundamental to the app’s ability to track user progress and preserve data integrity across sessions. These components form the **backbone of QuizMos’ reliability and user experience**.

Beyond implementation, I led key **architectural refinements** to improve cohesion and reduce coupling between `Ui`, `Messages`, and `Storage`. I also authored detailed **sequence and class diagrams** that accurately mirror system behavior, introduced **logging, assertions, and custom exceptions**, and **ideated the v1 and v2 feature roadmap** for future scalability.

---

## **Summary of Contributions**

### **Code Contributed**

* [View my code on tP Code Dashboard](https://nus-cs2113-ay2526s1.github.io/tp-dashboard/?search=ashutoshalung&breakdown=true&sort=groupTitle%20dsc&sortWithin=title&since=2025-09-19T00%3A00%3A00&timeframe=commit&mergegroup=&groupSelect=groupByRepos&checkedFileTypes=docs~functional-code~test-code~other&filteredFileName=)

---

## **Enhancements Implemented**

### **1. Core Starring System (`Star`, `Unstar`, `GetStar` Features)**

* Engineered the **complete starring subsystem**, introducing persistent “starred” states across the flashcard lifecycle.
* Developed three core commands:
    * **`StarCommand`** – Flags flashcards for priority review.
    * **`UnstarCommand`** – Removes the flag while maintaining index integrity.
    * **`GetStarCommand`** – Retrieves and displays all starred flashcards with formatted output.
* Refactored `FlashcardList` to efficiently handle and store large numbers of starred flashcards without performance degradation.
* Integrated with the `Storage` component for **persistent saving** of starred status using a consistent serialization format.
* Crafted intuitive `Ui` feedback and improved message consistency through `Messages` refactoring.
* Ensured robustness through **assertions, logging, and custom exception handling**, making the starring system reliable and extensible.

**Impact:**  
Transformed QuizMos from a static flashcard app into a **personalized learning platform** capable of tracking user focus and progress — a foundational step toward long-term usability and scalability.

---

### **2. Storage Component Architecture and Reliability Layer**

* Re-engineered the `Storage` class to ensure **stable read/write operations** and compatibility with evolving data models.
* Implemented **logging and assertions** to trace file operations, detect data corruption, and maintain fault tolerance.
* Designed for resilience — gracefully handling malformed input files and I/O errors using custom `QuizMosFileException` and `QuizMosInputException` classes.
* Enhanced serialization/deserialization logic to support new metadata (like starred status) while preserving backward compatibility.
* Validated through comprehensive **JUnit testing** to ensure correctness under edge cases and large data loads.

**Impact:**  
Built the **data persistence backbone** of QuizMos — ensuring that all future features, from starred flashcards to user progress tracking, function dependably and predictably.

---

### **3. Exception Framework, Logging, and Assertions**

* Developed the **QuizMos exception hierarchy** to deliver consistent and meaningful error reporting.
* Integrated **structured logging** across key operations to improve traceability and debugging.
* Introduced **assertions** at critical checkpoints to enforce data integrity and prevent silent failures.
* Ensured predictable exception propagation through the command–storage–UI pipeline.

**Impact:**  
Elevated QuizMos’ **engineering maturity** by introducing production-level safety mechanisms that strengthened system reliability and maintainability.

---

### **4. Architectural Refinement and Codebase Cohesion**

* Refactored the `Messages` and `Ui` classes to improve cohesion and clarity — ensuring `Messages` only stores static text while `Ui` focuses on display logic.
* Reduced coupling between `Ui`, `Storage`, and `Command` classes by enforcing single-responsibility principles.
* Enhanced `FlashcardList` to efficiently handle starred flashcards and integrate cleanly with persistent storage.
* Unified design conventions and Javadoc standards across the codebase.

---

### **5. Feature Ideation (v1 and v2 Roadmap)**

* **Version 1 Features:**
    * Dedicated “Starred Flashcards” view.
    * Sorting and filtering for starred flashcards.
* **Version 2 Features:**
    * Auto-starring based on usage frequency.
    * Integration with spaced repetition algorithms.
    * Visual indicators for flashcard importance and progress.

---

## **Contributions to the Developer Guide (DG)**

* Authored **sequence diagrams** for:
    * `StarCommand`
    * `UnstarCommand`
    * `GetStarCommand`
* Created and refined **class diagrams** for:
    * `Storage` component
    * `Ui` and `FlashcardList` integration
* Reorganized DG structure to mirror real code architecture and emphasize component interaction.
* Wrote detailed documentation on:
    * Storage read/write logic and exception handling.
    * `Messages` and `Ui` refactoring rationale.
    * System-wide logging and assertion usage.
* Drafted **Version 1 and 2 user stories** to guide long-term evolution of QuizMos.

**Impact:**  
Transformed the DG into a **technical blueprint** accurately reflecting real system behavior — improving maintainability and onboarding for future contributors.

---

## **Contributions to the User Guide (UG)**

* Authored and formatted sections for:
    * `star INDEX`
    * `unstar INDEX`
    * `getstar`
* Added real command examples, expected outputs, and error explanations.
* Enhanced UG layout for readability, consistency, and logical flow.
* Clarified data persistence behavior and how starred flashcards are saved and retrieved.

---

## **Team and Leadership Contributions**

* Reviewed and merged PRs across `Storage`, `Ui`, and `FlashcardList` to maintain architecture consistency.
* Debugged complex integration issues involving command–storage synchronization.
* Helped teammates adopt consistent **logging, exception handling**, and **testing** standards.
* Standardized diagram and documentation style across the team’s DG.
* Led brainstorming and roadmap sessions for v1/v2 feature expansion.

---

## **Overall Impact**

I built and reinforced the **foundational persistence and personalization layers** of QuizMos — transforming it from a simple flashcard system into a dynamic, stateful learning platform.

By designing the **starring system**, **re-engineering the Storage component**, and implementing **robust logging and exception frameworks**, I ensured that QuizMos operates with long-term **reliability, maintainability, and scalability** in mind.

My architectural refinements, documentation leadership, and forward-looking feature ideation strengthened the system’s technical depth and positioned QuizMos for continued evolution and innovation.

---
