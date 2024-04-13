package hexlet.code;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.TreeMap;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

public class Differ {
    public static String generate(File filepath1, File filepath2) throws Exception {
        String result = "";

        Path file1 = Paths.get(filepath1.toURI()).toAbsolutePath().normalize();
        if (!Files.exists(file1)) {
            throw new Exception("File '" + file1 + "' does not exist");
        }

        Path file2 = Paths.get(filepath2.toURI()).toAbsolutePath().normalize();
        if (!Files.exists(file2)) {
            throw new Exception("File '" + file2 + "' does not exist");
        }

        Map<String, String> map1 = null;
        Map<String, String> map2 = null;

        var fileType = getFileExtension(file1);

        switch (fileType) {
            case "json":
                ObjectMapper jsonMapper = new ObjectMapper();
                map1 = jsonMapper.readValue(Files.readString(file1), new TypeReference<>() { });
                map2 = jsonMapper.readValue(Files.readString(file2), new TypeReference<>() { });
                break;
            case "yml":
                ObjectMapper yamlMapper = new YAMLMapper();
                map1 = yamlMapper.readValue(Files.readString(file1), new TypeReference<>() { });
                map2 = yamlMapper.readValue(Files.readString(file2), new TypeReference<>() { });
                break;
            default:
                return null;
        }

        Map<String, String> sortedMap = new TreeMap<>(map1);
        sortedMap.putAll(map2);

        result += "{\n";

        for (var entry : sortedMap.entrySet()) {
            var key = String.valueOf(entry.getKey());
            var value = String.valueOf(entry.getValue());

            var value1 = map1.get(key);
            var value2 = map2.get(key);

            if (value.equals(value1) & value.equals(value2)) {
                result += "    " + key + ": " + value + "\n";
            } else if (value.equals(value1)) {
                result += "  - " + key + ": " + value + "\n";
            } else if (value.equals(value2)) {
                if (value1 != null) {
                    result += "  - " + key + ": " + value1 + "\n";
                }
                result += "  + " + key + ": " + value + "\n";
            }
        }

        result += "}";

        return result;
    }

    private static String getFileExtension(Path file) {
        return file.getFileName().toString().split("\\.")[1];
    }
}
