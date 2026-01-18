package com.financeall.service;

import com.financeall.model.Announcement;
import com.financeall.repository.AnnouncementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnnouncementService {
    private final AnnouncementRepository repository;

    // Method ini dipanggil oleh DashboardController
    public List<Announcement> findAllActive() {
        return repository.findAll(); 
    }

    public void createAnnouncement(String title, String content) {
        Announcement announcement = Announcement.builder()
                .title(title)
                .content(content) // Pastikan field di model bernama 'content'
                .createdAt(LocalDateTime.now())
                .build();
        repository.save(announcement);
    }
}