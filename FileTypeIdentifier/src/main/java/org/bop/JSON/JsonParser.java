package org.bop.JSON;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Ravi Raizada
 * @Description This class will traverse a JSON file and find out the details of Extensions provided.
 *
 */
public class JsonParser
{
	
	/**
	 * @param filePath
	 * @param types
	 * @return Map containing details of Language type of file types provided
	 * @throws FileNotFoundException
	 */
	public static Map<String, String> findTypeFromJson(String filePath, Set<String> types) throws FileNotFoundException
	{
		Map<String, String> returnMap = new HashMap<>();
		
		if(filePath == null || filePath == " " || types == null || types.isEmpty())
		{
			return returnMap; //Return empty map if filePath or types is null or empty
		}
		else
		{
		Map<String, String> fileDesc = new flexjson.JSONDeserializer<List<JSONData>>()
												  .use(null, ArrayList.class)
												  .use("values", JSONData.class)
												  .deserialize(new FileReader(filePath))
												  .stream()
												  .filter(data -> types.contains(data.getType()))
												  .collect(Collectors.toConcurrentMap(JSONData::getType, JSONData::getLanguage));
		
		
		if (!fileDesc.isEmpty())
			{
				types.stream().forEach(ext ->
				{
					if (fileDesc.get(ext) != null)
					{
						returnMap.put(ext, fileDesc.get(ext));
					}
					else
					{
						returnMap.put(ext, "No Information Available");
					}
				});
			}
		}
		return returnMap;
	}
}

/**
 * @author Ravi Raizada
 * @Description This class is used for JSON Serialization and Deserialization of JSON Data
 */
class JSONData
{
	private String Type;
	private String Language;

	public String getType()
	{
		return Type;
	}

	public void setType(String type)
	{
		Type = type.toUpperCase();
	}

	public String getLanguage()
	{
		return Language;
	}

	public void setLanguage(String language)
	{
		Language = language;
	}
}
