package glebkr.task_management_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import glebkr.task_management_system.entity.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {

}
