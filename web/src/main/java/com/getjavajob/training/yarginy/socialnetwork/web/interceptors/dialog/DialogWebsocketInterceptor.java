package com.getjavajob.training.yarginy.socialnetwork.web.interceptors.dialog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

public class DialogWebsocketInterceptor implements HandshakeInterceptor {
    private final Logger logger = LoggerFactory.getLogger(DialogWebsocketInterceptor.class);

    @Override
    public boolean beforeHandshake(ServerHttpRequest req, ServerHttpResponse resp, WebSocketHandler wsHandler,
                                   Map<String, Object> attributes) {
        logger.info("in websocket handshake interceptor");
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
    }
}
