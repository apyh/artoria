package artoria.template;

import artoria.io.StringBuilderWriter;
import artoria.logging.Logger;
import artoria.logging.LoggerFactory;
import artoria.util.Assert;

/**
 * Template render tools.
 * @author Kahle
 */
public class RenderUtils {
    private static final Renderer DEFAULT_RENDERER = new DefaultRenderer();
    private static Logger log = LoggerFactory.getLogger(RenderUtils.class);
    private static Renderer renderer;

    public static Renderer getRenderer() {

        return renderer != null ? renderer : DEFAULT_RENDERER;
    }

    public static void setRenderer(Renderer renderer) {
        Assert.notNull(renderer, "Parameter \"renderer\" must not null. ");
        log.info("Set template renderer: {}", renderer.getClass().getName());
        RenderUtils.renderer = renderer;
    }

    public static void render(Object data, Object output, String name) {

        getRenderer().render(data, output, name, null, null);
    }

    public static void render(Object data, Object output, String name, Object input) {

        getRenderer().render(data, output, name, input, null);
    }

    public static void render(Object data, Object output, String name, Object input, String charsetName) {

        getRenderer().render(data, output, name, input, charsetName);
    }

    public static String renderToString(Object data, String name) {
        StringBuilderWriter output = new StringBuilderWriter();
        getRenderer().render(data, output, name, null, null);
        return output.toString();
    }

    public static String renderToString(Object data, String name, Object input) {
        StringBuilderWriter output = new StringBuilderWriter();
        getRenderer().render(data, output, name, input, null);
        return output.toString();
    }

    public static String renderToString(Object data, String name, Object input, String charsetName) {
        StringBuilderWriter output = new StringBuilderWriter();
        getRenderer().render(data, output, name, input, charsetName);
        return output.toString();
    }

}