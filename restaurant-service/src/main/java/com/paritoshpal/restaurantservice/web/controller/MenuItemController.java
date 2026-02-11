package com.paritoshpal.restaurantservice.web.controller;

import com.paritoshpal.restaurantservice.domain.MenuCategory;
import com.paritoshpal.restaurantservice.domain.MenuItemService;
import com.paritoshpal.restaurantservice.domain.models.CreateMenuItemRequest;
import com.paritoshpal.restaurantservice.domain.models.MenuItemResponse;
import com.paritoshpal.restaurantservice.domain.models.MenuItemUpdateRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants/{restaurantId}/menus/{menuId}/items")
@RequiredArgsConstructor
public class MenuItemController {

    private static final Logger log = LoggerFactory.getLogger(MenuItemController.class);
    private final MenuItemService menuItemService;

    @PostMapping
    public ResponseEntity<MenuItemResponse> createMenuItem(
            @PathVariable Long restaurantId,
            @PathVariable Long menuId,
            @RequestBody @Valid CreateMenuItemRequest request
    ) {
        log.info("Received request to create menu item for menuId: {}", menuId);
        MenuItemResponse created = menuItemService.createMenuItem(restaurantId,menuId,request);
        log.info("Created menu item with id: {}", created.id());
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public ResponseEntity<List<MenuItemResponse>> getMenuItems(
            @PathVariable Long restaurantId,
            @PathVariable Long menuId,
            @RequestParam (required = false) MenuCategory category
    ) {
        if (category != null) {
            log.info("Received request to get menu items for menuId: {} and category: {}", menuId, category);
            List<MenuItemResponse> items = menuItemService.getMenuItemsByCategory(restaurantId,menuId, category);
            return ResponseEntity.ok(items);
        }
        log.info("Received request to get menu items for menuId: {}", menuId);
        List<MenuItemResponse> items = menuItemService.getMenuItemsByMenuId(restaurantId,menuId);
        return ResponseEntity.ok(items);
    }


    @GetMapping("/{id}")
    public ResponseEntity<MenuItemResponse> getMenuItemById(
            @PathVariable Long restaurantId,
            @PathVariable Long menuId,
            @PathVariable Long id
    ) {

        log.info("Received request to get menu item with id: {}", id);
        MenuItemResponse response = menuItemService.getMenuItemById(restaurantId,menuId,id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/bulk")
    public ResponseEntity<List<MenuItemResponse>> getMenuItemsBulk(
            @PathVariable Long restaurantId,
            @PathVariable Long menuId,
            @RequestBody List<Long> ids
    ){
        log.info("Received request to get menu items in bulk for menuId: {} and ids: {}", menuId, ids);
        List<MenuItemResponse> items = menuItemService.getMenuItemsBulk(restaurantId,menuId, ids);
        return ResponseEntity.ok(items);
    }


    @PutMapping("/{id}")
    public ResponseEntity<MenuItemResponse> updateMenuItem(
            @PathVariable Long restaurantId,
            @PathVariable Long menuId,
            @PathVariable Long id,
            @RequestBody @Valid MenuItemUpdateRequest request
    ) {
        log.info("Received request to update menu item with id: {}", id);
        MenuItemResponse updated = menuItemService.updateMenuItem(restaurantId,menuId,id, request);
        log.info("Updated menu item with id: {}", updated.id());
        return ResponseEntity.ok(updated);
    }
    @PatchMapping("/{id}/availability")
    public ResponseEntity<Void> updateAvailability(
            @PathVariable Long restaurantId,
            @PathVariable Long menuId,
            @PathVariable Long id,
            @RequestParam boolean available
    ) {
        log.info("Setting availability of item: {} to {}", id, available);
        menuItemService.updateAvailability(restaurantId, menuId, id, available);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMenuItem(
            @PathVariable Long restaurantId,
            @PathVariable Long menuId,
            @PathVariable Long id
    ) {
        log.info("Received request to delete menu item with id: {}", id);
        menuItemService.deleteMenuItem(restaurantId,menuId,id);
        log.info("Deleted menu item with id: {}", id);
        return ResponseEntity.noContent().build();
    }




}
