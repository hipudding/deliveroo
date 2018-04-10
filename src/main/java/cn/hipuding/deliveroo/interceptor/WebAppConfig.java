package cn.hipuding.deliveroo.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebAppConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 多个拦截器组成一个拦截器链
        // addPathPatterns 用于添加拦截规则
        // excludePathPatterns 用户排除拦截
        registry.addInterceptor(new UserInterceptor()).addPathPatterns("/user/**").excludePathPatterns("/user/login").excludePathPatterns("/user/dologin");;
        registry.addInterceptor(new SellerInterceptor()).addPathPatterns("/seller/**").excludePathPatterns("/seller/login").excludePathPatterns("/seller/dologin");

        super.addInterceptors(registry);
    }
}