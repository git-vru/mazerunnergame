# MAZE RUNNER GAME 
Welcome to the readme file 

## Game idea
Picture this game where you're guiding a character through a maze filled with challenges. The maze is like a puzzle, surrounded by walls, with a starting point and an exit to find. Inside, there are traps, enemies, and keys.

Your goal?

Find the exit. But it's not a walk in the park. The maze has tricky dead-ends and twists. Plus, there are traps waiting to catch you and enemies to deal with. The catch? You need the key to unlock the exit.

It's a thrilling adventure – navigating through the maze, facing challenges, dodging traps, dealing with enemies, and grabbing the key to reach the exit.
## Game Elements
### Walls and paths
You're stuck in the maze until you have the key.  
You start from the exit and after collecting the key there is an exit maybe multiple which you can get out from.  
Your free path is anywhere the grass is.
### Hero
The main character can move through the maze in four directions (up, left, down, right) using WASD/arrow keys respectively.  
The hero is bounded by the walls and has 5 lives.
### Obstacles
There are two kinds of obstacles in the game:  
#### 1.Traps(Fire)
The position of traps is fixed and if you walk over run of them then you lose one life.
#### 2.Enemies(Bats)
The enemies are dynamic that means that they move randomly inside the maze and if you come in contact with one of them you will lose a life.
### Key(Coin)
Collecting it is the ultimate goal of the game.  
The exit will behave won't open until the key is collected.
### MazeLoader
This is a special class which loads the maze. It has methods to read a properties file and get the coordinates and the object type from it. It contains methods to render and create those objects as needed from the properties file.

The data for the coordinates and the object type is stored within a map of which a key is a Point (Point is a special class in Java which stores X and Y coordinates) and value of that map is the object type which is provided by default.
### HUD
In the top left corner of the game you can see the HUD which tells you some crucial information about where you are in the game.
1. No of lives left (Hearts)
2. If the key is collected or not
3. If shield is activated or not.  
When the hero loses a life it has a shield for two seconds where he cannot lose another life.
### Pause Menu
When you pause the game a small menu shows up where you can either:  
1. Resume the game 
2. Go back to the main menu and start a new game or upload a new level or change any settings you want.
### Game Menu
The game menu loads when the game starts  
You can also come back to the game menu by pressing the escape key in the game  
When you press escape there are two options either to resume or to go to the game menu.    
In the game menu you have three options that is to:
1. Start new game  
When you press start new game you are given five preloaded map options.   
You can also upload a new map which has to be a java.properties file.
2. Change settings  
3. Exit the game
#### Settings
1. You can change volume in the settings.
2. You can change the language you want to play in the settings.
3. You can control if you want to play music or not.
### Music
Music is playing in the background when you are in:
1. Menu Screen
2. The Game
3. Win/Lose screen
### Sound effects
Sound effects are played when:
1. The hero loses a life.
2. When the hero collects the coin.
3. When the hero is walking.
## Code structure
### Inheritance
#### GameObject
These objects in the game extend from gameObject class  
1. Entry
2. Exits
3. Traps
4. Key
##### Character
Dynamic elements also extend from the character class which itself extends from the gameObject.  
5. Hero
6. Enemy
### Screens
There are a variety of screen used in this game namely:  
1. MenuScreen - The main menu
2. SelectMapScreen - where you select/upload a map
3. SettingsScreen - where you can control the music or the language
4. GameScreen - where the rendering of the entire game takes place
5. GoodEndScreen - screen which is shown when you win the game
6. BadEndScreen - screen which is shown when you lose the game
7. LanguageScreen - to choose the language in the settings screen

## Bonus Implementations
To keep it simple for the grading we count every new thing we add outside the basic requirements as one feature.  
(Grading depends on the tutor)
### Languages
We added 9 different languages with full implementation throughout the game covering major languages spoken by our classmates with english being the default
1. Turkish
2. English
3. Hindi
4. German
5. Spanish
6. Arabic
7. Chinese
8. French
9. Russian  

We achieved this by using resource bundles and in the resource bundle properties files where in every line is like a key-value pair.  
To access it, we just get the value for a key wherever needed throughout the game.
### Music Controller
We have a music controller in our settings  
1. We can turn on or off the music playing in the background of the game 
2. We can turn on or off the music playing in the background of the menu
3. We can also turn on or off the sound effects of the game  
4. We can also control the volume of all these together
### Shield
Shield is a type of cooldown.  
That the hero of our game has whenever he loses his life (either by coming in contact with a trap or an enemy).  
After losing a life, he is invulnerable for the next two seconds.
### Extra sound effects
The game also makes a sound of a person walking on a grass when the hero is moving
### Extra animations and aesthetic choices
1. We have implemented that the free path where the hero can move is a grass texture.  
2. We have also added extra animations for the movement of the enemies 
3. Added extra animations for movement of the Hero 
4. Added extra animation for the key 
5. Animation for the opening of entry
6. Animation for the opening of exits

# Creators
1. Vrushabh Jain:  
id - go34kid  
email - vrushabh.jain@tum.de
2. Emirhan Tepebaşı:   
id - go98pos  
email - go98pos@mytum.de

# Credits
LosingMusic.mp3 from link -  
https://opengameart.org/content/the-fallen-rpg-orchestral-essentials-defeated-music  
"The Fallen" Composed by Jonathan Shaw (www.jshaw.co.uk)

WinningMusic.mp3 from link -  
https://opengameart.org/content/victory-2  
"Victory!" Composed by: Jon K. Fite

grasssound.mp3 from link -  
https://opengameart.org/content/grass-foot-step-sounds-yo-frankie

5 background sounds from link -  
https://opengameart.org/content/nes-shooter-music-5-tracks-3-jingles  

coin,hurt,lifelost music link -  
https://opengameart.org/content/nes-sounds

newBackground link -  
https://opengameart.org/content/jaoan

OpenSans-BoldItalic.ttf file source -  
https://www.fontsquirrel.com/fonts/open-sans

foto.jpg & mazeinlook.jpeg  
both were generated using bing AI aand are copyright free

Rest everything is either not needed or was provided by default
# Before starting the game
In class SelectMapScreen finish the TODO  
TODO: PLEASE change the path according to your device  
format is "level-1/2/3/4/5.properties  
use finalI variable instead of number  

Fix the configuration for:  
1. Windows and linux remove the -XstartOnFirstThread
2. for Mac user with Mseries chips add this in the Vm option -XstartOnFirstThread
3. select the project sdk
4. -cp needs to end with .desktop.main
# Note of thanks 
We would like to thank our professor Stephan Krusche and our tutors which thought of this project because it was genuinely fun, we learned a lot on how to practically use Java with this game.