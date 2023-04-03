package flowix.main.flowixlabfinall.repositories;

import flowix.main.flowixlabfinall.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {
    Admin findAdminByTelegramId(String telegramId);
    Admin findAdminByUsername(String username);

}
