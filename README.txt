Solving A Maze - Interview Problem
Marcus Peacock

Running Solution
java -jar MazeSolver.jar path/to/input.txt

Example:

Input:

21 21
1 1
19 19
1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1
1 0 1 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0 1 0 1
1 0 1 0 1 0 1 0 1 1 1 1 1 1 1 0 1 0 1 0 1
1 0 1 0 1 0 1 0 0 0 0 0 0 0 1 0 1 0 1 0 1
1 0 1 0 1 0 1 1 1 1 1 1 1 1 1 0 1 0 1 0 1
1 0 1 0 1 0 1 0 0 0 0 0 0 0 1 0 1 0 0 0 1
1 0 1 0 1 0 1 0 1 1 1 1 1 0 1 0 1 1 1 1 1
1 0 1 0 1 0 1 0 0 0 0 0 1 0 1 0 1 0 0 0 1
1 0 1 1 1 0 1 1 1 0 1 0 1 0 1 0 1 0 1 0 1
1 0 0 0 0 0 1 0 0 0 1 0 1 0 1 0 1 0 1 0 1
1 1 1 0 1 1 1 1 1 1 1 0 1 0 1 0 1 1 1 0 1
1 0 0 0 0 0 1 0 0 0 0 0 1 0 1 0 0 0 1 0 1
1 1 1 1 1 0 1 0 1 1 1 1 1 0 1 1 1 0 1 0 1
1 0 0 0 1 0 0 0 0 0 1 0 0 0 0 0 1 0 1 0 1
1 0 1 0 1 1 1 1 1 0 1 1 1 0 1 0 1 0 1 0 1
1 0 1 0 0 0 0 0 1 0 1 0 0 0 1 0 0 0 1 0 1
1 0 1 1 1 1 1 0 1 0 1 0 1 1 1 0 1 1 1 0 1
1 0 1 0 0 0 1 0 1 0 1 0 0 0 1 0 1 0 0 0 1
1 0 1 0 1 0 1 0 1 1 1 1 1 0 1 0 1 0 1 0 1
1 0 0 0 1 0 1 0 0 0 0 0 0 0 1 0 0 0 1 0 1
1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1


Output:

#####################
#S#   #           # #
#X# # # ####### # # #
#X# # #       # # # #
#X# # ######### # # #
#X# # #XXXXXXX# #   #
#X# # #X#####X# #####
#X# # #XXXXX#X# #   #
#X### ### #X#X# # # #
#XXX  #   #X#X# # # #
###X#######X#X# ### #
#  XXX#XXXXX#X#   # #
#####X#X#####X### # #
#   #XXX  #  XXX# # #
# # ##### ### #X# # #
# #     # #   #X  # #
# ##### # # ###X### #
# #   # # #   #X#XXX#
# # # # ##### #X#X#X#
#   # #       #XXX#E#
#####################

There are many algorithms suitable for solving mazes; it is important to distinguish between algorithms which rely on complete information and which have incomplete information. For example, an algorithm for moving a robot through a maze may not begin with a map of the maze and an explorative search algorithm would have to be used. In the case of incomplete information, heuristics such as straight line distance to goal are often used to speed up the search space. However, requiring a heuristic implies that the goal is to find any solution and not the optimal one. Since the inputs were sized small enough to do so, I opted for implementing a solution which was guaranteed to find the optimally-short path to the goal; breadth-first-search. Breadth-first-search for mazes can be imagined as flooding the maze with water, if the water moves equally through the maze, the first bit of water to reach the goal has taken the shortest path.

My assumptions were that the maze input would be well formed and pre-validated, that the maze would not be greater in width or height than the max value of a Java int and that it would be small enough to fit in memory. 

The program operates by first reading the file using Java 8 file reading, parsing the parameters from the beginning of the file, making a copy of the maze input to be used for output and then performing breadth-first-search.

One thing to note about using breadth-first-search is that the vanilla version of breadth-first-search does not store the path to the goal discovered. Therefore, I opted to create a map: Position -> Position which stores the initial position which first discovered the new position. This allows the map to be traced backwards at the end in order to build the path back up, marking the path on the map to be output.

Secondly, breadth-first search is not the easiest to read and comprehend algorithm the first time, so there is a trade-off being made between absolute simplicity with producing optimal solutions.

I opted to maintain the solution in a single class file as the majority of the work was done operating on the 2D-array maze along with using the stored parameters. Therefore, there would be little value in splitting MazeSolver in its current form. It may be better practice to move the main function out of the class and create a startup class, but I think this is a minor issue.


The second class I created represents points in 2D space. As far as I know, the standard Java library does not have a good structure for storing integer points in 2-D space and I wanted to create a HashSet to store the explored positions and a HashTable to store the path, so I created a Tuple class for Integers and wrote overriding functions for equals and hashCode. 

The complexity of my solution is the complexity of breadth-first-search, which in this case for time will be O(V+E) where V is width * height and E is 4 * V for each edge, this simplifies to O(V). For space, the maze input is read and transformed, storing some references to discovered points and edges which will never exceed V or E, this makes space complexity also O(V).

The first difficulty I came across was that implementing a hashCode function for an (x, y) coordinate was not a completely trivial exercise. Therefore, I searched for a solution to this problem and found the Cantor Pairing Function [1] which solves this problem. My second problem was that when writing my solution I used the concept of x and y for the maze coordinates. However, the maze (0, 0) is in the top left corner, meaning it is easy to make mistakes when using x and y in this context. My initial solution failed on the large input because I was initializing the maze with the width and height reversed. These mistakes are bound to happen, therefore, if I were to rewrite it, I would remove the concept of x and y entirely and simple use up/down left/right or width/height.

In terms of testing, I opted not to use unit tests or TDD as I expected the code to be self contained within a single class. I did not draw out an initial design along with what each function would do, as would be required for a more time consuming problem, which meant the functions I opted to use changed and I don’t believe TDD would have improved the development process in this case.

If I wanted to extend the functionality of the solution, I would certainly write unit tests for the current functionality. My solution currently does as expected for all given inputs and for mazes which are impossible. Therefore, it would be important to maintain this functionality during any extensions.

Having said that, I did make use of a test using JUnit to be able to see the output for all the given inputs. I did not assert that these outputs were strictly correct, I simply verified each one manually.

I believe this problem is a good assessment of coding ability, knowledge of algorithms and data structures. It is a satisfying and enjoyable problem to solve, as it represents a real world problem, produces a tangible output, forces the consideration of multiple technical problems and produces a large enough solution that efforts to write clean code are visible and worth-while. This problem took me roughly an hour to write an initial solution and a further half an hour to do some cleanup, readability refactoring, packaging and writing this analysis.

[1] http://en.wikipedia.org/wiki/Pairing_function#Cantor_pairing_function
