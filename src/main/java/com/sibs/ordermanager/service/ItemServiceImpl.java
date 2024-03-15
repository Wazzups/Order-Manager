package com.sibs.ordermanager.service;

import com.sibs.ordermanager.model.Item;
import com.sibs.ordermanager.repository.ItemRepository;
import com.sibs.ordermanager.service.interfaces.ItemService;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ItemServiceImpl implements ItemService {

  private final ItemRepository itemRepository;

  @Autowired
  public ItemServiceImpl(ItemRepository itemRepository) {
    this.itemRepository = itemRepository;
  }

  @Override
  public Item saveItem(Item item) {
    return itemRepository.save(item);
  }

  @Override
  public Optional<Item> getItemById(Long id) {
    return itemRepository.findById(id);
  }

  @Override
  public List<Item> getAllItems() {
    return itemRepository.findAll();
  }

  @Override
  public void deleteItem(Long id) {
    itemRepository.deleteById(id);
  }
}
