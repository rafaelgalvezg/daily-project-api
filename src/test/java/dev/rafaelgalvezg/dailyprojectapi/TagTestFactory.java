package dev.rafaelgalvezg.dailyprojectapi;

import dev.rafaelgalvezg.dailyprojectapi.model.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.Collections;
import java.util.List;

public class TagTestFactory {
    public static Tag createTag() {
        Tag tag = new Tag();
        tag.setIdTag(1L);
        tag.setName("Test Tag");
        tag.setColor("Red");
        return tag;
    }

    public static Page<Tag> createTagsPage() {
        List<Tag> tags = Collections.singletonList(createTag());
        return new PageImpl<>(tags);
    }
}
