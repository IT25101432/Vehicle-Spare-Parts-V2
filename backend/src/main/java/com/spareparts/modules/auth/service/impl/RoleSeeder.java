package com.spareparts.modules.auth.service.impl;

import com.spareparts.modules.auth.entity.ERole;
import com.spareparts.modules.auth.entity.Role;
import com.spareparts.modules.auth.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class RoleSeeder implements CommandLineRunner {

    public RoleSeeder(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }


    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        Arrays.stream(ERole.values()).forEach(eRole -> {
            if (roleRepository.findByName(eRole).isEmpty()) {
                roleRepository.save(new Role(null, eRole));
            }
        });
    }
}
