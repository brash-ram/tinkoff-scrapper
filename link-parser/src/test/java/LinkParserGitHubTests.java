import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.tinkoff.edu.dto.LinkData;
import ru.tinkoff.edu.dto.LinkDataGithub;
import ru.tinkoff.edu.dto.LinkDataStackOverflow;
import ru.tinkoff.edu.enums.Site;
import ru.tinkoff.edu.service.LinkParseService;
import ru.tinkoff.edu.service.parser.ParserConfiguration;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { ParserConfiguration.class, LinkParseService.class })
public class LinkParserGitHubTests {
    @Autowired
    private LinkParseService linkParseService;

    @Test
    public void parseGithubLink(){
        URI link;
        try {
            link = new URI("https://github.com/brash-ram/tinkoff-screpper");
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        LinkData expected = new LinkDataGithub(link, Site.GITHUB, "brash-ram", "tinkoff-screpper");
        assertEquals(expected, linkParseService.parseLink(link));
    }

    @Test
    public void parseValidStackOverflowLink(){
        URI link;
        try {
            link = new URI("https://stackoverflow.com/questions/57772342/how-to-add-a-bean-in-springboottest");
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        LinkData expected = new LinkDataStackOverflow(link, Site.STACK_OVERFLOW, 57772342L);
        assertEquals(expected, linkParseService.parseLink(link));
    }

    @Test
    public void parseInvalidGitHubLink(){
        URI link;
        try {
            link = new URI("https://github.com/features");
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        assertNull(linkParseService.parseLink(link));
    }

    @Test
    public void parseInvalidStackOverflowLink(){
        URI link;
        try {
            link = new URI("https://stackoverflow.com/");
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        assertNull(linkParseService.parseLink(link));
    }
}
