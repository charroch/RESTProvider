
package novoda.rest.clag;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import novoda.rest.database.SQLiteType;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

public class SchemaParserTest {

    private static ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testSimpleCreate() throws JsonProcessingException, IOException {
        String json = "{\"name\":\"Example\",\"columns\":"
                + "[{\"name\":\"title\",\"type\":\"text\"},"
                + "{\"name\":\"description\",\"type\":\"text\"},"
                + "{\"name\":\"cost\",\"type\":\"integer\"},"
                + "{\"name\":\"id\",\"type\":\"integer\", \"key\": \"true\"}]}";
        JsonNode node = mapper.readTree(json);
        SchemaParser parser = SchemaParser.from(node);

        assertArraysEqualUnordered(parser.getTableFields(), new String[] {
                "title", "description", "cost", "id"
        });

        assertEquals(parser.getType("title"), SQLiteType.TEXT);
        assertEquals(parser.getType("cost"), SQLiteType.INTEGER);
        assertEquals(parser.getType("id"), SQLiteType.INTEGER);
        assertTrue(parser.isUnique("id"));
        assertTrue(!parser.isNullAllowed("id"));
    }

    private void assertArraysEqualUnordered(String[] a, String[] b) {
        assertTrue(a.length == b.length);
        boolean good;
        for (int i = 0; i < a.length; i++) {
            good = false;
            for (int j = 0; j < b.length; j++) {
                if (a[i].equals(b[j]))
                    good = true;
            }
            assertTrue(
                    "arrays does not match: " + Arrays.toString(a) + " vs " + Arrays.toString(b),
                    good);
        }
    }
}
