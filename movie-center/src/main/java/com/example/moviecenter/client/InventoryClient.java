package com.example.moviecenter.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name="inventory-service",
            fallback = InventoryClientFallBack.class)
public interface InventoryClient extends InventoryService{

}
