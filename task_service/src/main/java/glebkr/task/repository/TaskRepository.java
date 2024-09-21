package glebkr.task.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import glebkr.task.dto.TaskAnalyticsDTO;
import glebkr.task.entity.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {

    @Query("SELECT t FROM Task t WHERE t.resolvingDate >= :startDate AND t.resolvingDate <= :endDate")
    List<Task> findTasksByInterval(LocalDateTime startDate, LocalDateTime endDate);

    @Query("SELECT NEW glebkr.task.dto.TaskAnalyticsDTO(COUNT(t), CAST(AVG(DATEDIFF(HOUR, t.startDate, t.resolvingDate)) AS Integer)) " +
            "FROM Task t WHERE t.resolvingDate >= :startDate AND t.resolvingDate <= :endDate")
    List<TaskAnalyticsDTO> getTasksAnalyticsByInterval(LocalDateTime startDate, LocalDateTime endDate);

    @Query("SELECT NEW glebkr.task.dto.TaskAnalyticsDTO(COUNT(t), CAST(AVG(DATEDIFF(HOUR, t.startDate, t.resolvingDate)) AS Integer)) " +
            "FROM Task t WHERE t.memberId = :memberId")
    List<TaskAnalyticsDTO> getTasksAnalyticsByMemberId(UUID memberId);

}