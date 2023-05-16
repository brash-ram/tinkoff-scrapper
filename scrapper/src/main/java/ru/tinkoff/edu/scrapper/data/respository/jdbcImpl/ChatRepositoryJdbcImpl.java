package ru.tinkoff.edu.scrapper.data.respository.jdbcImpl;

import java.net.URISyntaxException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.scrapper.data.entity.Chat;
import ru.tinkoff.edu.scrapper.data.entity.Link;
import ru.tinkoff.edu.scrapper.data.respository.ChatRepository;
import ru.tinkoff.edu.scrapper.utils.JdbcMapper;


@RequiredArgsConstructor
@Transactional
public class ChatRepositoryJdbcImpl implements ChatRepository {

    private final String insert = "INSERT INTO chats (chat_id) VALUES (?)";

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Chat findByChatId(Long tgChatId) {
        String findByChatId =
            "SELECT c.id id, c.chat_id chat_id, l.id link_id, l.url url, l.last_update last_update" +
                " FROM chats AS c LEFT JOIN links AS l ON c.id = l.chat" +
                " WHERE c.chat_id = ?";
        return jdbcTemplate.query(findByChatId, rs -> {
            List<Chat> chats = mapListChats(rs);
            return chats.isEmpty() ? null : chats.get(0);
        }, tgChatId);
    }

    @Override
    public Optional<Chat> findById(Long id) {
        String findById = "SELECT c.id id, c.chat_id chat_id, l.id link_id, l.url url, l.last_update last_update" +
            " FROM chats AS c LEFT JOIN links AS l ON c.id = l.chat" +
            " WHERE c.id = ?";
        return Optional.ofNullable(jdbcTemplate.query(findById, rs -> {
            List<Chat> chats = mapListChats(rs);
            return chats.isEmpty() ? null : chats.get(0);
        }, id));
    }

    @Override
    public Chat save(Chat chat) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(insert, new String[] {"id"});
            ps.setLong(1, chat.getChatId());
            return ps;
        }, keyHolder);

        if (keyHolder.getKey() != null) {
            return chat.setId((long) keyHolder.getKey());
        } else {
            return chat;
        }
    }

    @Override
    public void remove(Long id) {
        String delete = "DELETE FROM chats WHERE id = ?";
        jdbcTemplate.update(delete, id);
    }

    @Override
    public List<Chat> findAll() {
        String findAll = "SELECT c.id id, c.chat_id chat_id, l.id link_id, l.url url, l.last_update last_update" +
            " FROM chats AS c LEFT JOIN links AS l ON c.id = l.chat";
        return jdbcTemplate.query(findAll, this::mapListChats);
    }

    private List<Chat> mapListChats(ResultSet rs) throws SQLException {
        Map<Long, Chat> resultMap = new LinkedHashMap<>();

        while (rs.next()) {

            Long id = rs.getLong("id");

            Chat chat = resultMap.getOrDefault(id, JdbcMapper.mapChat(rs));

            try {
                if (rs.getString("url") != null) {
                    List<Link> links = chat.getLinks();
                    links.add(JdbcMapper.mapLink(rs));
                }
            } catch (URISyntaxException e) {
                e.printStackTrace();
                continue;
            }

            resultMap.put(id, chat);
        }

        return new ArrayList<>(resultMap.values());
    }
}
