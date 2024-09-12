package hello.itemservice.domain.item;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ItemRepositoryTest {


    ItemRepository itemRepository = new ItemRepository();

    @AfterEach
    void afterEach() {
        itemRepository.clearStore();
    }

    @Test
    void save() {
        // Arrange
        // TODO: Initialize test data
        Item item = new Item("itemA", 1000, 10);

        // Act
        // TODO: Call the method to be tested
        Item savedItem = itemRepository.save(item);


        // Assert
        // TODO: Verify the results
        Item findItem = itemRepository.findById(item.getId());
        assertThat(findItem).isEqualTo(savedItem);
    }


    @Test
    void findAll() {
        // Arrange
        // TODO: Initialize test data
        Item itemA = new Item("itemA", 1000, 10);
        Item itemB = new Item("itemB", 2000, 20);

        // Act
        // TODO: Call the method to be tested
        itemRepository.save(itemA);
        itemRepository.save(itemB);

        // Assert
        // TODO: Verify the results
        List<Item> items = itemRepository.findAll();
        assertThat(items.size()).isEqualTo(2);
        assertThat(items).contains(itemA, itemB);
    }

    @Test
    void update() {
        // Arrange
        // TODO: Initialize test data
        Item itemA = new Item("itemA", 1000, 10);
        itemRepository.save(itemA);
        Long id = itemA.getId();
        // Act
        // TODO: Call the method to be tested
        Item updateParam = new Item("itemB", 2000, 20);
        itemRepository.update(id, updateParam);
        Item findItem = itemRepository.findById(id);


        // Assert
        // TODO: Verify the results
        assertThat(findItem.getItemName()).isEqualTo(updateParam.getItemName());
        assertThat(findItem.getPrice()).isEqualTo(updateParam.getPrice());
        assertThat(findItem.getQuantity()).isEqualTo(updateParam.getQuantity());
    }

}