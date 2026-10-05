package com.spareparts.modules.procurement.entity;

public enum OrderStatus {
    PENDING,
    APPROVED,
    RECEIVED,
    CANCELLED;

    // Allowed lifecycle:
    // PENDING -> APPROVED or CANCELLED
    // APPROVED -> RECEIVED or CANCELLED
    // RECEIVED and CANCELLED are final states
    public boolean canTransitionTo(OrderStatus next) {
        return switch (this) {
            case PENDING -> next == APPROVED || next == CANCELLED;
            case APPROVED -> next == RECEIVED || next == CANCELLED;
            case RECEIVED, CANCELLED -> false;
        };
    }
}
