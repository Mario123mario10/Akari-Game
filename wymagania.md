# Aplikacja do gry w Akari

## Opis

Aplikacja umożliwia grę w Akari (Light Up).

Gra jest przeznaczona dla jednej osoby i rozgrywa się na prostokątnej planszy
złożonych z białych i czarnych pól. Gracz umieszcza żarówki na białych polach,
które oświetlają planszę pionowo i poziomo, dopóki światło nie natrafi na
czarne pole. Na niektórych czarnych polach znajduje się numer od 0 do 4
określający ile żarówek musi być położonych obok nich (tylko bezpośrednio obok,
nie po skosie). Przy czarnych polach bez liczby może być dowolnie wiele
żarówek. Żarówki nie mogą świecić na siebie. Gra polega na wypełnieniu planszy
żarówkami, tak aby wszystkie białe pola zostały oświetlone, przy spełnieniu
tych warunków.

Aplikacja będzie umożliwiała tworzenie turniejów. Uczestnicy rozwiązują tę samą
planszę i dalej przechodzi ten, który rozwiąże ją szybciej. Z czasem zwiększany
jest poziom trudności plansz, na przykład poprzez zmianę rozmiaru planszy,
stopnia wypełnienia czarnym polami, większymi ograniczeniami na liczbę
dozwolonych żarówek obok czarnych pól.

## Przypadki użycia

- tworzenie planszy z możliwością zapisania jej do pliku
- wczytywanie planszy z pliku
- generowanie losowych plansz z możliwością ustawienia jej rozmiaru i poziomu trudności
- rozwiązywanie utworzonych, wczytanych i wygenerowanych plansz z pomiarem czasu
- planowanie turnieju
    * wybór ilości graczy
    * wybór map dla każdego etapu turnieju
    * uruchomienie turnieju
