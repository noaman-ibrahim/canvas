A simple console drawing program in java.

|Command 		|Description|
|----|----|
|C w h          | Create a new canvas of width w and height h.|
|L x1 y1 x2 y2  | Draw a new line from (x1,y1) to (x2,y2). Currently, only|
|               | horizontal or vertical lines are supported. Horizontal and vertical lines|
|               | will be drawn using the 'x' character.|
|R x1 y1 x2 y2  | Draw a rectangle whose upper left corner is (x1,y1) and|
|               | lower right corner is (x2,y2). Horizontal and vertical lines will be drawn|
|               | using the 'x' character.|
|B x y c        | Fill the entire area connected to (x,y) with "colour" c. The|
|               | behaviour of this is the same as that of the "bucket fill" tool in paint|
|               | programs.|
|Q              | Quit|

__Sample Run__

Below is the output from a sample run of the program.

Enter command: C 20 4
--------------------
|                  |
|                  |
|                  |
|                  |
--------------------

Enter command: L 1 2 6 2
--------------------
|                  |
|xxxxxx            |
|                  |
|                  |
--------------------

Enter command: L 6 3 6 4
--------------------
|                  |
|xxxxxx            |
|     x            |
|     x            |
--------------------

Enter command: R 14 1 18 3
--------------------
|             xxxxx|
|xxxxxx       x   x|
|     x       xxxxx|
|     x            |
--------------------

Enter command: B 10 3 o
--------------------
|oooooooooooooxxxxx|
|xxxxxxooooooox   x|
|     xoooooooxxxxx|
|     xoooooooooooo|
--------------------

Enter command: L 6 4 13 4
--------------------
|oooooooooooooxxxxx|
|xxxxxxooooooox   x|
|     xoooooooxxxxx|
|     xxxxxxxxooooo|
--------------------

Enter command: B 1 2 .
--------------------
|oooooooooooooxxxxx|
|......ooooooox   x|
|     .oooooooxxxxx|
|     ........ooooo|
--------------------

Enter command: Q