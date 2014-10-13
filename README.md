java-study-group-refactoring
================

Refactoring: the art of applying structural changes to code that _do not modify its behavior_ in order to achieve code that is more readable, maintainable, and easier to modify.  This repo contains a refactoring case study for the Women Who Code Boulder/Denver Java Study Group.

How to use this repository
----------------
This repo contains a code snippet excerpted from a real live production web system.  The first three commits (chronologically) set up the repo and the code example.  Each following commit performs a step in the refactoring process.  The end result is code with smaller classes, better leveraging of Java data structures, and lower "cyclometric complexity" (fancy term for "fewer conditionals").  In other words, the code becomes cleaner, more modular, more testable(!), and easier to change in the future.

It's highly recommended to clone the repo, check out the "starting point" commit, and get familiar with the code example.  Run the ProjectDataReloader.main() method.

From there, you can either step through the commit history and examine the difs between states; or give it a try yourself!

**Track 1: Easy**
This track has a simpler codeset that covers a smaller range of refactors; the refactoring steps are also smaller and more incremental.  We'll cover this in study group together.  It would also make a good at-home exercise for newer coders.

To use, check out the branch [simplerExample](https://github.com/abbybader/java-study-group-refactoring/tree/simplerExample).  The starting commit is tagged as "SimpleExampleStartsHere".

**Track 2: A little harder**
This track contains the full code example, including some complex timing issues.  The refactoring steps are a bit broad, assuming some experience with refactoring.

To use, check out the master branch.  The initial state of the code can be seen at [the third commit](https://github.com/abbybader/java-study-group-refactoring/tree/5af01ddbf6efbe0559f4ee1a60cb8e4a314002e9) and is tagged "StartHere".
