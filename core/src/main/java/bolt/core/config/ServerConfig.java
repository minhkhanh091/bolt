package bolt.core.config;

import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class ServerConfig {

    private String host = "0.0.0.0";
    private int port = 25565;

    public ServerConfig(String fileName) {
        Path externalFilePath = Paths.get(fileName);

        if (Files.notExists(externalFilePath)) {
            exportDefaultConfig(fileName, externalFilePath);
        }

        loadConfigFromExternal(externalFilePath);
    }

    private void exportDefaultConfig(String resourcePath, Path targetPath) {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(resourcePath)) {
            if (input == null) {
                System.err.println("❌ Không tìm thấy file " + resourcePath + " mặc định trong resources!");
                return;
            }

            Files.copy(input, targetPath);
            System.out.println(" Config mẫu đã được tạo tại: " + targetPath.toAbsolutePath());

        } catch (Exception e) {
            System.err.println("❌ Lỗi khi trích xuất file config mặc định:");
            e.printStackTrace();
        }
    }

    private void loadConfigFromExternal(Path filePath) {
        try (InputStream input = Files.newInputStream(filePath)) {
            Yaml yaml = new Yaml();
            Map<String, Object> data = yaml.load(input);

            if (data != null) {
                if (data.get("server-ip") instanceof String ip) {
                    this.host = ip;
                }
                if (data.get("port") instanceof Integer p) {
                    this.port = p;
                }
            }
            System.out.println(" Loaded configuration from " + filePath.getFileName());

        } catch (Exception e) {
            System.err.println("❌ Lỗi khi đọc file " + filePath + ", sử dụng cấu hình mặc định.");
            e.printStackTrace();
        }
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }
}