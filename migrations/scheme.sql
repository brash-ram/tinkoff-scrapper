CREATE SEQUENCE IF NOT EXISTS links_id_seq start 1;

CREATE SEQUENCE IF NOT EXISTS chats_id_seq start 1;

CREATE TABLE IF NOT EXISTS chats(
    id BIGINT NOT NULL DEFAULT nextval('chats_id_seq') PRIMARY KEY,
    chat_id BIGINT NOT NULL UNIQUE
    );

CREATE TABLE IF NOT EXISTS links(
    id BIGINT NOT NULL DEFAULT nextval('links_id_seq') PRIMARY KEY,
    url VARCHAR(255) NOT NULL,
    chat BIGINT REFERENCES chats (id) ON DELETE CASCADE,
    last_update TIMESTAMP(0) WITHOUT TIME ZONE NOT NULL
    );

ALTER SEQUENCE links_id_seq
    OWNED BY links.id;


ALTER TABLE
    links ADD CONSTRAINT links_chat_foreign FOREIGN KEY(chat) REFERENCES chats(id);

ALTER SEQUENCE chats_id_seq
    OWNED BY chats.id;