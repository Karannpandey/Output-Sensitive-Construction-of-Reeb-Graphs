
package vl.iiitb.reebgraph.cmd;

import java.io.PrintStream;

import vl.iiitb.reebgraph.ui.BranchDecomposition;
import vl.iiitb.reebgraph.ui.ReebGraphData;
import vl.iiitb.reebgraph.ui.ReebGraphLayout;
import vl.iiitb.reebgraph.ui.SimplifyReebGraph;
import vl.iiitb.reebgraph.ui.ReebGraphLayout.Point;

public class LayoutReebGraph {

	ReebGraphData reebGraph;
	BranchDecomposition decomp;
	SimplifyReebGraph simplify;
	ReebGraphLayout layout;
	
	public void readReebGraph(String rgFile) {
		reebGraph = new ReebGraphData(rgFile);
		decomp = new BranchDecomposition(reebGraph, true);
		simplify = new SimplifyReebGraph(reebGraph, decomp);
		layout = new ReebGraphLayout();
	}
	
	public void writeLayoutCoordinates(String op, float sim) {
		simplify.simplify(sim);
		layout.layoutBranches(decomp, reebGraph, simplify.lastIncludedBranch);
		try {
			PrintStream pr = new PrintStream(op);
			int i = 0;
			for (Point node : layout.nodes) {
				if(simplify.drawNode[i]) {
					int v = reebGraph.nodes[i].v;
					pr.println(v + " " + node.loc.x + " " + node.loc.y + " " + node.loc.z);
				}
				i ++;
			}
			pr.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	
	public static void main(String[] args) {
		if(args.length != 2 && args.length != 3) {
			System.err.println("Invalid arguments");
			System.err.println("Arguments must be as follows : <input rg file> <output file> [simplification factor]");
			System.err.println("simplification factor is optional");
			System.exit(0);
		}
		String ip = args[0];
		String op = args[1];
		float sim = 0;
		if(args.length == 3) {
			try {
				sim = Float.parseFloat(args[2]);
				if(sim < 0 || sim > 1) {
					System.err.println("invalid simplification factor: should be a value between 0 and 1");
					System.exit(0);
				}
			} catch (Exception e) { 
				System.err.println("invalid simplification factor: should be a value between 0 and 1");
				System.exit(0);
			}
		}
		LayoutReebGraph layout = new LayoutReebGraph();
		layout.readReebGraph(ip);
		layout.writeLayoutCoordinates(op, sim);
	}	
}
