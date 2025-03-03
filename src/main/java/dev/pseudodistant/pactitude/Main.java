package dev.pseudodistant.pactitude;

import org.apache.commons.cli.*;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws ParseException, IOException, InterruptedException {
        String version = "0.0.1";
        String vOutput = String.format(""" 
                 
                 .--.                     Pactitude v%s - %s v%s
                / _.-' .-.  .-.  .-.      Copyright (C) 2025 - 2025 PseudoDistant
                \\  '-. '-'  '-'  '-'
                 '--'                     This program may be freely redistributed under 
                                          the terms of the GNU General Public License.
                 """,
                version, System.getProperty("java.runtime.name"), System.getProperty("java.version"));

        Options options = new Options();

        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                System.out.println();
            }
        });

        options.addOption("S",false,"Synchronize Package, including dependencies.");
        options.addOption("R",false,"Remove package (or package group)");
        options.addOption("Q",false,"Query local database (view installed packages)");
        options.addOption("T",false,"Dependency Test (check package dependencies)");
        options.addOption("U",false,"Upgrade or add local package and synchronize dependencies.");
        options.addOption("V",false,"Print version.");
        options.addOption("s",false,"Search for packages available remotely or locally.");
        options.addOption("y",false,"Download a fresh copy of the master package databases (update repositories).");
        options.addOption("u",false,"Upgrade all out-of-date packages.");

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);

        PacManager manager = new PacManager(cmd.getArgs());
        for (byte x = 0; x < cmd.getOptions().length; x++) {
            Option option = cmd.getOptions()[x];
            switch (option.getOpt()) {
                case "S": {
                    manager.setStatus(PacManager.PacStatus.INSTALL);
                    break;
                } case "R": {
                    manager.setStatus(PacManager.PacStatus.REMOVE);
                    break;
                } case "Q": {
                    //TODO: Implement Query
                    manager.setStatus(PacManager.PacStatus.QUERY);
                    throw new RuntimeException("TODO");
                } case "T": {
                    //TODO: Add Dependency Test options
                    throw new RuntimeException("TODO");
                } case "U": {
                    //TODO: Implement properly
                    System.out.println("[WARN] Upgrade option not fully supported, only installing local packages...");
                    manager.setStatus(PacManager.PacStatus.INSTALL);
                    manager.doLocal();
                } case "V": {
                    System.out.println(vOutput);
                    return;
                } case "s": {
                    manager.doSearch();
                    break;
                } case "y": {
                    manager.doSync();
                    break;
                } case "u": {
                    manager.doUpgrade();
                    break;
                }
            }
        }
        PacCommandRunner pacman = new PacCommandRunner(cmd.getArgs(), manager);
        pacman.exec();

    }
}