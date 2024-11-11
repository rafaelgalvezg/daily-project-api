package dev.rafaelgalvezg.dailyprojectapi;

import dev.rafaelgalvezg.dailyprojectapi.model.Collaborator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.Collections;
import java.util.List;

public class CollaboratorTestFactory {

    public static Collaborator createCollaborator() {
        Collaborator collaborator = new Collaborator();
        collaborator.setIdCollaborator(1L);
        collaborator.setName("Juan Perez");
        collaborator.setEmail("juan.perez@example.com");
            return collaborator;
}

    public static Page<Collaborator> createCollaboratorsPage() {
        List<Collaborator> collaborators = Collections.singletonList(createCollaborator());
        return new PageImpl<>(collaborators);
    }
}
