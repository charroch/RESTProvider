package novoda.rest.cursors.xml;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;

import novoda.mixml.XMLNode;
import novoda.rest.cursors.RESTMarshaller;
import novoda.rest.database.SQLiteTableCreator;
import novoda.rest.utils.XMLMiXPath;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.xml.sax.SAXException;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

public class SimpleXMLCursor extends RESTMarshaller<SimpleXMLCursor> {

	private static final String TAG = SimpleXMLCursor.class.getSimpleName();

	public SimpleXMLCursor(Uri uri) {
		super(uri);
	}

	public SimpleXMLCursor() {
		super();
	}

	private CursorParams P;

	private XMLNode node;

	private String[] columnName;

	private List<XMLNode> nodeList = new ArrayList<XMLNode>();

	private List<List<SimpleXMLCursor>> children = new ArrayList<List<SimpleXMLCursor>>();

	private List<List<Uri>> childrenUri = new ArrayList<List<Uri>>();

	public static class CursorParams {
		public String rootName;

		public String fieldId;

		public String nodeName = null;

		public Map<String, String> mapper = new HashMap<String, String>();

		public SQLiteTableCreator sqlCreateMapper;

		public boolean withAutoId = false;

		public List<SimpleXMLCursor> withChildren;

		public Uri uri;

		public CursorParams() {
		}
	}

	public static class Builder {
		private final CursorParams P = new CursorParams();

		public Builder() {
		}

		public Builder withFieldID(final String fieldID) {
			return withFieldID(fieldID, false);
		}

		public Builder withRootNode(final String rootNode) {
			P.rootName = rootNode;
			return this;
		}

		public Builder withNodeName(final String nodeName) {
			P.nodeName = nodeName;
			return this;
		}

		public SimpleXMLCursor create() {
			final SimpleXMLCursor cursor = new SimpleXMLCursor();
			cursor.P = P;
			return cursor;
		}

		public Builder withFieldID(String fieldID, boolean shouldBeUnderscore) {
			if (shouldBeUnderscore)
				P.mapper.put("_id", fieldID);
			return this;
		}

		public Builder withMappedField(final String original,
				final String newValue) {
			P.mapper.put(original, newValue);
			return this;
		}

		public Builder withSQLTableCreator(SQLiteTableCreator creator) {
			P.sqlCreateMapper = creator;
			return this;
		}

		public Builder withAutoID() {
			P.withAutoId = true;
			return this;
		}

		public Builder withChildren(SimpleXMLCursor... child) {
			P.withChildren = new ArrayList<SimpleXMLCursor>(
					Arrays.asList(child));
			return this;
		}
	}

	@Override
	public String[] getColumnNames() {
		return columnName;
	}

	@Override
	public int getCount() {
		return nodeList.size();
	}

	@Override
	public double getDouble(int column) {
		return nodeList.get(mPos).path(getOriginalName(column)).getAsDouble();
	}

	@Override
	public float getFloat(int column) {
		return nodeList.get(mPos).path(getOriginalName(column)).getAsFloat();
	}

	@Override
	public int getInt(int column) {
		if (P.withAutoId && columnName[column].equals("_id")) {
			return mPos;
		}

		XMLNode ret = nodeList.get(mPos);
		if (getOriginalName(column).contains("/")) {
			XMLMiXPath p = new XMLMiXPath();
			ret = p.parse(ret, getOriginalName(column));
			return ret.getAsInt();
		}
		return nodeList.get(mPos).path(getOriginalName(column)).getAsInt();
	}

	private String getOriginalName(int column) {
		return P.mapper.containsKey(columnName[column]) ? P.mapper
				.get(columnName[column]) : columnName[column];
	}

	@Override
	public long getLong(int column) {
		if (P.withAutoId && columnName[column].equals("_id")) {
			return mPos;
		}
		return nodeList.get(mPos).path(getOriginalName(column)).getAsLong();
	}

	@Override
	public short getShort(int column) {
		return nodeList.get(mPos).path(getOriginalName(column)).getAsShort();
	}

	@Override
	public String getString(int column) {
		XMLNode ret = nodeList.get(mPos);
		if (getOriginalName(column).contains("/")) {
			XMLMiXPath p = new XMLMiXPath();
			ret = p.parse(ret, getOriginalName(column));
			return ret.getAsString();
		}
		return nodeList.get(mPos).path(getOriginalName(column)).getAsString();
	}

	@Override
	public boolean isNull(int column) {
		return false;
	}

	@Override
	public boolean onMove(int oldPosition, int newPosition) {
		if (P.withChildren != null) {
			List<SimpleXMLCursor> child = new ArrayList<SimpleXMLCursor>(
					P.withChildren.size());
			List<Uri> childUri = new ArrayList<Uri>(P.withChildren.size());
			for (SimpleXMLCursor c : P.withChildren) {
				c.initDom(nodeList.get(newPosition).path(c.P.rootName));
				child.add(c);
				childUri.add(c.P.uri);
			}
			children.add(child);
			childrenUri.add(childUri);
		}
		return super.onMove(oldPosition, newPosition);
	}

	private void initDom(XMLNode nodeC) {
		node = nodeC;
		try {
			for (String n : P.rootName.split("/")) {
				node = node.path(n);
			}
		} catch (IllegalStateException e) {
			e.printStackTrace();
		}
		init();
	}

	private XMLNode root;

	@Override
	public SimpleXMLCursor handleResponse(HttpResponse response)
			throws ClientProtocolException, IOException {
		if (response == null) {
			throw new IOException("response can't be null");
		}
		node = new XMLNode();
		root = node;
		try {
			node.parse(response.getEntity().getContent());
			for (String n : P.rootName.split("/")) {
				node = node.path(n);
			}
			init();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (FactoryConfigurationError e) {
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			Log.i(TAG, "Can not parse using options: " + e.getMessage());
		} finally {
			response.getEntity().consumeContent();
		}
		return this;
	}

	private void init() {
		if (P.nodeName != null) {
			nodeList = node.getAsList(P.nodeName);
		} else {
			nodeList.add(node);
		}

		// Arf!
		Map<String, String> m = nodeList.get(0).getAsMap();
		for (Entry<String, String> e : P.mapper.entrySet()) {
			m.put(e.getKey(), m.remove(e.getValue()));
		}
		columnName = m.keySet().toArray(new String[] {});

		if (P.withAutoId) {
			String[] tmp = new String[columnName.length + 1];
			System.arraycopy(columnName, 0, tmp, 0, columnName.length);
			tmp[tmp.length - 1] = "_id";
			columnName = tmp;
		}
	}

	@Override
	public SimpleXMLCursor getChild(Uri uri) {
		return children.get(0).get(0);
	}

	@Override
	public List<Uri> getChildUri() {
		return childrenUri.get(mPos);
	}

	@Override
	public SimpleXMLCursor getCursor() {
		return this;
	}

	@Override
	public void parse(HttpResponse response) throws ParseException {
	}

	@Override
	public Bundle getExtras() {
		Bundle bundle = new Bundle();
		bundle.putSerializable("response", root);
		return bundle;
	}
}
