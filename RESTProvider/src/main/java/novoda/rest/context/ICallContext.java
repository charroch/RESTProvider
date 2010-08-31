package novoda.rest.context;

import java.util.concurrent.Callable;

import novoda.rest.clag.Parser;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpUriRequest;

import android.database.sqlite.SQLiteOpenHelper;

public interface ICallContext<T> extends Callable<T>, Parser<T>,
		ResponseHandler<T> {
	
	public void handle(CallInfo info, T data, SQLiteOpenHelper db);

	public HttpUriRequest getRequest(CallInfo info);
}