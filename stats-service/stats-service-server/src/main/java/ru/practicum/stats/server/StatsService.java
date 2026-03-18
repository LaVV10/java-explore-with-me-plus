package ru.practicum.stats.server;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.stats.server.dto.HitDto;
import ru.practicum.stats.server.dto.ViewStatsDto;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatsService {
    private final StatsRepository repository;

    public void saveHit(HitDto hitDto) {
        EndpointHit hit = new EndpointHit(
                hitDto.getApp(),
                hitDto.getUri(),
                hitDto.getIp(),
                hitDto.getTimestamp()
        );
        repository.save(hit);
    }

    public List<ViewStatsDto> getStats(
            LocalDateTime start,
            LocalDateTime end,
            List<String> uris,
            boolean unique) {
        if (end.isBefore(start)) {
            throw new IllegalArgumentException("End time must be after start time");
        }

        if (unique) {
            return repository.findUniqueStats(start, end, uris);
        } else {
            return repository.findStats(start, end, uris);
        }
    }
}
