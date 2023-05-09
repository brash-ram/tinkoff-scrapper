package ru.tinkoff.edu.bot.tg;

import com.pengrad.telegrambot.Callback;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.response.BaseResponse;
import java.io.IOException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Getter
public class Bot {

    private final TelegramBot telegramBot;

    public <T extends BaseRequest<T, R>, R extends BaseResponse> void send(T request) {
        telegramBot.execute(request, new Callback<T, R>() {
            @Override
            public void onResponse(T request, R response) {
                System.out.println("onResponse");
            }

            @Override
            public void onFailure(T request, IOException e) {
                System.out.println("onFailure");
                e.printStackTrace();
            }
        });
    }
}
