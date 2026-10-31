package webit.script.spring.boot;

import org.junit.jupiter.api.Test;
import webit.script.Engine;
import webit.script.exceptions.ResourceNotFoundException;

import java.io.StringWriter;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class WebitRenderingTest {

    @Test
    void rendersTemplateWithBoundValue() throws Exception {
        Engine engine = Engine.create();
        StringWriter output = new StringWriter();

        engine.getTemplate("templates/greeting.wit")
                .merge(Map.of("name", "Ada"), output);

        assertThat(output.toString()).isEqualTo("Hello Ada!\n");
    }

    @Test
    void missingTemplateFailsInsteadOfProducingEmptyContent() {
        Engine engine = Engine.create();
        StringWriter output = new StringWriter();

        assertThatThrownBy(() -> engine.getTemplate("templates/absent.wit")
                .merge(Map.of("name", "Ada"), output))
                .isInstanceOf(ResourceNotFoundException.class);
        assertThat(output.toString()).isEmpty();
    }
}
