# Ants
Implementations of Breadth first, Dijkstra and AStar algorithms, applied to a grid of hexagons.

Why Ants? This was a programming test where the scenario given was a honeycomb where one cell was filled with honey, 
several cells were filled with wax which could not be eaten through and the ant could only eat through a fixed number of cells before it got tired, or gave up, or died or something. Task was to find the shortest path from the start position (given by the id of the cell) and the end
position (id), avoiding the wax cells.
This solution is heavily indebted to (i.e. ripped off from) https://www.redblobgames.com/ for both the search algorithm implementations and hex maps.
