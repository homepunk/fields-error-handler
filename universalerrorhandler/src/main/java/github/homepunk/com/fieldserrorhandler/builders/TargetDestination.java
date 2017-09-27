package github.homepunk.com.fieldserrorhandler.builders;

import android.support.design.widget.TextInputLayout;
import android.widget.TextView;

/**
 * Created by Homepunk on 27.09.2017.
 **/

public class TargetDestination {
    private TargetDestination(Builder builder) {

    }

    public static Builder getBuilder() {
        return new Builder();
    }

    public static class Builder {
        private Builder() {

        }

        public Builder setResultDestination(TextInputLayout resultDestination) {
            return this;
        }

        public Builder setResultDestination(TextView resultDestination) {
            return this;
        }

        public TargetDestination build() {
            return new TargetDestination(this);
        }
    }
}
