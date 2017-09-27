package github.homepunk.com.fieldserrorhandler.builders;

import android.widget.EditText;

/**
 * Created by Homepunk on 27.09.2017.
 **/

public class Target {
    private Target(Builder builder) {
    }

    public static Builder getBuilder() {
        return new Builder();
    }

    public static class Builder {
        private Builder() {
        }

        public Builder bind(EditText mPasswordVerification, int passwordVerification) {
            return this;
        }

        public Builder addType(int targetType) {
            return this;
        }

        public Builder addField(EditText targetField) {
            return this;
        }

        public Builder addHandler(TargetHandler targetHandler) {
            return this;
        }

        public Target build() {
            return new Target(this);
        }
    }
}
