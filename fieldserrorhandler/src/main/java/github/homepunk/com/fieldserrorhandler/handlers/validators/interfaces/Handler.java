package github.homepunk.com.fieldserrorhandler.handlers.validators.interfaces;

import github.homepunk.com.fieldserrorhandler.builders.TargetHandler;
import github.homepunk.com.fieldserrorhandler.models.HandleResult;

/**
 * Created by Homepunk on 05.09.2017.
 **/

public interface Handler {
    HandleResult handle(String target);

    void addHandler(TargetHandler handler);
}
