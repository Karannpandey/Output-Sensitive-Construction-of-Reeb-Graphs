
package vl.iiitb.cmd;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;

import vl.iiitb.cmd.WritePartitionedOutput;
import vl.iiitb.reebgraph.ui.ReebGraphData;

public class SegmentInput {

	ReebGraphData rgData;
	public int [] nodeComp;
	
	public void getPartitionInput(String partFile, String rgFile) throws IOException {
		rgData = new ReebGraphData(rgFile);
		try {
			BufferedReader reader = new BufferedReader(new FileReader(partFile));
			int nv = Integer.parseInt(reader.readLine().trim());
			nodeComp = new int[nv];
			for(int i = 0;i < nv;i ++) {
				nodeComp[i] = Integer.parseInt(reader.readLine().trim());
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void output(String op) {
		try {
			ObjectOutputStream p = new ObjectOutputStream(new FileOutputStream(op));
			p.writeObject(nodeComp);
			p.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void outputVRF(String op, String tetFile, String fn) {
		WritePartitionedOutput p = new WritePartitionedOutput();
		p.writePartition(tetFile, fn, rgData, nodeComp, op);
	}
	
	public void outputVRFFromRaw(String op, String rawFile, boolean ascii) {
		WritePartitionedOutput p = new WritePartitionedOutput();
		p.setRaw(true);
		p.writePartition(rawFile, nodeComp, op, ascii);
	}
	
	public void outputOFF(String offFile, String ip) {
		System.gc();
		WritePartitionedOutput p = new WritePartitionedOutput();
		p.writeOffFile(ip, offFile, nodeComp);
	}
}
