package github.homepunk.com.fieldserrorhandler.handlers.fields.validators.interfaces;

import github.homepunk.com.fieldserrorhandler.handlers.builders.FieldHandler;
import github.homepunk.com.fieldserrorhandler.models.HandleResult;

/**
 * Created by Homepunk on 05.09.2017.
 **/

public interface TargetHandler {
    HandleResult handle(String target);

    void addHandler(FieldHandler handler);
}
