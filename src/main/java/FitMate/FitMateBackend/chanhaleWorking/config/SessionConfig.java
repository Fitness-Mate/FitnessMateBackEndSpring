package FitMate.FitMateBackend.chanhaleWorking.config;

import FitMate.FitMateBackend.chanhaleWorking.config.argumentresolver.LoginUserArgumentResolver;
import FitMate.FitMateBackend.chanhaleWorking.config.interceptor.LoginAdminCheckInterceptor;
import FitMate.FitMateBackend.chanhaleWorking.config.interceptor.LoginUserCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

// 개발단계에선 인터셉터를 꺼놓을 것임.
@Configuration
public class SessionConfig implements WebMvcConfigurer {
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new LoginUserArgumentResolver());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(new LoginUserCheckInterceptor())
                .order(1)
                .addPathPatterns("/bodyData/**");
        // 사용자 로그인 인터셉터
        registry.addInterceptor(new LoginUserCheckInterceptor())
                .order(3)
                .addPathPatterns("/**")
                .excludePathPatterns("/admin/**", "/", "/login", "/css/**", "/*.ico", "/error", "/supplements/**", "/workouts/**", "/bodyParts/all", "/machines/list", "/recommendation/supplement/purposes");
        // 관리자 로그인 인터셉터
        registry.addInterceptor(new LoginAdminCheckInterceptor())
                .order(2)
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/admin/login", "/admin/logout", "/css/**", "/*.ico", "/error");
    }
}
