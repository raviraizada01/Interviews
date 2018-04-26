package org.bop.JSON;

import java.io.FileNotFoundException;
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


public class JsonParserTest
{
	private static Set<String> filesMap= null;
	private static Set<String> extensionMap= null;
	private static String JSONFilePath = null;
	private static Map<String, String> returnMap = new HashMap<>();
	
	@BeforeClass
	public static void loadData(){
		filesMap = new HashSet<String>(Arrays.asList("config.xml", "web.xml", "my.doc", "test.java", "mov.torrent", "song.mp3"));
		extensionMap =	filesMap.stream()
				 .map(name -> name.substring(name.lastIndexOf(".")).toUpperCase())
				 .collect(Collectors.toSet());
		JSONFilePath = "bin\\data\\languageData.json";
	}
	
	@Before
	public void loadReturnMap(){
		returnMap.put(".MP3", "No Information Available");
		returnMap.put(".TORRENT","No Information Available");
		returnMap.put(".JAVA","Java language source code");
		returnMap.put(".XML","XML document");
		returnMap.put(".DOC","No Information Available");
	}
	
	@Test
	public void testFindTypeFromJson() throws FileNotFoundException{
		Assert.assertEquals(returnMap, JsonParser.findTypeFromJson(JSONFilePath, extensionMap));
	}
	
	@Test
	public void testEmptyMap() throws FileNotFoundException{
		Assert.assertEquals(JsonParser.findTypeFromJson(JSONFilePath, new HashSet<>()), new HashMap<String,String>());
	} 
	
	@Test(expected = FileNotFoundException.class)
	public void testWrongPath() throws FileNotFoundException{
		JsonParser.findTypeFromJson("d:\\wrongpath", extensionMap);
	}
	
	@Test
	public void testForNullValues() throws IOException{
		Assert.assertNotNull(JsonParser.findTypeFromJson(null, null));
	}
	
}