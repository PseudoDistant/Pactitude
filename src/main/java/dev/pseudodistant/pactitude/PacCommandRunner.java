package dev.pseudodistant.pactitude;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class PacCommandRunner {
    private PacOSReader os = new PacOSReader();
    private PacManager manager;
    private String[] packages;
    private Process pacman;

    public PacCommandRunner(String[] args, PacManager manager) {
        this.packages = args;
        this.manager = manager;
    }

    public void exec() throws IOException, InterruptedException {
        if (os.getCurrentOS().equals(PacOSReader.OS.WINDOWS)) {
            //shell = Runtime.getRuntime().exec(String.format());
        }
        if (os.getCurrentOS().equals(PacOSReader.OS.DEBIAN)) {
            StringBuilder command = null;
            if (manager.status.equals(PacManager.PacStatus.INSTALL)) {
                if (manager.isSync) {
                    Process p = Runtime.getRuntime().exec("/bin/bash");
                    OutputStream stdin = p.getOutputStream();
                    PrintWriter pw = new PrintWriter(stdin);
                    pw.println("apt update < /dev/tty > /dev/tty");
                    pw.close();
                    p.waitFor();
                }
                if (manager.isUpgrade) {
                    command = new StringBuilder("apt upgrade");
                }

                // Build install command
                if (packages != null) {
                    try {
                        if (packages.length > 0) {
                            String inst = manager.isSearch ? "search" : "install";
                            command = new StringBuilder(String.format("apt %s ", inst));
                            for (String pack : packages) {
                                command.append(pack).append(' ');
                            }
                        }
                    } catch (ArrayIndexOutOfBoundsException ignored) {}
                }
            } else if (manager.status.equals(PacManager.PacStatus.REMOVE)) {
                if (packages != null) {
                    try {
                        if (packages.length > 0) {
                            command = new StringBuilder("apt remove ");
                            for (String pack : packages) {
                                command.append(pack).append(' ');
                            }
                        }
                    } catch (ArrayIndexOutOfBoundsException ignored) {}
                }
            }

            if (command != null) {
                pacman = Runtime.getRuntime().exec("/bin/bash");
                OutputStream stdin = pacman.getOutputStream();
                PrintWriter pw = new PrintWriter(stdin);
                pw.println(command.append(" < /dev/tty > /dev/tty"));
                pw.close();
                pacman.waitFor();
            }

        }

        else if (false) {

        }
    }
}
