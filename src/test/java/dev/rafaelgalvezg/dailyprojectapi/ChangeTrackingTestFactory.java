package dev.rafaelgalvezg.dailyprojectapi;

import dev.rafaelgalvezg.dailyprojectapi.model.ChangeTracking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.Collections;
import java.util.List;

public class ChangeTrackingTestFactory {

    public static ChangeTracking createChangeTracking() {
        ChangeTracking changeTracking = new ChangeTracking();
        changeTracking.setIdTracking(1L);
        changeTracking.setDescription("Test Description");
        return changeTracking;
    }

    public static Page<ChangeTracking> createChangeTrackingPage() {
        List<ChangeTracking> changeTracking = Collections.singletonList(createChangeTracking());
        return new PageImpl<>(changeTracking);
    }
}
