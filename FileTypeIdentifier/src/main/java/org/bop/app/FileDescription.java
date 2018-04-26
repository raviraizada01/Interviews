package org.bop.app;

import java.io.Serializable;

/**
 * @author Ravi Raizada
 * @Description This class is for creating the final output containing all the information about the file provided.
 */
public class FileDescription implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private String fileName;
	private String fileType;
	private String description;
	private String category;
	private String categoryDescription;
	private String popularity;
	private String language;
	
	public String getFileName()
	{
		return fileName;
	}
	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}
	public String getFileType()
	{
		return fileType;
	}
	public void setFileType(String fileType)
	{
		this.fileType = fileType;
	}
	public String getDescription()
	{
		return description;
	}
	public void setDescription(String description)
	{
		this.description = description;
	}
	public String getCategory()
	{
		return category;
	}
	public void setCategory(String category)
	{
		this.category = category;
	}
	public String getCategoryDescription()
	{
		return categoryDescription;
	}
	public void setCategoryDescription(String categoryDescription)
	{
		this.categoryDescription = categoryDescription;
	}
	public String getPopularity()
	{
		return popularity;
	}
	public void setPopularity(String popularity)
	{
		this.popularity = popularity;
	}
	public String getLanguage()
	{
		return language;
	}
	public void setLanguage(String language)
	{
		this.language = language;
	}
}
