package weblab.rating.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import weblab.rating.entities.Rating;

import java.util.List;

public interface RatingDao extends JpaRepository<Rating, Integer>{
    Rating getById(int id);

    Rating getByWebsite(String website);

    Rating getRatingsByWebsite(String website);

    List<Rating> findAllByWebsite(String website);

    int countRatingByWebsite(String website);
}
