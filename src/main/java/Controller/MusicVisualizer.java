package Controller;

import Model.FrequencySpectrum;
import Model.IAudioData;
import Model.RawAudioSamples;
import View.*;
import View.GraphPanel.*;
import org.apache.commons.cli.*;

import javax.sound.sampled.*;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class MusicVisualizer {
    private static final String FILE = "file";
    private static final String TYPE = "type";

    public static void main(String[] args) {
        Options options = makeOptions();
        CommandLine cmd = parseCommandLine(options, args);

        File wavFile = new File(cmd.getOptionValue(FILE));
        RenderType type = RenderType.valueOf(cmd.getOptionValue(TYPE));

        AbstractGraphPanel panel = null;
        switch (type) {
            case PCMFrequencySpectrum:
                panel = new PCMFrequencySpectrumGraph();
                break;
            case RawPCMAmplitude:
                panel = new RawPCMAmplitudeGraph();
                break;
            case RawPCMDots:
                panel = new RawPCMDotsGraph();
                break;
            case RawPCMLine:
                panel = new RawPCMLineGraph();
                break;
            default:
                showUsageAndExit(options, 3);
                break;
        }

        panel.setData(new FrequencySpectrum(new short[0]));
        JFrame frame = new VisualizerFrame();
        frame.add(panel);

        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(wavFile);
            SourceDataLine audioOut = AudioSystem.getSourceDataLine(ais.getFormat());

            AudioFormat format = ais.getFormat();
            System.out.println(format);
            int frameSize = format.getFrameSize();
            int frameRate = (int) format.getFrameRate();

            // Open the line buffer
            audioOut.open(ais.getFormat());
            // Begin playback of whatever is placed in the buffer
            audioOut.start();
            // Start rendering
            frame.setVisible(true);

            int bufferSize = frameRate / 100 * frameSize;
            ByteBuffer data = ByteBuffer.allocate(bufferSize).order(ByteOrder.LITTLE_ENDIAN);
            long totalBytesRead = 0L;
            while (totalBytesRead < wavFile.length()) {
                int numBytesRead = ais.read(data.array(), 0, bufferSize);

                if (numBytesRead == -1) break;
                totalBytesRead += numBytesRead;

                short[] samples = new short[bufferSize / frameSize];
                int j = 0;
                for (int i = 0; i < bufferSize / frameSize; i++) {
                    samples[i] = data.getShort(j);
                    j += frameSize;
                }

                IAudioData audioData = null;
                switch(type) {
                    case PCMFrequencySpectrum:
                        audioData = new FrequencySpectrum(samples);
                        break;
                    case RawPCMAmplitude:
                    case RawPCMDots:
                    case RawPCMLine:
                        audioData = new RawAudioSamples(samples);
                        break;
                }

                panel.setData(audioData);
                panel.repaint();
                audioOut.write(data.array(), 0, numBytesRead);

                data.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
            showUsageAndExit(options, 2, e.getMessage());
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
            showUsageAndExit(options, 2);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
            showUsageAndExit(options, 100, e.getMessage());
        }
    }

    /**
     * Parse command line for inputs.
     * @return A CommandLine object for easily accessing user options
     */
    private static CommandLine parseCommandLine(Options options, String[] args) {
        DefaultParser parser = new DefaultParser();
        try {
            return parser.parse(options, args);
        } catch (ParseException e) {
            System.err.println(e.getMessage());
            showUsageAndExit(options, 1, e.getMessage());
            return null;
        }
    }

    /**
     * Define command line options
     * @return Command Line Options
     */
    private static Options makeOptions() {
        Option file = new Option(FILE.substring(0, 1), FILE, true, "Audio file to visualize");
        file.setRequired(true);

        Option type = new Option(TYPE.substring(0, 1), TYPE, true, "Render method");
        type.setRequired(true);

        Options options = new Options();
        options.addOption(file);
        options.addOption(type);

        return options;
    }

    /**
     * Show usage and exit with generic error details.
     * @param options   Command line options
     * @param code      Error code
     */
    private static void showUsageAndExit(Options options, int code) {
        HelpFormatter formatter = new HelpFormatter();
        String reason;
        switch (code) {
            case 1:
                reason = "Couldn't parse command line.";
                break;
            case 2:
                reason = "File is invalid.";
                break;
            default:
                reason = "Unknown error.";
                break;
        }
        formatter.printHelp(MusicVisualizer.class.getSimpleName(), null, options, reason, true);
        System.exit(code);
    }

    /**
     * Show usage and exit with specific error details.
     * @param options   Command line options
     * @param code      Error code
     * @param reason    Error details
     */
    private static void showUsageAndExit(Options options, int code, String reason) {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp(MusicVisualizer.class.getSimpleName(), null, options, reason, true);
        System.exit(code);
    }
}
