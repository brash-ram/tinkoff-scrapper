package ru.tinkoff.edu.scrapper.data.respository.jpa;

import java.sql.Timestamp;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.tinkoff.edu.scrapper.data.entity.Link;

@Repository
public interface JpaLinkRepository extends JpaRepository<Link, Long> {
    List<Link> findAllByLastUpdateLessThan(Timestamp timestamp);
}
