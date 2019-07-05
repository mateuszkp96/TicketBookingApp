package com.mkopec.ticketbookingapp.repository;

import com.mkopec.ticketbookingapp.domain.Screening;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ScreeningRepository extends JpaRepository<Screening, Long> {

    @Query("SELECT s FROM Screening s WHERE s.date BETWEEN ?1 AND ?2 ORDER BY s.movie.title, s.date")
    List<Screening> findByDateAndTimeInterval(LocalDateTime dateStart, LocalDateTime dateEnd);
}
