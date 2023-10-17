

package vl.iiitb.external.loader.impl;


import java.io.BufferedReader;
import java.io.FileReader;
import java.util.StringTokenizer;

import vl.iiitb.external.loader.MeshLoader;
import vl.iiitb.external.types.Simplex;
import vl.iiitb.external.types.Tetrahedron;
import vl.iiitb.external.types.Vertex;

/**
 * 
 * This class is used to load a tetrahedral mesh. It assumes the following format for the mesh:
 * <ul>
 * <li>	First line specifies the no. of vertices (nv) followed by the number of tetrahedrons (nt) (space seperated) </li>
 * <li>	The next nv lines contains <br/>
 * 		x y z [f] <br/>
 * 		where x, y & z specify the co-ordinates of the vertex and f specifies the function value. </li>
 * <li>	the next nt lines has <br/>
 * 		v1 v2 v3 v4 <br/>
 * 		where v1, v2, v3 and v4 are the vertex indices of the vertices that form the tetrahedron. </li>
 * </ul>
 * 
 */
public class TetMeshLoader implements MeshLoader {

	private BufferedReader reader;
	private int noVertices;
	private int noTets;
	private int curVertex;
	private int curTet;
	private String mesh;
	
	@Override
	public void setInputFile(String inputMesh) {
		try {
			mesh = inputMesh;
			reader = new BufferedReader(new FileReader(inputMesh));
			String s = reader.readLine();
			if(s.equalsIgnoreCase("OFF")) {
				s = reader.readLine();
			}
			String[] r = splitString(s);
			noVertices = Integer.parseInt(r[0].trim());
			if (r.length == 1) {
				s = reader.readLine();
				r = splitString(s);
				noTets = Integer.parseInt(r[0].trim());
			} else {
				noTets = Integer.parseInt(r[1].trim());
			}

			System.out.println("No. of Vertices : " + noVertices);
			System.out.println("No. of Tetrahedrons : " + noTets);

			curVertex = 0;
			curTet = 0;
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	@Override
	public int getVertexCount() {
		return noVertices;
	}

	@Override
	public int getSimplexCount() {
		return noTets;
	}

	@Override
	public Simplex getNextSimplex() {
		try {
			if (curVertex < noVertices) {
				float x, y, z, fn;
				String s = reader.readLine();
				String[] r = splitString(s);
				x = Float.parseFloat(r[0].trim());
				y = Float.parseFloat(r[1].trim());
				z = Float.parseFloat(r[2].trim());
				if (r.length == 4 && r[3].trim().length() > 0) {
					fn = Float.parseFloat(r[3].trim());
				} else {
					fn = -1;
				}
				Vertex vertex = new Vertex();
				vertex.c = new float[3];
				vertex.c[0] = x;
				vertex.c[1] = y;
				vertex.c[2] = z;
				vertex.f = fn;

				curVertex++;
				return vertex;
			}
			if (curTet < noTets) {
				String s = reader.readLine();
				String[] r = splitString(s);
				int v1 = Integer.parseInt(r[0]);
				int v2 = Integer.parseInt(r[1]);
				int v3 = Integer.parseInt(r[2]);
				int v4 = Integer.parseInt(r[3]);

				Tetrahedron tet = new Tetrahedron();
				tet.v1 = v1;
				tet.v2 = v2;
				tet.v3 = v3;
				tet.v4 = v4;

				curTet++;
				return tet;
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
		return null;
	}

	private static String[] splitString(String s) {
		String[] ret = null;
		StringTokenizer tok = new StringTokenizer(s);
		ret = new String[tok.countTokens()];
		for (int i = 0; i < ret.length; i++) {
			ret[i] = tok.nextToken();
		}
		return ret;
	}
	
	@Override
	public void reset() {
		try {
			reader.close();
			setInputFile(mesh);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public int[] getVertexMap() {
		return null;
	}

}
