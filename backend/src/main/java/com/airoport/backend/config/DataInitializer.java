package com.airoport.backend.config;

import com.airoport.backend.model.Technicien; // تأكد من الـ package الصحيح ديال الـ Entity
import com.airoport.backend.model.Aeroport;   // تأكد من الـ package الصحيح ديال الـ Entity
import com.airoport.backend.repository.TechnicienRepository; // تأكد من الـ package ديال الـ Repository
import com.airoport.backend.repository.AeroportRepository;   // تأكد من الـ package ديال الـ Repository
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(TechnicienRepository technicienRepository, AeroportRepository aeroportRepository) {
        return args -> {

            // 1. غانكرييو أولا نجبدو Aeroport حيت ضروري للـ Technicien (على حساب الـ Constraints)
            Aeroport aeroport = aeroportRepository.findByName("Aéroport Marrakech Menara")
                    .orElseGet(() -> {
                        Aeroport newAeroport = new Aeroport();
                        newAeroport.setName("Aéroport Marrakech Menara");
                        return aeroportRepository.save(newAeroport);
                    });

            // 2. غانقلبو واش كاين شي Technicien عنده هاد الـ pseudoname مثلاً
            String defaultPseudo = "taha";

            if (!technicienRepository.existsByPseudoname(defaultPseudo)) {

                Technicien defaultTech = new Technicien();
                defaultTech.setFirstname("Taha");
                defaultTech.setLastname("Dev");
                defaultTech.setPseudoname(defaultPseudo);
                defaultTech.setMotDePass("123456"); // من بعد إلى زدتي Spring Security دير BCryptPasswordEncoder
                defaultTech.setRole("technicien");
                defaultTech.setAeroport(aeroport); // ربط الحساب مع الـ Aeroport لي كريينا الفوق

                technicienRepository.save(defaultTech);

                System.out.println("✅ تم إنشاء حساب التقني الافتراضي بنجاح: " + defaultPseudo);
            } else {
                System.out.println("ℹ️ حساب التقني الافتراضي موجود مسبقاً ف قاعدة البيانات.");
            }

            // 3. حساب admin افتراضي
            String adminPseudo = "admin";
            if (!technicienRepository.existsByPseudoname(adminPseudo)) {
                Technicien admin = new Technicien();
                admin.setFirstname("Admin");
                admin.setLastname("User");
                admin.setPseudoname(adminPseudo);
                admin.setMotDePass("admin"); // من بعد إلى زدتي Spring Security دير BCryptPasswordEncoder
                admin.setRole("admin");
                admin.setAeroport(aeroport);

                technicienRepository.save(admin);

                System.out.println("✅ تم إنشاء حساب admin الافتراضي بنجاح: " + adminPseudo);
            } else {
                System.out.println("ℹ️ حساب admin الافتراضي موجود مسبقاً ف قاعدة البيانات.");
            }
        };
    }
}