import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;


public class Bot extends TelegramLongPollingBot {
    final private String BOT_NAME = "@smartcitatybot";
    Storage storage;

    Bot() {
        storage = new Storage();
    }

    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    @Override
    public String getBotToken() {
        //создаем две константы, присваиваем им значения токена и имя бота соответсвтенно
        String BOT_TOKEN = "6264974943:AAH4Oi6CJ23RPKT6GpWk5iHCgCFXmbhBt38";
        return BOT_TOKEN;
    }
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasCallbackQuery()) {
            // Обработка нажатий на кнопки
            CallbackQuery callbackQuery = update.getCallbackQuery();
            String callbackData = callbackQuery.getData();
            long chatId = callbackQuery.getMessage().getChatId();
            try {
                String response = parseMessage(callbackData);
                SendMessage message = new SendMessage();
                message.setChatId(String.valueOf(chatId));
                message.setText(response);
                execute(message);
                SendMessage buttonMessage = new SendMessage();
                buttonMessage.setChatId(String.valueOf(chatId));
                buttonMessage.setReplyMarkup(createInlineKeyboardMarkup());
                buttonMessage.setText("\uD83D\uDCAB");
                execute(buttonMessage);

            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        } else if (update.hasMessage() && update.getMessage().hasText()) {
            // Обработка сообщений пользователя
            String chatId = update.getMessage().getChatId().toString();
            String response = parseMessage(update.getMessage().getText());
            try {
                SendMessage message = new SendMessage();
                message.setChatId(chatId);
                message.setText(response);
                message.setReplyMarkup(createInlineKeyboardMarkup());
                execute(message);

            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    private String parseMessage(String textMsg) {
        String response;
        if (textMsg.equals("/start"))
            response = "Приветствую, бот знает много цитат. Чтобы получить одну случайную из них жми \uD83D\uDC47";
        else if (textMsg.equals("/enlighten"))
            response = storage.getRandQuote();
        else if (textMsg.equals("Просвяти"))
            response = storage.getRandQuote();

        else
            response = "Сообщение не распознано";

        return response;
    }

    private InlineKeyboardMarkup createInlineKeyboardMarkup() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton button1 = new InlineKeyboardButton();
        button1.setText("Просвяти");
        button1.setCallbackData("/enlighten");

        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();

        keyboardButtonsRow1.add(button1);


        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow1);

        inlineKeyboardMarkup.setKeyboard(rowList);
        return inlineKeyboardMarkup;
    }
}



    /*@Override
    public void onUpdateReceived(Update update) {
        if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            // Получение данных обратного вызова (callback data)
            String callbackData = callbackQuery.getData();
            // Получение идентификатора чата
            long chatId = callbackQuery.getMessage().getChatId();

            // Ваша обработка обратного вызова на основе callbackData
            // Например, отправка ответа в чат
            try {
                execute(new SendMessage().setChatId(chatId).setText("Callback Data: " + callbackData));


        try {
            if (update.hasMessage() && update.getMessage().hasText()) {
                //Извлекаем из объекта сообщение пользователя
                Message inMess = update.getMessage();
                //Достаем из inMess id чата пользователя
                String chatId = inMess.getChatId().toString();
                //Получаем текст сообщения пользователя, отправляем в написанный нами обработчик
                String response = parseMessage(inMess.getText());




                // Создаем клавиатуру
                /*ReplyKeyboardMarkup replyKeyboardMarkup = new
                        ReplyKeyboardMarkup();
                replyKeyboardMarkup.setSelective(true);
                replyKeyboardMarkup.setResizeKeyboard(true);
                replyKeyboardMarkup.setOneTimeKeyboard(false);

                // Создаем список строк клавиатуры
                List<KeyboardRow> keyboard = new ArrayList<>();

                // Первая строчка клавиатуры
                KeyboardRow keyboardFirstRow = new KeyboardRow();
                // Добавляем кнопки в первую строчку клавиатуры
                keyboardFirstRow.add("▶Старт");
                keyboardFirstRow.add("\uD83D\uDD04Просвяти");

                // Вторая строчка клавиатуры
                KeyboardRow keyboardSecondRow = new KeyboardRow();
                // Добавляем кнопки во вторую строчку клавиатуры
                keyboardSecondRow.add("citatnica.ru");
                keyboardSecondRow.add("Инфо");

                // Добавляем все строчки клавиатуры в список
                keyboard.add(keyboardFirstRow);
                keyboard.add(keyboardSecondRow);
                // и устанавливаем этот список нашей клавиатуре
                replyKeyboardMarkup.setKeyboard(keyboard);*/

               /* InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
                InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
                inlineKeyboardButton.setText("Просвяти");
                inlineKeyboardButton.setCallbackData("Button Просвяти has been pressed");
                List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();
                keyboardButtonsRow.add(inlineKeyboardButton);
                //keyboardButtonsRow1.add(new InlineKeyboardButton().setText("Fi4a").CallbackData("CallFi4a"));
                List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
                rowList.add(keyboardButtonsRow);
                inlineKeyboardMarkup.setKeyboard(rowList);
                //Создаем объект класса SendMessage - наш будущий ответ пользователю
                SendMessage outMess = new SendMessage();




                //Добавляем в наше сообщение id чата а также наш ответ
                outMess.setChatId(chatId);
                outMess.setText(response);
                outMess.setReplyMarkup(inlineKeyboardMarkup);
                //outMess.setReplyMarkup(replyKeyboardMarkup);

                //Отправка в чат
                execute(outMess);

            }
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }

    public String parseMessage(String textMsg) {
        String response;

        //Сравниваем текст пользователя с нашими командами, на основе этого формируем ответ
        if (textMsg.equals("/start"))
            response = "Приветствую, бот знает много цитат. Жми Просвяти, чтобы получить случайную из них";
        else if (textMsg.equals("/enlighten"))
            response = storage.getRandQuote();
        else if(textMsg.equals("/start") || textMsg.equals("▶Старт"))
            response = "Приветствую, бот знает много цитат. Жми \uD83D\uDC49 Просвяти, чтобы получить случайную из них";
        else if(textMsg.equals("/enlighten") || textMsg.equals("\uD83D\uDD04Просвяти"))
            response = storage.getRandQuote();
        else if (textMsg.equals("citatnica.ru"))
            response = "https://citatnica.ru/";
        else
            response = "Сообщение не распознано";

        return response;
    }

    }*/


