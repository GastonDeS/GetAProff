package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.services.UtilsService;
import org.springframework.stereotype.Service;

import java.util.StringTokenizer;

@Service
public class UtilsServiceImpl implements UtilsService {

    @Override
    public String capitalizeString(String str) {
        StringBuilder stringBuilder = new StringBuilder();
        StringTokenizer tokenizer = new StringTokenizer(str, " ");
        while (tokenizer.hasMoreElements()) {
            stringBuilder.append(capitalizeFirstLetter(tokenizer.nextToken())).append(" ");
        }
        return stringBuilder.substring(0, stringBuilder.length() - 1);
    }

    private String capitalizeFirstLetter(String word) {
        return word.substring(0, 1).toUpperCase() + word.substring(1);
    }

}
