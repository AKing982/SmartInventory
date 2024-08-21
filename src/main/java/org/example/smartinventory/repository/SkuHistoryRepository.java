package org.example.smartinventory.repository;

import org.example.smartinventory.entities.SkuHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.annotation.RequestScope;

import java.util.Optional;

@Repository
public interface SkuHistoryRepository extends JpaRepository<SkuHistoryEntity, Long>
{
    @Query("SELECT s.sequence FROM SkuHistoryEntity s WHERE s.id =:key")
    Optional<Integer> findLastSequence(@Param("key") Long key);

    @Modifying
    @Query("UPDATE SkuHistoryEntity s SET s.sequence =:seq WHERE s.id =:id")
    void updateSequence(@Param("id") Long id, @Param("seq") Integer seq);

}
