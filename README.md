# TypingRaceSimulator

Object Oriented Programming Project — ECS414U

## Project Structure

```
TypingRaceSimulator/
│
├── .git/          # Git repository folder (hidden)
├── Part1/         # Textual version (terminal-based simulation)
├── Part2/         # Graphical version (Swing GUI)
└── README.md
```

Dependencies

Java JDK 11 or higher
No external libraries required — both parts use only the Java standard library and Swing, which is included in the JDK

## Part 1 — Textual Simulation

### How to compile
Open a terminal or PowerShell, navigate to the Part1 folder, and compile:

```bash
cd Part1
javac Typist.java TypingRace.java
```

### How to run

The race is started by calling `startRace()` on a `TypingRace` object.
A simple way to test this is to add a `main` method to `TypingRace`, for example:

```java
public static void main(String[] args) {
    TypingRace race = new TypingRace(40);
    race.addTypist(new Typist('①', "TURBOFINGERS", 0.85), 1);
    race.addTypist(new Typist('②', "QWERTY_QUEEN",  0.60), 2);
    race.addTypist(new Typist('③', "HUNT_N_PECK",   0.30), 3);
    race.startRace();
}
```

Then run:

```bash
java TypingRace
```

## Part 2 — GUI Simulation

To be implemented as part of the coursework. Place all GUI-related source files in this folder. The graphical version is started by calling `startRaceGUI()`.

### How to Compile
### Navigate to the Part2 folder and compile:

```bash
cd Part2
javac *.java
```

### How to Run
```bash
java TypingRaceGUI
```

GUI Features
Race Configuration
- Ability to choose between pre determined short, medium, or long passages, or custom passage
- Number of typists competing between 2 to 6
- Global difficulty modifiers can be applied
- Specific typist modifiers can be applied

## Dependencies

- Java Development Kit (JDK) 11 or higher
- No external libraries required for Part 1
- Part 2 may use Java Swing (included in standard JDK) or JavaFX

## Notes

- All code should compile and run using standard command-line tools without any IDE-specific configuration.
- The starter code in Part1 was originally written by Ty Posaurus. It contains known issues — finding and fixing them is part of the coursework.
