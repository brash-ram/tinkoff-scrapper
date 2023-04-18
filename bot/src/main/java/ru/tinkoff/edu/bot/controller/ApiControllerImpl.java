package ru.tinkoff.edu.bot.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.edu.bot.dto.LinkUpdate;
import ru.tinkoff.edu.bot.tg.Bot;
import ru.tinkoff.edu.bot.tg.SendMessageAdapter;

@RestController
@RequiredArgsConstructor
public class ApiControllerImpl implements ApiController {

    private final Bot bot;

    @Override
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/updates",
            produces = { "application/json" },
            consumes = { "application/json" }
    )
    public ResponseEntity<Void> updatesPost(@RequestBody LinkUpdate linkUpdate) {
        bot.send(new SendMessageAdapter(linkUpdate.id(), linkUpdate.description()).getSendMessage());
        return ResponseEntity.ok().build();
    }
}
