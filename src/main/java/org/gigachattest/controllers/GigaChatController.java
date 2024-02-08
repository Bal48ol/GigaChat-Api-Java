package org.gigachattest.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.io.IOException;

import org.gigachattest.services.GigaChatServiceImpl;
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
    public ResponseEntity<String> generate() throws IOException, InterruptedException{
        String text = "Новинка получила название Сoca-Cola Spiced, а её вкус в самой компании характеризуют как «Классическая кола с нотками малины и пряностей». Примечательно, что это не очередная лимитированная серия, а такой же полноправный член семейства Сoca-Cola, как Cherry, Vanilla и божественная Cherry Vanilla. Поэтому разрабатывался напиток с максимальной дотошностью к желаниям потребителей. Корпорация в кои-то веки почувствовала нарастающую угрозу со стороны конкурентов на рынке сладких напитков, да и мировые темпы роста продаж традиционной колы в 2023 году оказались ниже ожидаемых, так что ситуация требовала решительных действий. Сообщается, что проведено масштабное маркетинговое исследование, по результатам которого было установлено, что людям не хватает колы со вкусом специй (это у них просто «Байкала» нет). Вкус малины было решено добавить, поскольку малиновая кола стала самым популярным напитком в экспериментальных автоматах Coca-Cola Freestyle, где покупатели могут смешивать и создавать новые сочетания. К счастью для всех, Сoca-Cola Spiced будет сразу выпускаться в двух версиях: вкусная с сахаром и без него. Продажи газировки в скором времени начнутся на территории США и Канады.";
        String summary = gigaChatService.getAnswer(text);

        return ResponseEntity.ok(summary);
    }
}