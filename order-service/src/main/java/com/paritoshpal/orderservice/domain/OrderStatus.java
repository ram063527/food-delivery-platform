package com.paritoshpal.orderservice.domain;

public enum OrderStatus {
    CREATED,
    PAYMENT_PENDING,
    PAYMENT_COMPLETED,
    CONFIRMED,
    PREPARING,
    READY,
    OUT_FOR_DELIVERY,
    DELIVERED,
    CANCELLED,
    REFUNDED;


    public boolean canTransitionTo(OrderStatus newStatus) {
        return switch (this) {
            case CREATED -> newStatus == PAYMENT_PENDING || newStatus == CANCELLED;
            case PAYMENT_PENDING -> newStatus == PAYMENT_COMPLETED || newStatus == CANCELLED;
            case PAYMENT_COMPLETED -> newStatus == CONFIRMED || newStatus == CANCELLED;
            case CONFIRMED -> newStatus == PREPARING || newStatus == CANCELLED;
            case PREPARING -> newStatus == READY || newStatus == CANCELLED;
            case READY -> newStatus == OUT_FOR_DELIVERY || newStatus == CANCELLED;
            case OUT_FOR_DELIVERY -> newStatus == DELIVERED || newStatus == CANCELLED;
            case DELIVERED -> false; // No transitions allowed from DELIVERED
            case CANCELLED -> false; // No transitions allowed from CANCELLED
            case REFUNDED -> false; // No transitions allowed from REFUNDED
        };
    }
}


