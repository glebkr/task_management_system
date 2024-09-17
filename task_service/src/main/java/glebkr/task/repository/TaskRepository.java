package glebkr.task.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

import glebkr.task.entity.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {

}
