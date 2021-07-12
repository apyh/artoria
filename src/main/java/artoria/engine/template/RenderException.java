package artoria.engine.template;

import artoria.exception.UncheckedException;

/**
 * Template render exception.
 * @author Kahle
 */
public class RenderException extends UncheckedException {

    public RenderException() {

        super();
    }

    public RenderException(String message) {

        super(message);
    }

    public RenderException(Throwable cause) {

        super(cause);
    }

    public RenderException(String message, Throwable cause) {

        super(message, cause);
    }

}