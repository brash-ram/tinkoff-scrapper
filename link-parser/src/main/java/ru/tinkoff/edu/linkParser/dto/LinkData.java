package ru.tinkoff.edu.linkParser.dto;

import java.net.URI;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.tinkoff.edu.linkParser.enums.Site;

@AllArgsConstructor
@NoArgsConstructor
@Data
public abstract class LinkData {
    private URI url;
    private Site site;
}
