package no.kristiania.exam.security

import no.kristiania.exam.security.filter.AuthenticationFilter
import no.kristiania.exam.security.filter.AuthorizationFilter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@EnableWebSecurity
@Configuration
class SecurityConfig(
    @Autowired private val userDetailService: UserDetailsService,
    @Autowired private val passwordEncoder: BCryptPasswordEncoder
) : WebSecurityConfigurerAdapter() {

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userDetailService).passwordEncoder(passwordEncoder)
    }

    override fun configure(http: HttpSecurity) {
        val authFilter = AuthenticationFilter(authenticationManagerBean())
        authFilter.setFilterProcessesUrl("/api/login")
        http.csrf().disable()
        http.sessionManagement().disable()
        http.authorizeRequests()
            .antMatchers("/api/login").permitAll()
            .antMatchers("/api/register").permitAll()
            .antMatchers("/api/user/**").hasAnyAuthority("USER", "ADMIN")
            .antMatchers("/api/shelter/**").hasAnyAuthority("USER", "ADMIN")
            .antMatchers("/api/authority/**").hasAuthority("ADMIN")
            .and().formLogin()
        http.authorizeRequests().anyRequest().authenticated()
        http.addFilter(authFilter)
        http.addFilterBefore(AuthorizationFilter(), UsernamePasswordAuthenticationFilter::class.java)
    }

    @Bean
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }
}