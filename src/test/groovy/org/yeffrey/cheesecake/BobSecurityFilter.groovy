package org.yeffrey.cheesecake

import io.micronaut.context.annotation.Requires
import io.micronaut.context.env.Environment
import io.micronaut.http.HttpResponse
import io.micronaut.http.MutableHttpRequest
import io.micronaut.http.annotation.Filter
import io.micronaut.http.filter.ClientFilterChain
import io.micronaut.http.filter.HttpClientFilter
import org.reactivestreams.Publisher

// https://www.youtube.com/watch?v=wKF_vq0KsY0&t=1068s

/**
 * Filter which sets bob as the current authenticated user !
 */
@Filter("/**")
@Requires(env = Environment.TEST)
@Requires(property = "security.user.bob")
class BobSecurityFilter implements HttpClientFilter {

    @Override
    Publisher<? extends HttpResponse<?>> doFilter(MutableHttpRequest<?> request, ClientFilterChain chain) {
        request.basicAuth("bob@bob.com", "secret77")
        return chain.proceed(request)
    }
}

