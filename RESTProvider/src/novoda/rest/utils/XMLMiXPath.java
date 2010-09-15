
package novoda.rest.utils;

import novoda.mixml.XMLNode;

public class XMLMiXPath extends MiXPath<XMLNode> {

    @Override
    public XMLNode eventArray(XMLNode data, int index) {
        return data.get(index);
    }

    @Override
    public XMLNode eventPath(XMLNode data, String path) {
        return data.path(path);
    }
}
