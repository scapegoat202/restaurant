package cn.varfunc.restaurant.domain.model;

public enum OrderStatus {
    SUBMITTED, CANCELLED, COMPLETED;

    /**
     * Parse status string into <code>OrderStatus</code> enum.
     *
     * @param status must not be null, value has to be
     *               one of <code>["submitted", "cancelled", "completed"]</code> (Ignore case)
     */
    public static OrderStatus parse(String status) {
        if (status == null) {
            throw new IllegalArgumentException("status must not be null!");
        }
        switch (status.trim().toLowerCase()) {
            case "submitted":
                return SUBMITTED;
            case "cancelled":
                return CANCELLED;
            case "completed":
                return COMPLETED;
            default:
                throw new IllegalArgumentException("Unmapped status!");
        }
    }
}
