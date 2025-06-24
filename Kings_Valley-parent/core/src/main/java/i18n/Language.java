package i18n;

import java.util.HashMap;

public class Language
{
    private String fileCode;
    private HashMap<String, String> words = new HashMap<String, String>();

    public Language(String fileCode, HashMap<String, String> words)
    {
	super();
	this.fileCode = fileCode;
	this.words = words;
    }

    public String getFileCode()
    {
        return fileCode;
    }

    public void setFileCode(String fileCode)
    {
        this.fileCode = fileCode;
    }

    public HashMap<String, String> getWords()
    {
        return words;
    }

    public void setWords(HashMap<String, String> words)
    {
        this.words = words;
    }


}
