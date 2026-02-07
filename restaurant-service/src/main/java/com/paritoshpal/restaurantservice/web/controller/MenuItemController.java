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
@RequestMapping("/api/menu-items")
@RequiredArgsConstructor
public class MenuItemController {

    private static final Logger log = LoggerFactory.getLogger(MenuItemController.class);
    private final MenuItemService menuItemService;

    @PostMapping
    public ResponseEntity<MenuItemResponse> createMenuItem(
            @RequestBody @Valid CreateMenuItemRequest request
    ) {
        log.info("Received request to create menu item for menuId: {}", request.menuId());
        MenuItemResponse created = menuItemService.createMenuItem(request);
        log.info("Created menu item with id: {}", created.id());
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{menuItemId}")
    public ResponseEntity<MenuItemResponse> getMenuItemById(
            @PathVariable Long menuItemId
    ) {

        log.info("Received request to get menu item with id: {}", menuItemId);
        MenuItemResponse response = menuItemService.getMenuItemById(menuItemId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{menuItemId}")
    public ResponseEntity<MenuItemResponse> updateMenuItem(
            @PathVariable Long menuItemId,
            @RequestBody @Valid MenuItemUpdateRequest request
    ) {
        log.info("Received request to update menu item with id: {}", menuItemId);
        MenuItemResponse updated = menuItemService.updateMenuItem(menuItemId, request);
        log.info("Updated menu item with id: {}", updated.id());
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{menuItemId}")
    public ResponseEntity<Void> deleteMenuItem(
            @PathVariable Long menuItemId
    ) {
        log.info("Received request to delete menu item with id: {}", menuItemId);
        menuItemService.deleteMenuItem(menuItemId);
        log.info("Deleted menu item with id: {}", menuItemId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/menu/{menuId}")
    public ResponseEntity<List<MenuItemResponse>> getMenuItemsByMenuId(
            @PathVariable Long menuId
    ) {
        log.info("Received request to get menu items for menuId: {}", menuId);
        List<MenuItemResponse> items = menuItemService.getMenuItemsByMenuId(menuId);
        return ResponseEntity.ok(items);
    }

    @GetMapping("/menu/{menuId}/category/{category}")
    public ResponseEntity<List<MenuItemResponse>> getMenuItemsByCategory(
            @PathVariable Long menuId,
            @PathVariable MenuCategory category
    ) {
        log.info("Received request to get menu items for menuId: {} and category: {}", menuId, category);
        List<MenuItemResponse> items = menuItemService.getMenuItemsByCategory(menuId, category);
        return ResponseEntity.ok(items);
    }

    @PatchMapping("/{menuItemId}/availability")
    public ResponseEntity<Void> updateAvailability(
            @PathVariable Long menuItemId,
            @RequestParam boolean available
    ) {
        log.info("Received request to update availability of menu item with id: {} to {}", menuItemId, available);
        menuItemService.updateAvailability(menuItemId, available);
        log.info("Updated availability of menu item with id: {} to {}", menuItemId, available);
        return ResponseEntity.noContent().build();
    }
}
