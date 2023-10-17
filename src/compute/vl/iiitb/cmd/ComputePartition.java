
package vl.iiitb.cmd;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import vl.iiitb.utils.Utilities;

public class ComputePartition {

	public static void main(String[] args) {
		try {
			Properties p = new Properties();
			p.load(new FileInputStream("input.properties"));
			String ip = p.getProperty("inputFile").trim();
			String fn = p.getProperty("inputFunction").trim();
			String op = null;
			try {
				 op = p.getProperty("output").trim();	
			} catch (Exception e) {
				op = null;
			}
			if(op != null && op.equalsIgnoreCase("")) {
				op = null;
			}
			String pFile = null;
			try {
				pFile = p.getProperty("partFile").trim();	
			} catch (Exception e) {
				pFile = null;
			}
			if(pFile != null && pFile.equalsIgnoreCase("")) {
				pFile = null;
			}
			
			String rawFile = null;
			try {
				rawFile = p.getProperty("rawFile").trim();	
			} catch (Exception e) {
				rawFile = null;
			}
			if(rawFile != null && rawFile.equalsIgnoreCase("")) {
				rawFile = null;
			}
			
			SegmentInput part = new SegmentInput();
			if(pFile != null) {
				part.getPartitionInput(pFile, op);
			} else {
				Utilities.er("partition data missing");
			}

			String vrfFile = null;
			try {
				vrfFile = p.getProperty("vrfFile").trim();	
			} catch (Exception e) {
				vrfFile = null;
			}
			if(vrfFile != null && vrfFile.equalsIgnoreCase("")) {
				vrfFile = null;
			}
			if(vrfFile != null) {
				if(vrfFile.endsWith(".off")) {
					part.outputOFF(vrfFile, ip);
				} else {
					if(rawFile == null) {
						part.outputVRF(vrfFile,ip,fn);
					} else {
						String as = p.getProperty("ascii").trim();
						boolean ascii;
						try { 
							ascii = Boolean.parseBoolean(as);
						} catch (Exception e) {
							ascii = false;
						}
						part.outputVRFFromRaw(vrfFile, rawFile, ascii);
					}
				}
			}
			System.out.println("Done!");
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

}
