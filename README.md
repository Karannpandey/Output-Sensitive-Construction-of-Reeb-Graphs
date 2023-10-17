This folder contains the following files / folders:
1. src folder
2. loaders.xml
3. computeReebGraph.sh
4. input.properties
5. Eclipse project files, in case you want to import project into Eclipse
6. build.xml
7. and this Readme

Requirements
------------
Run on linux operating system.
This program requires **Java 1.5** or higher.
To compile this code using the given build.xml Apache Ant is required. (tested on Apache Ant 1.8.0)


Compiling the code
------------------
Compile the code by running the "ant" command
This will create recon.jar file in the build folder.

Usage
----- 
Run the computeReebGraph.sh after setting the following in the input.properties file
* loader - Specifies the input mesh loader type. It should be one of the loaders mentioned in loaders.xml. Currently OFF, TET and SIM are supported.
* inputFile - Location of input file (.off file given in the examples folder).
* inputFunction - Input function should be 0, or i, where i is the co-ordinate index (to be used for the height function along the ith axis).
* output - The location of the output file to be created after running computeReebGraph.sh to display the Reeb graph vertices and edges.
* partFile - File storing the partitioning of the input based on the Reeb graph. The file consists of one line for each vertex denoting the edge that vertex is part of.

Input
------
Some of the input files are given in the "examples" folder.
The library currently supports the following format for the input mesh:

OFF
***
1. Optional first line containing "OFF". (the other two options are TET and SIM but we will use OFF only for triangular mesh).
2. Next line specifies the no. of vertices (nv) followed by the number of triangles (nt) (space seperated).
3. The next nv lines contains x y z [f] where x, y & z specify the co-ordinates of the vertex and f specifies the function value. (If the input type is not f, then the function value is optional).
4. the next nt lines has [3] v1 v2 v3 where v1, v2 and v3 are the vertex indices of the vertices that form the triangles (the 3 is optional).


Output
------
It will be stored in the output file you want to be created, whose path is given in the "output" attribute of input.properties file.

1. The first line of output contains the number of critical points or vertices of the reeb graph n and the number of edges m of the reeb graph separated by a space.
2. The next n lines will display the critical points of the reeb graph and shows whether it is maxima,minima or saddle.
3. After those n lines, the next m lines contain edges of the graph.


Extending code to support other file formats
---------------------------------------------

1. You need to write a loader that extend the interface vl.iiitb.external.loader.MeshLoader
2. Add this loader source in the src/meshloader folder in the appropriate package
3. Register this new loader by providing its name and class information in the loaders.xml file present in the root folder.
4. You can now use this loader by providing its registered name in the input.properties file.
See the javadoc for more details regarding the Loader interface. 


In case of any errors
---------------------
1. If you get a OutOfMemory (java heap) exception, try increasing the memory allocated to the jvm in the run.sh file (there is a -Xmx parameter) and run again.
2. Any other error/exception, kindly let me know of the error. It would be great if you can provide the stack trace of the exception (if it is an exception that has occured) along with the input data.



