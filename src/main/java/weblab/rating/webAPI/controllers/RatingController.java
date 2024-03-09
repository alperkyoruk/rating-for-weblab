package weblab.rating.webAPI.controllers;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import weblab.rating.business.abstracts.RatingService;
import weblab.rating.core.utilities.Result;
import weblab.rating.entities.Rating;

@RestController
@RequestMapping("api/ratings")
public class RatingController {
    private RatingService ratingService;

    @Autowired
    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @PostMapping("/setCookie")
    public String setCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie("isRated", "true");
        response.addCookie(cookie);
        return "Cookie is set";
    }

    @GetMapping("/getCookie")
    public String getCookie(@CookieValue(value = "isRated", defaultValue = "false") String isRated) {
        return isRated;
    }

    @DeleteMapping("/deleteCookie")
    public void deleteCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie("isRated", "");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

    @PostMapping("/add")
    public Result addRating(@RequestBody Rating rating, HttpServletResponse response, @CookieValue(value = "isRated", defaultValue = "false") String isRated) {
        if(isRated.equals("true")){
            return new Result(false, "You have already rated this website");
        }
        setCookie(response);
        return ratingService.addRating(rating);
    }

    @GetMapping("/getRatingsByWebsite")
    public Result getRatingsByWebsite(@RequestParam String website) {
        return ratingService.getRatingsByWebsite(website);
    }

    @GetMapping("/getRatingById")
    public Result getRatingById(@RequestParam int id) {
        return ratingService.getRatingById(id);
    }

    @GetMapping("/getCalculatedRating")
    public Result getCalculatedRating(@RequestParam String website) {
        return ratingService.getCalculatedRating(website);
    }
}