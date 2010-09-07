package novoda.rest.context.command;

import novoda.rest.parsers.Node;
import novoda.rest.parsers.Node.ParsingOptions;

import org.codehaus.jackson.JsonNode;

import android.util.Log;

public class QueryCommand<T> implements Command<Node<T>> {

    public void applyOptions(ParsingOptions options) {
    }

    @Override
    public void execute(Node<T> data) {
        Log.i("TEST", ((JsonNode)data.getData()).toString());
    }
}
