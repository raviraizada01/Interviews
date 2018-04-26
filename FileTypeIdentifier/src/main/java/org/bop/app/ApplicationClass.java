package org.bop.app;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.bop.JSON.JsonParser;
import org.bop.csv.CSVFileReader;
import org.bop.xls.XLSReader;

/**
 * @author Ravi Raizada
 * @Description This is the main application class from where all the functions will be called and output will be generated 
 *
 */
public class ApplicationClass
{
	public static void main(String[] args) throws IOException
	{
		//Here we can set the list of files as input, Later on we can take this input from some webservice or from UI.
		
		Set<String> files = new HashSet<String>(Arrays.asList("config.xml", "web.xml", "my.doc", "test.java", "mov.torrent", "song.mp3"));
		
		//This will create a set of File Extensions from the files list to avoid duplicate extension. 
		Set<String> types =	files.stream()
								 .map(name -> name.substring(name.lastIndexOf(".")).toUpperCase())
								 .collect(Collectors.toSet());
		
		String XlsFilePath = "bin\\data\\categoryData.xls";
		String JSONFilePath = "bin\\data\\languageData.json";
		String CSVFilePath = "bin\\data\\generalData.csv";
		
		Map<String, Map<String, String>> categoryMap = XLSReader.findCategoryFromXls(XlsFilePath, types); //Get category data from XLS file 
		Map<String, String> typeMap = JsonParser.findTypeFromJson(JSONFilePath, types); //Get Language data from JSON file
		Map<String, String> descMap = CSVFileReader.getDescriptionFromCSV(CSVFilePath, types); //Get Description from CSV file. 
		
		List<FileDescription> fileDescriptionList = ApplicationClass.setFileData(categoryMap, typeMap, descMap, files);

		//In order to serialize this List into JSON format which can be sent back through the API.
		String fileDescJson = new flexjson.JSONSerializer().exclude("class").prettyPrint(true).deepSerialize(fileDescriptionList);
		
		System.out.println(fileDescJson);
	}
	
	public static List<FileDescription> setFileData(Map<String, Map<String, String>> categoryMap, Map<String, String> typeMap, Map<String, String> descMap, Set<String> files){
		List<FileDescription> fileDescriptionList = new ArrayList<>();
		for (String file:files)
		{
			FileDescription fileDesc = new FileDescription();
			String type = file.substring(file.lastIndexOf(".")).toUpperCase();
			fileDesc.setFileType(type);
			fileDesc.setFileName(file);
			fileDesc.setCategory(categoryMap.get(type).get("category"));
			fileDesc.setCategoryDescription(categoryMap.get(type).get("categoryDescription"));
			fileDesc.setPopularity(categoryMap.get(type).get("popularity"));
			fileDesc.setDescription(descMap.getOrDefault(type, "No Information Available"));
			fileDesc.setLanguage(typeMap.getOrDefault(type, "No Information Available"));
			fileDescriptionList.add(fileDesc);
		}
		return fileDescriptionList;
	}
}

