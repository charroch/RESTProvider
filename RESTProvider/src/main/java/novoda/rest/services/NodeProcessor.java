package novoda.rest.services;

import novoda.rest.context.CallContext;
import novoda.rest.parsers.Node;

public interface NodeProcessor {
    
    /*
     * process the node
     */
    public void process(Node<?> node, CallContext context);
}
