# OwnScrobbler
Попытка устранения фатального недостатка скробблера Last.fm как итоговый проект Android Study Jams.
## Суть
Я организую свою музыкальную коллекцию с помощью [beets](https://github.com/beetbox/beets) и mpd. 
Для beets я использую [плагин](https://beets.readthedocs.org/en/v1.3.17/plugins/mpdstats.html), который ведет простой рейтинг для композиций.
На его основе в beets можно генерить динамические плейлисты, типа любимые-нелюбимые треки и комбинировать это все с другими критериями. 
Проблема в том, что львиную долю музыки я слушаю на Android, интернет есть не всегда, поэтому вариант с помещением mpd-сервера онлайн не годится. 
Но вести логи и обновлять рейтинги я хочу. Собс-на, когда я записывался на Study Jam, по его итогам я и хотел сделать себе личный скробблер.
## Что и как сделано
В итоге получилось приложение из одной activity c кнопкой включения-отключения лога, списком последних 5 композиций.
IntentReceiver ловит интенты от плеера (однако, не все плееры их отправляют, у меня PowerAMP), 
и по ним считает статус трека (Проигран, Пропущен или на Паузе) и отправляет на сервер (Firebase).
## Что не сделано
Хотел еще вторую activity сделать с полным логом и поддержкой редактирования, но по ряду причин не успел. 
В целом для использования мною по назначению годится, остается написать импортер логов в beets по примеру плагина. 