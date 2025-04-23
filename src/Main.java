import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("File path: ");
        String filePath = scanner.nextLine().trim();

        System.out.print("Select format (mp3, wav, flac, aac, ogg): ");
        String format = scanner.nextLine().trim().toUpperCase();

        String outputFile = filePath + '.' + format;

        System.out.println("Converting " + filePath + " to " + format + "...");
        convert(filePath, outputFile, format);

        scanner.close();
    }

    public static void convert(String path, String output, String format) {
        File ffmpeg = new File("libs/ffmpeg.exe");
        List<String> command = new ArrayList<>();
        command.add(ffmpeg.getAbsolutePath());
        command.add("-y");
        command.add("-i");
        command.add(path);
        command.add("-codec:a");
        command.add(getCodec(format));
        command.add(output);

        ProcessBuilder pb = new ProcessBuilder(command);
        pb.redirectErrorStream(true);

        try {
            Process process = pb.start();
            int exitCode = process.waitFor();

            if (exitCode == 0) {
                System.out.println("Conversion complete!");
            } else {
                System.out.println("Conversion failed! " + exitCode);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static String getCodec(String format) {
        switch (format) {
            case "MP3": return "libmp3lame";
            case "WAV": return "pcm_s16le";
            case "FLAC": return "flac";
            case "AAC": return "aac";
            case "OGG": return "libvorbis";
            default: System.out.println("Invalid format");
        }

        return null;
    }
}