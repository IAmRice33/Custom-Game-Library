# Custom-Game-Library

A Java program made to manage a game library using user input as commands and store the library in a plain text file.

## Project Goal

I play games quite often and have a lot of games that I have played in the past or games that I want to play eventually, 
but because I switch games often, I can easily forget some of these games and never get back to them. I want to 
create a game library that can help me track these games and remind me of games I may have forgotten and want to revisit.

## How It Works (MVP)

- [x] Add and remove games from the library
- [x] Display all games in the library
- [x] Save and load the game library from a text file
- [x] Use dynamic user input to control program execution

![System Diagram](docs/Custom%20Game%20Library%20System%20Diagram.drawio.png)

## Tech Stack

- **Language:** Java (JDK 25+)
- **Java Standard Libraries:**
  - java.util.ArrayList
  - java.io.BufferedReader
  - java.io.FileReader
  - java.io.PrintWriter
  - java.io.FileNotFoundException
  - java.io.IOException
  - java.lang.IO
  - java.nio.file.FileAlreadyExistsException
  - java.nio.file.Files
  - java.nio.file.InvalidPathException
  - java.nio.file.Path
- **Data Storage:** Plain text file

## Setup

**Requirements:**
- JDK 25+ (found [here](https://www.oracle.com/java/technologies/downloads/))

**Optional:**
- IDE with Java support (Ex. [IntelliJ IDEA](https://www.jetbrains.com/idea/))

**Process**
1. Clone the repository

    Using Git:
    ```bash
    git clone https://github.com/IAmRice33/Custom-Game-Library
    cd Custom-Game-Library
    ```
    OR
    Use GitHub desktop to clone the repository

2. Running the program
    1. Through terminal
       - Compile all .java files in src
       ```bash
       javac ./src/*.java
       ```
       Or if you want a separate folder
       ```bash
       javac -d ./folder-name/ ./src/*.java
       ```
       - Run the compiled Java program (`src` can be replaced by your custom folder)
       ```bash
       java -cp ./src/ Main
       ```
    2. Using an IDE
       - Open the repository in your chosen IDE
       - Either use above terminal commands in the built in IDE terminal
       - Or run using your IDE's run button

## Roadmap

**Create a specialized game class**
- [x] Create a game object for future support
- [x] Add multiple games at once
- [x] Game/Genre tags
- [ ] Status tags
- [ ] Hours played
- [ ] Have last played time

**Sorting the Game Library**
- [ ] Compare games by name and tags
- [ ] Sort by tag, playtime, last played, and name 
- [ ] Randomly sort all games
- [ ] Randomly select one game from the library

**Search features**
- [ ] Search for games based on substring
- [ ] Search for games based on tag

**Miscellaneous**
- [ ] Add links to game (web page)

## Author

Kyle Yang, [GitHub Profile](https://github.com/IAmRice33).

