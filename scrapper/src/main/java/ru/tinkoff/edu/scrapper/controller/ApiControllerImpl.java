package ru.tinkoff.edu.scrapper.controller;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.edu.scrapper.data.entity.Link;
import ru.tinkoff.edu.scrapper.dto.request.AddLinkRequest;
import ru.tinkoff.edu.scrapper.dto.request.RemoveLinkRequest;
import ru.tinkoff.edu.scrapper.dto.response.LinkResponse;
import ru.tinkoff.edu.scrapper.dto.response.ListLinksResponse;
import ru.tinkoff.edu.scrapper.service.ChatService;
import ru.tinkoff.edu.scrapper.service.LinkService;
import ru.tinkoff.edu.scrapper.utils.DtoMapper;

@RestController
@RequiredArgsConstructor
public class ApiControllerImpl implements ApiController {

    private final LinkService linkService;
    private final ChatService chatService;
    private final DtoMapper dtoMapper;

    @Override
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/links/delete",
            produces = { "application/json" },
            consumes = { "application/json" }
    )
     public ResponseEntity<LinkResponse> linksDelete(@NotNull @RequestHeader(value = "Tg-Chat-Id") Long tgChatId,
                                                     @RequestBody RemoveLinkRequest removeLinkRequest) {
        Link link = linkService.remove(tgChatId, removeLinkRequest.link());
        return ResponseEntity.ok(dtoMapper.convertLinkToLinkResponse(link));
    }

    @Override
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/links",
            produces = { "application/json" }
    )
    public ResponseEntity<ListLinksResponse> linksGet(@NotNull @RequestHeader(value = "Tg-Chat-Id") Long tgChatId) {
        List<Link> links = linkService.listAll(tgChatId);
        return ResponseEntity.ok(dtoMapper.convertListLinkToListLinkResponse(links));
    }

    @Override
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/links",
            produces = { "application/json" },
            consumes = { "application/json" }
    )
    public ResponseEntity<LinkResponse> linksPost(@NotNull @RequestHeader(value = "Tg-Chat-Id") Long tgChatId,
                                                  @RequestBody AddLinkRequest addLinkRequest) {
        Link link = linkService.add(tgChatId, addLinkRequest.link());
        return ResponseEntity.ok(dtoMapper.convertLinkToLinkResponse(link));
    }

    @Override
    @RequestMapping(
            method = RequestMethod.DELETE,
            value = "/tg-chat/{id}",
            produces = { "application/json" }
    )
    public ResponseEntity<Void> tgChatIdDelete(@PathVariable Long id) {
        chatService.unregister(id);
        return ResponseEntity.ok().build();
    }

    @Override
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/tg-chat/{id}",
            produces = { "application/json" }
    )
    public ResponseEntity<Void> tgChatIdPost(@PathVariable("id") Long id) {
        chatService.register(id);
        return ResponseEntity.ok().build();
    }
}
