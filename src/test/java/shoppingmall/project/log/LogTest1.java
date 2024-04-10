package shoppingmall.project.log;

import org.junit.jupiter.api.Test;
import shoppingmall.project.additional.log.ExTrace;
import shoppingmall.project.additional.log.trace.TraceStatus;

public class LogTest1 {

    @Test
    void begin_end() {
        ExTrace exTrace = new ExTrace();
        TraceStatus status = exTrace.begin("hello");
        exTrace.end(status);
    }
    @Test
    void begin_exception() {
        ExTrace exTrace = new ExTrace();
        TraceStatus status = exTrace.begin("hello");
        exTrace.exception(status, new IllegalStateException());
    }

}
