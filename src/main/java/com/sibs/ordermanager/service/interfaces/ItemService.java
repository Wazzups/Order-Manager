package com.sibs.ordermanager.service.interfaces;

import com.sibs.ordermanager.model.Item;
import java.util.List;
import java.util.Optional;

public interface ItemService {

  Item saveItem(Item item);

  Optional<Item> getItemById(Long id);

  List<Item> getAllItems();

  void deleteItem(Long id);
}
