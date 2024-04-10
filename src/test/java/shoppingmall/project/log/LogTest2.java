package shoppingmall.project.log;

import org.junit.jupiter.api.Test;
import shoppingmall.project.additional.log.ExTrace;
import shoppingmall.project.additional.log.Trace2;
import shoppingmall.project.additional.log.trace.TraceStatus;

public class LogTest2 {

    @Test
    void begin_end() {
        Trace2 trace2 = new Trace2();
        TraceStatus status1 = trace2.begin("hello1");
        TraceStatus status2 = trace2.beginSync(status1.getTraceId(),"hello2");
        trace2.end(status2);
        trace2.end(status1);
    }
    @Test
    void begin_exception() {
        Trace2 trace2 = new Trace2();
        TraceStatus status1 = trace2.begin("hello1");
        TraceStatus status2 = trace2.beginSync(status1.getTraceId(),"hello2");
        trace2.exception(status2, new IllegalStateException());
        trace2.exception(status1, new IllegalStateException());
    }

}
