package dev.pseudodistant.pactitude;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class PacOSReader {
    public enum OS {
        ARCH,
        WINDOWS,
        DEBIAN,
        RHEL,
        OPENSUSE,
        MACOS
    }

    private OS currentOS;

    public PacOSReader() {
        if (System.getProperty("os.name").toLowerCase().startsWith("windows")) {
            currentOS = OS.WINDOWS;
        } else if (System.getProperty("os.name").toLowerCase().startsWith("linux")) {
            try {
                String osName;
                File osRelease = new File("/etc/os-release");
                Scanner osReader = new Scanner(osRelease);
                while (osReader.hasNextLine()) {
                    String data = osReader.nextLine().toLowerCase();
                    if(data.contains("arch")) {
                        currentOS = OS.ARCH;
                        break;
                    } else if (data.contains("debian") || data.contains("ubuntu")) {
                        currentOS = OS.DEBIAN;
                        break;
                    } else {
                        throw new RuntimeException("This Linux distribution is unsupported...");
                    }

                }
            } catch (FileNotFoundException e) {
                throw new RuntimeException("This Linux distribution is unsupported...");
            }
        } else if (System.getProperty("os.name").toLowerCase().startsWith("mac")) {
            throw new RuntimeException("macOS is not currently supported. Homebrew may be supported in the future...");
        }
    }

    public OS getCurrentOS() {
        return currentOS;
    }
}
