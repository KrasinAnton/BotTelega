import com.cloudimpl.outstack.app.App;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Bot extends TelegramLongPollingBot {

    final private String BOT_NAME = "@smartcitatybot";
    Storage storage;

    Bot() throws IOException {
        storage = new Storage();
    }

    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return loadTokenFromProperties();
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

    private String loadTokenFromProperties() {
        Properties prop = new Properties();
        try {
            // Загружаем файл свойств из classpath
            prop.load(getClass().getClassLoader().getResourceAsStream("config.properties"));

            // Получаем значение свойства "token"
            return prop.getProperty("token");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}




