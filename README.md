# Custom-Game-Library

A Java program made to manage a game library using user input as commands and store the library in a `.txt` file.

## Project Goal

I play games quite often and have a lot of games that I have played in the past or games that I want to play eventually, 
but because I switch games often, I can easily forget some of these games and never get back to them. I want to 
create a game library that can help me track these games and remind me of games I may have forgotten and want to revisit.

## How It Will Work (MVP)

- [ ] Add and remove games from the library
- [ ] Display all games in the library
- [ ] Save and load the game library from a `.txt` file

![System Diagram](docs/Custom%20Game%20Library%20System%20Diagram.drawio.png)

## Tech Stack

- **Language:** Java (JDK 25+)
- **Java Standard Libraries:**
  - java.util.ArrayList
  - java.io.BufferedReader
  - java.io.FileReader
  - java.io.PrintWriter
  - java.io.FileWriter
  - java.io.IOException
  - java.lang.IO
  - java.nio.file
- **Data Storage:** `.txt`

## Roadmap

**Create a specialized game class**
- [ ] Create a game object for future support
- [ ] Add multiple games at once
- [ ] Game/Genre tags
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

