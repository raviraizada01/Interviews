package org.bop.xls;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;


public class XLSReaderTest
{
	private static Map<String, Map<String, String>> finalMap = new HashMap<>();
	private static Set<String> filesMap= null;
	private static Set<String> extensionMap= null;
	private static String XlsFilePath = null;
	
	@BeforeClass
	public static void loadData(){
		
		filesMap = new HashSet<String>(Arrays.asList("config.xml", "web.xml", "my.doc", "test.java", "mov.torrent", "song.mp3"));
		extensionMap =	filesMap.stream()
				 .map(name -> name.substring(name.lastIndexOf(".")).toUpperCase())
				 .collect(Collectors.toSet());
		XlsFilePath = "bin\\data\\categoryData.xls";
		
		Map<String, String> tempMap = new HashMap<>();
		tempMap.put("type", ".MP3");
		tempMap.put("categoryDescription", "MP3 Audio File");
		tempMap.put("popularity", "172.0");
		tempMap.put("category", "Audio");
		finalMap.put(".MP3", Collections.unmodifiableMap(tempMap));
	
		tempMap = new HashMap<>();
		tempMap.put("type", ".TORRENT");
		tempMap.put("categoryDescription", "BitTorrent File");
		tempMap.put("popularity", "175.0");
		tempMap.put("category", "Misc");
		finalMap.put(".TORRENT", Collections.unmodifiableMap(tempMap));
		
		tempMap = new HashMap<>();
		tempMap.put("type", ".JAVA");
		tempMap.put("categoryDescription", "Java Source Code File");
		tempMap.put("popularity", "178.0");
		tempMap.put("category", "Developer");
		finalMap.put(".JAVA", Collections.unmodifiableMap(tempMap));
		
		tempMap = new HashMap<>();
		tempMap.put("type", ".DOC");
		tempMap.put("categoryDescription", "WordPad Document");
		tempMap.put("popularity", "226.0");
		tempMap.put("category", "Text");
		finalMap.put(".DOC", Collections.unmodifiableMap(tempMap));
		
		tempMap = new HashMap<>();
		tempMap.put("type", ".XML");
		tempMap.put("categoryDescription", "XML File");
		tempMap.put("popularity", "239.0");
		tempMap.put("category", "Data");
		finalMap.put(".XML", Collections.unmodifiableMap(tempMap));
	}
	
	@Test
	public void testfindCategoryFromXls() throws FileNotFoundException, IOException{
		Assert.assertEquals(finalMap, XLSReader.findCategoryFromXls(XlsFilePath, extensionMap));
	}
	
	@Test
	public void testEmptyMap() throws IOException{
		Assert.assertEquals(XLSReader.findCategoryFromXls(XlsFilePath, new HashSet<>()), new HashMap<String,String>());
	} 
	
	@Test(expected = FileNotFoundException.class)
	public void testWrongPath() throws IOException{
		XLSReader.findCategoryFromXls("d:\\wrongPath", extensionMap);
	}
	
	@Test
	public void testForNullValues() throws FileNotFoundException, IOException{
		Assert.assertNotNull(XLSReader.findCategoryFromXls(null, null));
	}
}
