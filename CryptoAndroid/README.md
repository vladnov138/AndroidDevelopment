# Получение котировок криптовалют с помощью сервисов

Нужно использовать API CoinMarketCap (по-видимому стала платной)  https://coinmarketcap.com/api/documentation/v1/#section/Quick-Start-Guide

Более удачный вариант  https://min-api.cryptocompare.com/documentation

Реализуйте приложение, следящее за котировкой вашей любимой криптовалютой.

Приложение-заготовка  https://github.com/emityakov/GetUSDServiceTemplate

Пример запроса к бирже (см. ключ в заголовке)

curl -H "X-CMC_PRO_API_KEY: b54bcf4d-1bca-4e8e-9a24-22ff2c3d462c" -H "Accept: application/json" -d "start=1&limit=5000&convert=USD" -G https://sandbox-api.coinmarketcap.com/v1/cryptocurrency/listings/latest
Вариант усложнённый: сохранять историю котировок в списке (дату, время, котировку).

Пример ответа API биржи в виде файла прилагается к заданию.

## Демонстрация
[Screen_recording_20240510_215020.webm](https://github.com/vladnov138/AndroidDevelopment/assets/113700660/e2623d2b-02a4-4c86-8687-cc3c86610324)
