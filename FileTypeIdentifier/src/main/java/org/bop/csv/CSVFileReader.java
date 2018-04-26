package org.bop.csv;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Ravi Raizada
 * @Description This class will traverse a CSV file and find out the details of Extensions provided.
 *
 */
public class CSVFileReader
{
	private static final String SEPERATOR = ",";
	/**
	 * @param filePath
	 * @param types
	 * @return Map of details of Extensions  
	 * @throws IOException 
	 */
	public static Map<String, String> getDescriptionFromCSV(String filePath, Set<String> types) throws IOException
	{
		Map<String, String> returnMap = new HashMap<>();
		try (Stream<String> stream = Files.lines(Paths.get((filePath))))
		{
		if(types!=null && !types.isEmpty()){
				 returnMap = stream.map(line -> line.split(SEPERATOR))
						.filter(line -> types.contains(line[0].toUpperCase()))
						.collect(Collectors.toMap(line -> line[0], line -> line[1], (p1, p2) -> p1));
				}
		}	
		return returnMap;
	}
}
