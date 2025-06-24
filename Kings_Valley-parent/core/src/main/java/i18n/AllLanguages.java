package i18n;

import java.util.HashMap;
import java.util.Iterator;

public class AllLanguages
{
    private HashMap<String, Language> languages = new HashMap<String, Language>();

    public void addLanguaje(Language language)
    {
	this.languages.put(language.getWords().get("LANGUAGE_NAME"), language);
    }

    public Language getLanguage(String languageName)
    {
	return this.languages.get(languageName);
    }

    public Iterator<String> getLanguagesName()
    {
	return this.languages.keySet().iterator();
    }

    
}
