package dev.rafaelgalvezg.dailyprojectapi;

import dev.rafaelgalvezg.dailyprojectapi.exception.CustomOptimisticLockException;
import dev.rafaelgalvezg.dailyprojectapi.exception.ModelNotFoundException;
import dev.rafaelgalvezg.dailyprojectapi.model.ChangeTracking;
import dev.rafaelgalvezg.dailyprojectapi.repository.ChangeTrackingRepository;
import dev.rafaelgalvezg.dailyprojectapi.service.impl.ChangeTrackingServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ChangeTrackingTest {

    @InjectMocks
    private ChangeTrackingServiceImpl changeTrackingService;

    @Mock
    private ChangeTrackingRepository changeTrackingRepository;

    @Test
    @DisplayName("Test save ChangeTracking successfully")
    void testSaveChangeTracking() {
        ChangeTracking changeTracking = ChangeTrackingTestFactory.createChangeTracking();
        when(changeTrackingRepository.save(changeTracking)).thenReturn(changeTracking);

        ChangeTracking savedChangeTracking = changeTrackingService.save(changeTracking);
        assertThat(savedChangeTracking).isNotNull();
        assertThat(savedChangeTracking.getDescription()).isEqualTo(changeTracking.getDescription());

        verify(changeTrackingRepository, times(1)).save(changeTracking);
    }

    @Test
    @DisplayName("Test findAll successfully")
    void testFindAll() {
        Page<ChangeTracking> changeTrackingsPage = ChangeTrackingTestFactory.createChangeTrackingPage();
        Pageable pageable = PageRequest.of(0, 10);
        when(changeTrackingRepository.findAll(pageable)).thenReturn(changeTrackingsPage);

        Page<ChangeTracking> resultPage = changeTrackingService.findAll(pageable);
        assertThat(resultPage).isNotEmpty();

        verify(changeTrackingRepository, times(1)).findAll(pageable);
    }

    @Test
    @DisplayName("Test findById successfully")
    void testFindById() {
        ChangeTracking changeTracking = ChangeTrackingTestFactory.createChangeTracking();
        when(changeTrackingRepository.findById(changeTracking.getIdTracking())).thenReturn(Optional.of(changeTracking));

        ChangeTracking foundChangeTracking = changeTrackingService.findById(changeTracking.getIdTracking());
        assertThat(foundChangeTracking).isNotNull();
        assertThat(foundChangeTracking.getIdTracking()).isEqualTo(changeTracking.getIdTracking());

        verify(changeTrackingRepository, times(1)).findById(changeTracking.getIdTracking());
    }

    @Test
    @DisplayName("Test findById throws exception")
    void testFindByIdThrowsException() {
        Long id = 1L;
        when(changeTrackingRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> changeTrackingService.findById(id))
                .isInstanceOf(ModelNotFoundException.class)
                .hasMessageContaining("ID NOT FOUND: " + id);

        verify(changeTrackingRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("Test delete successfully")
    void testDelete() {
        ChangeTracking changeTracking = ChangeTrackingTestFactory.createChangeTracking();
        when(changeTrackingRepository.findById(changeTracking.getIdTracking())).thenReturn(Optional.of(changeTracking));

        changeTrackingService.delete(changeTracking.getIdTracking());
        verify(changeTrackingRepository, times(1)).delete(changeTracking);
    }

    @Test
    @DisplayName("Test delete throws exception")
    void testDeleteThrowsException() {
        Long id = 1L;
        when(changeTrackingRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> changeTrackingService.delete(id))
                .isInstanceOf(ModelNotFoundException.class)
                .hasMessageContaining("ID NOT FOUND: " + id);

        verify(changeTrackingRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("Test update successfully")
    void testUpdate() {
        ChangeTracking changeTracking = ChangeTrackingTestFactory.createChangeTracking();
        when(changeTrackingRepository.existsById(changeTracking.getIdTracking())).thenReturn(true);
        when(changeTrackingRepository.save(changeTracking)).thenReturn(changeTracking);

        ChangeTracking updatedChangeTracking = changeTrackingService.update(changeTracking.getIdTracking(), changeTracking);
        assertThat(updatedChangeTracking).isNotNull();
        assertThat(updatedChangeTracking.getDescription()).isEqualTo(changeTracking.getDescription());

        verify(changeTrackingRepository, times(1)).existsById(changeTracking.getIdTracking());
        verify(changeTrackingRepository, times(1)).save(changeTracking);
    }

    @Test
    @DisplayName("Test update throws exception")
    void testUpdateThrowsException() {
        Long id = 1L;
        ChangeTracking changeTracking = ChangeTrackingTestFactory.createChangeTracking();
        when(changeTrackingRepository.existsById(id)).thenReturn(false);

        assertThatThrownBy(() -> changeTrackingService.update(id, changeTracking))
                .isInstanceOf(ModelNotFoundException.class)
                .hasMessageContaining("ID NOT FOUND: " + id);

        verify(changeTrackingRepository, times(1)).existsById(id);
    }

    @Test
    @DisplayName("Should throw CustomOptimisticLockException on optimistic lock failure")
    void testUpdateThrowsCustomOptimisticLockException() {
        ChangeTracking changeTracking = ChangeTrackingTestFactory.createChangeTracking();
        Long changeTrackingId = changeTracking.getIdTracking();

        when(changeTrackingRepository.existsById(changeTrackingId)).thenReturn(true);
        when(changeTrackingRepository.save(any())).thenThrow(ObjectOptimisticLockingFailureException.class);

        assertThrows(CustomOptimisticLockException.class, () -> changeTrackingService.update(changeTrackingId, changeTracking));
    }
}
