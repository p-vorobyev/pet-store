package voroby.petstore.config

import io.netty.channel.nio.NioEventLoopGroup
import org.springframework.boot.web.embedded.netty.NettyReactiveWebServerFactory
import org.springframework.boot.web.embedded.netty.NettyServerCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import reactor.netty.http.server.HttpServer

@Configuration
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

}
