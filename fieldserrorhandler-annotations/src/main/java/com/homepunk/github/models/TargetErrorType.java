package com.homepunk.github.models;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;

import static com.homepunk.github.models.TargetErrorType.TARGETS_TEXT_DONT_MATCH;
import static com.homepunk.github.models.TargetErrorType.TEXT_CANT_BE_EMPTY;
import static com.homepunk.github.models.TargetErrorType.TEXT_CONTAINS_SPACES;
import static com.homepunk.github.models.TargetErrorType.TEXT_CONTAINS_UNRESOLVED_CHARACTERS;
import static com.homepunk.github.models.TargetErrorType.TEXT_DOESNT_CONTAIN_CYRILLIC_ALPHABET;
import static com.homepunk.github.models.TargetErrorType.TEXT_CONTAINS_LOWER_CASE;
import static com.homepunk.github.models.TargetErrorType.TEXT_CONTAINS_NUMBERS;
import static com.homepunk.github.models.TargetErrorType.TEXT_CONTAINS_UPPER_CASE;
import static com.homepunk.github.models.TargetErrorType.TEXT_IS_NOT_VALID;
import static com.homepunk.github.models.TargetErrorType.TEXT_IS_VALID;
import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Created by Homepunk on 21.09.2017.
 **/

@IntDef({
        TEXT_IS_VALID,
        TEXT_IS_NOT_VALID,
        TEXT_CANT_BE_EMPTY,
        TEXT_CONTAINS_SPACES,
        TEXT_CONTAINS_NUMBERS,
        TEXT_CONTAINS_UPPER_CASE,
        TEXT_CONTAINS_LOWER_CASE,
        TEXT_CONTAINS_UNRESOLVED_CHARACTERS,
        TEXT_DOESNT_CONTAIN_CYRILLIC_ALPHABET,
        TARGETS_TEXT_DONT_MATCH
})
@Retention(SOURCE)
public @interface TargetErrorType {
    int TEXT_IS_VALID = 420;
    int TEXT_IS_NOT_VALID = 0;
    int TEXT_CANT_BE_EMPTY = 1;
    int TEXT_CONTAINS_SPACES = 2;
    int TEXT_CONTAINS_NUMBERS = 3;
    int TEXT_CONTAINS_UPPER_CASE = 4;
    int TEXT_CONTAINS_LOWER_CASE = 5;
    int TEXT_CONTAINS_UNRESOLVED_CHARACTERS = 6;
    int TEXT_DOESNT_CONTAIN_CYRILLIC_ALPHABET = 7;

    int TARGETS_TEXT_DONT_MATCH = 8;
}
