# CS3560-Prog3-API_Integration_Project
#### Collaborators: Nicolas Tran, Diego Mejia

## How To Run

### 1. Clone the repository
```bash
git clone https://github.com/chablades/CS3560-Prog3-API_Integration_Project.git
cd CS3560-Prog3-API_Integration_Project
```

### 2. Run the project from root
Since this is a Maven project, run main using this command:
```bash
mvn clean compile exec:java
```
The app will prompt you for an **Google Gemini API** key during first run, in order for the Gemini functionalities to work.

## Features
**Story Generation**: Uses Gemini AI to generate or brainstorm creative stories based on how you prompt it. The three modes of generation are:
- **Continue:** The AI will take the existing story (or blank) and your input instructions and will continue the story based on the context.
- **Rewrite:** The AI will take the existing story and rewrite it while considering your special input instructions.
- **Brainstorm:** The AI will brainstorm a story or creative ideas given your instructions and also the story context if it exists
**Multiple Stories and Chapters**: The app allows you to create as many stories as you like, and each story can also have as many chapters as needed. It also lets you customize the description and title for each story.
**Save/Load Stories**: The app lets you save stories so that you can come back to them any time.

## Design Patterns
- **Strategy:** Different AI generation modes (ContinueStrategy, RewriteStrategy, BrainstormStrategy).
- **Factory:** Creation of the correct AI generation mode (PromptStrategyFactory).
- **Observer:** AIStreamListener, which listens to token streaming events, completion, errors, and GUI events, and communicates across the program to notify of state change.
- **Singleton:** Many classes are instantiated only once (such as controllers) but the GeminiClient class has explicit one-time only instantiation code

## Demo
- [https://drive.google.com/file/d/18-jU6HyQTyFlyGs5SUYXqyZQC4Ke8jpi/view?usp=sharing](https://drive.google.com/file/d/18-jU6HyQTyFlyGs5SUYXqyZQC4Ke8jpi/view?usp=sharing)
