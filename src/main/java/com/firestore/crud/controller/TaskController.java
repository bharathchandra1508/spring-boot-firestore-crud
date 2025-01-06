package com.firestore.crud.controller;

import com.firestore.crud.model.Task;
import com.firestore.crud.service.FirestoreService;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.WriteResult;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/tasks")
@AllArgsConstructor
public class TaskController
{

    @Autowired
    private FirestoreService firestoreService;

    @PostMapping("/addTask")
    public ResponseEntity<String> addTask(@RequestBody Task task) throws ExecutionException, InterruptedException
    {
        return ResponseEntity.ok(firestoreService.addTask(task));
    }

    @GetMapping("/{taskTile}")
    public ResponseEntity<Task> getTask(@PathVariable String taskTitle) throws ExecutionException, InterruptedException
    {
        ApiFuture<DocumentSnapshot> future = firestoreService.getTask(taskTitle);
        DocumentSnapshot document = future.get();
        if(document.exists())
        {
            Task task = document.toObject(Task.class);
            return ResponseEntity.ok(task);
        }
        else
        {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{taskTile}")
    public ResponseEntity<String> updateTask(@PathVariable String taskTitle, @RequestBody Task task) throws ExecutionException, InterruptedException
    {
        ApiFuture<WriteResult> future = firestoreService.updateTask(taskTitle,task);
        return ResponseEntity.ok("Task Updated: "+future.get().getUpdateTime());
    }

    @DeleteMapping("/{taskTile}")
    public ResponseEntity<String> deleteTask(@PathVariable String taskTitle) throws ExecutionException, InterruptedException {
        ApiFuture<WriteResult> future = firestoreService.deleteTask(taskTitle);
        return ResponseEntity.ok("User Deleted: "+future.get().getUpdateTime());
    }

}
