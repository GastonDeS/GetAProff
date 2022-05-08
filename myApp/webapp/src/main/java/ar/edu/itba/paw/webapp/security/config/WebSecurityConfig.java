package ar.edu.itba.paw.webapp.security.config;

import ar.edu.itba.paw.webapp.security.api.AuthenticationEntryPoint;
import ar.edu.itba.paw.webapp.security.api.BridgeAuthenticationFilter;
import ar.edu.itba.paw.webapp.security.api.basic.BasicAuthenticationProvider;
import ar.edu.itba.paw.webapp.security.api.handlers.AuthenticationFailureHandler;
import ar.edu.itba.paw.webapp.security.api.handlers.AuthenticationSuccessHandler;
import ar.edu.itba.paw.webapp.security.api.handlers.CustomAccessDeniedHandler;
import ar.edu.itba.paw.webapp.security.api.jwt.JwtAuthenticationProvider;
import ar.edu.itba.paw.webapp.security.services.implementation.PawUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
@ComponentScan({"ar.edu.itba.paw.webapp.security"})
@PropertySource(value= {"classpath:application.properties"})
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PawUserDetailsService userDetailsService;

    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private BasicAuthenticationProvider basicAuthenticationProvider;

    @Autowired
    private JwtAuthenticationProvider jwtAuthenticationProvider;

    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    public static final String API_PREFIX = "/api";

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
        auth.authenticationProvider(basicAuthenticationProvider).authenticationProvider(jwtAuthenticationProvider);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public BridgeAuthenticationFilter bridgeAuthenticationFilter() throws Exception {
        BridgeAuthenticationFilter bridgeAuthenticationFilter = new BridgeAuthenticationFilter();
        bridgeAuthenticationFilter.setAuthenticationManager(authenticationManagerBean());
        bridgeAuthenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
        bridgeAuthenticationFilter.setAuthenticationFailureHandler(authenticationFailureHandler);
        return bridgeAuthenticationFilter;
    }


//    @Bean
//    public AntMatcherVoter antMatcherVoter() { return new AntMatcherVoter();}

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        http
                .csrf()
                    .disable()
                .exceptionHandling()
                    .authenticationEntryPoint(authenticationEntryPoint)
                    .accessDeniedHandler(accessDeniedHandler())
                .and()
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .authorizeRequests()//TODO ADD GRANULARITY
//                        .antMatchers("/users/login").authenticated()

                .antMatchers(HttpMethod.GET,"/auth/login").hasAuthority("USER_STUDENT")
                        .antMatchers(HttpMethod.GET,"/classes").hasAuthority("USER_STUDENT") // TODO ADD ACCESS
                        .antMatchers(HttpMethod.GET, "/classroom").hasAuthority("USER_STUDENT") // TODO ADD ACCESS
                        .antMatchers(HttpMethod.GET, "/classroom/{classId}").hasAuthority("USER_STUDENT")
                        .antMatchers(HttpMethod.GET, "/classroom/{classId}/posts").hasAuthority("USER_STUDENT")
                        .antMatchers(HttpMethod.GET, "/classroom/{classId}/files").hasAuthority("USER_STUDENT")
                        .antMatchers(HttpMethod.POST, "/classroom/{classId}/files").hasAuthority("USER_STUDENT") // TODO ADD ACCESS
                        .antMatchers(HttpMethod.POST, "/classroom/{classId}/posts").hasAuthority("USER_STUDENT") // TODO ADD ACCESS
                        .antMatchers(HttpMethod.POST, "/classroom/{classId}/status").hasAuthority("USER_STUDENT") // TODO ADD ACCESS
                        .antMatchers(HttpMethod.GET,"/post/{postId}/file").hasAuthority("USER_STUDENT")
                        .antMatchers(HttpMethod.GET,"/files/user/{id}").hasAuthority("USER_STUDENT") //TODO VERIFY THIS AUTH
                        .antMatchers(HttpMethod.POST,"/ratings/new-rating").hasAuthority("USER_STUDENNT") //TODO ONLY IF HAS AN OPEN CLASS
                        .antMatchers(HttpMethod.GET, "/subject-files/{id}").hasAuthority("USER_STUDENT") //TODO RESTRICTED TO OWNER AND IF SHARED
                        .antMatchers(HttpMethod.DELETE, "/subject-files/{file}").hasAuthority("USER_STUDENT") //TODO RESTRICTED TO OWNER
                        .antMatchers(HttpMethod.POST, "/subject-files/{id}/{subject}/{level}").hasAuthority("USER_STUDENT") //TODO RESTRICTED TO OWNER
                        .antMatchers(HttpMethod.DELETE,"/user-files/{file}").hasAuthority("USER_STUDENT") // TODO OWNER
                        .antMatchers(HttpMethod.POST, "/user-files/{id}").hasAuthority("USER_STUDENT")//TODO OWNER
                        .antMatchers(HttpMethod.POST,"/users/").hasAuthority("USER_STUDENT")
                        .antMatchers(HttpMethod.POST,"/users/{uid}/image").hasAuthority("USER_STUDENT")
                        .antMatchers(HttpMethod.DELETE,"/users/{userId}/{subjectId}/{level}").hasAuthority("USER_STUDENT")
                        .antMatchers(HttpMethod.POST,"/users/{id}").hasAuthority("USER_STUDENT")
                        .antMatchers(HttpMethod.POST,"/users/{userId}").hasAuthority("USER_STUDENT")
                        .antMatchers(HttpMethod.GET,"/users/{uid}/favorites").hasAuthority("USER_STUDENT")
                        .antMatchers(HttpMethod.POST,"/users/{uid}/favorites").hasAuthority("USER_STUDENT")
                        .antMatchers(HttpMethod.DELETE,"/users/{uid}/favorites/{favTeacherId}").hasAuthority("USER_STUDENT")
                        .antMatchers(HttpMethod.POST,"/users/{uid}/classes").hasAuthority("USER_STUDENT")
                        .antMatchers("/**").permitAll()
                .and()
                    .addFilterBefore(bridgeAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Collections.singletonList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token"));
        configuration.setExposedHeaders(Arrays.asList("x-auth-token", "authorization", "X-Total-Pages", "Content-Disposition"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Override
    public void configure(final WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers(HttpMethod.GET,"/user-files/{id}")
                .antMatchers(HttpMethod.GET,"/ratings/{id}")
                .antMatchers(HttpMethod.GET,"/subjects/**" /*all public*/)
                .antMatchers(HttpMethod.GET,"/users")
                .antMatchers(HttpMethod.GET,"/users/{id}") // TODO CHANGE this endpoinnt so it doesnt crash
                .antMatchers(HttpMethod.GET,"/users/most-requested")
                .antMatchers(HttpMethod.GET,"/users/{id}/subjects")
                .antMatchers(HttpMethod.GET,"/users/subjects/levels/{id}")
                .antMatchers(HttpMethod.GET,"/users/available-subjects/{id}")
                .antMatchers(HttpMethod.GET,"/users/{uid}/image")
                .antMatchers(HttpMethod.GET,"/users/top-rated")
                .antMatchers("/")
                .antMatchers("/*.js")
                .antMatchers("/*.css")
                .antMatchers("/favicon.ico")
                .antMatchers("/manifest.json");
    }
}