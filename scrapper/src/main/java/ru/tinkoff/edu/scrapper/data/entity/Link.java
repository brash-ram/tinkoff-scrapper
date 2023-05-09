package ru.tinkoff.edu.scrapper.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.net.URI;
import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "links")
@SequenceGenerator(name = "links_id_seq", sequenceName = "links_id_seq", allocationSize = 1)
public class Link {

    @Id
    @GeneratedValue(generator = "links_id_seq")
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "url")
    private URI url;

    @ManyToOne()
    @JoinColumn(name = "chat")
    private Chat chat;

    @Column(name = "last_update")
    private Timestamp lastUpdate;
}
