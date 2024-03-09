package weblab.rating.business.abstracts;

import weblab.rating.core.utilities.DataResult;
import weblab.rating.core.utilities.Result;
import weblab.rating.entities.Rating;

import java.util.List;

public interface RatingService {
    Result addRating(Rating rating);

    DataResult<List<Rating>> getRatingsByWebsite(String website);

    DataResult<Rating> getRatingById(int id);

    DataResult <Rating> getCalculatedRating(String website);

}
