package com.paritoshpal.restaurantservice.web.controller;

import com.paritoshpal.restaurantservice.domain.MenuService;
import com.paritoshpal.restaurantservice.domain.models.CreateMenuRequest;
import com.paritoshpal.restaurantservice.domain.models.MenuResponse;
import com.paritoshpal.restaurantservice.domain.models.MenuSummaryResponse;
import com.paritoshpal.restaurantservice.domain.models.MenuUpdateRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menus")
@RequiredArgsConstructor
public class MenuController {

    private static final Logger log = LoggerFactory.getLogger(MenuController.class);
    private final MenuService menuService;




    @PostMapping
    public ResponseEntity<MenuResponse> createMenu(
            @RequestBody @Valid CreateMenuRequest request
    ) {
        log.info("Received request to create menu for restaurantId: {}", request.restaurantId());
        MenuResponse createdMenu = menuService.createMenu(request);
        log.info("Created menu with id: {}", createdMenu.id());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMenu);
    }

    @GetMapping("/{menuId}")
    public ResponseEntity<MenuResponse> getMenuById(
            @PathVariable Long menuId
    ) {
        log.info("Received request to get menu with id: {}", menuId);
        MenuResponse menu = menuService.getMenuById(menuId);
        return ResponseEntity.ok(menu);
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<MenuSummaryResponse>> getMenusByRestaurantId(
            @PathVariable Long restaurantId
    ) {
        log.info("Received request to get menus for restaurantId: {}", restaurantId);
        List<MenuSummaryResponse> menus = menuService.getMenusByRestaurantId(restaurantId);
        return ResponseEntity.ok(menus);
    }

    @PutMapping("/{menuId}")
    public ResponseEntity<MenuResponse> updateMenu(
            @PathVariable Long menuId,
            @RequestBody @Valid MenuUpdateRequest request
    ) {

        log.info("Received request to update menu with id: {}", menuId);
        MenuResponse updatedMenu = menuService.updateMenu(menuId, request);
        log.info("Updated menu with id: {}", updatedMenu.id());
        return ResponseEntity.ok(updatedMenu);
    }

    @DeleteMapping("/{menuId}")
    public ResponseEntity<Void> deleteMenuById(
            @PathVariable Long menuId
    ) {
        log.info("Received request to delete menu with id: {}", menuId);
        menuService.deleteMenuById(menuId);
        log.info("Deleted menu with id: {}", menuId);
        return ResponseEntity.noContent().build();
    }

}
