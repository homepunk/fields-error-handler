package com.homepunk.github.models;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Created by Homepunk on 21.09.2017.
 **/

@IntDef
@Retention(SOURCE)
public @interface UniversalErrorType {
    int FIELD_TEXT_IS_VALID = 420;
    int FIELD_TEXT_CANT_BE_EMPTY = 1;
    int FIELD_TEXT_CONTAINS_UNRESOLVED_CHARACTERS = 2;
    int FIELDS_TEXT_DONT_MATCH = 3;
}
