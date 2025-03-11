package br.com.ichin23.rest_spring_boot.request.converters;

public class NumberConverter {

    public static Double convertToDouble(String strNumber) {
        if(strNumber==null && strNumber.isEmpty()) throw new UnsupportedOperationException("Please set a numeric value!");

        String number = strNumber.replace(",", ".");
        return Double.parseDouble(number);
    }

    public static boolean isNumeric(String strNumber) {
        if(strNumber==null && strNumber.isEmpty()){return false;}

        String number = strNumber.replace(",", ".");
        return number.matches("[+-]?[0-9]*\\.?[0-9]+");
    }
}
