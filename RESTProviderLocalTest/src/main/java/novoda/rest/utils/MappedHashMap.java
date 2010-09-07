
package novoda.rest.utils;

import java.util.HashMap;
import java.util.Map;

public class MappedHashMap<K, V> extends HashMap<K, V> {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private Map<String, String> mapper = new HashMap<String, String>();

    @Override
    public V get(Object key) {
        return super.get(getMapper().containsKey(key) ? getMapper().get(key) : key);
    }

    public V getFromOriginalKey(Object key) {
        return super.get(key);
    }

    public void setMapper(Map<String, String> mapper) {
        this.mapper = mapper;
    }

    public Map<String, String> getMapper() {
        return mapper;
    }
}
