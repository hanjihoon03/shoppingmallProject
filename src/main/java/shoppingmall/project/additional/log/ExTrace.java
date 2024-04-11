package shoppingmall.project.additional.log;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import shoppingmall.project.additional.log.trace.TraceId;
import shoppingmall.project.additional.log.trace.TraceStatus;

/**
 * traceId : 내부에 트랜잭션ID와 level을 가지고 있다.
 * startTimeMs : 로그 시작시간이다. 로그 종료시 이 시작 시간을 기준으로 시작~종료까지 전체 수행 시간을 구할 수 있다.
 * message : 시작시 사용한 메시지이다. 이후 로그 종료시에도 이 메시지를 사용해서 출력한다.
 */
@Slf4j
@Component
public class ExTrace {
    private static final String START_PREFIX = "-->";
    private static final String COMPLETE_PREFIX = "<--";
    private static final String EX_PREFIX = "<X-";
    public TraceStatus begin(String message) {
        //로그 메시지를 파라미터로 받아 로그 출력
        TraceId traceId = new TraceId();
        Long startTimeMs = System.currentTimeMillis();
        log.info("[{}] {}{}", traceId.getId(), addSpace(START_PREFIX,
                traceId.getLevel()), message);
        return new TraceStatus(traceId, startTimeMs, message);
    }
    public void end(TraceStatus status) {
        //로그 정상 종료
        complete(status, null);
    }
    public void exception(TraceStatus status, Exception e) {
        //로그 예외 상황으로 종료 에외정보 포함해 반환
        complete(status, e);
    }
    private void complete(TraceStatus status, Exception e) {
        //실행시간 계산
        Long stopTimeMs = System.currentTimeMillis();
        long resultTimeMs = stopTimeMs - status.getStartTimeMs();
        TraceId traceId = status.getTraceId();
        if (e == null) {
            log.info("[{}] {}{} time={}ms", traceId.getId(),
                    addSpace(COMPLETE_PREFIX, traceId.getLevel()), status.getMessage(),
                    resultTimeMs);
        } else {
            log.info("[{}] {}{} time={}ms ex={}", traceId.getId(),
                    addSpace(EX_PREFIX, traceId.getLevel()), status.getMessage(), resultTimeMs,
                    e.toString());
        } }
    private static String addSpace(String prefix, int level) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < level; i++) {
            sb.append( (i == level - 1) ? "|" + prefix : "|   ");
        }
        return sb.toString();
    }

}
