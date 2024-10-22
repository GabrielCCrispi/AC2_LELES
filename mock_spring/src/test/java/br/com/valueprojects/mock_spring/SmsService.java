package br.com.valueprojects.mock_spring;

public class SmsService {

    public void enviarSms(String numeroTelefone, String mensagem) {
        // Lógica para enviar SMS
        // Isso pode envolver chamar uma API externa, por exemplo, Twilio ou outro serviço de SMS.

        System.out.println("Enviando SMS para: " + numeroTelefone);
        System.out.println("Mensagem: " + mensagem);
        
        // Aqui você implementaria a chamada para a API de SMS
    }

    public void enviar(String nomeVencedor) {
        // Aqui você implementaria a lógica para enviar o SMS
        // Por exemplo, chamar uma API externa de SMS

        System.out.println("Enviando SMS para: " + nomeVencedor);
        System.out.println("Mensagem: Parabéns! Você venceu o jogo!");
}
}