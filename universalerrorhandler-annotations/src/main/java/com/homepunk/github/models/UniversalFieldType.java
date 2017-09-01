package com.homepunk.github.models;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;

import static com.homepunk.github.models.UniversalFieldType.CREDIT_CARD;
import static com.homepunk.github.models.UniversalFieldType.EMAIL;
import static com.homepunk.github.models.UniversalFieldType.NAME;
import static com.homepunk.github.models.UniversalFieldType.PASSWORD;
import static com.homepunk.github.models.UniversalFieldType.PHONE;
import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Created by homepunk on 24.08.17.
 */

@Retention(SOURCE)
@StringDef({NAME, EMAIL, PHONE, PASSWORD, CREDIT_CARD})
public @interface UniversalFieldType {
    String NAME = "Name";
    String EMAIL = "Email";
    String PHONE = "Phone";
    String PASSWORD = "Password";
    String CREDIT_CARD = "CreditCard";
}
