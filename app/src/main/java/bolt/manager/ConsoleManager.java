package bolt.manager;

import java.io.IOException;

import org.jline.reader.UserInterruptException;
import org.jline.reader.EndOfFileException;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.minestom.server.MinecraftServer;
import net.minestom.server.command.CommandManager;
import net.minestom.server.command.ConsoleSender;

public final class ConsoleManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConsoleManager.class);

    private final Thread consoleThread;
    private final CommandManager commandManager;
    private final ConsoleSender consoleSender;
    
    private Terminal terminal;
    private LineReader reader;

    public ConsoleManager() {
        this.commandManager = MinecraftServer.getCommandManager();
        
        // CỬA SỔ CHUẨN: Lấy ConsoleSender chính thức của Minestom thay vì new ConsoleSender()
        this.consoleSender = commandManager.getConsoleSender();

        this.consoleThread = new Thread(this::runConsoleLoop, "CONSOLE-READER");
        this.consoleThread.setDaemon(true);
    }

    public void start() {
        try {
            this.terminal = TerminalBuilder.builder()
                    .system(true)
                    .dumb(true)
                    .build();

            // Cấu hình LineReader nâng cao cho JLine
            this.reader = LineReaderBuilder.builder()
                    .terminal(terminal)
                    .option(LineReader.Option.DISABLE_EVENT_EXPANSION, true) // Tránh lỗi khi gõ ký tự '!'
                    .option(LineReader.Option.INSERT_TAB, false)             // Hỗ trợ Tab-completion mượt hơn
                    .build();

            this.consoleThread.start();
            LOGGER.info("JLine Console Reader đã khởi động thành công.");
        } catch (IOException e) {
            LOGGER.error("Không thể khởi tạo JLine Terminal: {}", e.getMessage(), e);
        }
    }

    private void runConsoleLoop() {
        while (MinecraftServer.isStarted() && !MinecraftServer.isStopping()) {
            try {
                // Luôn gọi readLine với Prompt cố định
                String line = reader.readLine("> ");
                if (line == null) continue;

                line = line.trim();
                if (!line.isEmpty()) {
                    commandManager.execute(consoleSender, line);
                }
            } catch (UserInterruptException e) {
                LOGGER.info("Đã nhận tín hiệu ngắt (Ctrl+C). Đang dừng máy chủ an toàn...");
                MinecraftServer.stopCleanly();
                break;
            } catch (EndOfFileException e) {
                break;
            } catch (Exception e) {
                LOGGER.error("Lỗi xảy ra trong luồng đọc Console: {}", e.getMessage(), e);
            }
        }

        stopTerminal();
    }

    private void stopTerminal() {
        if (terminal != null) {
            try {
                terminal.close();
            } catch (IOException ignored) {}
        }
    }
}