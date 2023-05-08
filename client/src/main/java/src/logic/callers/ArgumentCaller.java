package src.logic.callers;

import module.logic.exceptions.CannotCreateObjectException;
import module.stored.Dragon;
import module.utils.ObjectUtils;

public class ArgumentCaller extends BaseCaller {
    public CallStatus getObjectArgument() throws CannotCreateObjectException {
//        to create array from strings of Dragon Object
        ObjectUtils.createObjectInteractively(Dragon.class);
        return CallStatus.SUCCESSFULLY;
    }

    public CallStatus getStringArrayFromObject() {
//        getting string array from Dragon object
        return CallStatus.SUCCESSFULLY;
    }
}
