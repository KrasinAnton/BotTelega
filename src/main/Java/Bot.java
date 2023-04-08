import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;



public class Bot extends TelegramLongPollingBot {
        //создаем две константы, присваиваем им значения токена и имя бота соответсвтенно
        //вместо звездочек подставляйте свои данные
        final private String BOT_TOKEN = "5761711012:AAEAkUKwI8970PyLeGRRKLxikFRN45bYRtQ";
        final private String BOT_NAME = "@appeal_30day_bot";
        Storage storage;

        Bot()
        {
            storage = new Storage();
        }

        @Override
        public String getBotUsername() {
            return BOT_NAME;
        }

        @Override
        public String getBotToken() {
            return BOT_TOKEN;
        }

        @Override
        public void onUpdateReceived(Update update) {
            try{
                if(update.hasMessage() && update.getMessage().hasText())
                {
                    //Извлекаем из объекта сообщение пользователя
                    Message inMess = update.getMessage();
                    //Достаем из inMess id чата пользователя
                    String chatId = inMess.getChatId().toString();
                    //Получаем текст сообщения пользователя, отправляем в написанный нами обработчик
                    String response = parseMessage(inMess.getText());
                    //Создаем объект класса SendMessage - наш будущий ответ пользователю
                    SendMessage outMess = new SendMessage();

                    //Добавляем в наше сообщение id чата а также наш ответ
                    outMess.setChatId(chatId);
                    outMess.setText(response);

                    /*// Создание клавиатуры
                    ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
                    keyboardMarkup.setResizeKeyboard(true); // автоматически изменять размер клавиатуры

                    // Создание ряда кнопок клавиатуры
                    KeyboardRow row = new KeyboardRow();
                    row.add(new KeyboardButton("Кнопка 1"));
                    row.add(new KeyboardButton("Кнопка 2"));

                    // Добавление ряда кнопок к клавиатуре
                    keyboardMarkup.getKeyboard().add(row);

                    // Установка клавиатуры в сообщении
                    outMess.setReplyMarkup(keyboardMarkup);*/

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
            if(textMsg.equals("/start"))
                response = "Приветствую, бот знает много цитат. Жми /get, чтобы получить случайную из них";
            else if(textMsg.equals("/get"))
                response = storage.getRandQuote();
            else
                response = "Сообщение не распознано";

            return response;
        }

    }
