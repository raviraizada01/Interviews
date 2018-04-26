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

public class ApplicationClass
{
	public static void main(String[] args) throws IOException
	{
		Set<String> files = new HashSet<String>(Arrays.asList("config.xml", "web.xml", "my.doc", "test.java", "mov.torrent", "song.mp3"));
		Set<String> types =	files.stream()
								 .map(name -> name.substring(name.lastIndexOf(".")).toUpperCase())
								 .collect(Collectors.toSet());
		
		String XlsFilePath = "bin\\data\\categoryData.xls";
		String JSONFilePath = "bin\\data\\languageData.json";
		String CSVFilePath = "bin\\data\\generalData.csv";
		
		Map<String, Map<String, String>> categoryMap = XLSReader.findCategoryFromXls(XlsFilePath, types);
		Map<String, String> typeMap = JsonParser.findTypeFromJson(JSONFilePath, types);
		Map<String, String> descMap = CSVFileReader.getDescriptionFromCSV(CSVFilePath, types);
		
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
		
		String fileDescJson = new flexjson.JSONSerializer().exclude("class").prettyPrint(true).deepSerialize(fileDescriptionList);
		System.out.println(fileDescJson);

	}
}

