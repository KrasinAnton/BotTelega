import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class Storage {
    private ArrayList<String> quoteList;
    public Storage() {
        quoteList = new ArrayList<String>();
        parser("https://citatnica.ru/citaty/mudrye-tsitaty-velikih-lyudej");
    }
    String getRandQuote() {
        //получаем случайное значение в интервале от 0 до самого большого индекса
        int randValue = (int)(Math.random() * quoteList.size());
        //Из коллекции получаем цитату со случайным индексом и возвращаем ее
        return quoteList.get(randValue);
    }

    public void parser(String strURL) {
        String classNmae = "su-note-inner su-u-clearfix su-u-trim";
        Document doc = null;
        try {
            //Получаем документ нужной нам страницы
            doc = Jsoup.connect(strURL).maxBodySize(0).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Получаем группу объектов, обращаясь методом из Jsoup к определенному блоку
        assert doc != null;
        Elements elQuote = doc.getElementsByClass(classNmae);

        //Достаем текст из каждого объекта поочереди и добавляем в наше хранилище
        elQuote.forEach(el -> {
            quoteList.add(el.text());
        });
    }
    public ArrayList<String> getQuoteList() {
        return quoteList;
    }
}
