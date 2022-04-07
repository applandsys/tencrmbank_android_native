package com.example.a10crmbank;

public class URLs {
    private static final String ROOT_URL = "http://192.168.0.103/tencrmbank/";
    public static final String URL_REGISTER = ROOT_URL + "api/signup";
    public static final String URL_LOGIN= ROOT_URL + "api/login";
    public static final String ACCOUNT_INFO= ROOT_URL + "api/account";
    public static final String LOGOUT= ROOT_URL + "api/logout";
    public static final String OFFER= ROOT_URL + "api/offer";
    public static final String WITHDRAAW = ROOT_URL + "api/withdrawtpg";
    public static final String GIFT_TPG = ROOT_URL + "api/gifttpg";
    public static final String TRANSFER = ROOT_URL + "api/transfer";
    public static final String CHANGEPASS = ROOT_URL + "api/changePass";

    public static final String VIPPACKAGE = ROOT_URL + "api/vip_pakcage";
    public static final String GULLACK_PACKAGE = ROOT_URL + "api/gullack_package";
    public static final String RUPICARD_PACKAGE = ROOT_URL + "api/rupicard_package";
// Version 2//
    public static final String PAYMENT_INSTRUCTION = ROOT_URL + "api/v2/payment_instuction";
    public static final String PAYMENT_CONRIFM = ROOT_URL + "api/v2/payment_confirm";
}
