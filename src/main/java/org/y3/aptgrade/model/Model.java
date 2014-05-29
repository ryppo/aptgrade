package org.y3.aptgrade.model;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;
import org.y3.aptgrade.database.GraphDatabase;
import org.y3.aptgrade.model.picture.Picture;

public abstract class Model implements Comparable<Model> {

    private Node underlyingNode = null;

    public static final String RESOURCE_KEY_ID = "RESOURCE_KEY_ID";
    public static final String RESOURCE_KEY_MODEL_TYPE = "RESOURCE_KEY_MODEL_TYPE";
    public static final String RESOURCE_KEY_LOGICAL_ID = "RESOURCE_KEY_LOGICAL_ID";
    public static final String RESOURCE_KEY_CREATOR_NAME = "RESOURCE_KEY_CREATOR_NAME";
    public static final String RESOURCE_KEY_CREATION_DATE = "RESOURCE_KEY_CREATION_DATE";
    public static final String RESOURCE_KEY_CHANGER_NAME = "RESOURCE_KEY_CHANGER_NAME";
    public static final String RESOURCE_KEY_CHANGE_DATE = "RESOURCE_KEY_CHANGE_DATE";
    public static final String RESOURCE_KEY_ORIGIN_SYSTEM = "RESOURCE_KEY_ORIGIN_SYSTEM";
    public static final String RESOURCE_KEY_VERSION = "RESOURCE_KEY_VERSION";
    
    public static final String RESOURCE_KEY_PLURAL = "MODELS";
    
    public final static String RESOURCE_KEY_NAME = "NAME";
    public final static String RESOURCE_KEY_DESCRIPTION = "DESCRIPTION";

    public void setProperty(String property, Object value) {
        Transaction tx = underlyingNode.getGraphDatabase().beginTx();
        if (value != null) {
            underlyingNode.setProperty(property, value);
        } else {
            underlyingNode.removeProperty(property);
        }
        underlyingNode.setProperty(RESOURCE_KEY_CHANGE_DATE, new Date().getTime());
        tx.success();
        tx.finish();
    }
    
    public Object getProperty(String property) {
        return getProperty(property, underlyingNode);
    }
    
    public Object getProperty(String property, Node node) {
        Object object = null;
        Transaction tx = node.getGraphDatabase().beginTx();
        Iterable<String> propertyKeys = node.getPropertyKeys();
        if (propertyKeys != null) {
            Iterator<String> iter = propertyKeys.iterator();
            while (iter.hasNext()) {
                String key = iter.next();
                if (key != null && key.equals(property)) {
                    object = node.getProperty(property);
                    break;
                }
            }
        }
        tx.success();
        tx.finish();
        return object;
    }
    
    public void addRelationShipToModel(Model relatedModel, GraphDatabase.RelTypes relationType) {
        Transaction tx = underlyingNode.getGraphDatabase().beginTx();
        underlyingNode.createRelationshipTo(relatedModel.getUnderlyingNode(), relationType);
        tx.success();
        tx.finish();
    }
    
    public void addRelationShipToModel(Node relatedModelNode, RelationshipType relationType) {
        Transaction tx = underlyingNode.getGraphDatabase().beginTx();
        underlyingNode.createRelationshipTo(relatedModelNode, relationType);
        tx.success();
        tx.finish();
    }
    
    public void addRelationShipToModel(Model relatedModel, RelationshipType relationType) {
        Transaction tx = underlyingNode.getGraphDatabase().beginTx();
        underlyingNode.createRelationshipTo(relatedModel.getUnderlyingNode(), relationType);
        tx.success();
        tx.finish();
    }

    public Model(Node modelNode) {
        underlyingNode = modelNode;
    }

    public Node getUnderlyingNode() {
        return underlyingNode;
    }

    @Override
    public abstract String toString();
    
    public String getModelType() {
        return (String) getProperty(RESOURCE_KEY_MODEL_TYPE);
    }

    public int getID() {
        return Integer.parseInt((String) getProperty(RESOURCE_KEY_ID));
    }

    public void setID(int iD) {
        setProperty(RESOURCE_KEY_ID, iD);
    }

    public void setName(String name) {
        setProperty(RESOURCE_KEY_NAME, name);
    }
    
    public String getName() {
        return getPropertyAsString(RESOURCE_KEY_NAME);
    }
    
    public void setDescription(String description) {
        setProperty(RESOURCE_KEY_DESCRIPTION, description);
    }
    
    public String getDescription() {
        return getPropertyAsString(RESOURCE_KEY_DESCRIPTION);
    }
    
    public String getLogicalID() {
        return (String) getProperty(RESOURCE_KEY_LOGICAL_ID);
    }

    public void setLogicalID(String logicalID) {
        setProperty(RESOURCE_KEY_LOGICAL_ID, logicalID);
    }

    public String getCreatorName() {
        return (String) getProperty(RESOURCE_KEY_CREATOR_NAME);
    }

    public void setCreatorName(String creatorName) {
        setProperty(RESOURCE_KEY_CREATOR_NAME, creatorName);
    }

    public Date getCreationDate() {
        return getPropertyAsDate(RESOURCE_KEY_CREATION_DATE);
    }

    public void setCreationDate(Date creationDate) {
        setProperty(RESOURCE_KEY_CREATION_DATE, creationDate);
    }

    public String getChangerName() {
        return (String) getProperty(RESOURCE_KEY_CHANGER_NAME);
    }

    public void setChangerName(String changerName) {
        setProperty(RESOURCE_KEY_CHANGER_NAME, changerName);
    }

    public Date getChangeDate() {
        return getPropertyAsDate(RESOURCE_KEY_CHANGE_DATE);
    }
    
    public void setProperty(String property, Image image) {
        String storableImage = null;
        if (image != null) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            BufferedImage bufferedImage = (BufferedImage) image;
            try {
                ImageIO.write(bufferedImage, "jpg", baos);
                storableImage = Base64.encode(baos.toByteArray());
            } catch (IOException ex) {
                Logger.getLogger(Picture.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        setProperty(property, storableImage);
    }
    
    public Image getPropertyAsImage(String property) {
        Image image = null;
        String encodedImage = (String) getProperty(property);
        if (encodedImage != null) {
            byte[] imageBytes = Base64.decode(encodedImage);
            ByteArrayInputStream bais = new ByteArrayInputStream(imageBytes);
            try {
                image = ImageIO.read(bais);
            } catch (IOException ex) {
                Logger.getLogger(Picture.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return image;
    }
    
    public void setProperty(String property, URL value) {
        String urlString = null;
        if (value != null) {
            urlString = value.toString();
        }
        setProperty(property, urlString);
    }
    
    public URL getPropertyAsUrl(String property) {
        URL url = null;
        String urlString = getPropertyAsString(property);
        if (urlString != null && urlString.length() > 0) {
            try{
                url = new URL(urlString);
            } catch(MalformedURLException ex) {
                Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return url;
    }
    
    public int getPropertyAsInt(String property) {
        int integer = 0;
        String propertyValue = (String) getProperty(property);
        if (propertyValue != null) {
            integer = Integer.parseInt(propertyValue);
        }
        return integer;
    }
    
    public void setProperty(String property, int propertyValue) {
        setProperty(property, Integer.toString(propertyValue));
    }
    
    public double getPropertyAsDouble(String property) {
        double doubleValue = 0d;
        String propertyValue = (String) getProperty(property);
        if (propertyValue != null) {
            doubleValue = Double.parseDouble(propertyValue);
        }
        return doubleValue;
    }
    
    public String getPropertyAsString(String property) {
        String propertyString = null;
        Object propertyValue = getProperty(property);
        if (propertyValue != null && propertyValue instanceof String) {
            propertyString = (String) propertyValue;
        }
        return propertyString;
    }
    
    public void setProperty(String property, double propertyValue) {
        setProperty(property, Double.toString(propertyValue));
    }
    
    public void setProperty(String property, Date date) {
        Long propertyDate = null;
        if (date != null) {
            propertyDate = date.getTime();
        }
        setProperty(property, propertyDate);
    }
    
    public Date getPropertyAsDate(String property) {
        Date dateFromProperty = null;
        Object objectFromProperty = getProperty(property);
        if (objectFromProperty != null && objectFromProperty instanceof Long) {
            dateFromProperty = new Date((long) objectFromProperty);
        }
        return dateFromProperty;
    }

    public void setChangeDate(Date changeDate) {
        setProperty(RESOURCE_KEY_CHANGE_DATE, changeDate);
    }

    public String getOriginSystem() {
        return (String) getProperty(RESOURCE_KEY_ORIGIN_SYSTEM);
    }

    public void setOriginSystem(String originSystem) {
        setProperty(RESOURCE_KEY_ORIGIN_SYSTEM, originSystem);
    }

    public int getVersion() {
        return Integer.parseInt((String) getProperty(RESOURCE_KEY_VERSION));
    }

    public void setVersion(int version) {
        setProperty(RESOURCE_KEY_VERSION, version);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Model
                && underlyingNode.equals(((Model) o).getUnderlyingNode());
    }

    @Override
    public int hashCode() {
        return underlyingNode.hashCode();
    }
    
    @Override
    public int compareTo(Model otherModel) {
        String thisString = toString();
        String otherString = null;
        
        if (thisString == null) {
            thisString = new String();
        }
        
        if (otherModel != null && otherModel.toString() != null) {
            otherString = otherModel.toString();
        }
        if (otherString == null) {
            otherString = new String();
        }
        
        return thisString.compareTo(otherString);
    }
    
    public String getFormattedDate(Date rawDate) {
        String formattedDate = null;
        if (rawDate != null) {
            SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
            formattedDate = formatter.format(rawDate);
        }
        return formattedDate;
    }
    
    public Node getRelatedNode(RelationshipType relationshipType) {
        Node relatedNode = null;
        if (relationshipType != null) {
            Relationship relationShip = getSingleRelationship(relationshipType);
            if (relationShip != null) {
                Transaction tx = underlyingNode.getGraphDatabase().beginTx();
                try {
                    relatedNode = relationShip.getOtherNode(getUnderlyingNode());
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    tx.finish();
                }
            }
        }
        return relatedNode;
    }
    
    public Relationship getSingleRelationship(RelationshipType relationshipType) {
        Relationship relationShip = null;
        if (relationshipType != null) {
            Transaction tx = underlyingNode.getGraphDatabase().beginTx();
            try {
                relationShip = getUnderlyingNode().getSingleRelationship(relationshipType, Direction.BOTH);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                tx.finish();
            }
        }
        return relationShip;
    }
    
    public void deleteRelationship(Relationship relationshipToDelete) {
        Transaction tx = underlyingNode.getGraphDatabase().beginTx();
        try {
            relationshipToDelete.delete();
            tx.success();
        } finally {
            tx.finish();
        }
    }
    
    public static String getPluralString() {
        return RESOURCE_KEY_PLURAL;
    }
    
 }
