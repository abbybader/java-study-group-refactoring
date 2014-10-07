java-study-group-refactoring
================

Refactoring: the art of applying structural changes to code that _do not modify its behavior_ in order to achieve code that is more readable, maintainable, and easier to modify.  This repo contains a refactoring case study for the Women Who Code Boulder/Denver Java Study Group.

How to use this repository
----------------
This repo contains a code snippet excerpted from a real live production web system.  The first three commits (chronologically) set up the repo and the code example.  Each following commit performs a step in the refactoring process.  The end result is code with smaller classes, better leveraging of Java data structures, and lower "cyclometric complexity" (fancy term for "fewer conditionals").  In other words, the code becomes cleaner, more modular, more testable(!), and easier to change in the future.

The initial state of the code can be seen at [the third commit](https://github.com/abbybader/java-study-group-refactoring/tree/5af01ddbf6efbe0559f4ee1a60cb8e4a314002e9), and the final state can be seen [here](https://github.com/abbybader/java-study-group-refactoring/commit/1c04d3326d5d87369a03956a89fcb3d8cd6fd0fb).

It's highly recommended to clone the repo, check out the third commit, and get familiar with the code example.  Run the ProjectDataReloader.main() method.

From there, you can either step through the commit history and check out the difs; or give it a try yourself!