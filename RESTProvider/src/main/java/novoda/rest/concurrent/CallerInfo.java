package novoda.rest.concurrent;

import novoda.rest.context.CallInfo;
import novoda.rest.context.CallResult;

public class CallerInfo extends QTask<CallResult> {

    public void addToQueue(CallInfo info) {
         compService.submit(info);
    }

}
