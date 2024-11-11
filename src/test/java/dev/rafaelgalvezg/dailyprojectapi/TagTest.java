package dev.rafaelgalvezg.dailyprojectapi;

import dev.rafaelgalvezg.dailyprojectapi.model.Tag;
import dev.rafaelgalvezg.dailyprojectapi.repository.TagRepository;
import dev.rafaelgalvezg.dailyprojectapi.exception.ModelNotFoundException;
import dev.rafaelgalvezg.dailyprojectapi.service.impl.TagServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TagTest {

    @InjectMocks
    private TagServiceImpl tagService;

    @Mock
    private TagRepository tagRepository;

    @Test
    @DisplayName("Test save tag successfully")
    void testSave() {
        Tag tag = TagTestFactory.createTag();
        when(tagRepository.save(tag)).thenReturn(tag);

        Tag savedTag = tagService.save(tag);
        assertThat(savedTag).isNotNull();
        assertThat(savedTag.getName()).isEqualTo(tag.getName());
        assertSame(savedTag, tag, "The saved tag is the same as the tag");
        assertNotNull(savedTag, "The saved tag is not null");

        verify(tagRepository, times(1)).save(tag);
    }

    @Test
    @DisplayName("Test findAll successfully")
    void testFindAll() {
        Page<Tag> tagsPage = TagTestFactory.createTagsPage();
        Pageable pageable = PageRequest.of(0, 10);
        when(tagRepository.findAll(pageable)).thenReturn(tagsPage);

        Page<Tag> resultPage = tagService.findAll(pageable);
        assertThat(resultPage).isNotEmpty();

        verify(tagRepository, times(1)).findAll(pageable);
    }

    @Test
    @DisplayName("Test findById successfully")
    void testFindById() {
        Tag tag = TagTestFactory.createTag();
        when(tagRepository.findById(tag.getIdTag())).thenReturn(Optional.of(tag));

        Tag foundTag = tagService.findById(tag.getIdTag());
        assertThat(foundTag).isNotNull();
        assertThat(foundTag.getIdTag()).isEqualTo(tag.getIdTag());

        verify(tagRepository, times(1)).findById(tag.getIdTag());
    }

    @Test
    @DisplayName("Test findById throws exception")
    void testFindByIdThrowsException() {
        Long id = 1L;
        when(tagRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> tagService.findById(id))
                .isInstanceOf(ModelNotFoundException.class)
                .hasMessageContaining("ID NOT FOUND: " + id);

        verify(tagRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("Test delete successfully")
    void testDelete() {
        Tag tag = TagTestFactory.createTag();
        when(tagRepository.findById(tag.getIdTag())).thenReturn(Optional.of(tag));

        tagService.delete(tag.getIdTag());
        verify(tagRepository, times(1)).delete(tag);
    }

    @Test
    @DisplayName("Test delete throws exception")
    void testDeleteThrowsException() {
        Long id = 1L;
        when(tagRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> tagService.delete(id))
                .isInstanceOf(ModelNotFoundException.class)
                .hasMessageContaining("ID NOT FOUND: " + id);

        verify(tagRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("Test update successfully")
    void testUpdate() {
        Tag tag = TagTestFactory.createTag();
        when(tagRepository.existsById(tag.getIdTag())).thenReturn(true);
        when(tagRepository.save(tag)).thenReturn(tag);

        Tag updatedTag = tagService.update(tag.getIdTag(), tag);
        assertThat(updatedTag).isNotNull();
        assertThat(updatedTag.getName()).isEqualTo(tag.getName());

        verify(tagRepository, times(1)).existsById(tag.getIdTag());
        verify(tagRepository, times(1)).save(tag);
    }

    @Test
    @DisplayName("Test update throws exception")
    void testUpdateThrowsException() {
        Long id = 1L;
        Tag tag = TagTestFactory.createTag();
        when(tagRepository.existsById(id)).thenReturn(false);

        assertThatThrownBy(() -> tagService.update(id, tag))
                .isInstanceOf(ModelNotFoundException.class)
                .hasMessageContaining("ID NOT FOUND: " + id);

        verify(tagRepository, times(1)).existsById(id);
    }
}
