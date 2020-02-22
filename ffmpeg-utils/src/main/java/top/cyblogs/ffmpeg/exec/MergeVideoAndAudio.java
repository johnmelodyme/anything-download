package top.cyblogs.ffmpeg.exec;

import top.cyblogs.ffmpeg.command.FFMpegCommand;
import top.cyblogs.ffmpeg.listener.FFMpegListener;
import top.cyblogs.util.FileUtils;

import java.io.File;
import java.util.List;

/**
 * 合并视频和音频
 */
public class MergeVideoAndAudio {


    /**
     * 合并视频和音频
     * 【源文件不会被删除】
     *
     * @param video 视频文件
     * @param audio 音频文件
     * @param out   输出文件
     */
    public synchronized static void exec(File video, File audio, File out, FFMpegListener listener) {

        // 检查参数
        if (video == null || audio == null || out == null) {
            throw new IllegalArgumentException("合并音视频时参数异常!");
        }

        if (listener != null) {
            listener.start();
        }

        FileUtils.deleteOnExists(out);

        // 建立目标文件夹
        FileUtils.mkdirs(out);

        // 获取命令
        List<String> command = FFMpegCommand.mergeVideoAndAudio(video, audio, out);

        // 执行命令
        ExecUtils.exec(command, s -> {
            // TODO 命令执行进度
        });

        try {
            // 给一个延时，不要和FFMpeg进程抢资源
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {
        }

        if (listener != null) listener.over();
    }
}
