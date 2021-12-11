# Ants
Implementations of Breadth first, Dijkstra and AStar algorithms, applied to a grid of hexagons.

Why Ants? This was a programming test where the scenario given was:
A honeycomb of hexagonals. Some filled with inedible wax and one with honey.
An ant, tring to git his ant ass to the cell with honey in it. 
The ant can only eat through a fixed number of cells before it gets tired, or gives up, 
or dies or something. 
Find the shortest path from the start position (given by the id of the cell) and the end 
position (id), avoiding the wax cells.

This solution is heavily indebted to (i.e. ripped off from) https://www.redblobgames.com/ for 
both the search algorithm implementations and hex maps.
