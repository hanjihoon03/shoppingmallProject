package shoppingmall.project.additional.test;

import org.springframework.stereotype.Component;
import shoppingmall.project.additional.annotation.ClassAop;
import shoppingmall.project.additional.annotation.MethodAop;

@ClassAop
@Component
public class MemberServiceImpl implements MemberService{
    @Override
    @MethodAop("test value")
    public String hello(String param) {
        return "ok";
    }

    public String internal(String param) {
        return "ok";
    }
}
