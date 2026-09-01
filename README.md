# Crossway Game

[![Java CI with Gradle](https://github.com/AriannaMucig/Crossway/actions/workflows/build.yml/badge.svg)](https://github.com/AriannaMucig/Crossway/actions/workflows/build.yml)

A Java implementation of the two-player board game Crossway.

## Game Overview and Rules

Crossway is an abstract strategy board game played on an $19 \times 19$ grid between two players: Black (X) and White (0).

- White attempts to form a continuous chain connecting the North and South borders.
- Black attempts to form a continuous chain connecting the West and East borders.
- Chains can connect orthogonally (up/down/left/right) or diagonally.
- Crossway Constraint: A player is forbidden from placing a piece that completes a $2 \times 2$ square of alternating pieces ($W-B / B-W$), as this creates an illegal diagonal intersection.
- Pie Rule: After Black makes the very first move, White has the option to swap colors and adopt Black's position.

## Tech Stack

- **Language:** Java 17+
- **Build Tool:** Gradle
- **Testing Framework:** JUnit 5 & AssertJ
- **CI System:** GitHub Actions (`build.yml`)

