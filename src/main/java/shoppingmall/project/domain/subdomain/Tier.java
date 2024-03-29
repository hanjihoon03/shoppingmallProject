package shoppingmall.project.domain.subdomain;

public enum Tier {
    BRONZE(1), SILVER(2), GOLD(3);

    private final int value;

    Tier(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
