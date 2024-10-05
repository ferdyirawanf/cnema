package com.alpine.cnema.service.impl;

import com.alpine.cnema.model.*;
import com.alpine.cnema.repository.RecomendationRepository;
import com.alpine.cnema.service.GenreService;
import com.alpine.cnema.service.MovieService;
import com.alpine.cnema.service.RecomendationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class RecomendationServiceImpl implements RecomendationService {
    private final RecomendationRepository recomendationRepository;
    private final MovieService movieService;
    private final GenreService genreService;

    @Override
    public List<Movie> getAll(User user) {
        List<Transaction> transactions = recomendationRepository.findByUser(user);

        if (transactions.isEmpty()) { return Collections.emptyList(); }

        List<TransactionMovie> movieTransactions = new ArrayList<>();
        for (Transaction transaction : transactions) {
            if(!transaction.getMovieTransaction().isEmpty()) {
                movieTransactions.add(transaction.getMovieTransaction().get(0));
            }
        }

        List<Genre> genres = new ArrayList<>();
        for (TransactionMovie movieTransaction : movieTransactions) {
            genres.add(movieTransaction.getSeat().getSection().getMovie().getGenre());
        }

        Map<String, Integer> mapValue = new HashMap<String, Integer>();
        for (Genre g : genres) {
            mapValue.compute(g.getName(), (k, count) -> (count == null) ? 1 : count + 1);
        }

        Integer maxValue=0;
        String maxKey="";
        for (Map.Entry<String, Integer> entry: mapValue.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            if(value>maxValue){
                maxKey = key;
                maxValue=value;
            }
        }

        return movieService.getByGenre(genreService.getByNameContaining(maxKey).get(0).getId());
    }
}
