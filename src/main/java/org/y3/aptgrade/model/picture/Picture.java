package org.y3.aptgrade.model.picture;

import com.sebn.gsd.aptgrade.core.database.Model;
import java.awt.Image;
import org.neo4j.graphdb.Node;

/**
 *
 * @author christian.rybotycky
 */
public class Picture extends Model {
    
    public final static String RESOURCE_KEY_PICTURE = "PICTURE";
    
    public Picture(Node modelNode) {
        super(modelNode);
    }
    
    public void setPicture(Image image) {
        setProperty(RESOURCE_KEY_PICTURE, image);
    }
    
    public Image getPicture() {
        return getPropertyAsImage(RESOURCE_KEY_PICTURE);
    }

    @Override
    public String toString() {
        return getName();
    }
    
}
