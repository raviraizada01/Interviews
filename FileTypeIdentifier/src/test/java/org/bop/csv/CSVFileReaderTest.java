package org.bop.csv;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

public class CSVFileReaderTest
{
	private static Set<String> filesMap= null;
	private static Set<String> extensionMap= null;
	private static String CSVFilePath = null;
	private static Map<String, String> returnMap = new HashMap<>();
	
	@BeforeClass
	public static void loadData(){
		filesMap = new HashSet<String>(Arrays.asList("config.xml", "web.xml", "my.doc", "test.java", "mov.torrent", "song.mp3"));
		extensionMap =	filesMap.stream()
				 .map(name -> name.substring(name.lastIndexOf(".")).toUpperCase())
				 .collect(Collectors.toSet());
		CSVFilePath = "bin\\data\\generalData.csv";
	}
	
	@Before
	public void loadReturnMap(){
		returnMap.put(".mp3", "mp3PRO Audio file");
		returnMap.put(".java", "Java source code file");
		returnMap.put(".doc", "Document text file");
		returnMap.put(".xml", "Extensible Markup Language file");
	}
	
	@Test
	public void testGetDescriptionFromCSV() throws IOException{
		Assert.assertEquals(CSVFileReader.getDescriptionFromCSV(CSVFilePath, extensionMap), returnMap);
	}
	
	@Test
	public void testEmptyMap() throws IOException{
		Assert.assertEquals(CSVFileReader.getDescriptionFromCSV(CSVFilePath, new HashSet<String>()), new HashMap<String,String>());
	}
	
	@Test(expected = IOException.class)
	public void testWrongPath() throws IOException{
		CSVFileReader.getDescriptionFromCSV("d:\\ab.csv", extensionMap);
	}
	
	@Test
	public void testForNullValues() throws IOException{
		Assert.assertNotNull(CSVFileReader.getDescriptionFromCSV(null, null));
	}
	
}
