package org.bop.xls;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;

/**
 * @author Ravi Raizada
 * @Description This class will traverse a XLS Workbook and find out the category and details of Extensions provided.
 *
 */
public class XLSReader
{
	private static Map<String, Map<String, String>> finalMap = new HashMap<>();
	private static String NO_INFO = "No Information Available";

	/**
	 * @param filePath
	 * @param types
	 * @return 
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Map<String, String>> findCategoryFromXls(String filePath, Set<String> types) throws FileNotFoundException, IOException
	{
		if(filePath == null || filePath == " " || types == null || types.isEmpty())
		{
			return new HashMap<String, Map<String, String>>(); //Return empty map if filePath or types is null or empty
		}
		try (FileInputStream file = new FileInputStream(new File(filePath)); HSSFWorkbook workbook = new HSSFWorkbook(file))
		{
			int length = workbook.getNumberOfSheets();
			int sheetNo = 0;
			
			List<Callable<SearchSheet>> sheets = new ArrayList<>();

			ExecutorService service = Executors.newFixedThreadPool(3);

			while (sheetNo < length)
			{
				sheets.add(new SearchSheet(workbook.getSheetAt(sheetNo), types));
				sheetNo++;
			}

			List<Future<SearchSheet>> future = service.invokeAll(sheets);

			for (Future<SearchSheet> s : future)
			{
				Map<String, String> returnMap = (Map<String, String>) s.get();
				if (returnMap != null && !returnMap.isEmpty())
				{
					finalMap.put(returnMap.get("type"), returnMap);
				}
			}
			for (String type : types)
			{
				if(finalMap.get(type)==null){
					Map<String, String> tempMap = new HashMap<>();
					tempMap.put("type", type);
					tempMap.put("categoryDescription", NO_INFO);
					tempMap.put("popularity", NO_INFO);
					tempMap.put("category", NO_INFO);
					finalMap.put(type, tempMap);
				}
			}
		}
		catch (InterruptedException | ExecutionException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return finalMap;
	}
}

@SuppressWarnings("rawtypes")
class SearchSheet implements Callable
{
	private HSSFSheet searchableSheet;
	private Set<String> types;

	public SearchSheet(HSSFSheet searchableSheet, Set<String> types)
	{
		super();
		this.searchableSheet = searchableSheet;
		this.types = types;
	}

	@Override
	public Map<String, String> call() throws Exception
	{
		Map<String, String> returnMap = new HashMap<>();
		try
		{
			List<Row> categories = StreamSupport.stream(searchableSheet.spliterator(), true)
											  .parallel()
											  .filter(row -> types.contains(row.getCell(0).toString()))
											  .collect(Collectors.toList());
			if (!categories.isEmpty())
			{
				for (Row row : categories)
				{
					returnMap.put("type", row.getCell(0).toString());
					returnMap.put("categoryDescription", row.getCell(1).toString());
					returnMap.put("popularity", row.getCell(2).toString());
					returnMap.put("category", searchableSheet.getSheetName());
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return returnMap;
	}
}
