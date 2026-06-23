package com.example.Food.Delivery.Platform.Utils;

import com.example.Food.Delivery.Platform.Entities.Customer;
import com.example.Food.Delivery.Platform.Exceptions.ResourceNotFoundException;
import com.example.Food.Delivery.Platform.Repositories.CustomerRepository;

import java.time.LocalTime;
import java.util.UUID;

public class HelperUtils {

    public static String generateCode(String prefix) {
        return generateCode(prefix, 4);
    }


    public static String generateCode(String prefix, int length) {
        if (prefix == null || prefix.trim().isEmpty()) {
            prefix = "CUST";
        }

        String uuidPart = UUID.randomUUID()
                    .toString().replace("-","").substring(0,8).toUpperCase();

        return prefix + "-" + uuidPart;
    }


    public static double calculateDistance(double lat1, double lng1, double lat2, double lng2) {
        //Radious of earth in km
        final int R = 6371;
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lng2 - lng1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }

    public static double calculateTotal(double subtotal, double fee){
        return subtotal + fee;
    }

    public static double calculateTotal(double subtotal, double fee, double discount) {
        return(subtotal + fee) - discount;
    }

    public static String formatCurrency(double amount, String currencyCode){
        return String.format("%.3f %s", amount, currencyCode.toUpperCase());
    }

    public static String formatCurrency(double amount) {
        return formatCurrency(amount, "OMR"); // العملة الافتراضية للريال العماني
    }

    public static boolean isBusinessOpen(String openTime, String closeTime) {
        LocalTime now = LocalTime.now();

        LocalTime open = LocalTime.parse(openTime);
        LocalTime close = LocalTime.parse(closeTime);

        // normal case (same day)
        if (open.isBefore(close)) {
            return now.isAfter(open) && now.isBefore(close);
        }

        // overnight case (e.g. 22:00 - 02:00)
        return now.isAfter(open) || now.isBefore(close);
    }

    public static Customer findActiveCustomer(Integer id) {

        CustomerRepository customerRepository = null;

        return customerRepository.getByActiveId(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Customer not found with id: " + id
                        ));
    }
}
