package glebkr.task.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import glebkr.task.entity.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {

    @Query("SELECT t FROM Task t WHERE t.startDate >= :startDate AND t.resolvingDate <= :resolvingDate")
    List<Task> findBetweenStartDateAndCompleteDate(LocalDate startDate, LocalDate resolvingDate);

}