
package novoda.rest.mock;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.content.res.XmlResourceParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

public class StringResourceParser implements XmlResourceParser {

    private XmlPullParser xpp;

    public StringResourceParser(XmlPullParser xpp) {
        this.xpp = xpp;
    }

    @Override
    public void close() {
    }

    @Override
    public void defineEntityReplacementText(String entityName, String replacementText)
            throws XmlPullParserException {
        xpp.defineEntityReplacementText(entityName, replacementText);
    }

    @Override
    public int getAttributeCount() {
        return xpp.getAttributeCount();
    }

    @Override
    public String getAttributeName(int index) {
        return xpp.getAttributeName(index);
    }

    @Override
    public String getAttributeNamespace(int index) {
        return xpp.getAttributeNamespace(index);
    }

    @Override
    public String getAttributePrefix(int index) {
        return xpp.getAttributePrefix(index);
    }

    @Override
    public String getAttributeType(int index) {
        return xpp.getAttributeType(index);
    }

    @Override
    public String getAttributeValue(int index) {
        return xpp.getAttributeValue(index);
    }

    @Override
    public String getAttributeValue(String namespace, String name) {
        return xpp.getAttributeValue(namespace, name);
    }

    @Override
    public int getColumnNumber() {
        return xpp.getColumnNumber();
    }

    @Override
    public int getDepth() {
        return xpp.getDepth();
    }

    @Override
    public int getEventType() throws XmlPullParserException {
        return xpp.getEventType();
    }

    @Override
    public boolean getFeature(String name) {
        return xpp.getFeature(name);
    }

    @Override
    public String getInputEncoding() {
        return xpp.getInputEncoding();
    }

    @Override
    public int getLineNumber() {
        return xpp.getLineNumber();
    }

    @Override
    public String getName() {
        return xpp.getName();
    }

    @Override
    public String getNamespace() {
        return xpp.getNamespace();
    }

    @Override
    public String getNamespace(String prefix) {
        return xpp.getNamespace(prefix);
    }

    @Override
    public int getNamespaceCount(int depth) throws XmlPullParserException {
        return xpp.getNamespaceCount(depth);
    }

    @Override
    public String getNamespacePrefix(int pos) throws XmlPullParserException {
        return xpp.getNamespacePrefix(pos);
    }

    @Override
    public String getNamespaceUri(int pos) throws XmlPullParserException {
        return xpp.getNamespaceUri(pos);
    }

    @Override
    public String getPositionDescription() {
        return xpp.getPositionDescription();
    }

    @Override
    public String getPrefix() {
        return xpp.getPrefix();
    }

    @Override
    public Object getProperty(String name) {
        return xpp.getProperty(name);
    }

    @Override
    public String getText() {
        return xpp.getText();
    }

    @Override
    public char[] getTextCharacters(int[] holderForStartAndLength) {
        return xpp.getTextCharacters(holderForStartAndLength);
    }

    @Override
    public boolean isAttributeDefault(int index) {
        return xpp.isAttributeDefault(index);
    }

    @Override
    public boolean isEmptyElementTag() throws XmlPullParserException {
        return xpp.isEmptyElementTag();
    }

    @Override
    public boolean isWhitespace() throws XmlPullParserException {
        return xpp.isWhitespace();
    }

    @Override
    public int next() throws XmlPullParserException, IOException {
        return xpp.next();
    }

    @Override
    public int nextTag() throws XmlPullParserException, IOException {
        return xpp.nextTag();
    }

    @Override
    public String nextText() throws XmlPullParserException, IOException {
        return xpp.nextText();
    }

    @Override
    public int nextToken() throws XmlPullParserException, IOException {
        return xpp.nextToken();
    }

    @Override
    public void require(int type, String namespace, String name) throws XmlPullParserException,
            IOException {
        xpp.require(type, namespace, name);
    }

    @Override
    public void setFeature(String name, boolean state) throws XmlPullParserException {
        xpp.setFeature(name, state);
    }

    @Override
    public void setInput(Reader in) throws XmlPullParserException {
        xpp.setInput(in);
    }

    @Override
    public void setInput(InputStream inputStream, String inputEncoding)
            throws XmlPullParserException {
        xpp.setInput(inputStream, inputEncoding);
    }

    @Override
    public void setProperty(String name, Object value) throws XmlPullParserException {
        xpp.setProperty(name, value);
    }

    @Override
    public boolean getAttributeBooleanValue(int index, boolean defaultValue) {
        return false;
    }

    @Override
    public boolean getAttributeBooleanValue(String namespace, String attribute, boolean defaultValue) {
        return false;
    }

    @Override
    public float getAttributeFloatValue(int index, float defaultValue) {
        return 0;
    }

    @Override
    public float getAttributeFloatValue(String namespace, String attribute, float defaultValue) {
        return 0;
    }

    @Override
    public int getAttributeIntValue(int index, int defaultValue) {
        return 0;
    }

    @Override
    public int getAttributeIntValue(String namespace, String attribute, int defaultValue) {
        return 0;
    }

    @Override
    public int getAttributeListValue(int index, String[] options, int defaultValue) {
        return 0;
    }

    @Override
    public int getAttributeListValue(String namespace, String attribute, String[] options,
            int defaultValue) {
        return 0;
    }

    @Override
    public int getAttributeNameResource(int index) {
        return 0;
    }

    @Override
    public int getAttributeResourceValue(int index, int defaultValue) {
        return 0;
    }

    @Override
    public int getAttributeResourceValue(String namespace, String attribute, int defaultValue) {
        return 0;
    }

    @Override
    public int getAttributeUnsignedIntValue(int index, int defaultValue) {
        return 0;
    }

    @Override
    public int getAttributeUnsignedIntValue(String namespace, String attribute, int defaultValue) {
        return 0;
    }

    @Override
    public String getClassAttribute() {
        return null;
    }

    @Override
    public String getIdAttribute() {
        return null;
    }

    @Override
    public int getIdAttributeResourceValue(int defaultValue) {
        return 0;
    }

    @Override
    public int getStyleAttribute() {
        return 0;
    }

}
