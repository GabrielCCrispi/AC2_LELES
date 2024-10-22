package br.com.valueprojects.mock_spring;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.com.valueprojects.mock_spring.model.FinalizaJogo;
import br.com.valueprojects.mock_spring.model.Jogo;
import br.com.valueprojects.mock_spring.builder.CriadorDeJogo;
import infra.JogoDao;  // Verifique se esta importação é válida e se a classe existe

public class SmsService5{
@Test
public void naoDeveEnviarSmsParaJogosSemVencedor() {
    // Cria um jogo sem vencedor
    Calendar antiga = Calendar.getInstance();
    antiga.set(1999, 1, 20);

    Jogo jogoSemVencedor = new CriadorDeJogo().para("Caça moedas").naData(antiga).constroi(); // Sem vencedor

    List<Jogo> jogosAnteriores = Arrays.asList(jogoSemVencedor);
    
    // Mock do DAO e do serviço de SMS
    JogoDao daoFalso = mock(JogoDao.class);
    SmsService smsServiceFalso = mock(SmsService.class);

    // Simula o retorno dos jogos em andamento
    when(daoFalso.emAndamento()).thenReturn(jogosAnteriores);

    // Instancia o serviço que finaliza os jogos
    FinalizaJogo finalizador = new FinalizaJogo(daoFalso);
    finalizador.finaliza();

    // Verifica que o jogo foi finalizado, mas nenhum SMS foi enviado
    verify(daoFalso, times(1)).atualiza(jogoSemVencedor);
    verifyNoInteractions(smsServiceFalso); // Nenhum SMS enviado
}}
