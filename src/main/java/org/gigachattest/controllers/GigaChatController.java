package org.gigachattest.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.gigachattest.services.gigachat.GigaChatServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class GigaChatController {
    private final GigaChatServiceImpl gigaChatService;

    @GetMapping("/hello")
    public ResponseEntity<String> hello(){
        String hello = "я тебя победю, Гигачат!";

        return ResponseEntity.ok(hello);
    }

    @GetMapping("/summary")
    public ResponseEntity<String> generate() {
        String summary = gigaChatService.getAnswer("Специалистам iFixit удалось выяснить, что разрешение каждого экрана Vision Pro составляет 3660 × 3200 пикселей, когда 4K вашего телевизора — 3840 × 2160. В целом, разница невелика, а с учётом того, что дисплеи там вообще малюсенькие, потеря не выглядит большой. Но всё равно неприятно: де-юре, это не 4К-дисплеи, хоть разрешение и больше, чем у большинства телевизоров. Также стоит учитывать, что со всех углов дисплеи обрезаны: просто нет смысла делать их прямоугольными. Поэтому итоговое разрешение составляет 11 437 866 пикселей на глаз. Это очень близко к обещанному Apple разрешению в 23 млн пикселей. Из разборки iFixit мы выяснили ещё несколько фактов: площадь дисплея составляет 6,3 см², а плотность пикселей каждого из них — 3386 ppi. Для примера: в один пиксель вашего айфона уместится 50 пикселей Apple Vision Pro. Также стало известно, что у Vision Pro примерно 34 пикселя на градус. И ещё интересное из разбора: аккумулятор в Apple Vision Pro на 20 % больше, чем заявлено. И в одном блоке содержится три аккумуляторных элемента, условная ёмкость каждого из которых составляет по 3969 мА·ч. Судя по всему, он заряжается только до 80 % ради увеличения срока службы. Реальная ёмкость каждой ячейки составляет 15,36 Вт·ч, а итоговая со всеми ограничениями — 35,9 Вт·ч.");
        return ResponseEntity.ok(summary);
    }
}