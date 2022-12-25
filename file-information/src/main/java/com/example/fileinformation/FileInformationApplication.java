package com.example.fileinformation;

import com.example.fileinformation.modules.Module;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.io.File;
import java.util.Collection;
import java.util.Scanner;

@SpringBootApplication
public class FileInformationApplication {

    private final Collection<Module> modules;

    @Autowired
    public FileInformationApplication(Collection<Module> modules) {
        this.modules = modules;
    }

    public static void main(String[] args) {
        SpringApplication.run(FileInformationApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Введите путь к файлу: ");
            String path = scanner.nextLine();
            File file = new File(path);
            for (Module module : modules) {
                if (module.isSuitableExtension(file)) {
                    applyModule(module, file);
                }
            }
        };
    }

    public void applyModule(Module module, File file) {
        System.out.println("Список команд: ");
        System.out.println(module.getDescription());
        System.out.println("Введите команду: ");
        Scanner scanner = new Scanner(System.in);
        module.executeCommand(scanner.nextLine(), file);
    }

}
