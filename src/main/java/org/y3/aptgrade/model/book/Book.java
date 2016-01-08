package org.y3.aptgrade.model.book;

import com.sebn.gsd.aptgrade.core.database.Model;
import org.neo4j.graphdb.Node;

/**
 *
 * @author christian.rybotycky
 */
public class Book extends Model {
    
    public final static String RESOURCE_KEY_TITLE = "TITLE";
    public final static String RESOURCE_KEY_YEAR = "YEAR";
    public final static String RESOURCE_KEY_AUTHOR = "AUTHOR";

    public Book(Node modelNode) {
        super(modelNode);
    }
    
    public void setTitle(String title) {
        setProperty(RESOURCE_KEY_TITLE, title);
    }
    
    public String getTitle() {
        return getPropertyAsString(RESOURCE_KEY_TITLE);
    }
    
    public void setYear(int year) {
        setProperty(RESOURCE_KEY_YEAR, year);
    }
    
    public int getYear() {
        return getPropertyAsInt(RESOURCE_KEY_YEAR);
    }
    
    public void setAuthor(String author) {
        setProperty(RESOURCE_KEY_AUTHOR, author);
    }
    
    public String getAuthor() {
        return getPropertyAsString(RESOURCE_KEY_AUTHOR);
    }

    @Override
    public String toString() {
        return getTitle();
    }
    
}
