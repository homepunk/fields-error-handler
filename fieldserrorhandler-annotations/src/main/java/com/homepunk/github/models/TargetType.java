package com.homepunk.github.models;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;

import static com.homepunk.github.models.TargetType.CREDIT_CARD;
import static com.homepunk.github.models.TargetType.EMAIL;
import static com.homepunk.github.models.TargetType.NAME;
import static com.homepunk.github.models.TargetType.PASSWORD;
import static com.homepunk.github.models.TargetType.PHONE;
import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Created by homepunk on 24.08.17.
 */

@Retention(SOURCE)
@IntDef({NAME, EMAIL, PHONE, PASSWORD, CREDIT_CARD})
public @interface TargetType {
    int NAME = 4206661;
    int EMAIL = 4206662;
    int PHONE = 4206663;
    int PASSWORD = 4206664;
    int CREDIT_CARD = 4206666;
}
