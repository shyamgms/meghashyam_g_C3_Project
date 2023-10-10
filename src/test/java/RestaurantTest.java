import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {
    Restaurant restaurant;
    LocalTime openingTime;
    LocalTime closingTime;


    @BeforeEach
    public void setUp() {
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant = new Restaurant("Amelie's cafe", "Chennai", openingTime, closingTime);
        restaurant.addToMenu("Sweet corn soup", 119);
        restaurant.addToMenu("Vegetable lasagne", 269);
    }

    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time() {
        LocalTime currentTimeWithinOpeningHours = LocalTime.parse("12:30:00");
        restaurant.setCurrentTime(currentTimeWithinOpeningHours);

        assertTrue(restaurant.isRestaurantOpen());
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time() {
        LocalTime currentTimeOutsideOpeningHours = LocalTime.parse("09:00:00");
        restaurant.setCurrentTime(currentTimeOutsideOpeningHours);

        assertFalse(restaurant.isRestaurantOpen());
    }

    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1() {
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie", 319);
        assertEquals(initialMenuSize + 1, restaurant.getMenu().size());
    }

    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws ItemNotFoundException {
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize - 1, restaurant.getMenu().size());
    }

    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
        assertThrows(ItemNotFoundException.class,
                () -> restaurant.removeFromMenu("French fries"));
    }

    @Test
    public void calculate_order_value_for_items_in_menu() {
        // Add items to the menu
        restaurant.addToMenu("Sweet corn soup", 119);
        restaurant.addToMenu("Vegetable lasagne", 269);
        restaurant.addToMenu("Sizzling brownie", 319);

        // Calculate the order value for selected items
        int orderValue = restaurant.calculateOrderValue("Sweet corn soup", "Vegetable lasagne");

        // Assert that the order value is correct
        assertEquals(119 + 269, orderValue);
    }

    @Test
    public void calculate_order_value_for_items_not_in_menu() {

        int orderValue = restaurant.calculateOrderValue("French fries", "Burger");

        assertEquals(0, orderValue);
    }

    @Test
    public void calculate_order_value_for_empty_order() {

        int orderValue = restaurant.calculateOrderValue();

        assertEquals(0, orderValue);
    }
}

