package me.splm.app.inject.processor.component.proxy;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;


public class ActionTaskQueue {
    private Queue<ActionTask> taskQueue = new LinkedBlockingQueue<>();

    public static ActionTaskQueue construct(){
        return new ActionTaskQueue();
    }

    public static ActionTaskQueue construct(ActionTask...tasks){
        return new ActionTaskQueue(tasks);
    }

    private ActionTaskQueue(ActionTask...tasks) {
        add(tasks);
    }

    public boolean isEmpty(){
        return taskQueue.isEmpty();
    }

    public boolean add(ActionTask task) {
       return taskQueue.offer(task);
    }

    public void add(ActionTask...tasks){
        for(ActionTask task:tasks){
            add(task);
        }
    }
    public ActionTask remove() {
        return taskQueue.poll();
    }

    public Queue<ActionTask> values() {
        return this.taskQueue;
    }
}
