package by.itechart.film2night.service.impl;

import by.itechart.film2night.dao.impl.FilmDaoImpl;
import by.itechart.film2night.entity.Film;
import by.itechart.film2night.entity.FilmTop250;
import by.itechart.film2night.service.KinopoiskService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.*;


public class KinopoiskServiceImpl implements KinopoiskService {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final String APP_API_KEY = "app.apiKey";
    private final Properties urlProperties = new Properties();
    private HttpURLConnection connection;

    private static KinopoiskServiceImpl instance;

    private KinopoiskServiceImpl() {
    }

    public static KinopoiskServiceImpl getInstance() {
        if (instance == null) {
            synchronized (FilmDaoImpl.class) {
                if (instance == null) {
                    instance = new KinopoiskServiceImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public HttpURLConnection connectToKinopoiskApi(String path, HttpServletResponse res) {
        try {
            urlProperties.load(getClass().getClassLoader().getResourceAsStream("url.properties"));
            String key = urlProperties.getProperty(APP_API_KEY);
            LOGGER.info(path);
            URL url = new URL(path);
            connection = (HttpURLConnection) url.openConnection();
            connection.addRequestProperty("x-api-key", key);
            connection.setRequestMethod("GET");
            connection.addRequestProperty("Content-Type", "application/json");
            res.setContentType("application/json");
            res.setCharacterEncoding("UTF-8");
            int status = connection.getResponseCode();

            if (status < 299) {
                LOGGER.info("Connect successfull");
                return connection;
            } else {
                LOGGER.info("Connect is not successful");
            }
        } catch (IOException e) {
            LOGGER.info("Failed to connect to kinopoisk api");
        }
        return null;
    }

    public HttpURLConnection connectToKinopoiskApi(String path) {
        try {
            urlProperties.load(getClass().getClassLoader().getResourceAsStream("url.properties"));
            String key = urlProperties.getProperty(APP_API_KEY);
            LOGGER.info(path);
            URL url = new URL(path);
            connection = (HttpURLConnection) url.openConnection();
            connection.addRequestProperty("x-api-key", key);
            connection.setRequestMethod("GET");
            connection.addRequestProperty("Content-Type", "application/json");


            int status = connection.getResponseCode();

            if (status < 299) {
                LOGGER.info("Connect successfull");
                return connection;
            } else {
                LOGGER.info("Connect is not successful");
            }

        } catch (IOException e) {
            LOGGER.info("Failed to connect to kinopoisk api");
        }
        return null;
    }

    @Override
    public StringBuffer readResponse(HttpURLConnection connection) {
        StringBuffer responseContent = new StringBuffer();
        BufferedReader reader;
        String line;
        try {
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
            while ((line = reader.readLine()) != null) {
                responseContent.append(line);
                LOGGER.info(responseContent);
            }
        } catch (IOException e) {
            LOGGER.error("Failed to read response");
        }
        return responseContent;
    }


    @Override
    public void writeResponse(StringBuffer responseContent, HttpServletResponse res) {
        PrintWriter printWriter;
        try {
            printWriter = res.getWriter();
            printWriter.print(responseContent);
        } catch (IOException e) {
            LOGGER.error("Failed to write response");
        }
        LOGGER.info(responseContent);
    }

    @Override
    public List<FilmTop250> parseJsonTop250Films(String responseBody) {

        List<FilmTop250> filmTop250List = new ArrayList<>();
        LOGGER.info("parseJsonTop250Films");
        LOGGER.info("in parse");
        JSONObject jsonObject1 = new JSONObject(responseBody);

        JSONArray rows = jsonObject1.getJSONArray("films");


        for (int i = 0; i < rows.length(); i++) {
            JSONObject jsonObject = rows.getJSONObject(i);
            LOGGER.info(jsonObject);
            int id = jsonObject.getInt("filmId");
            LOGGER.info(id);
            String name = jsonObject.getString("nameRu");
            LOGGER.info(name);
            FilmTop250 filmTop250 = new FilmTop250.filmTop250Builder()
                    .setId(id)
                    .setName(name)
                    .build();
            filmTop250List.add(filmTop250);
        }
        return filmTop250List;
    }

    @Override
    public Film parseJsonFilm(String responseBody) {

        LOGGER.info("in parse");
        JSONObject jsonObject = new JSONObject(responseBody);
        Set<String> setOfCountries = new HashSet<>();
        Set<String> setOfGenres = new HashSet<>();
        JSONArray country = jsonObject.getJSONArray("countries");
        JSONArray genres = jsonObject.getJSONArray("genres");
        List<Object> listOfCountries = country.toList();
        List<Object> listOfGenres = genres.toList();

        for (Object o : listOfCountries) {
            String s = o.toString().replaceAll("country=", "");
            s = s.substring(1);
            s = s.replaceAll("}", "");
            setOfCountries.add(s);
        }

        for (Object o : listOfGenres) {
            String s = o.toString().replaceAll("genre=", "");
            s = s.substring(1);
            s = s.replaceAll("}", "");
            setOfGenres.add(s);
        }

        LOGGER.warn(setOfCountries);
        LOGGER.warn(setOfGenres);

        int id = jsonObject.getInt("kinopoiskId");
        LOGGER.info(id);
        String name = jsonObject.optString("nameOriginal");
        LOGGER.info(name);
        String posterUrl = jsonObject.getString("posterUrl");
        LOGGER.info(posterUrl);
        float ratingKinopoisk = jsonObject.getFloat("ratingKinopoisk");
        LOGGER.info(ratingKinopoisk);
        int vote = jsonObject.getInt("ratingKinopoiskVoteCount");
        LOGGER.info(vote);
        String webUrl = jsonObject.getString("webUrl");
        LOGGER.info(webUrl);
        int year = jsonObject.getInt("year");
        LOGGER.info(year);
        int filmLength = jsonObject.getInt("filmLength");
        LOGGER.info(filmLength);
        String lastSync = jsonObject.getString("lastSync");
        LOGGER.info(lastSync);
        lastSync = lastSync.replace("T", " ");
        LOGGER.info(lastSync);
        Timestamp timestamp = Timestamp.valueOf(lastSync);

        return new Film.filmBuilder()
                .setKinopoiskId(id)
                .setNameOriginal(name)
                .setPosterUrl(posterUrl)
                .setRatingKinopoisk(ratingKinopoisk)
                .setRatingKinopoiskVoteCount(vote)
                .setWebUrl(webUrl)
                .setYear(year)
                .setFilmLength(filmLength)
                .setCountries(setOfCountries)
                .setGenres(setOfGenres)
                .setLastSync(timestamp)
                .setIsBlocked("false")
                .build();
    }

    @Override
    public void downloadPoster(String path) throws IOException {

        String filename = parseUrl(path);
        LOGGER.info("download poster" + filename);
        LOGGER.info("download poster" + path);
        try {
            urlProperties.load(getClass().getClassLoader().getResourceAsStream("url.properties"));
            String key = urlProperties.getProperty(APP_API_KEY);
            LOGGER.info(path);
            URL url = new URL(path);
            connection = (HttpURLConnection) url.openConnection();
            connection.addRequestProperty("x-api-key", key);
            connection.setRequestMethod("GET");
            connection.addRequestProperty("Content-Type", "application/json");

            int status = connection.getResponseCode();

            if (status < 299) {
                LOGGER.info("Connect successfull");
            } else {
                LOGGER.info("Connect is not successful");
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        connection.setConnectTimeout(5 * 1000);
        InputStream is = connection.getInputStream();
        byte[] bs = new byte[1024];
        int len;

        //File sf = new File("/src/main/resources/posters");
        File sf = new File("f:\\film2night\\src\\main\\resources\\posters\\");
        if (!sf.exists()) {
            sf.mkdirs();
        }

        OutputStream os = new FileOutputStream(sf.getPath() + "/" + filename);

        while ((len = is.read(bs)) != -1) {
            os.write(bs, 0, len);
        }

        os.close();
        is.close();
    }

    @Override
    public String parseUrl(String path) {
        StringBuilder filename = new StringBuilder();
        LOGGER.info(path);

        for (int i = path.length() - 1; i > 0; i--) {

            if (path.charAt(i) != '/') {
                filename.append(path.charAt(i));
            } else {
                break;
            }
        }
        return filename.reverse().toString();
    }
}

