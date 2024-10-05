package com.alpine.cnema.utils.constant;

import java.util.Arrays;
import java.util.List;

public interface Messages {
    public static final String USER = "User";
    public static final String FNB = "FnB";
    public static final String GENRE = "Genre";
    public static final String MOVIE = "Movie";
    public static final String CINEMA = "Cinema";
    public static final String SEAT = "Seat";
    public static final String SECTION = "Section";
    public static final String MERCHANDISE = "Merchandise";
    public static final String RECOMENDATION = "Recomendation";
    public static final String TRANSACTION = "Transaction";
    public static final String BOOKED = " is Booked";
    public static final String COMPANY = "Company";
    public static final String STUDIO = "Studio";
    public static final String QUANTITY = "Quntity";

    public static final String NAME = " name";
    public static final String PRICE = " price";
    public static final String LOCATION = " location";
    public static final String TITLE = " title";
    public static final String DURATION = " duration";
    public static final String RATING = " rating";
    public static final String CAST = " cast";
    public static final String DESCRIPTION = " description";
    public static final String YEAR = " year";
    public static final String GENRES = " genre";
    public static final String CONJUNCTION = " and ";
    public static final String OR = " or ";
    public static final String NOT = " not";
    public static final String SUCCESS = "Successfully ";
    public static final String EMPTY = "Empty";
    public static final String CREATE = "created ";
    public static final String DELETE = "deleted ";
    public static final String UPDATE = "updated ";
    public static final String FOUND = "  found";
    public static final String EXPIRED = "Expired";
    public static final String START_TIME = " start time";
    public static final String END_TIME = " end time";
    public static final String INITIATED = " has been initiated";
    public static final String NOT_INITIATED = " has not been initiated";
    public static final String RESERVED = " has been reserved ";

    // Messages for FnB service
    public static final String FNB_FOUND = FNB + FOUND;
    public static final String FNB_NOT_FOUND = FNB + NOT + FOUND;
    public static final String SUCCESS_CREATE_FNB = SUCCESS + CREATE + FNB;
    public static final String SUCCESS_DELETE_FNB = SUCCESS + DELETE + FNB;
    public static final String SUCCESS_UPDATE_FNB = SUCCESS + UPDATE + FNB;

    // Messages for Cinema service
    public static final String CINEMA_FOUND = CINEMA + FOUND;
    public static final String CINEMA_NOT_FOUND = CINEMA + NOT + FOUND;
    public static final String SUCCESS_CREATE_CINEMA = SUCCESS + CREATE + CINEMA;
    public static final String SUCCESS_DELETE_CINEMA = SUCCESS + DELETE + CINEMA;
    public static final String SUCCESS_UPDATE_CINEMA = SUCCESS + UPDATE + CINEMA;

    // Messages for Genre service
    public static final String GENRE_FOUND = GENRE + FOUND;
    public static final String GENRE_NOT_FOUND = GENRE + NOT + FOUND;
    public static final String SUCCESS_CREATE_GENRE = SUCCESS + CREATE + GENRE;
    public static final String SUCCESS_DELETE_GENRE = SUCCESS + DELETE + GENRE;
    public static final String SUCCESS_UPDATE_GENRE = SUCCESS + UPDATE + GENRE;

    // Messages for Movie service
    public static final String MOVIE_FOUND = MOVIE + FOUND;
    public static final String MOVIE_NOT_FOUND = MOVIE + NOT + FOUND;
    public static final String SUCCESS_CREATE_MOVIE = SUCCESS + CREATE + MOVIE;
    public static final String SUCCESS_DELETE_MOVIE = SUCCESS + DELETE + MOVIE;
    public static final String SUCCESS_UPDATE_MOVIE = SUCCESS + UPDATE + MOVIE;

    // Messages for Merchandise service
    public static final String MERCHANDISE_FOUND = MERCHANDISE + FOUND;
    public static final String MERCHANDISE_NOT_FOUND = MERCHANDISE + NOT + FOUND;
    public static final String SUCCESS_CREATE_MERCHANDISE = SUCCESS + CREATE + MERCHANDISE;
    public static final String SUCCESS_DELETE_MERCHANDISE = SUCCESS + DELETE + MERCHANDISE;
    public static final String SUCCESS_UPDATE_MERCHANDISE = SUCCESS + UPDATE + MERCHANDISE;
    public static final String MERCHANDISE_EMPTY = EMPTY + " " + MERCHANDISE;

    // Messages for Seat service
    public static final String SEAT_FOUND = SEAT + FOUND;
    public static final String SEAT_NOT_FOUND = SEAT + NOT + FOUND;
    public static final String SUCCESS_CREATE_SEAT = SUCCESS + CREATE + SEAT;
    public static final String SUCCESS_DELETE_SEAT = SUCCESS + DELETE + SEAT;
    public static final String SUCCESS_UPDATE_SEAT = SUCCESS + UPDATE + SEAT;
    public static final String SEAT_BOOKED = SEAT + BOOKED;

    public static final String SECTION_FOUND = SECTION + FOUND;
    public static final String SECTION_NOT_FOUND = SECTION + NOT + FOUND;
    public static final String SUCCESS_CREATE_SECTION = SUCCESS + CREATE + SECTION;
    public static final String SUCCESS_DELETE_SECTION = SUCCESS + DELETE + SECTION;
    public static final String SUCCESS_UPDATE_SECTION = SUCCESS + UPDATE + SECTION;

    public static final String RECOMENDATION_FOUND = RECOMENDATION + FOUND;

    public static final String SUCCESS_CREATE_TRANSACTION = SUCCESS + CREATE + TRANSACTION;

    public static final String USER_FOUND = USER + FOUND;
    public static final String USER_NOT_FOUND = USER + NOT + FOUND;
    public static final String SUCCESS_CREATE_USER = SUCCESS + CREATE + USER;
    public static final String LOGIN = " login ";
    public static final String SUCCESS_LOGIN_USER = USER + LOGIN + SUCCESS;

    public static final String NAME_EMPTY = EMPTY + NAME;
    public static final String PRICE_EMPTY = EMPTY + PRICE;
    public static final String LOCATION_EMPTY = EMPTY + LOCATION;
    public static final String TITLE_EMPTY = EMPTY + TITLE;
    public static final String DURATION_EMPTY = EMPTY + DURATION;
    public static final String RATING_EMPTY = EMPTY + RATING;
    public static final String CAST_EMPTY = EMPTY + CAST;
    public static final String DESCRIPTION_EMPTY = EMPTY + DESCRIPTION;
    public static final String YEAR_EMPTY = EMPTY + YEAR;
    public static final String GENRE_EMPTY = EMPTY + GENRE;
    public static final String SEAT_EMPTY = EMPTY + " " + SEAT;
    public static final String SECTION_EMPTY = EMPTY + " " + SECTION;
    public static final String BOOKED_EMPTY = BOOKED + " " + EMPTY;
    public static final String COMPANY_EMPTY = COMPANY + " " + EMPTY;
    public static final String EXPIRED_STRAT_TIME = EXPIRED + START_TIME;
    public static final String SECTION_INITIATED = SECTION + INITIATED;
    public static final String START_TIME_EMPTY = EMPTY + START_TIME;
    public static final String STUDIO_EMPTY = EMPTY + " " + STUDIO;
    public static final String CINEMA_EMPTY = EMPTY + " " + CINEMA;
    public static final String MOVIE_EMPTY = EMPTY + " " + MOVIE;
    public static final String TRANSACTION_EMPTY = EMPTY + " " + TRANSACTION;
    public static final String QUANTITY_EMPTY = EMPTY + " " + QUANTITY;
    public static final String FNB_EMPTY = EMPTY + " " + FNB;
    public static final String MOVIE_CINEMA_NOT_INITIATED = MOVIE + OR + CINEMA + NOT_INITIATED;
    public static final String PRICE_NEGATIVE = "Price cannot be negative number";
    public static final String PRICE_EMPTY_OR_NEGATIVE = PRICE_EMPTY + CONJUNCTION + PRICE_NEGATIVE;

    // Messages for user service
    public static final String USERNAME_ALREADY_EXIST = "Username already exists";
    public static final String PHONE_ALREADY_EXIST = "Phone already exists";
    public static final String EMAIL_ALREADY_EXIST = "Email already exists, use a different one or make a new account.";
    public static final String EMAIL_INVALID_FORMAT = "Invalid email format, please input the right format";
    public static final String INVALID_CREDENTIAL = "Invalid Credential";
    public static final List<String> VALID_TLDS = Arrays.asList("com", "net", "org", "co.id", "id", "gov", "edu", "io", "co.uk", "ac.id");
}