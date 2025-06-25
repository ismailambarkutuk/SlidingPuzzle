# Sliding Puzzle Game

## Description
This is a Java-based *Sliding Puzzle* game. The player selects an image and tries to complete the puzzle by sliding the pieces into the correct position. The game features sound effects for valid and invalid moves, as well as optional background music.

## Project Structure
```
SlidingPuzzle/
├── src/                    → Source Java files
│   └── SlidingPuzzle/
│       └── btn.java        → Main game class
├── bin/                    → Compiled class files
├── .classpath              → Eclipse classpath settings
├── .project                → Eclipse project metadata
├── .settings/              → Java settings for Eclipse
├── *.jpg, *.png            → Puzzle and UI images
├── *.wav                   → Sound effects and background music
```

## Requirements
- Java JDK 8 or later
- Eclipse IDE (optional but recommended)
- A system with sound support (for playing audio)

## How to Run

### 1. Using Eclipse:
1. Open Eclipse.
2. Go to `File > Import > Existing Projects into Workspace`.
3. Select the `SlidingPuzzle` directory.
4. Open `src/SlidingPuzzle/btn.java`.
5. Right-click the file → `Run As > Java Application`.

### 2. Using Command Line:
```bash
cd SlidingPuzzle/src
javac SlidingPuzzle/btn.java
java SlidingPuzzle.btn
```

## Features
- Selectable images for the puzzle
- Audio feedback on valid/invalid moves
- Background music support
- Mouse-controlled gameplay

## Image Files
- `picture1.jpg` to `picture6.jpg`: Puzzle images
- `choosePicture1.png` to `choosePicture6.jpg`: Image selection screen visuals

## Sound Files
- `backgroundMusic.wav`, `backgroundMusic2.wav`: Background music tracks
- `validMove.wav`: Sound for a valid move
- `wrongMove.wav`, `wrongMove2.wav`: Sounds for invalid moves

## Developer Info
- Name: İsmail Ambarkütük
- Email: ismailakutuk@gmail.com
- Name: Emre Erdem
- Email: emre.erdem22@tedu.edu.tr
- Name: Tuna Bekiş
- Email: tuna.bekis@tedu.edu.tr
- Name: Metehan Kartop
- Email: metehan.kartop@tedu.edu.tr
- Note: This project was created for educational purposes.
