package cn.varfunc.restaurant.domain.model;

public enum Gender {
    UNKNOWN, MALE, FEMALE;

    /**
     * Parse a <code>gender</code> string into a <code>gender</code> enum.
     *
     * @param gender it has to be one of <code>["man", "male", "woman", "female"]</code>,
     *               otherwise the return value would be <code>UNKNOWN</code>.
     */
    public static Gender parse(String gender) {
        if (gender == null) {
            return UNKNOWN;
        }
        switch (gender.trim().toLowerCase()) {
            case "man":
            case "male":
                return MALE;
            case "woman":
            case "female":
                return FEMALE;
            default:
                return UNKNOWN;
        }
    }
}
