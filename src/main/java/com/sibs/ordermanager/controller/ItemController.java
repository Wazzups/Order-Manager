package com.sibs.ordermanager.controller;

import com.sibs.ordermanager.model.Item;
import com.sibs.ordermanager.service.interfaces.ItemService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/items")
public class ItemController {

  private final ItemService itemService;

  @Autowired
  public ItemController(ItemService itemService) {
    this.itemService = itemService;
  }

  @PostMapping
  public ResponseEntity<Item> createItem(@RequestBody Item item) {
    Item savedItem = itemService.saveItem(item);
    return new ResponseEntity<>(savedItem, HttpStatus.CREATED);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Item> getItemById(@PathVariable Long id) {
    return itemService.getItemById(id).map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @GetMapping
  public ResponseEntity<List<Item>> getAllItems() {
    List<Item> items = itemService.getAllItems();
    return new ResponseEntity<>(items, HttpStatus.OK);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Item> updateItem(@PathVariable Long id, @RequestBody Item itemDetails) {
    return itemService.getItemById(id).map(item -> {
      item.setName(itemDetails.getName());
      Item updatedItem = itemService.saveItem(item);
      return new ResponseEntity<>(updatedItem, HttpStatus.OK);
    }).orElseGet(() -> ResponseEntity.notFound().build());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
    return itemService.getItemById(id).map(item -> {
      itemService.deleteItem(id);
      return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }).orElseGet(() -> ResponseEntity.notFound().build());
  }
}
