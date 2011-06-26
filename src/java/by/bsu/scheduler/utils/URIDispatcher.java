package by.bsu.scheduler.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import org.json.simple.parser.ContainerFactory;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class URIDispatcher {

    private Map<String, String> mapping;

    public URIDispatcher(ServletContext mainServletContext) {
        this.mapping = new HashMap<String, String>();

        File file = new File(mainServletContext.getRealPath("/WEB-INF/url.json"));
        StringBuffer contents = new StringBuffer();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String text = null;
            while ((text = reader.readLine()) != null) {
                contents.append(text);
                contents.append(System.getProperty("line.separator"));
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(URIDispatcher.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(URIDispatcher.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                reader.close();
            } catch (IOException ex) {
                Logger.getLogger(URIDispatcher.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NullPointerException ex) {
                Logger.getLogger(URIDispatcher.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        JSONParser parser = new JSONParser();
        ContainerFactory containerFactory = new ContainerFactory() {

            public Map createObjectContainer() {
                return new HashMap();
            }

            public List createArrayContainer() {
                return new ArrayList();
            }
        };

        try {
            this.mapping = (Map) parser.parse(contents.toString(), containerFactory);
        } catch (ParseException ex) {
            Logger.getLogger(URIDispatcher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getClassByURI(String url) {
        return this.mapping.get(url);
    }
}
