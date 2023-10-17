
package vl.iiitb.reebgraph.ui;

import java.util.ArrayList;
import java.util.Iterator;

import vl.iiitb.reebgraph.ui.BranchDecomposition.Branch;

public class SimplifyReebGraph {
	
	public class Partition {
		public ArrayList<Integer> children = new ArrayList<Integer>();
	}
	
	private BranchDecomposition decomp;
	private ReebGraphData rgData;
	
	int noBranches;
	int noNodes;
	float maxPersistence;

	public Partition [] edgePartition;
	public int lastIncludedBranch;
	public boolean [] drawNode;
	
	public SimplifyReebGraph(ReebGraphData rgData, BranchDecomposition decomp) {
		this.rgData = rgData;
		this.decomp = decomp;
		
		setup();
	}
	
	private void setup() {
		noBranches = decomp.branches.size();
		noNodes = rgData.noNodes;
		maxPersistence = decomp.branches.get(0).fn;
		
		edgePartition = new Partition[rgData.noArcs];
		
		for(int i = 0;i < rgData.noArcs;i ++) {
			edgePartition[i] = new Partition();
			edgePartition[i].children.add(i);
		}
		
		drawNode = new boolean[noNodes];
		lastIncludedBranch = noBranches - 1;
		for(int i = 0;i < noNodes;i ++) {
			drawNode[i] = true;
		}
	}
	
	public void simplify(float sim) {
		lastIncludedBranch = noBranches - 1;
		float val = sim * maxPersistence;
		for(int i = 0;i < rgData.noArcs;i ++) {
			edgePartition[i].children.clear();
			edgePartition[i].children.add(i);
		}
		
		for(int i = 0;i < noNodes;i ++) {
			drawNode[i] = false;
		}
		
		for(int i = noBranches-1;i >= 0;i --) {
			Branch br = decomp.branches.get(i);
			if(br.fn < val) {
				removeBranch(i, br);
				lastIncludedBranch --;
			} else {
				break;
			}
		}
		
		for(int i = 0;i <= lastIncludedBranch;i ++) {
			Branch br = decomp.branches.get(i);
			drawNode[br.from] = true;
			drawNode[br.to] = true;
		}
	}

	public void simplify(int simBranches) {
		lastIncludedBranch = noBranches - 1;
		for(int i = 0;i < rgData.noArcs;i ++) {
			edgePartition[i].children.clear();
			edgePartition[i].children.add(i);
		}
		
		for(int i = 0;i < noNodes;i ++) {
			drawNode[i] = false;
		}
		
		for(int i = noBranches-1;i >= 0;i --) {
			Branch br = decomp.branches.get(i);
			if(br.fn == 0 || lastIncludedBranch >= simBranches) {
				removeBranch(i, br);
				lastIncludedBranch --;
			} else {
				break;
			}
		}
		
		for(int i = 0;i <= lastIncludedBranch;i ++) {
			Branch br = decomp.branches.get(i);
			drawNode[br.from] = true;
			drawNode[br.to] = true;
		}
	}
	
	private void removeBranch(int in, Branch br) {
		Branch par = br.parent;
		if(decomp.nodeBranch[br.from] == par.id) {
			// top
			for(Iterator<Integer> it = rgData.nodes[br.from].next.iterator();it.hasNext();) {
				int e = it.next();
				if(par.arcs.contains(e)) {
					for(Iterator<Integer> at = br.arcs.iterator();at.hasNext();) {
						int ee = at.next();
						edgePartition[e].children.addAll(edgePartition[ee].children);
						edgePartition[ee].children.clear();
					}
				}
			}
		} else {
			// bottom
			for(Iterator<Integer> it = rgData.nodes[br.to].prev.iterator();it.hasNext();) {
				int e = it.next();
				if(par.arcs.contains(e)) {
					for(Iterator<Integer> at = br.arcs.iterator();at.hasNext();) {
						int ee = at.next();
						edgePartition[e].children.addAll(edgePartition[ee].children);
						edgePartition[ee].children.clear();
					}
				}
			}
		}
	}
}
