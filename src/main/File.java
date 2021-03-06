package main;

import utils.FileManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class File {
    private static final String DATA_FILE_PATH_USER = "../resources/users.txt";
    private static final String DATA_FILE_PATH_FOOD = "../resources/foods.txt";
    private static final String DATA_FILE_PATH_ORDER = "../resources/orders.txt";

    public Restaurant read(Restaurant restaurant) throws ParseException {
        FileManager fileManager = new FileManager(DATA_FILE_PATH_USER);

        String content = fileManager.readAll();
        String[] us = content.split("\n");

        for (int i = 0; i < us.length; i++) {
            String[] line = us[i].split(";");

            Date registrationDate = new SimpleDateFormat("dd/MM/yyyy").parse(line[6]);
            Date lastLoginDate = new SimpleDateFormat("dd/MM/yyyy").parse(line[7]);

            switch (line[5]) {
                case "MA" -> restaurant.users.add(new Manager(line[0], line[1], line[2], line[3], line[4],
                        AccessLevel.MANAGER, registrationDate, lastLoginDate));

                case "CA" -> restaurant.users.add(new Cashier(line[0], line[1], line[2], line[3], line[4],
                        AccessLevel.CASHIER, registrationDate, lastLoginDate));

                case "CH" -> restaurant.users.add(new Chef(line[0], line[1], line[2], line[3], line[4],
                        AccessLevel.CHEF, registrationDate, lastLoginDate));

                case "DE" -> restaurant.users.add(new DeliveryMan(line[0], line[1], line[2], line[3], line[4],
                        AccessLevel.DELIVERYMAN, registrationDate, lastLoginDate));

                default -> restaurant.users.add(new Client(line[0], line[1], line[2], line[3], line[4],
                        AccessLevel.CLIENT, registrationDate, lastLoginDate, line[8]));
            }
        }

        fileManager = new FileManager(DATA_FILE_PATH_FOOD);

        content = fileManager.readAll();
        us = content.split("\n");

        for (int i = 0; i < us.length; i++) {
            String[] line = us[i].split(";");
            String[] ingredients = line[2].split("-");

            boolean isAvailable;

            if (line[3].equals("Yes"))
                isAvailable = true;
            else
                isAvailable = false;

            restaurant.menu.add(
                    new Food(Integer.parseInt(line[0]), line[1], ingredients, isAvailable, Integer.parseInt(line[4])));
        }

        fileManager = new FileManager(DATA_FILE_PATH_ORDER);

        content = fileManager.readAll();
        us = content.split("\n");

        for (int i = 0; i < us.length; i++) {
            String[] line = us[i].split(";");

            OrderState state;
            Date orderDate = new SimpleDateFormat("dd/MM/yyyy").parse(line[5]);

            state = switch (line[4]) {
                case "MA" -> OrderState.MADE;
                case "CN" -> OrderState.CONFIRMED;
                case "CO" -> OrderState.COOKED;
                default -> OrderState.DELIVERED;
            };

            restaurant.orders.add(new Order(line[0], line[1], Integer.parseInt(line[2]), Integer.parseInt(line[3]),
                    state, orderDate, line[6]));
        }
        return restaurant;
    }

    public void save(Restaurant restaurant) {
        FileManager fileManager = new FileManager(DATA_FILE_PATH_USER);

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        String users = "";
        String regdate, logdate;

        for (int i = 0; i < restaurant.users.size(); i++) {
            String acc;
            AccessLevel acclvl = restaurant.users.get(i).accessLevel;

            if (acclvl == AccessLevel.MANAGER)
                acc = "MA";
            else if (acclvl == AccessLevel.CHEF)
                acc = "CH";
            else if (acclvl == AccessLevel.CASHIER)
                acc = "CA";
            else if (acclvl == AccessLevel.DELIVERYMAN)
                acc = "DE";
            else
                acc = "CL";

            regdate = formatter.format(restaurant.users.get(i).registrationDate);
            logdate = formatter.format(restaurant.users.get(i).lastLoginDate);

            users += restaurant.users.get(i).firstName + ";" + restaurant.users.get(i).lastName + ";"
                    + restaurant.users.get(i).phoneNumber + ";" + restaurant.users.get(i).username + ";"
                    + restaurant.users.get(i).password + ";" + acc + ";" + regdate + ";" + logdate;

            if (acclvl == AccessLevel.CLIENT) {
                Client client = (Client) restaurant.users.get(i);
                users += ";" + client.address;
            }
            if (i != restaurant.users.size() - 1)
                users += "\n";
        }
        fileManager.write(users, false);

        fileManager = new FileManager(DATA_FILE_PATH_FOOD);

        String isAvailable;
        String menu = "";

        for (int i = 0; i < restaurant.menu.size(); i++) {
            String ingredients = "";

            if (restaurant.menu.get(i).isAvailable)
                isAvailable = "Yes";
            else
                isAvailable = "No";

            for (int j = 0; j < restaurant.menu.get(i).ingredients.length; j++) {
                ingredients += restaurant.menu.get(i).ingredients[j];

                if (j != restaurant.menu.get(i).ingredients.length - 1)
                    ingredients += "-";
            }
            menu += restaurant.menu.get(i).id + ";" + restaurant.menu.get(i).name + ";" + ingredients + ";"
                    + isAvailable + ";" + restaurant.menu.get(i).price;

            if (i != restaurant.menu.size() - 1)
                menu += "\n";
        }
        fileManager.write(menu, false);

        fileManager = new FileManager(DATA_FILE_PATH_ORDER);

        String orders = "";
        OrderState orderState;
        String oState;
        String oDate;

        for (int i = 0; i < restaurant.orders.size(); i++) {
            orderState = restaurant.orders.get(i).state;

            if (orderState == OrderState.MADE)
                oState = "MA";
            else if (orderState == OrderState.CONFIRMED)
                oState = "CN";
            else if (orderState == OrderState.COOKED)
                oState = "CO";
            else
                oState = "DE";

            oDate = formatter.format(restaurant.orders.get(i).orderDate);

            orders += restaurant.orders.get(i).id + ";" + restaurant.orders.get(i).username + ";"
                    + restaurant.orders.get(i).foodId + ";" + restaurant.orders.get(i).numbers + ";" + oState + ";"
                    + oDate + ";" + restaurant.orders.get(i).address;

            if (i != restaurant.orders.size() - 1)
                orders += "\n";
        }
        fileManager.write(orders, false);
    }
}
