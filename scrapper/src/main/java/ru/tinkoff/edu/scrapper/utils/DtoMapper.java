package ru.tinkoff.edu.scrapper.utils;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.scrapper.data.entity.Link;
import ru.tinkoff.edu.scrapper.dto.response.LinkResponse;
import ru.tinkoff.edu.scrapper.dto.response.ListLinksResponse;

@Component
@RequiredArgsConstructor
public class DtoMapper {

    public LinkResponse convertLinkToLinkResponse(Link link) {
        return new LinkResponse(link.getId(), link.getUrl());
    }

    public ListLinksResponse convertListLinkToListLinkResponse(List<Link> links) {
        return new ListLinksResponse(
                links.stream()
                        .map(this::convertLinkToLinkResponse)
                        .collect(Collectors.toList()),
                links.size()
        );
    }
}
