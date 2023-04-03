package flowix.main.flowixlabfinall.repositories;

import flowix.main.flowixlabfinall.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer>{
    RefreshToken findRefreshTokenById(int id);
}
