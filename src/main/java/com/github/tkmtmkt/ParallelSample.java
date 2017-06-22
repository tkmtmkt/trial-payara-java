package com.github.tkmtmkt;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.enterprise.concurrent.ManagedExecutorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless
public class ParallelSample {
    final static Logger logger = LoggerFactory.getLogger(ParallelSample.class);

    final Random rnd = new Random();

    @Resource
    ManagedExecutorService executor;

    public void execute() {
        logger.info(">>>>> " + getClass().getSimpleName() + ".execute() start");

        //タスク一覧
        List<Supplier<Void>> taskList = new ArrayList<Supplier<Void>>(){{
            add(() -> task1());
            add(() -> task2());
        }};

        //並列処理
        List<CompletableFuture<Void>> futureList =
            taskList.stream()
                .map(task -> CompletableFuture.supplyAsync(task, executor))
                .collect(Collectors.toList());

        CompletableFuture
            //リストから配列に変換
            .allOf(futureList.toArray(new CompletableFuture[futureList.size()]))
            //全ての処理が完了したら
            .whenComplete((Void result, Throwable e) -> {
                if (e != null) {
                    logger.error(e.getMessage(), e);
                }
                logger.info("call whenComplete");
            }).join();

        logger.info(">>>>> " + getClass().getSimpleName() + ".execute() end");
    }

    private Void task1() {
        IntStream.range(0, 10).forEach((i) -> {
            try {
                logger.info("task1 (%d)", i);
                Thread.sleep(rnd.nextInt(1000));
            } catch (InterruptedException e) {
                logger.error(e.getMessage());
            }
        });
        logger.info("task1 completed!");
        return null;
    }

    private Void task2() {
        IntStream.range(0, 10).forEach((i) -> {
            try {
                logger.info("task2 (%d)", i);
                Thread.sleep(rnd.nextInt(1000));
            } catch (InterruptedException e) {
                logger.error(e.getMessage());
            }
        });
        logger.info("task2 completed!");
        return null;
    }
}
