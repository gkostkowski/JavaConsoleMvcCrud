package com.kostkowski.view;

import com.kostkowski.model.IShop;

import java.util.*;

/**
 * Created by Grzegorz Kostkowski on 2017-03-04.
 */
public class View {

    public static final String DATA_CONTAINER_SETUP = "Wskaz mechanizm persystencji danych (bd/xml): \n";
    public static final String SETUP_FAIL = "Niepowodzenie operacji. Sprobuj ponownie.";
    public static final String SETUP_SUCC = "Mechanizm persystencji danych zostal pomyslnie ustawiony.";
    public static final String EXIT = "exit";
    public static final String ASSIGN_SUCCESS_MSG = "Pomyślnie przydzielono sprzedawce.";
    public static final String RETRACT_SUCCESS_MSG = "Pomyślnie odwolano pracownika";
    //Collection<Shelf> rememberedShelves;
    private IShop model;
    Scanner sc = new Scanner(System.in);

    private static final String START_MSG = "\tAplikacja obslugujaca polki sklepowe oraz produkty znajdujace " +
            "sie na nich.\n\tAby wyswietlic liste dostepnych polecen wpisz '?'.";
    public static final String INVALID_CMD_MSG = "Niepoprawne polecenie. Sprobuj ponownie.";
    public static final String INVALID_DATA_MSG = "Niepoprawne dane. Sprobuj ponownie.";
    public static final String INVALID_ID_MSG = "Niepoprawny identyfikator. Sprobuj ponownie.";
    public static final String ADD_SUCCESS_MSG = "Wpis pomyslnie dodany.";
    public static final String UPDATE_SUCCESS_MSG = "Wpis pomyslnie zmieniony.";
    public static final String DELETE_SUCCESS_MSG = "Nastepujacy wpis zostal usuniety:\n";
    public static final String PROMPT_MSG = "Wprowadz polecenie: \n > ";
    private static final String [][] cmds = {
            {"","Uwaga:","Aby dodac nazwe skladajaca sie z wielu wyrazow uzyj znaku _ zamiast spacji."},
            {"cs","cs id_polki pojemnosc kategoria","Dodaje nowa polke sklepowa"},
            {"rs","rs id_polki","Wyswietla polke o zadanym numerze"},
            {"us","us id_polki nowa_pojemnosc nowa_kategoria","Aktualizuje parametry polki o zadanym numerze"},
            {"ds","ds id_polki","Usuwa polke o zadanym numerze"},
            {"cp","cp id_produktu cena nazwa_produktu id_polki","Dodaje nowy produkt na polke o zadanym numerze"},
            {"rp","rp id_produktu id_polki","Wyswietla dany identyfikatorem produkt z polki o zadanym numerze"},
            {"up","up id_produktu id_polki nowa_cena nowa_nazwa_produktu","Aktualizuje produkt z polki o zadanym numerze"},
            {"dp","dp id_produktu id_polki","Usuwa zadany produkt z okreslonej numerem polki"},
            {"cw","cw id_sprzedawcy imie nazwisko","Dodaje nowego sprzedawce"},
            {"rw","rw id_sprzedawcy","Wyswietla sprzedawce o zadanym numerze"},
            {"dw","dw id_sprzedawcy","Zwalnia sprzedawce o zadanym id"},
            {"asgn","asgn id_sprzedawcy id_stoiska","Przypisuje sprzedawce do stoiska o zadanym id"},
            {"wrtr","wrtr id_sprzedawcy","Odwoluje sprzedawce o zadanym id ze stoiska"},
            {"trtr","trtr id_stoiska","Odwoluje sprzedawce ze stoiska o zadanym id"},
    };


    private static String HELP_MSG = "Lista dostepnych polecen wraz ze skladnia:\n";
    private static boolean MULTI_WORDS_MODE = true;

    private static final int SYNTAX_CMD_LEN = 60;

    public View(IShop model) {
        this.model = model;
    }


    public void printlnToScreen(String s) {
        System.out.println(s);
    }
    public void printToScreen(String s) {
        System.out.print(s);
    }
    void fixedPrint(String s, int len) {
        StringBuilder fixed= new StringBuilder(s);
        for(int i=0; i<len-s.length();i++) fixed.append(" ");
        System.out.print(fixed);
    }

    public void printHelp(String cmd) {
        printlnToScreen(HELP_MSG);
        for (String [] s: cmds) {
            if(cmd == null || cmd.equals(s[0])){ // show all cmds help when null
                fixedPrint(s[1], SYNTAX_CMD_LEN);
                printlnToScreen(s[2]);
            }

        }
    }

    public void initPrint() {
        printlnToScreen(START_MSG);
    }

    public void printPromptSign() {
        printToScreen(PROMPT_MSG);
    }
    public void printInvalidCmdWarn() {printlnToScreen(INVALID_CMD_MSG);}

    public void printFeedback(boolean actionRes, String onSuccess, String onFailure) {
        printlnToScreen(actionRes ? onSuccess : onFailure);
    }
    public void printFeedbackEx(Object actionRes, String onFailure) {
        printlnToScreen(actionRes != null ? actionRes.toString() : onFailure);
    }

    public String readLine() {
        String res = sc.nextLine();
        if (MULTI_WORDS_MODE)
            return res.replaceAll("_"," ");
        return res;
    }

    public String readLine(boolean replaceUnderscores) {
        String res = sc.nextLine();
        if (replaceUnderscores)
            return replaceUnderscores(res);
        return res;
    }

    private String replaceUnderscores(String str) {
        return str.replaceAll("_"," ");
    }

    public List<String> readAndSplitLine(boolean replaceUnderscores) {
        List<String> splitted = new LinkedList<String>(Arrays.asList(readLine(false).split(" ")));
        List<String> res = new LinkedList<>();
        if (replaceUnderscores) {
            for (String s: splitted)
                res.add(replaceUnderscores(s));
            return res;
        }
        return splitted;
    }

}
