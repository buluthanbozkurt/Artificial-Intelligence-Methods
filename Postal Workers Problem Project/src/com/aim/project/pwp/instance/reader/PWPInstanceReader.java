package com.aim.project.pwp.instance.reader;


import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Random;
import com.aim.project.pwp.instance.Location;
import com.aim.project.pwp.instance.PWPInstance;
import com.aim.project.pwp.interfaces.PWPInstanceInterface;
import com.aim.project.pwp.interfaces.PWPInstanceReaderInterface;


public class PWPInstanceReader implements PWPInstanceReaderInterface {
	
	@Override
	public PWPInstanceInterface readPWPInstance(Path path, Random random) {

		BufferedReader bfr;
		String line = null;
		Location[] arrayLocations = null;
		int locationCount=0;
		Location postOffice = null;
		Location workerAddress = null;
		
        try {
			
			bfr = Files.newBufferedReader(path);    
			
			while ((line = bfr.readLine()) != null) {
			
                arrayLocations = new Location[38];

		        if (line.equalsIgnoreCase("POSTAL_OFFICE")) { //get post office depot location
					if ((line = bfr.readLine()) != null) {
	                        String[] y1 = line.split("\\s+");
	                        postOffice = new Location(Double.parseDouble(y1[0]), Double.parseDouble(y1[1])); 
					}
		        }
		        else if (line.equalsIgnoreCase("WORKER_ADDRESS")) { //get workers home address location
					if ((line = bfr.readLine()) != null) {						
	                        String[] y2 = line.split("\\s+");
	                        workerAddress = new Location(Double.parseDouble(y2[0]), Double.parseDouble(y2[1]));  
					}
		        }
		        else if (line.equalsIgnoreCase("POSTAL_ADDRESSES")) { //get delivery locations
					for(int i = 0; i < 38; i++) {
						if ((line = bfr.readLine())!=null) {
							if(line.compareTo("EOF")!=0)
							{
	                        String[] y3 = line.split("\\s+");
	                        arrayLocations[i] = new Location(Double.parseDouble(y3[0].trim()), Double.parseDouble(y3[1].trim()));
	                        locationCount++;// how many locations are there
							}
						}
		        }
			  }
			}
			
		} catch (IOException e) {

			e.printStackTrace();
			return null;
		}
        // recreate location list
        Location[] arrayLocationsFit= new Location[locationCount];
        for (int i = 0; i < locationCount; i++) {
        	arrayLocationsFit[i]=arrayLocations[i];
		}
        
    	//PWPInstance instance = new PWPInstance(38, arrayLocations, postOffice, workerAddress, random);
		PWPInstance instance = new PWPInstance(arrayLocationsFit.length, arrayLocationsFit, postOffice, workerAddress, random);
		
		
		return instance;
	}
	
}