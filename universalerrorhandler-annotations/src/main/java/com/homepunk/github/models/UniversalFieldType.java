package com.homepunk.github.models;

import android.support.annotation.IntDef;

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
@IntDef({NAME, EMAIL, PHONE, PASSWORD, CREDIT_CARD})
public @interface UniversalFieldType {
    int NAME = 1;
    int EMAIL = 2;
    int PHONE = 3;
    int PASSWORD = 4;
    int CREDIT_CARD = 5;
}
