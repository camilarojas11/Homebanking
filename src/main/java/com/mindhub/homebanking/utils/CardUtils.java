package com.mindhub.homebanking.utils;

public final class CardUtils {
    private CardUtils(){}
    public static String getCardNumber(int min, int max) {
        return ("8888-"+(int)((Math.random() * (max - min)) + min)
                +"-"+(int)((Math.random() * (max - min)) + min)
                +"-"+(int)((Math.random() * (max - min)) + min));
    }

    public static int getCVVNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    } }
