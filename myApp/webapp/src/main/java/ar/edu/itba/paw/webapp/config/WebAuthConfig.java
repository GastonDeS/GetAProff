package ar.edu.itba.paw.webapp.config;

import ar.edu.itba.paw.webapp.auth.PawUserDetailsService;
import ar.edu.itba.paw.webapp.config.filter.JwtAuthorizationFilter;
import ar.edu.itba.paw.webapp.auth.AuthEntryPointJwt;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

@EnableWebSecurity
@ComponentScan({"ar.edu.itba.paw.webapp.auth", "ar.edu.itba.paw.webapp.util"})
@Configuration
public class WebAuthConfig extends WebSecurityConfigurerAdapter {

    @Value("classpath:key.txt")
    private Resource resource;

    @Autowired
    private PawUserDetailsService userDetailsService;

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Bean
    public JwtAuthorizationFilter authenticationJwtTokenFilter() {
        return new JwtAuthorizationFilter();
    }

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .invalidSessionUrl("/")
                .and().authorizeRequests()
//                    .antMatchers(HttpMethod.POST, "/api/auth/login").permitAll()
//                    .antMatchers(HttpMethod.GET, "/api/images/*").permitAll()
//                    .antMatchers(HttpMethod.GET, "/api/subject-files/*").permitAll()
//                    .antMatchers(HttpMethod.GET, "/api/teachers/*").permitAll()
//                    .antMatchers("/profile/**/**", "/", "/tutors/**", "/image/*").permitAll()
//                    .antMatchers("/editSubjects/*", "/newSubjectForm", "/newSubjectFormSent").hasAuthority("USER_TEACHER")
//                    .antMatchers("/editProfile/startTeaching").hasAnyAuthority("USER_STUDENT")
//                    .antMatchers("/myClasses", "/editCertifications", "/favourites", "/classroom/*", "/contact/*").hasAnyAuthority("USER_TEACHER", "USER_STUDENT")
//                    .antMatchers("/login", "/register").anonymous()
                    .antMatchers("/**").permitAll()
//                .and().formLogin()
//                    .usernameParameter("j_email")
//                    .passwordParameter("j_password")
//                    .defaultSuccessUrl("/default", false)
//                    .loginPage("/login")
//                    .failureUrl("/login?error=true")
                .and().rememberMe()
                    .rememberMeParameter("j_rememberme")
                    .userDetailsService(userDetailsService)
                    .key(FileUtils.readFileToString(resource.getFile(), String.valueOf(StandardCharsets.UTF_8)))
                    .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(30))
                .and().logout()
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/login")
                .and().exceptionHandling()
                    .authenticationEntryPoint(unauthorizedHandler)
                .and().csrf().disable();

        http.addFilterAfter(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public void configure(final WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/styles/**", "/resources/js/**", "/resources/images/**", "/resources/favicon.ico", "/403");
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Collections.singletonList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token"));
        configuration.setExposedHeaders(Arrays.asList("x-auth-token", "authorization", "X-Total-Pages", "Content-Disposition"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
