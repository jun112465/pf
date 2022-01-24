package jun.studyHelper.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
//@RequiredArgsConstructor
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final ChatHandler chatHandler;

    WebSocketConfig(ChatHandler chatHandler){
        this.chatHandler = chatHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {

        //path : 웹소켓이 연결될 서버의 주소를 적는 공간 ( 웹페이지와 동일한 주소를 이용할 시 오류 발생 )
        registry.addHandler(chatHandler, "ws/chat").setAllowedOrigins("*");
    }
}

