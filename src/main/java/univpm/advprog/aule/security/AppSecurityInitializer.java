package univpm.advprog.aule.security;

import org.apache.catalina.security.SecurityConfig;
import org.springframework.security.web.context.*;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

public class AppSecurityInitializer extends AbstractSecurityWebApplicationInitializer {

    public AppSecurityInitializer() {
        super(SecurityConfig.class);
    }
}