package novoda.rest.services;

import novoda.rest.context.QueryCallInfo;

interface IRemoteCallService {

	// should we return a reference?
	void query(in QueryCallInfo query, out CallResult);
	void delete();
	void update();
	void insert();
	void registerCallback();
	void unregisterCallback();
}