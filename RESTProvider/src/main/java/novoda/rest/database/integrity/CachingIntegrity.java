
package novoda.rest.database.integrity;

import novoda.rest.database.CachingStrategy;
import novoda.rest.parsers.Node;

import android.net.Uri;

public interface CachingIntegrity {

    public String getWhereClause(final Uri uri, final Node<?> root);

    public String[] getWhereClauseArgv(final Uri uri, final Node<?> root);
    
    public CachingStrategy getStrategy(final Uri uri);

}
