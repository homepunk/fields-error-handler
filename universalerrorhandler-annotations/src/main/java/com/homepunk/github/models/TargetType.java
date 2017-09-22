package com.homepunk.github.models;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;

import static com.homepunk.github.models.TargetHandleType.CREDIT_CARD;
import static com.homepunk.github.models.TargetHandleType.EMAIL;
import static com.homepunk.github.models.TargetHandleType.NAME;
import static com.homepunk.github.models.TargetHandleType.PASSWORD;
import static com.homepunk.github.models.TargetHandleType.PASSWORD_CONFIRMATION;
import static com.homepunk.github.models.TargetHandleType.PHONE;
import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Created by homepunk on 24.08.17.
 */

@Retention(SOURCE)
@IntDef({NAME, EMAIL, PHONE, PASSWORD, PASSWORD_CONFIRMATION, CREDIT_CARD})
public @interface TargetHandleType {
    int NAME = 1;
    int EMAIL = 2;
    int PHONE = 3;
    int PASSWORD = 4;
    int PASSWORD_CONFIRMATION = 5;
    int CREDIT_CARD = 6;
}
