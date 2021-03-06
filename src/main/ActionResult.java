package main;

public enum ActionResult {
    SUCCESS("Action was successful"), INCORRECT_PASSWORD("Password is incorrect! Try again"),
    INVALID_USERNAME("Username is invalid!"), INVALID_PASSWORD("Password is invalid!"),
    USERNAME_NOT_FOUND("Username is not found!"), USERNAME_ALREADY_EXIST("Username is already exist!"),
    FOOD_NOT_FOUND("Food is not found!"), FOOD_NOT_AVAILABLE("The food is not availavble too confirm."),
    FOOD_ALREADY_EXIST("Food is already exist!"), ORDER_NOT_FOUND("Order is not found!"),
    ORDER_ALREADY_EXIST("Order is already exist"), ORDER_NOT_MADE("Order has not been made!"),
    ORDER_NOT_CONFIRMED("Order is not confirmed yet!"), ORDER_NOT_COOKED("Order has not been cooked!"),
    ORDER_COOKED("You can't revoke your order because it has been cooked"),
    UNKNOWN_ERROR("Sorry sir there is an unknown error!!!"), ROLE_NOT_MATCH("Your selected role does not match"),
    NOT_MANAGER("Sorry you cannot edit or remove the Manager");

    String action;

    ActionResult(String action) {
        this.action = action;
    }

    @Override
    public String toString() {
        return action;
    }
}