package hexlet.code;

//import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DifferTest {
    private static File filepath1;
    private static File filepath2;
    private static File result;

    @Test
    public void testDifferJson() throws Exception {
        filepath1 = new File("./src/test/resources/filepath1.json");
        filepath2 = new File("./src/test/resources/filepath2.json");
        result = new File("./src/test/resources/result");

        String expected = Files.readString(result.toPath());
        String actual = Differ.generate(filepath1, filepath2);
        assertEquals(expected, actual);
    }

    @Test
    public void testDifferYaml() throws Exception {
        filepath1 = new File("./src/test/resources/filepath1.yml");
        filepath2 = new File("./src/test/resources/filepath2.yml");
        result = new File("./src/test/resources/result");

        String expected = Files.readString(result.toPath());
        String actual = Differ.generate(filepath1, filepath2);
        assertEquals(expected, actual);
    }

}
