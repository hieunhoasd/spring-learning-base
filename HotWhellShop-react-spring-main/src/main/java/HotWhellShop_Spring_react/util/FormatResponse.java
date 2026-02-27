package HotWhellShop_Spring_react.util;

import org.springframework.core.io.Resource;
import org.apache.catalina.connector.Response;
import org.jspecify.annotations.Nullable;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import HotWhellShop_Spring_react.domain.RestResponse;

@RestControllerAdvice
public class FormatResponse implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType,
            MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType,
            ServerHttpRequest request, ServerHttpResponse response) {

        if (body instanceof String || body instanceof Response) {
            return body;
        }
        if (response instanceof ServerHttpResponse) {
            int status = ((ServletServerHttpResponse) response).getServletResponse().getStatus();
            if (status >= 400) {
                return body;
            }
        }

        RestResponse<Object> res = RestResponse.builder().status(HttpStatus.OK.value())
                .message("success")
                .data(body)
                .build();

        return res;
    }

}
