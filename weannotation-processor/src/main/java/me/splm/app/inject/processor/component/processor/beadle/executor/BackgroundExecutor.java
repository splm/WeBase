package me.splm.app.inject.processor.component.processor.beadle.executor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class BackgroundExecutor {

    private static final ExecutorService threadExecute = Executors.newScheduledThreadPool(2 * Runtime.getRuntime().availableProcessors());

    public static final List<Task> TASKS = new ArrayList<>();

    public static void execute(Task task) {
        if (task.id != null || task.serial != null) {
            TASKS.add(task);
        }
        if (task.serial == null || !hasBeenAddedToQueue(task.serial)) {
            task.executionAsked = true;
            task.future = directExecutor(task, task.delay);
        }
    }

    public static void execute(final Task task, String id, String serial, long delay) {
        execute(new Task(id, serial, delay) {

            @Override
            public void execute() {
                task.run();
            }
        });
    }

    private static Future<?> directExecutor(Runnable runnable, long delay) {
        if (delay > 0) {
            ScheduledExecutorService scheduledExecutorService = (ScheduledExecutorService) threadExecute;
            return scheduledExecutorService.schedule(runnable, delay, TimeUnit.MILLISECONDS);
        } else {
            return threadExecute.submit(runnable);
        }
    }

    private static boolean hasBeenAddedToQueue(String serial) {
        if (serial != null) {
            for (Task task : TASKS) {
                if (task.executionAsked && serial.equals(task.serial)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static Task take(String serial) {
        int size = TASKS.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                if (serial.equals(TASKS.get(i).serial)) {
                    return TASKS.remove(i);
                }
            }
        }
        return null;
    }

    public static abstract class Task implements Runnable {

        String id;
        String serial;
        long delay;
        boolean executionAsked;
        Future<?> future;

        public Task(String id, String serial, long delay) {
            this.id = id;
            this.serial = serial;
            this.delay = delay;
        }

        @Override
        public void run() {
            try {
                execute();
            } finally {
                postNextExecute();
            }
        }

        public abstract void execute();

        public void postNextExecute() {
            if (id == null || serial == null) {
                return;
            }
            synchronized (BackgroundExecutor.class) {
                Task next = take(serial);
                if (next != null) {
                    BackgroundExecutor.execute(next);
                }
                TASKS.remove(this);
            }
        }

    }

}
