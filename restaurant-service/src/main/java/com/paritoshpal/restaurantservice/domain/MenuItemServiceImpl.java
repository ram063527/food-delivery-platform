package com.paritoshpal.restaurantservice.domain;

import com.paritoshpal.restaurantservice.domain.exceptions.MenuItemNotFoundException;
import com.paritoshpal.restaurantservice.domain.exceptions.MenuNotFoundException;
import com.paritoshpal.restaurantservice.domain.mapper.MenuItemMapper;
import com.paritoshpal.restaurantservice.domain.models.CreateMenuItemRequest;
import com.paritoshpal.restaurantservice.domain.models.MenuItemResponse;
import com.paritoshpal.restaurantservice.domain.models.MenuItemUpdateRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MenuItemServiceImpl implements  MenuItemService {

    private final MenuItemRepository menuItemRepository;
    private final MenuRepository menuRepository;
    private final MenuItemMapper menuItemMapper;


    @Override
    public MenuItemResponse createMenuItem(
            Long restaurantId,
            Long menuId,
            CreateMenuItemRequest request) {
        // 1. Validate menu exists for the given restaurant
        MenuEntity menu = menuRepository.findByIdAndRestaurantId(menuId, restaurantId)
                .orElseThrow(() -> MenuNotFoundException.forRestaurant(restaurantId, menuId));
        // 2. Map request to entity
        MenuItemEntity menuItem = menuItemMapper.toMenuItemEntity(request);
        menuItem.setMenu(menu);
        // 3. Save menu item
        MenuItemEntity savedMenuItem = menuItemRepository.save(menuItem);
        // 4. Map saved entity to response
        return menuItemMapper.toMenuItemResponse(savedMenuItem);
    }

    @Override
    public MenuItemResponse getMenuItemById(
            Long restaurantId,
            Long menuId,
            Long id
    ) {
        // 1. Validate menu exists for the given restaurant
        if(!menuRepository.existsByIdAndRestaurantId(menuId, restaurantId)){
            throw MenuNotFoundException.forRestaurant(restaurantId, menuId);
        }
        // 2. Fetch menu item by id and menu id
        MenuItemEntity menuItem = menuItemRepository.findByIdAndMenu_Id(id, menuId)
                .orElseThrow(() -> MenuItemNotFoundException.forId(id));
        // 3. Map entity to response
        return menuItemMapper.toMenuItemResponse(menuItem);
    }

    @Override
    public MenuItemResponse updateMenuItem(
            Long restaurantId,
            Long menuId,
            Long id,
            MenuItemUpdateRequest request) {
        // 1. Validate menu exists for the given restaurant
        if(!menuRepository.existsByIdAndRestaurantId(menuId, restaurantId)){
            throw MenuNotFoundException.forRestaurant(restaurantId, menuId);
        }
        // 2a. Fetch existing menu item
        MenuItemEntity existingMenuItem = menuItemRepository.findByIdAndMenu_Id(id, menuId)
                .orElseThrow(() -> MenuItemNotFoundException.forId(id));
        // 2b. Update fields from request
        menuItemMapper.updateMenuItemFromRequest(request, existingMenuItem);
        // 3. Save updated menu item
        MenuItemEntity updatedMenuItem = menuItemRepository.save(existingMenuItem);
        // 4. Map updated entity to response
        return menuItemMapper.toMenuItemResponse(updatedMenuItem);
    }

    @Override
    public void deleteMenuItem(
            Long restaurantId,
            Long menuId,
            Long id) {
            // 1. Validate menu exists for the given restaurant
        if(!menuRepository.existsByIdAndRestaurantId(menuId, restaurantId)){
            throw MenuNotFoundException.forRestaurant(restaurantId, menuId);
        }
        // 2. Validate menu item exists for the given menu
        MenuItemEntity menuItem = menuItemRepository.findByIdAndMenu_Id(id, menuId)
                .orElseThrow(() -> MenuItemNotFoundException.forId(id));
        // 3. Delete menu item
        menuItemRepository.delete(menuItem);
    }

    @Override
    public void updateAvailability(
            Long restaurantId,
            Long menuId,
            Long id,
            boolean available
    ) {
        // 1. Validate menu exists for the given restaurant
        if(!menuRepository.existsByIdAndRestaurantId(menuId, restaurantId)){
            throw MenuNotFoundException.forRestaurant(restaurantId, menuId);
        }
        // 2. Fetch menu item by id and menu id
        MenuItemEntity menuItem = menuItemRepository.findByIdAndMenu_Id(id, menuId)
                .orElseThrow(() -> MenuItemNotFoundException.forId(id));
        // 3. Update availability
        menuItem.setAvailability(available);
        // 4. Save updated menu item
        menuItemRepository.save(menuItem);
    }

    @Override
    public List<MenuItemResponse> getMenuItemsByMenuId(
            Long restaurantId,
            Long menuId
    ) {
        // 1. Validate menu exists for the given restaurant
        if(!menuRepository.existsByIdAndRestaurantId(menuId, restaurantId)){
            throw MenuNotFoundException.forRestaurant(restaurantId, menuId);
        }
        // 2. Fetch menu items by menu id
        return menuItemRepository.findByMenuId(menuId)
                .stream().map(menuItemMapper::toMenuItemResponse).toList();

    }

    @Override
    public List<MenuItemResponse> getMenuItemsByCategory(
            Long restaurantId,
            Long menuId,
            MenuCategory category) {
        // 1. Validate menu exists for the given restaurant
        if(!menuRepository.existsByIdAndRestaurantId(menuId, restaurantId)) {
            throw MenuNotFoundException.forRestaurant(restaurantId, menuId);
        }
        // 2. Fetch menu items by menu id and category
        return menuItemRepository.findByMenu_IdAndCategory(menuId, category)
                .stream().map(menuItemMapper::toMenuItemResponse).toList();


    }

    private boolean validateMenuBelongsToRestaurant(Long restaurantId, Long menuId) {
        return menuRepository.existsByIdAndRestaurantId(menuId, restaurantId);
    }




}
