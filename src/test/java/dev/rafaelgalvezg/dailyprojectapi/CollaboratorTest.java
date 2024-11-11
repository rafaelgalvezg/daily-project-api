package dev.rafaelgalvezg.dailyprojectapi;

import dev.rafaelgalvezg.dailyprojectapi.exception.ModelNotFoundException;
import dev.rafaelgalvezg.dailyprojectapi.model.Collaborator;
import dev.rafaelgalvezg.dailyprojectapi.repository.CollaboratorRepository;
import dev.rafaelgalvezg.dailyprojectapi.service.impl.CollaboratorServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CollaboratorTest {

    @InjectMocks
    private CollaboratorServiceImpl collaboratorService;

    @Mock
    private CollaboratorRepository collaboratorRepository;

    @Test
    @DisplayName("Test save collaborator successfully")
    void testSaveCollaborator() {
        Collaborator collaborator = CollaboratorTestFactory.createCollaborator();
        when(collaboratorRepository.save(collaborator)).thenReturn(collaborator);

        Collaborator savedCollaborator = collaboratorService.save(collaborator);
        assertThat(savedCollaborator).isNotNull();
        assertThat(savedCollaborator.getName()).isEqualTo(collaborator.getName());

        verify(collaboratorRepository, Mockito.times(1)).save(collaborator);
    }

    @Test
    @DisplayName("Test findAll successfully")
    void testFindAll() {
        Page<Collaborator> collaboratorsPage = CollaboratorTestFactory.createCollaboratorsPage();
        Pageable pageable = PageRequest.of(0, 10);
        when(collaboratorRepository.findAll(pageable)).thenReturn(collaboratorsPage);

        Page<Collaborator> resultPage = collaboratorService.findAll(pageable);
        assertThat(resultPage).isNotEmpty();

        verify(collaboratorRepository, Mockito.times(1)).findAll(pageable);
    }

    @Test
    @DisplayName("Test findById successfully")
    void testFindById() {
        Collaborator collaborator = CollaboratorTestFactory.createCollaborator();
        when(collaboratorRepository.findById(collaborator.getIdCollaborator())).thenReturn(Optional.of(collaborator));

        Collaborator foundCollaborator = collaboratorService.findById(collaborator.getIdCollaborator());
        assertThat(foundCollaborator).isNotNull();
        assertThat(foundCollaborator.getIdCollaborator()).isEqualTo(collaborator.getIdCollaborator());

        verify(collaboratorRepository, Mockito.times(1)).findById(collaborator.getIdCollaborator());
    }

    @Test
    @DisplayName("Test findById throws exception")
    void testFindByIdThrowsException() {
        Long id = 1L;
        when(collaboratorRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> collaboratorService.findById(id))
                .isInstanceOf(ModelNotFoundException.class)
                .hasMessageContaining("ID NOT FOUND: " + id);

        verify(collaboratorRepository, Mockito.times(1)).findById(id);
    }

    @Test
    @DisplayName("Test delete successfully")
    void testDelete() {
        Collaborator collaborator = CollaboratorTestFactory.createCollaborator();
        when(collaboratorRepository.findById(collaborator.getIdCollaborator())).thenReturn(Optional.of(collaborator));

        collaboratorService.delete(collaborator.getIdCollaborator());
        verify(collaboratorRepository, Mockito.times(1)).delete(collaborator);
    }

    @Test
    @DisplayName("Test delete throws exception")
    void testDeleteThrowsException() {
        Long id = 1L;
        when(collaboratorRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> collaboratorService.delete(id))
                .isInstanceOf(ModelNotFoundException.class)
                .hasMessageContaining("ID NOT FOUND: " + id);

        verify(collaboratorRepository, Mockito.times(1)).findById(id);
    }

    @Test
    @DisplayName("Test update successfully")
    void testUpdate() {
        Collaborator collaborator = CollaboratorTestFactory.createCollaborator();
        when(collaboratorRepository.existsById(collaborator.getIdCollaborator())).thenReturn(true);
        when(collaboratorRepository.save(collaborator)).thenReturn(collaborator);

        Collaborator updatedCollaborator = collaboratorService.update(collaborator.getIdCollaborator(), collaborator);
        assertThat(updatedCollaborator).isNotNull();
        assertThat(updatedCollaborator.getName()).isEqualTo(collaborator.getName());

        verify(collaboratorRepository, Mockito.times(1)).existsById(collaborator.getIdCollaborator());
        verify(collaboratorRepository, Mockito.times(1)).save(collaborator);
    }

    @Test
    @DisplayName("Test update throws exception")
    void testUpdateThrowsException() {
        Long id = 1L;
        Collaborator collaborator = CollaboratorTestFactory.createCollaborator();
        when(collaboratorRepository.existsById(id)).thenReturn(false);

        assertThatThrownBy(() -> collaboratorService.update(id, collaborator))
                .isInstanceOf(ModelNotFoundException.class)
                .hasMessageContaining("ID NOT FOUND: " + id);

        verify(collaboratorRepository, Mockito.times(1)).existsById(id);
    }
}
