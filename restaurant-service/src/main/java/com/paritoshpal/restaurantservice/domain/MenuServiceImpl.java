package com.paritoshpal.restaurantservice.domain;

import com.paritoshpal.restaurantservice.domain.exceptions.MenuNotFoundException;
import com.paritoshpal.restaurantservice.domain.exceptions.RestaurantNotFoundException;
import com.paritoshpal.restaurantservice.domain.mapper.MenuItemMapper;
import com.paritoshpal.restaurantservice.domain.mapper.MenuMapper;
import com.paritoshpal.restaurantservice.domain.models.CreateMenuRequest;
import com.paritoshpal.restaurantservice.domain.models.MenuResponse;
import com.paritoshpal.restaurantservice.domain.models.MenuSummaryResponse;
import com.paritoshpal.restaurantservice.domain.models.MenuUpdateRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService{

    private final MenuRepository menuRepository;
    private final RestaurantRepository restaurantRepository;
    private final MenuMapper menuMapper;
    private final MenuItemMapper menuItemMapper;


    @Override
    public MenuResponse createMenu(Long restaurantId, CreateMenuRequest createMenuRequest) {
        // 1. Validate restaurant exists
        RestaurantEntity restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> RestaurantNotFoundException.forId(restaurantId));

        // 2. Map request to entity
        MenuEntity menuEntity = menuMapper.toMenuEntity(createMenuRequest);
        menuEntity.setRestaurant(restaurant);

        // Handle Bulk
        if(createMenuRequest.items()!=null){
            createMenuRequest.items().forEach( item -> {
                MenuItemEntity menuItemEntity = menuItemMapper.toMenuItemEntity(item);
                menuItemEntity.setMenu(menuEntity);
                menuEntity.getMenuItems().add(menuItemEntity);
            });
        }
        // 3. Save menu
        MenuEntity savedMenu = menuRepository.save(menuEntity);
        // 4. Map saved entity to response
        return menuMapper.toMenuResponse(savedMenu);

    }

    @Override
    public List<MenuSummaryResponse> getMenusByRestaurantId(Long restaurantId) {
        // 1. validate Restaurant
        if(!restaurantRepository.existsById(restaurantId)){
            throw RestaurantNotFoundException.forId(restaurantId);
        }
        // 2. Fetch menus for restaurant
        List<MenuEntity> menuEntities = menuRepository.findByRestaurantId(restaurantId);
        // 3. Map to summary response
        return  menuEntities.stream().map(menuMapper::toMenuSummaryResponse).toList();
    }

    @Override
    public void deleteMenuById(Long menuId) {
        // 1. Validate menu exists
        if(!menuRepository.existsById(menuId)){
            throw MenuNotFoundException.forId(menuId);
        }
        // 2. Delete menu
        menuRepository.deleteById(menuId);
    }

    @Override
    public MenuResponse getMenuById(Long menuId) {
        // 1. Fetch menu by ID
        MenuEntity menuEntity = menuRepository.findById(menuId)
                .orElseThrow(() -> MenuNotFoundException.forId(menuId));
        // 2. Map to response
        return menuMapper.toMenuResponse(menuEntity);
    }

    @Override
    public MenuResponse updateMenu(Long menuId, MenuUpdateRequest updateMenuRequest) {
        MenuEntity menu = menuRepository.findById(menuId)
                .orElseThrow(()-> MenuNotFoundException.forId(menuId));
        menuMapper.updateMenuEntityFromRequest(updateMenuRequest, menu);
        return menuMapper.toMenuResponse(menuRepository.save(menu));
    }

    @Override
    public void updateAllPricesInMenu(Long menuId, BigDecimal percentageIncrease) {
        MenuEntity menu = menuRepository.findById(menuId)
                .orElseThrow(()-> MenuNotFoundException.forId(menuId));

        BigDecimal multiplier  = BigDecimal.ONE.add(percentageIncrease.divide(BigDecimal.valueOf(100),2, RoundingMode.HALF_UP));

        menu.getMenuItems().forEach(item -> {
            BigDecimal currentPrice = item.getPrice();
            BigDecimal increaseAmount = currentPrice.multiply(multiplier);
            item.setPrice(increaseAmount);
        });
        menuRepository.save(menu);
    }
}
