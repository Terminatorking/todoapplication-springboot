package ghaimoradi.soheil.todoapp.services;

import ghaimoradi.soheil.todoapp.models.Task;
import ghaimoradi.soheil.todoapp.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReminderService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    // This method will be executed every 1 seconds
    @Scheduled(fixedRate = 1000)
    public void sendReminders() {
        LocalDateTime now = LocalDateTime.now();
        List<Task> tasksToRemind = taskRepository.findByReminderDate(now);

        if (tasksToRemind.isEmpty()) {
            return;
        }

        for (Task task : tasksToRemind) {
            try {
                String destination = "/topic/reminders/" + task.getUser().getId();
                messagingTemplate.convertAndSend(destination, "Reminder: " + task.getTitle());
                System.out.println("Sent message to " + destination);

                task.setReminderSent(true);
                taskRepository.save(task);
            } catch (Exception e) {
                System.err.println("Error processing reminder for task ID: " + task.getId() + " - " + e.getMessage());
            }
        }
    }
}
