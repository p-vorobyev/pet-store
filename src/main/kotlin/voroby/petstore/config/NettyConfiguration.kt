package voroby.petstore.config

import io.netty.channel.nio.NioEventLoopGroup
import org.springframework.boot.web.embedded.netty.NettyReactiveWebServerFactory
import org.springframework.boot.web.embedded.netty.NettyServerCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.SecurityWebFiltersOrder
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.authentication.AuthenticationWebFilter
import reactor.netty.http.server.HttpServer

@Configuration
@EnableWebFluxSecurity
class NettyConfiguration {

    @Bean
    fun nioEventLoopGroup(): NioEventLoopGroup {
        val processors = Runtime.getRuntime().availableProcessors()
        return NioEventLoopGroup((processors * 1.5).toInt())
    }

    @Bean
    fun webServerFactory(eventLoopGroup: NioEventLoopGroup): NettyReactiveWebServerFactory {
        val factory = NettyReactiveWebServerFactory()
        factory.serverCustomizers.add(NettyServerCustomizer {
                httpServer: HttpServer? -> httpServer?.runOn(eventLoopGroup)
        })
        return factory
    }

    @Bean
    fun securityWebFilterChain(http: ServerHttpSecurity,
                               authenticationManager: AuthenticationManager): SecurityWebFilterChain {
        val authenticationWebFilter = AuthenticationWebFilter(authenticationManager)
        //TODO
        //authenticationWebFilter.setServerAuthenticationConverter {  }
        return http.authorizeExchange {
            it.pathMatchers("/api/login").permitAll()
            it.pathMatchers("/orders").hasRole("ROLE_USER")
            it.pathMatchers("/products").hasRole("ROLE_USER")
            it.anyExchange().authenticated()
        }
            .addFilterAt(authenticationWebFilter, SecurityWebFiltersOrder.AUTHENTICATION)
            .httpBasic().disable()
            .csrf().disable()
            .formLogin().disable()
            .logout().disable()
            .build()
    }

}
