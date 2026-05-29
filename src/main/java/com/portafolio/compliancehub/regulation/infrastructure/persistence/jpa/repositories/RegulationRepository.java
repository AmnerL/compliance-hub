package com.portafolio.compliancehub.regulation.infrastructure.persistence.jpa.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.portafolio.compliancehub.regulation.domain.model.aggregates.Regulation;
import com.portafolio.compliancehub.regulation.domain.model.valueobjects.Category;

@Repository
public interface RegulationRepository extends JpaRepository<Regulation, Long> {

        @Query("SELECT r FROM Regulation r " +
                        "WHERE (:searchTerm IS NULL OR LOWER(r.title) LIKE LOWER(CONCAT('%', :searchTerm, '%'))) " +
                        "AND (:category IS NULL OR r.category = :category)")
        Page<Regulation> findByFilters(@Param("searchTerm") String searchTerm,
                        @Param("category") Category category,
                        Pageable pageable);

}
