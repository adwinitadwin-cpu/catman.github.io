package com.example.kotolud.util;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface StorageService {
    /**
     * Загружает файл изображения и возвращает результат загрузки
     */
    StorageResult uploadImage(MultipartFile file) throws IOException;

    /**
     * Удаляет изображение по его идентификатору
     */
    boolean deleteImage(String imageId);

    /**
     * Результат загрузки изображения
     */
    @Getter
    class StorageResult {
        private final String url;
        private final String imageId;

        public StorageResult(String url, String imageId) {
            this.url = url;
            this.imageId = imageId;
        }
    }
}