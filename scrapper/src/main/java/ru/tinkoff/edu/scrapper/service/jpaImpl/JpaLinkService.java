package ru.tinkoff.edu.scrapper.service.jpaImpl;

import java.net.URI;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.scrapper.data.entity.Link;
import ru.tinkoff.edu.scrapper.data.respository.jpa.JpaLinkRepository;
import ru.tinkoff.edu.scrapper.service.ChatService;
import ru.tinkoff.edu.scrapper.service.LinkService;


@RequiredArgsConstructor
public class JpaLinkService implements LinkService {

    private final JpaLinkRepository jpaLinkRepository;
    private final ChatService chatService;

    @Override
    public List<Link> getAllBefore(Timestamp borderTime) {
        return jpaLinkRepository.findAllByLastUpdateLessThan(borderTime);
    }

    @Override
    public List<Link> getAll() {
        return jpaLinkRepository.findAll();
    }

    @Override
    @Transactional
    public void updateTimeUpdate(Long linkId, Timestamp timeUpdate) {
        jpaLinkRepository.save(
                jpaLinkRepository.findById(linkId)
                        .orElseThrow(() -> new RuntimeException("Link with id [" + linkId + "] not found"))
                        .setLastUpdate(timeUpdate)
        );
    }

    @Override
    public Link add(long tgChatId, URI url) {
        return jpaLinkRepository.save(new Link()
                .setUrl(url)
                .setChat(chatService.getByChatId(tgChatId))
                .setLastUpdate(new Timestamp(System.currentTimeMillis()))
        );
    }

    @Override
    @Transactional
    public Link remove(long tgChatId, URI url) {
        Optional<Link> resultLink = chatService.getByChatId(tgChatId).getLinks()
                .stream()
                .filter(link -> url.equals(link.getUrl()))
                .findFirst();
        if (resultLink.isPresent()) {
            Link link = resultLink.get();
            jpaLinkRepository.delete(link);
            return link;
        } else {
            throw new RuntimeException("URL not found");
        }
    }

    @Override
    public List<Link> listAll(long tgChatId) {
        return chatService.getByChatId(tgChatId).getLinks();
    }
}
