package cn.varfunc.restaurant.domain.model;

import org.springframework.lang.NonNull;

public enum CommodityStatus {
    NORMAL, NOT_SELLING, DELETED, UNKNOWN;

    public static CommodityStatus parse(@NonNull String status) {
        switch (status.trim().toLowerCase()) {
            case "normal":
                return NORMAL;
            case "notselling":
            case "not-selling":
            case "not_selling":
                return NOT_SELLING;
            case "deleted":
                return DELETED;
            default:
                return UNKNOWN;
        }
    }
}
