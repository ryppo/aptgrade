package org.y3.aptgrade.model;

import org.neo4j.graphdb.Node;

public class DefaultModel extends Model {

    public DefaultModel(Node modelNode) {
        super(modelNode);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " [" + this.getModelType() + "]";
    }

}
