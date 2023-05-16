package ru.tinkoff.edu.bot.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.edu.bot.dto.LinkUpdate;
import ru.tinkoff.edu.bot.tg.BotMessageSender;

@RestController
@RequiredArgsConstructor
public class ApiControllerImpl implements ApiController {

    private final BotMessageSender botMessageSender;

    @Override
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/updates",
            produces = { "application/json" },
            consumes = { "application/json" }
    )
    public ResponseEntity<Void> updatesPost(@RequestBody LinkUpdate linkUpdate) {
        botMessageSender.sendMessage(linkUpdate);
        return ResponseEntity.ok().build();
    }
}
