package ru.tinkoff.edu.scrapper.service.jdbcImpl;

import java.net.URI;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import ru.tinkoff.edu.scrapper.data.entity.Chat;
import ru.tinkoff.edu.scrapper.data.entity.Link;
import ru.tinkoff.edu.scrapper.data.respository.LinkRepository;
import ru.tinkoff.edu.scrapper.service.ChatService;
import ru.tinkoff.edu.scrapper.service.LinkService;

@RequiredArgsConstructor
public class JdbcLinkService implements LinkService {

    private final LinkRepository linkRepository;
    private final ChatService jdbcChatService;

    @Override
    public List<Link> getAllBefore(Timestamp borderTime) {
        return linkRepository.findAllBefore(borderTime);
    }

    @Override
    public List<Link> getAll() {
        return linkRepository.findAll();
    }

    @Override
    public void updateTimeUpdate(Long linkId, Timestamp timeUpdate) {
        linkRepository.updateLastUpdate(linkId, timeUpdate);
    }

    @Override
    public Link add(long tgChatId, URI url) {
        return linkRepository.save(new Link()
                .setUrl(url)
                .setChat(jdbcChatService.getByChatId(tgChatId))
                .setLastUpdate(new Timestamp(System.currentTimeMillis()))
        );
    }

    @Override
    public Link remove(long tgChatId, URI url) {
        Optional<Link> resultLink = jdbcChatService.getByChatId(tgChatId).getLinks()
                .stream()
                .filter(link -> url.equals(link.getUrl()))
                .findFirst();
        if (resultLink.isPresent()) {
            Link link = resultLink.get();
            linkRepository.remove(link.getId());
            return link;
        } else {
            throw new RuntimeException("URL not found");
        }
    }

    @Override
    public List<Link> listAll(long tgChatId) {
        Chat chat = jdbcChatService.getByChatId(tgChatId);
        if (!chat.getLinks().isEmpty()) {
            return chat.getLinks();
        } else {
            throw new RuntimeException("Links not found");
        }
    }
}
