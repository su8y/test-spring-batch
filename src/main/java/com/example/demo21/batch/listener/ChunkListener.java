package com.example.demo21.batch.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.scope.context.ChunkContext;

@Slf4j
public class ChunkListener implements org.springframework.batch.core.ChunkListener {
    @Override
    public void beforeChunk(ChunkContext context) {
        System.out.println("HELLO");
        log.error("##### before Chunk");
        org.springframework.batch.core.ChunkListener.super.beforeChunk(context);
    }

    @Override
    public void afterChunkError(ChunkContext context) {
        System.out.println("HELLO");
        log.error("#### error");
        org.springframework.batch.core.ChunkListener.super.afterChunkError(context);
    }

    @Override
    public void afterChunk(ChunkContext context) {
        log.error("ERROR CHUNK");
        org.springframework.batch.core.ChunkListener.super.afterChunk(context);
    }
}
