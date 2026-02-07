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
    public MenuItemResponse createMenuItem(CreateMenuItemRequest request) {
        // 1. Validate menu exists
        MenuEntity menu = menuRepository.findById(request.menuId())
                .orElseThrow(() -> MenuNotFoundException.forId(request.menuId()));
        // 2. Map request to entity
        MenuItemEntity menuItem = menuItemMapper.toMenuItemEntity(request);
        menuItem.setMenu(menu);
        // 3. Save menu item
        MenuItemEntity savedMenuItem = menuItemRepository.save(menuItem);
        // 4. Map saved entity to response
        return menuItemMapper.toMenuItemResponse(savedMenuItem);
    }

    @Override
    public MenuItemResponse getMenuItemById(Long menuItemId) {
        return menuItemRepository.findById(menuItemId)
                .map(menuItemMapper::toMenuItemResponse)
                .orElseThrow(() -> MenuItemNotFoundException.forId(menuItemId));
    }

    @Override
    public MenuItemResponse updateMenuItem(Long menuItemId, MenuItemUpdateRequest request) {
        // 1. Fetch the existing menuItem
        MenuItemEntity existingMenuItem = menuItemRepository.findById(menuItemId)
                .orElseThrow(() -> MenuNotFoundException.forId(menuItemId));
        // 2. Update fields from request
        menuItemMapper.updateMenuItemFromRequest(request, existingMenuItem);
        // 3. Save updated menu item
        MenuItemEntity updatedMenuItem = menuItemRepository.save(existingMenuItem);
        // 4. Map updated entity to response
        return menuItemMapper.toMenuItemResponse(updatedMenuItem);
    }

    @Override
    public void deleteMenuItem(Long menuItemId) {
        if(!menuItemRepository.existsById(menuItemId)){
            throw MenuItemNotFoundException.forId(menuItemId);
        }
        menuItemRepository.deleteById(menuItemId);
    }

    @Override
    public List<MenuItemResponse> getMenuItemsByMenuId(Long menuId) {
        return menuItemRepository.findByMenuId(menuId).stream()
                .map(menuItemMapper::toMenuItemResponse)
                .toList();
    }

    @Override
    public List<MenuItemResponse> getMenuItemsByCategory(Long menuId, MenuCategory category) {
       return menuItemRepository.findByMenu_IdAndCategory(menuId,category).
                stream().map(menuItemMapper::toMenuItemResponse).toList();
    }

    @Override
    public void updateAvailability(Long menuItemId, boolean available) {
        MenuItemEntity menuItem = menuItemRepository.findById(menuItemId)
                .orElseThrow(() -> MenuItemNotFoundException.forId(menuItemId));
        menuItem.setAvailability(available);
        menuItemRepository.save(menuItem);
    }
}
