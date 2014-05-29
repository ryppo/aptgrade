package org.y3.aptgrade.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.ResourceIterable;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.index.IndexHits;
import org.neo4j.graphdb.index.ReadableIndex;
import org.neo4j.kernel.EmbeddedGraphDatabase;
import org.y3.aptgrade.model.DefaultModel;
import org.y3.aptgrade.model.Model;
import org.y3.aptgrade.model.ModelFactory;

/**
 *
 * @author christian.rybotycky
 */
public class GraphDatabase {
    
    private EmbeddedGraphDatabase graphDb = null;
    private String pathToDatabase;
    
    private String toConsole;
    private Node rootNode;
    private Node secondNode;
    private Relationship relationShip;

    public GraphDatabase() {
        registeredModelFactories = new HashMap();
    }
    
    public static enum RelTypes implements RelationshipType {
        KNOWS, BELONGS_TO
    }
    
    /**
     * First parameter is modeltype
     */
    private final Map<String, ModelFactory> registeredModelFactories;
    
    public boolean init(String _pathToDatabase) {
        pathToDatabase = _pathToDatabase;
        boolean success = false;
        if (pathToDatabase != null && pathToDatabase.length() > 0) {
            Map<String, String> config = new HashMap<>();
            config.put("neostore.nodestore.db.mapped_memory", "10M");
            config.put("string_block_size", "60");
            config.put("array_block_size", "300");
            //config.put(GraphDatabaseSettings.node_keys_indexable.name(), Model.RESOURCE_KEY_MODEL_TYPE);
            //config.put(GraphDatabaseSettings.node_auto_indexing.name(), "true");
            graphDb = (EmbeddedGraphDatabase) new GraphDatabaseFactory().newEmbeddedDatabaseBuilder(pathToDatabase)
                    .setConfig(config).newGraphDatabase();
            Runtime.getRuntime().addShutdownHook(new Thread() {
                @Override
                public void run() {
                    shutdown();
                }
            });
            if (graphDb != null) {
                success = true;
            }
        }
        return success;
    }
    
    public void shutdown() {
        graphDb.shutdown();
    }
    
    public Node createNode(String modelType) {
        Node newNode = null;
        Transaction tx = graphDb.beginTx();
        try {
            newNode = graphDb.createNode();
            newNode.setProperty(Model.RESOURCE_KEY_MODEL_TYPE.toString(), modelType);
            tx.success();
        } finally {
            tx.finish();
        }
        return newNode;
    }
    
    public void deleteNode(Node nodeToDelete) {
        Transaction tx = graphDb.beginTx();
        try {
            nodeToDelete.delete();
            tx.success();
        } finally {
            tx.finish();
        }
    }
    
    public void listAllModelsToConsole() {
        ArrayList<Model> models = getAllModels(true);
        int i = 1;
        for (Iterator<Model> it = models.iterator(); it.hasNext();) {
            Model model = it.next();
            System.out.println(i + "] " + model.getModelType() + " --> " + model.toString());
            i++;
        }
    }
    
    public void listAllNodesToConsole() {
        Transaction tx = graphDb.beginTx();
        try {
            Iterable<Node> nodes = getAllDatabaseNodes();
            int i = 1;
            for (Node node : nodes) {
                System.out.println(i + "] " + node.toString());
                i++;
                tx.success();
            }
        } finally {
            tx.finish();
        }
    }
    
    public Iterable<Node> getAllDatabaseNodes() {
        Iterable<Node> nodes = null;
        Transaction tx = graphDb.beginTx();
        try {
            nodes = graphDb.getAllNodes();
            tx.success();
        } finally {
            tx.finish();
        }
        return nodes;
    }
    
    public void deleteRelationship(Relationship relationshipToDelete) {
        Transaction tx = graphDb.beginTx();
        try {
            relationshipToDelete.delete();
            tx.success();
        } finally {
            tx.finish();
        }
    }
    
    public ArrayList<Model> getAllModelsOfTypeUsingAutoIndex(String modelType) {
        ArrayList<Model> models = null;
        Transaction tx = graphDb.beginTx();
        try {
            ReadableIndex<Node> autoNodeIndex = graphDb.index().getNodeAutoIndexer().getAutoIndex();
            IndexHits<Node> indexHits = autoNodeIndex.get(Model.RESOURCE_KEY_MODEL_TYPE.toString(), modelType);
            if (indexHits != null) {
                models = new ArrayList<>();
                for (Node node : indexHits) {
                    if (node != null) {
                        if (registeredModelFactories.containsKey(modelType)) {
                            Model modelFromNode = registeredModelFactories.get(modelType).createModelFromNode(node);
                            models.add(modelFromNode);
                        }
                    }
                }
            }
        } finally {
            tx.finish();
        }
        return models;
    }
    
    public ArrayList<Model> getAllModelsOfType(String modelType) {
        ArrayList<Model> models = null;
        Transaction tx = graphDb.beginTx();
        try {
            Iterable<Node> allNodes = getAllDatabaseNodes();
            if (allNodes != null) {
                models = new ArrayList<>();
                for (Iterator<Node> it = allNodes.iterator(); it.hasNext();) {
                    Node node = it.next();
                    if (node.hasProperty(Model.RESOURCE_KEY_MODEL_TYPE) && modelType.equals(node.getProperty(Model.RESOURCE_KEY_MODEL_TYPE))) {
                        Model model = getModelFromNode(node);
                        models.add(model);
                    }
                }
            }
        } finally {
            tx.finish();
        }
        return models;
    }
    
    public ArrayList<Relationship> getAllRelationships(Model model) {
        ArrayList<Relationship> relationships = null;
        Transaction tx = graphDb.beginTx();
        try {
            Iterable<Relationship> relationShips = model.getUnderlyingNode().getRelationships();
            if (relationShips != null) {
                relationships = new ArrayList<>();
                for (Relationship relationship : relationShips) {
                    relationships.add(relationship);
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            tx.finish();
        }
        return relationships;
    }
    
    public Model getRelatedModel(Node startNode, Relationship relationship) {
        Model relatedModel = null;
        if (startNode != null && relationship != null) {
            Transaction tx = graphDb.beginTx();
            try {
                Node otherNode = relationship.getOtherNode(startNode);
                relatedModel = getModelFromNode(otherNode);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                tx.finish();
            }
        }
        return relatedModel;
    }
    
    public Model getRelatedModel(Model startModel, Relationship relationship) {
        if (startModel != null) {
            return getRelatedModel(startModel.getUnderlyingNode(), relationship);
        } else {
            return null;
        }
    }
    
    public ArrayList<Model> getAllRelatedModels(Model startModel, RelationshipType relationshipType) {
        ArrayList<Model> relatedModels = null;
        if (startModel != null && relationshipType != null) {
            Transaction tx = graphDb.beginTx();
            try {
                Iterable<Relationship> relationships = startModel.getUnderlyingNode().getRelationships(relationshipType, Direction.BOTH);
                if (relationships != null) {
                    relatedModels = new ArrayList<>();
                    for (Iterator<Relationship> it = relationships.iterator(); it.hasNext();) {
                        Relationship relationship = it.next();
                        if (relationship != null) {
                            Node node = relationship.getOtherNode(startModel.getUnderlyingNode());
                            relatedModels.add(getModelFromNode(node));
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                tx.finish();
            }
        }
        return relatedModels;
    }
    
    public Model getRelatedModel(Model startModel, RelationshipType relationshipType) {
    Model relatedModel = null;
        if (startModel != null && relationshipType != null) {
            Transaction tx = graphDb.beginTx();
            try {
                Relationship relationShip = startModel.getUnderlyingNode().getSingleRelationship(relationshipType, Direction.BOTH);
                if (relationShip != null) {
                    Node otherNode = relationShip.getOtherNode(startModel.getUnderlyingNode());
                    relatedModel = getModelFromNode(otherNode);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                tx.finish();
            }
        }
        return relatedModel;
    }
    
    public String getRelationShipTypeName(Relationship relationShip) {
        String relationShipTypeName = null;
        if (relationShip != null) {
            Transaction tx = graphDb.beginTx();
            try {
                relationShipTypeName = relationShip.getType().name();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                tx.finish();
            }
        }
        return relationShipTypeName;
    }
    
    public Model getModelFromNode(Node node) {
        Model model = null;
        if (node != null) {
            Transaction tx = graphDb.beginTx();
            try {
                model = new DefaultModel(node);
                if (node.hasProperty(Model.RESOURCE_KEY_MODEL_TYPE)) {
                    String modelType = (String) node.getProperty(Model.RESOURCE_KEY_MODEL_TYPE);
                    if (registeredModelFactories.containsKey(modelType)) {
                        model = registeredModelFactories.get(modelType).createModelFromNode(node);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                tx.finish();
            }
        }
        return model;
    }
    
    public ArrayList<Model> getAllRelatedModels(Model model) {
        ArrayList<Model> relatedModels = null;
        Transaction tx = graphDb.beginTx();
        try {
            Iterable<Relationship> relationShips = model.getUnderlyingNode().getRelationships();
            if (relationShips != null) {
                relatedModels = new ArrayList<>();
                for (Relationship relationship : relationShips) {
                    relatedModels.add(getRelatedModel(model, relationship));
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            tx.finish();
        }
        return relatedModels;
    }
    
    public ArrayList<Model> getAllModels(boolean useProperModelType) {
        Iterable<Node> nodes = getAllDatabaseNodes();
        ArrayList<Model> models = new ArrayList<>();
        Transaction tx = graphDb.beginTx();
        int i = 0;
        try {
            for (Node node : nodes) {
                Model model = new ModelFactory().createModelFromNode(node);
                if (useProperModelType) {
                    if (node.hasProperty(Model.RESOURCE_KEY_MODEL_TYPE)) {
                        String modelType = (String) node.getProperty(Model.RESOURCE_KEY_MODEL_TYPE);
                        if (registeredModelFactories.containsKey(modelType)) {
                            model = registeredModelFactories.get(modelType).createModelFromNode(node);
                        }
                    }
                }
                models.add(model);
            }
            tx.success();
        } finally {
            tx.finish();
        }
        return models;
    }
    
    public ArrayList<Node> getNodesByProperty(String property, Object value) {
        ArrayList<Node> nodes = null;
        ResourceIterable<Node> foundNodes = graphDb.findNodesByLabelAndProperty(null, property, value);
        if (nodes != null) {
            Iterator<Node> iter = foundNodes.iterator();
            while (iter.hasNext()) {
                nodes.add(iter.next());
            }
        }
        return nodes;
    }
    
    public boolean isDatabaseOnline() {
        return graphDb != null;
    }
    
    public void registerModelFactory(ModelFactory modelFactory) {
        if (!registeredModelFactories.containsKey(modelFactory.getModelType())) {
            registeredModelFactories.put(modelFactory.getModelType(), modelFactory);
        }
    }
    
    public void removedModelFactory(ModelFactory modelFactory) {
        if (registeredModelFactories.containsKey(modelFactory.getModelType())) {
            registeredModelFactories.remove(modelFactory.getModelType());
        }
    }
    
    public Map<String, ModelFactory> getAllRegisteredModelFactories() {
        return registeredModelFactories;
    }
    
}
