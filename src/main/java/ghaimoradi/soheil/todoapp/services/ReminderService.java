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

    // This method will be executed every 10 seconds
    @Scheduled(fixedRate = 10000)
    public void sendReminders() {
        LocalDateTime now = LocalDateTime.now();
        List<Task> tasksToRemind = taskRepository.findByReminderDate(now);

        for (Task task : tasksToRemind) {
            // Send notification to the user's topic
            // We assume the user is subscribed to /topic/reminders/{userId}
            String destination = "/topic/reminders/" + task.getUser().getId();
            messagingTemplate.convertAndSend(destination, "Reminder: " + task.getTitle());

            // Mark the reminder as sent
            task.setReminderSent(true);
            taskRepository.save(task);
        }
    }
}
