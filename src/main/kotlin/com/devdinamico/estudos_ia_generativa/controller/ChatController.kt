package com.devdinamico.estudos_ia_generativa.controller

import org.springframework.ai.chat.prompt.PromptTemplate
import org.springframework.ai.openai.OpenAiChatClient
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/chat")
class ChatController(private val chatClient: OpenAiChatClient) {

    @GetMapping("/cidade")
    fun perguntarCidade(@RequestParam(name = "name") name: String): ResponseEntity<String> {
        val promptTemplate = PromptTemplate(
            "Quando alguém te mandar o nome de um cidade você vai tentar identificar e retornará " +
                    "o que mais se assemelha com o que a pessoa digitou ou retorne uma lista" +
                    "com as possíveis opções que são parecidas com o que ele digitou e caso não consiga retorne uma " +
                    "mensagem pedindo para digitar novamente" +
                    " : {cidade}"
        )
        promptTemplate.add("cidade", name)
        chatClient.call(promptTemplate.create()).let {
            return ResponseEntity.ok(it.result.output.content)
        }
    }

    @GetMapping("/guia-turistico")
    fun perguntarGuiaTuristico(@RequestParam(name = "pergunta") pergunta: String): ResponseEntity<String> {
        val promptTemplate = PromptTemplate(
            "Você é um guia turístico e alguém te fez uma pergunta sobre um lugar turístico, você deve responder " +
                    "a pergunta da melhor forma possível, caso não saiba a resposta retorne uma mensagem pedindo para " +
                    "a pessoa perguntar novamente. Pergunta: {pergunta}"
        )
        promptTemplate.add("pergunta", pergunta)
        chatClient.call(promptTemplate.create()).let {
            return ResponseEntity.ok(it.result.output.content)
        }
    }


}