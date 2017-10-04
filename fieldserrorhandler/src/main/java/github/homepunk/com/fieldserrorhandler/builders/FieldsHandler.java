package github.homepunk.com.fieldserrorhandler.builders;

import github.homepunk.com.fieldserrorhandler.managers.AnnotationsManager;

/**
 * Created by Homepunk on 27.09.2017.
 **/

public class FieldsHandler {
    private AnnotationsManager manager;

    public FieldsHandler(AnnotationsManager manager) {
        this.manager = manager;
    }

    public FieldsHandler target(int targetType, TargetHandler handler) {
        manager.addTargetHandler(targetType, handler);
        return this;
    }
}
