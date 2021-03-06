package top.cyblogs.ffmpeg.utils;

import top.cyblogs.ffmpeg.exception.FFMpegException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.function.Consumer;

/**
 * FFMpeg命令的执行器
 *
 * @author CY
 */
public class ExecUtils {

    /**
     * 执行命令
     *
     * @param command 命令
     */
    public synchronized static void exec(List<String> command, Consumer<String> consumer) {
        // 获取控制台实时内容
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                new ProcessBuilder(command).redirectErrorStream(true).start().getInputStream()))) {
            // 打印控制台的回调
            reader.lines().forEach(x -> {
                if (consumer != null) {
                    consumer.accept(x);
                }
            });
        } catch (IOException e) {
            throw new FFMpegException("FFMpeg命令执行出错!");
        }
    }
}
