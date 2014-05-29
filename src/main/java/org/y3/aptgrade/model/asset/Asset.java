package org.y3.aptgrade.model.asset;

import java.net.URL;
import org.neo4j.graphdb.Node;
import org.y3.aptgrade.model.Model;

/**
 *
 * @author christian.rybotycky
 */
public class Asset extends Model {
    
    public final static String RESOURCE_KEY_ASSET_OWNER = "ASSET_OWNER";
    public final static String RESOURCE_KEY_ASSET_LINK = "ASSET_LINK";

    public Asset(Node modelNode) {
        super(modelNode);
    }
    
    public void setAssetOwner(String assetOwner) {
        setProperty(RESOURCE_KEY_ASSET_OWNER, assetOwner);
    }
    
    public String getAssetOwner() {
        return getPropertyAsString(RESOURCE_KEY_ASSET_OWNER);
    }
    
    public void setAssetLink(URL assetLink) {
        setProperty(RESOURCE_KEY_ASSET_LINK, assetLink);
    }
    
    public URL getAssetLink() {
        return getPropertyAsUrl(RESOURCE_KEY_ASSET_LINK);
    }

    @Override
    public String toString() {
        return getName() + " [" + getAssetOwner() + "]";
    }
    
}
