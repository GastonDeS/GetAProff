package ar.edu.itba.paw.webapp.security.config;

import ar.edu.itba.paw.webapp.security.api.AuthenticationEntryPoint;
import ar.edu.itba.paw.webapp.security.api.BridgeAuthenticationFilter;
import ar.edu.itba.paw.webapp.security.api.basic.BasicAuthenticationProvider;
import ar.edu.itba.paw.webapp.security.api.handlers.AuthenticationFailureHandler;
import ar.edu.itba.paw.webapp.security.api.handlers.AuthenticationSuccessHandler;
import ar.edu.itba.paw.webapp.security.api.handlers.CustomAccessDeniedHandler;
import ar.edu.itba.paw.webapp.security.api.jwt.JwtAuthenticationProvider;
import ar.edu.itba.paw.webapp.security.services.implementation.PawUserDetailsService;
import ar.edu.itba.paw.webapp.security.voters.AntMatcherVoter;
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


    @Bean
    public AntMatcherVoter antMatcherVoter() { return new AntMatcherVoter();}

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
                .cors()
                    .and()
                .csrf()
                    .disable()
                .exceptionHandling()
                    .authenticationEntryPoint(authenticationEntryPoint)
                    .accessDeniedHandler(accessDeniedHandler())
                .and()
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .antMatcher(API_PREFIX+"/**").authorizeRequests()
                        .antMatchers(HttpMethod.GET,API_PREFIX+"/user").hasAuthority("USER_STUDENT")
                        .antMatchers(HttpMethod.GET,API_PREFIX+"/classes").hasAuthority("USER_STUDENT")
                        .antMatchers(HttpMethod.POST,API_PREFIX+"/classes").hasAuthority("USER_STUDENT")
                        .antMatchers(HttpMethod.GET, API_PREFIX+"/classroom/{classId}").access("@antMatcherVoter.canAccessClassroom(authentication, #classId)")
                        .antMatchers(HttpMethod.GET, API_PREFIX+"/classroom/{classId}/posts").access("@antMatcherVoter.canAccessClassroom(authentication, #classId)")
                        .antMatchers(HttpMethod.GET, API_PREFIX+"/classroom/{classId}/files").access("@antMatcherVoter.canAccessClassroom(authentication, #classId)")
                        .antMatchers(HttpMethod.POST, API_PREFIX+"/classroom/{classId}/files").access("@antMatcherVoter.canAccessClassroomAsTeacher(authentication, #classId)")
                        .antMatchers(HttpMethod.POST, API_PREFIX+"/classroom/{classId}/posts").access("@antMatcherVoter.canAccessClassroom(authentication, #classId)")
                        .antMatchers(HttpMethod.POST, API_PREFIX+"/classroom/{classId}/status").access("@antMatcherVoter.canAccessClassroom(authentication, #classId)") // Only de student can rate, and idk if teacher can only one thing
                        .antMatchers(HttpMethod.GET,API_PREFIX+"/post/{postId}/file").access("@antMatcherVoter.canAccessPostFile(authentication, #postId)")
                        .antMatchers(HttpMethod.GET, API_PREFIX+"/subject-files/{uid}").access("@antMatcherVoter.canAccessWithSameId(authentication, #uid)")
                        .antMatchers(HttpMethod.DELETE, API_PREFIX+"/subject-files/{fileId}").access("@antMatcherVoter.canAccessDeleteSubjectFile(authentication, #fileId)")
                        .antMatchers(HttpMethod.POST, API_PREFIX+"/subject-files/{uid}/{subject}/{level}").access("@antMatcherVoter.canAccessWithSameId(authentication, #uid)")
                        .antMatchers(HttpMethod.DELETE,API_PREFIX+"/user-files/{fileId}").access("@antMatcherVoter.canAccessDeleteCertification(authentication, #fileId)")
                        .antMatchers(HttpMethod.POST, API_PREFIX+"/user-files/{uid}").access("@antMatcherVoter.canAccessWithSameId(authentication, #uid)")
                        .antMatchers(HttpMethod.POST,API_PREFIX+"/users/{uid}/image").access("@antMatcherVoter.canAccessWithSameId(authentication, #uid)")
                        .antMatchers(HttpMethod.DELETE,API_PREFIX+"/users/{userId}/{subjectId}/{level}").access("@antMatcherVoter.canAccessWithSameId(authentication, #userId)")
                        .antMatchers(HttpMethod.POST,API_PREFIX+"/users/{uid}/teacher").access("@antMatcherVoter.canAccessWithSameId(authentication, #uid)")
                        .antMatchers(HttpMethod.POST,API_PREFIX+"/users/{uid}/student").access("@antMatcherVoter.canAccessWithSameId(authentication, #uid)")
                        .antMatchers(HttpMethod.POST,API_PREFIX+"/users/{uid}").access("@antMatcherVoter.canAccessWithSameId(authentication, #uid)")
                        .antMatchers(HttpMethod.POST,API_PREFIX+"/users/{uid}/subjects").access("@antMatcherVoter.canAccessWithSameId(authentication, #uid)")
                        .antMatchers(HttpMethod.GET,API_PREFIX+"/users/available-subjects/{id}").access("@antMatcherVoter.canAccessWithSameId(authentication, #id)")
                        .antMatchers(HttpMethod.GET, API_PREFIX+"/favourites").hasAuthority("USER_STUDENT")
                        .antMatchers(HttpMethod.GET,API_PREFIX+"/favourites/{teacherId}").hasAuthority("USER_STUDENT")
                        .antMatchers(HttpMethod.POST,API_PREFIX+"/favourites/{teacherId}").hasAuthority("USER_STUDENT")
                        .antMatchers(HttpMethod.DELETE,API_PREFIX+"/favourites/{teacherId}").hasAuthority("USER_STUDENT")// TODO check if this works if not separate into the 4 endpoints all with USER_STUDENT
                        .antMatchers(HttpMethod.POST,API_PREFIX+"/ratings/{teacherId}").access("@antMatcherVoter.canRate(authentication, #teacherId)")
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
                .antMatchers(HttpMethod.GET,API_PREFIX+"/user-files/{id}")
                .antMatchers(HttpMethod.GET,API_PREFIX+"/ratings/{id}")
                .antMatchers(HttpMethod.GET,API_PREFIX+"/subjects/**" /*all public*/)
                .antMatchers(HttpMethod.GET,API_PREFIX+"/users")
                .antMatchers(HttpMethod.GET,API_PREFIX+"/users/{id}")
                .antMatchers(HttpMethod.GET,API_PREFIX+"/users/most-requested")
                .antMatchers(HttpMethod.GET,API_PREFIX+"/users/{id}/subjects")
                .antMatchers(HttpMethod.GET,API_PREFIX+"/users/subjects/levels/{id}")
                .antMatchers(HttpMethod.GET,API_PREFIX+"/users/{uid}/image")
                .antMatchers(HttpMethod.GET,API_PREFIX+"/users/top-rated")
                .antMatchers(HttpMethod.POST, API_PREFIX+"/users/teacher")
                .antMatchers(HttpMethod.POST, API_PREFIX+"/users/student")
                .antMatchers(HttpMethod.GET,API_PREFIX+"/files/user/{id}")
                .antMatchers("/")
                .antMatchers("/*.js")
                .antMatchers("/*.css")
                .antMatchers("/favicon.ico")
                .antMatchers("/manifest.json");
    }
}