//package FitMate.FitMateBackend.chanhaleWorking.config.interceptor;
//
//import FitMate.FitMateBackend.consts.SessionConst;
//import FitMate.FitMateBackend.domain.User;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.web.servlet.HandlerInterceptor;
//
//@Slf4j
//public class LoginUserCheckInterceptor implements HandlerInterceptor {
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//
//        String requestURI = request.getRequestURI();
//
//        log.info("사용자 인증 체크 인터셉터 실행 {}", requestURI);
//
//        HttpSession session = request.getSession();
//
//        if (session == null || session.getAttribute(SessionConst.LOGIN_USER) == null) {
//            log.info("미인증 사용자 요청!\n/login 에 Login 요청을 보내서 loginSession 을 만들고 오세요");
//            // 로그인으로 redirect
//            response.sendError(401,"미인증 사용자 요청!\n/login 에 Login 요청을 보내서 loginSession 을 만들고 오세요");
//
//            // 화이트 리스트 등록을 인터셉터 등록시에 할 수 있어서 여기서 구현하지 않아도 된다.
//            return false;
//        }
//        log.info("사용자 {}님 로그인 session 확인", ((User)session.getAttribute(SessionConst.LOGIN_USER)).getUserName());
//
//        return true;
//    }
//}
