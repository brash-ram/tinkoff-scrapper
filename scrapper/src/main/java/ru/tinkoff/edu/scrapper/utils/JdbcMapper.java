package ru.tinkoff.edu.scrapper.utils;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import ru.tinkoff.edu.scrapper.data.entity.Chat;
import ru.tinkoff.edu.scrapper.data.entity.Link;

public class JdbcMapper {
    public static Chat mapChat(ResultSet rs) throws SQLException {
        return new Chat()
                .setId(rs.getLong("id"))
                .setChatId(rs.getLong("chat_id"))
                .setLinks(new ArrayList<>());
    }

    public static Link mapLink(ResultSet rs) throws SQLException, URISyntaxException {
        return new Link()
                .setId(rs.getLong("link_id"))
                .setLastUpdate(rs.getTimestamp("last_update"))
                .setUrl(new URI(rs.getString("url")));
    }
}
