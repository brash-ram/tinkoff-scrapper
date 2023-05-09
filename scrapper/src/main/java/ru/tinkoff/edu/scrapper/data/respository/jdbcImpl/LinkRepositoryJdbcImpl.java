package ru.tinkoff.edu.scrapper.data.respository.jdbcImpl;

import java.net.URISyntaxException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.scrapper.data.entity.Link;
import ru.tinkoff.edu.scrapper.data.respository.LinkRepository;
import ru.tinkoff.edu.scrapper.utils.JdbcMapper;


@RequiredArgsConstructor
@Transactional
public class LinkRepositoryJdbcImpl implements LinkRepository {

    private final String insert = "INSERT INTO links (url, chat, last_update) VALUES (?, ?, ?)";

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void updateLastUpdate(Long id, Timestamp timestamp) {
        String updateLastUpdate = "UPDATE links" +
            " SET last_update = ?" +
            " WHERE id = ?";
        jdbcTemplate.update(updateLastUpdate, timestamp, id);
    }

    @Override
    public List<Link> findAllBefore(Timestamp borderTime) {
        String findAllBefore =
            "SELECT c.id id, c.chat_id chat_id, l.id link_id, l.url url, l.last_update last_update" +
                " FROM chats AS c RIGHT JOIN links AS l ON c.id = l.chat" +
                " WHERE l.last_update < ?";
        return jdbcTemplate.query(findAllBefore, this::mapListLinks, borderTime);
    }

    @Override
    public Link save(Link link) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(insert, new String[] {"id"});
            ps.setString(1, link.getUrl().toString());
            ps.setObject(2, link.getChat() != null ? link.getChat().getId() : null);
            ps.setTimestamp(3, link.getLastUpdate());
            return ps;
        }, keyHolder);

        if (keyHolder.getKey() != null) {
            return link.setId((long) keyHolder.getKey());
        } else {
            return link;
        }

    }

    @Override
    public void remove(Long id) {
        String delete = "DELETE FROM links WHERE id = ?";
        jdbcTemplate.update(delete, id);
    }

    @Override
    public List<Link> findAll() {
        String findAll = "SELECT c.id id, c.chat_id chat_id, l.id link_id, l.url url, l.last_update last_update" +
            " FROM chats AS c RIGHT JOIN links AS l ON c.id = l.chat";
        return jdbcTemplate.query(findAll, this::mapListLinks);
    }

    private List<Link> mapListLinks(ResultSet rs) {
        try {
            List<Link> links = new ArrayList<>();
            while (rs.next()) {
                var test = rs.getLong("id");
                links.add(JdbcMapper.mapLink(rs).setChat(JdbcMapper.mapChat(rs)));
            }
            return links;

        } catch (URISyntaxException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
