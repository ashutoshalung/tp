# Developer Guide

---

## Table of Contents

1. [Acknowledgements](#acknowledgements)
2. [Architecture](#architecture)
  - [UI Component](#ui-component)
  - [Logic Component](#logic-component)
  - [Model Component](#model-component)
  - [Main Component](#main-component)
  - [Storage Component](#storage-component)
  - [Commons Component](#commons-component)
  - [Overall Architecture Diagram](#overall-architecture-diagram)
3. [Features](#features)
  - [Feature 1: Add Flashcard](#feature-1-add-flashcard)
  - [Feature 2: Delete Flashcard](#feature-2-delete-flashcard)
  - [Feature 3: Search Flashcard](#feature-3-search-flashcard)
  - [Feature 4: Review Flashcards](#feature-4-review-flashcards)
  - [Feature 5: Star Flashcard](#feature-5-star-flashcard)
4. [Requirements](#requirements)
  - [Use Cases](#use-cases)
  - [Non-Functional Requirements (NFRs)](#non-functional-requirements-nfrs)
  - [Glossary](#glossary)
5. [Instructions for Manual Testing](#instructions-for-manual-testing)

---

## Acknowledgements

{list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the original source as well}

---

## Architecture

This section explains the high-level architecture of the application.  
The app follows a **Command-based architecture**, where the user interacts via text commands that are parsed and executed by the system.

### UI Component


### Logic Component


### Model Component

**Responsibilities:**
- Manages the in-memory representation of flashcards.
- Provides methods to add, delete, and search flashcards.

**Classes:**
- Flashcard
- FlashcardList

### Main Component

**Responsibilities:**
- Initializes the application.
- Coordinates between UI, Logic, and Storage components.

**Class:**
- QuizMos

### Storage Component

### Commons Component

**Responsibilities:**
- Contains shared classes used by multiple components (e.g., Messages, Exceptions).

**Class:**
- Exception
- Messages

### Overall Architecture Diagram


---

## Features

### Feature 1: Add Flashcard

**Command:** `add q/<question> a/<answer>`
**Explanation:** 
- The `Parser` identifies the add command and creates a new `AddFlashcardCommand`.
- The `AddFlashcardCommand` parses the user input.
- Validates that both question and answer fields are valid.
- Adds a new flashcard to the `FlashcardList` and saves it via `Storage`.
- Calls the `Ui` to display the flashcard was added.

{Sequence Diagram goes here}

### Feature 2: Delete Flashcard

**Command:** `delete <index>`
**Explanation:**
- The `Parser` identifies the delete command and creates a new `RemoveFlashcardCommand`.
- The `RemoveFlashcardCommand` parses the user input.
- Validates that the index is valid.
- Removes the flashcard and updates the `FlashcardList`.
- Calls the `Ui` to display the flashcard was removed.

{Sequence Diagram goes here}

### Feature 3: Search Flashcard

**Command:** `search <keyphrase>`
**Explanation:**
- The `Parser` identifies the search command and creates a new `SearchFlashcardCommand`.
- The `SearchFlashcardCommand` parses the user input.
- Validates that the keyphrase is valid.
- Searches through all flashcards for matching keywords in question or answer.
- Displays a list of matches or an error if none are found.

{Sequence Diagram goes here}

### Feature 4: Review Flashcards

### Feature 5: Star Flashcard

---

## Requirements

### Use Cases

### Non-Functional Requirements (NFRs)

---

## Glossary

---

## Instructions for Manual Testing

---
