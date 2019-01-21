package support;

import java.util.List;

import pojo.Genre;
import pojo.MovieDetailsPOJO;
import pojo.ProductionCompany;
import pojo.ProductionCountry;
import pojo.SpokenLanguage;

public class ComposeDetails {

    public StringBuilder getComposedDetails(MovieDetailsPOJO movieDetailsPOJO) {
         List<Genre> genres = movieDetailsPOJO.getGenres();
         List<SpokenLanguage> spokenLanguages = movieDetailsPOJO.getSpokenLanguages();
         List<ProductionCompany> productionCompanies = movieDetailsPOJO.getProductionCompanies();
         List<ProductionCountry> productionCountries = movieDetailsPOJO.getProductionCountries();
         StringBuilder stringBuilder = new StringBuilder();

        //kompozice detailu o filmu pro texView
        try {
            stringBuilder.append("Original title: " + movieDetailsPOJO.getOriginalTitle());
            stringBuilder.append("\n");
            stringBuilder.append("Title: " + movieDetailsPOJO.getTitle());
            stringBuilder.append("\n");

            for (Genre g : genres) {
                stringBuilder.append("Genre " + g.getName()+"\n");
            }
            stringBuilder.append("\n");
            stringBuilder.append("Relase date: " + movieDetailsPOJO.getReleaseDate());
            stringBuilder.append("\n");
            stringBuilder.append("Language: " + movieDetailsPOJO.getOriginalLanguage());
            stringBuilder.append("\n");
            stringBuilder.append("Budget: " + movieDetailsPOJO.getBudget().toString() + " USD");
            stringBuilder.append("\n");
            stringBuilder.append("Revenue: " + movieDetailsPOJO.getRevenue().toString() + " USD");
            stringBuilder.append("\n");
            stringBuilder.append("Length: " + movieDetailsPOJO.getRuntime().toString() + " minutes");
            stringBuilder.append("\n");
            stringBuilder.append("Available on video: " + movieDetailsPOJO.getVideo().toString());
            stringBuilder.append("\n");
            stringBuilder.append("Average vote: " + movieDetailsPOJO.getVoteAverage().toString());
            stringBuilder.append("\n");
            stringBuilder.append("Vote count: " + movieDetailsPOJO.getVoteCount().toString());
            stringBuilder.append("\n");

            for (SpokenLanguage s : spokenLanguages) {
                stringBuilder.append("Language ISO: " + s.getIso6391() + "\n Language: " + s.getName());
                stringBuilder.append("\n");
            }

            for (ProductionCompany p : productionCompanies) {
                stringBuilder.append("Production company: " + p.getName() + "\n Production company origin country: " + p.getOriginCountry());
                stringBuilder.append("\n");
            }

            for (ProductionCountry c : productionCountries) {
                stringBuilder.append("Country ISO: " + c.getIso31661() + "\n Country : " + c.getName());
                stringBuilder.append("\n");
            }
            stringBuilder.append("\n");


        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringBuilder;
    }
}
