package webit.script.spring.boot;

import org.junit.jupiter.api.Test;
import webit.script.Engine;
import webit.script.exceptions.ResourceNotFoundException;
import webit.script.exceptions.ParseException;

import java.io.StringWriter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class WebitRenderingTest {

    @Test
    void rendersTemplateWithBoundValue() throws Exception {
        Engine engine = Engine.create();
        StringWriter output = new StringWriter();

        engine.getTemplate("templates/greeting.wit")
                .merge(output);

        assertThat(output.toString()).isEqualTo("Hello Ada!\n");
    }

    @Test
    void missingTemplateFailsInsteadOfProducingEmptyContent() {
        Engine engine = Engine.create();
        StringWriter output = new StringWriter();

        assertThatThrownBy(() -> engine.getTemplate("templates/absent.wit")
                .merge(output))
                .isInstanceOf(ParseException.class)
                .hasCauseInstanceOf(ResourceNotFoundException.class);
        assertThat(output.toString()).isEmpty();
    }
}
