package main;

public enum ActionResult {
    SUCCESS("Action was successful"),
    INVALID_USERNAME("Username is invalid!"),
    INVALID_PASSWORD("Password is invalid!"),
    USERNAME_NOT_FOUND("Username is not found!"),
    USERNAME_ALREADY_EXIST("Username is already exist!"),
    FOOD_NOT_FOUND("Food is not found!"),
    FOOD_ALREADY_EXIST("Food is already exist!"),
    ORDER_NOT_FOUND("Order is not found!"),
    ORDER_ALREADY_EXIST("Order is already exist"),
    UNKNOWN_ERROR("Sorry sir there is an unknown error!!!");

    String action;

    ActionResult(String action) {
        this.action = action;
    }

    @Override
    public String toString() {
        return action;
    }
}