package com.firestore.crud.service;

import com.firestore.crud.model.Task;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class FirestoreService
{

    private final Firestore firestore;

    FirestoreService(Firestore firestore)
    {
        this.firestore = firestore;
    }

    public String addTask(Task task) throws ExecutionException, InterruptedException
    {
        ApiFuture<DocumentReference> tasks = firestore.collection("tasks").add(task);
        return "Document Saved: TaskID is :: " + tasks.get().getId();
    }

    public ApiFuture<DocumentSnapshot> getTask(String taskTitle)
    {
        DocumentReference docRef = firestore.collection("tasks").document(taskTitle);
        return docRef.get();
    }

    public ApiFuture<WriteResult> updateTask(String taskTitle, Task task)
    {
        DocumentReference docRef = firestore.collection("tasks").document(taskTitle);
        return docRef.set(task);
    }

    public ApiFuture<WriteResult> deleteTask(String taskTitle)
    {
        DocumentReference docRef = firestore.collection("tasks").document(taskTitle);
        return docRef.delete();
    }

}
