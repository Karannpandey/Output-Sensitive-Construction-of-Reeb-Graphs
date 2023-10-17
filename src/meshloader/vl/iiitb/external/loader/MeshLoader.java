
package vl.iiitb.external.loader;

import vl.iiitb.external.types.Edge;
import vl.iiitb.external.types.Simplex;
import vl.iiitb.external.types.Tetrahedron;
import vl.iiitb.external.types.Triangle;
import vl.iiitb.external.types.Vertex;

/**
 * 
 * This interface should be extended in order to write a custom loader to support different input file formats.
 *   
 */
public interface MeshLoader {
	
	/**
	 * This method is first called to set the input. You need to perform the required initialization
	 * in this method.
	 * 
	 * @param inputMesh File name of the input mesh
	 */
	public void setInputFile(String inputMesh);
	
	/**
	 * This method is called after the setInputFile() method.
	 * 
	 * @return	The number of vertices in the input. Should be a positive number
	 */
	public int getVertexCount(); 
	
	/**
	 * This method is called until a null is returned, signifying that the mesh is loaded. 
	 * Care should be taken to ensure that the vertices are returned before the edges, 
	 * triangles or tetrahedra incident of that vertex.
	 * 
	 * @return	The next simplex of the input mesh. This can be either {@link Vertex}, {@link Edge}, {@link Triangle} or {@link Tetrahedron}.
	 * 			<br/>
	 * 			null after the last simplex is returned
	 */
	public Simplex getNextSimplex();
	
	/**
	 * This method is called resets the file pointer to the beginning of the input file. 
	 */
	public void reset();

	public int getSimplexCount(); 
	
	public int [] getVertexMap();
}
