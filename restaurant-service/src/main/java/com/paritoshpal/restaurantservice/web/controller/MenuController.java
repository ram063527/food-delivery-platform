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

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/restaurants/{restaurantId}/menus")
@RequiredArgsConstructor
public class MenuController {


    private static final Logger log = LoggerFactory.getLogger(MenuController.class);
    private final MenuService menuService;


    @PostMapping
    public ResponseEntity<MenuResponse> createMenu(
            @PathVariable Long restaurantId,
            @RequestBody @Valid CreateMenuRequest request
    ) {
        log.info("Received Request for adding ");
        MenuResponse createdMenu = menuService.createMenu(restaurantId,request);
        log.info("Created menu with id: {}", createdMenu.id());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMenu);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MenuResponse> getMenuById(
            @PathVariable Long restaurantId,
            @PathVariable Long id
    ) {
        log.info("Received request to get menu with id: {}", id);
        MenuResponse menu = menuService.getMenuById(restaurantId,id);
        return ResponseEntity.ok(menu);
    }

    @GetMapping
    public ResponseEntity<List<MenuSummaryResponse>> getMenusByRestaurantId(
            @PathVariable Long restaurantId
    ) {
        log.info("Received request to get menus for restaurantId: {}", restaurantId);
        List<MenuSummaryResponse> menus = menuService.getMenusByRestaurantId(restaurantId);
        return ResponseEntity.ok(menus);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MenuResponse> updateMenu(
            @PathVariable Long restaurantId,
            @PathVariable Long id,
            @RequestBody @Valid MenuUpdateRequest request
    ) {

        log.info("Received request to update menu with id: {}", id);
        MenuResponse updatedMenu = menuService.updateMenu(restaurantId,id, request);
        log.info("Updated menu with id: {}", updatedMenu.id());
        return ResponseEntity.ok(updatedMenu);
    }

    @PatchMapping("/{id}/prices")
    public ResponseEntity<Void> updateMenuPrices(
            @PathVariable Long restaurantId,
            @PathVariable Long id,
            @RequestParam BigDecimal percentage
            ){
        log.info("Received request to update menu prices for menuId: {} with percentage: {}", id, percentage);
        menuService.updateAllPricesInMenu(restaurantId,id, percentage);
        log.info("Updated menu prices for menuId: {} with percentage: {}", id, percentage);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMenuById(
            @PathVariable Long id
    ) {
        log.info("Received request to delete menu with id: {}", id);
        menuService.deleteMenuById(id);
        log.info("Deleted menu with id: {}", id);
        return ResponseEntity.noContent().build();
    }

}
