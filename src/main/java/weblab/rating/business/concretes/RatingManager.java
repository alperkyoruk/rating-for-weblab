package weblab.rating.business.concretes;

import org.aspectj.bridge.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import weblab.rating.business.abstracts.RatingService;
import weblab.rating.business.constants.Messages;
import weblab.rating.core.utilities.*;
import weblab.rating.dataAccess.abstracts.RatingDao;
import weblab.rating.entities.Rating;

import java.util.List;

@Service
public class RatingManager implements RatingService {

    private RatingDao ratingDao;

    @Autowired
    public RatingManager(RatingDao ratingDao) {
        this.ratingDao = ratingDao;
    }

    @Override
    public Result addRating(Rating rating) {
        if (rating.getWebsite().isEmpty()) {
            return new ErrorResult(Messages.websiteCannotBeNull);
        }
        if (rating.getRating() < 0 || rating.getRating() > 5) {
            return new ErrorResult(Messages.ratingValueNotValid);
        }

        ratingDao.save(rating);

        return new SuccessResult(Messages.ratingAddSuccess);
    }

    @Override
    public DataResult<List<Rating>> getRatingsByWebsite(String website) {
        var result = ratingDao.findAllByWebsite(website);
        if (result == null) {
            return new SuccessDataResult<List<Rating>>(Messages.ratingDoesntExist);
        }

        return new SuccessDataResult<List<Rating>>(result, Messages.getRatingsByWebsiteSuccess);
    }

    @Override
    public DataResult<Rating> getRatingById(int id) {
        var result = ratingDao.getById(id);
        if (result == null) {
            return new SuccessDataResult<Rating>(Messages.ratingDoesntExist);
        }

        return new SuccessDataResult<>(result, Messages.getRatingByIdSuccess);
    }

    @Override
    public DataResult<Rating> getCalculatedRating(String website) {
        var ratings = ratingDao.findAllByWebsite(website);
        if (ratings == null || ratings.isEmpty()) {
            return new SuccessDataResult<Rating>(Messages.ratingDoesntExist);
        }

        double totalRating = ratings.stream().mapToDouble(Rating::getRating).sum();

        double averageRating = (double) totalRating / ratings.size();

        averageRating = Math.round(averageRating * 100.0) / 100.0;

        Rating calculatedRating = new Rating();
        calculatedRating.setWebsite(website);
        calculatedRating.setRating(averageRating);

        return new SuccessDataResult<Rating>(calculatedRating);
    }


}
